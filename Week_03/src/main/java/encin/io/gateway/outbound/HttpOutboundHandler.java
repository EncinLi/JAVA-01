// Copyright (c) 1998-2021 Core Solutions Limited. All rights reserved.
// ============================================================================
// CURRENT VERSION CBX 10.x
// ============================================================================
// CHANGE LOG
// CBX 11.x : 2021-01-30, Encin.Li, creation
// ============================================================================
package encin.io.gateway.outbound;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import encin.io.gateway.filter.HeaderHttpResponseFilter;
import encin.io.gateway.filter.HttpRequestFilter;
import encin.io.gateway.filter.HttpResponseFilter;
import encin.io.gateway.router.HttpEndpointRouter;
import encin.io.gateway.router.RandomHttpEndpointRouter;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpUtil;

import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @author Encin.Li
 * @create 2021-01-30
 */
public class HttpOutboundHandler {

    private final CloseableHttpAsyncClient httpclient;
    private final ExecutorService proxyService;
    private final List<String> backendUrls;

    HttpResponseFilter filter = new HeaderHttpResponseFilter();
    HttpEndpointRouter router = new RandomHttpEndpointRouter();

    public HttpOutboundHandler(final List<String> backends) {

        backendUrls = backends.stream().map(this::formatUrl).collect(Collectors.toList());

        final int cores = Runtime.getRuntime().availableProcessors();
        final long keepAliveTime = 1000;
        final int queueSize = 2048;
        final RejectedExecutionHandler handler = new ThreadPoolExecutor.CallerRunsPolicy();//.DiscardPolicy();
        proxyService = new ThreadPoolExecutor(cores, cores,
                                              keepAliveTime, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(queueSize),
                                              new NamedThreadFactory("proxyService"), handler);

        final IOReactorConfig ioConfig = IOReactorConfig.custom()
                                                        .setConnectTimeout(1000)
                                                        .setSoTimeout(1000)
                                                        .setIoThreadCount(cores)
                                                        .setRcvBufSize(32 * 1024)
                                                        .build();

        httpclient = HttpAsyncClients.custom().setMaxConnTotal(40)
                                     .setMaxConnPerRoute(8)
                                     .setDefaultIOReactorConfig(ioConfig)
                                     .setKeepAliveStrategy((response, context) -> 6000)
                                     .build();
        httpclient.start();
    }

    private String formatUrl(final String url) {
        return url.endsWith("/") ? url.substring(0, url.length() - 1) : url;
    }

    public void handle(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx, final HttpRequestFilter filter) {
        final String backendUrl = router.route(backendUrls);
        final String url = backendUrl + fullRequest.uri();
        filter.filter(fullRequest, ctx);
        proxyService.submit(() -> fetchGet(fullRequest, ctx, url));
    }

    private void fetchGet(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx, final String url) {
        final HttpGet httpGet = new HttpGet(url);
        //httpGet.setHeader(HTTP.CONN_DIRECTIVE, HTTP.CONN_CLOSE);
        httpGet.setHeader(HTTP.CONN_DIRECTIVE, HTTP.CONN_KEEP_ALIVE);
        httpGet.setHeader("mao", fullRequest.headers().get("mao"));

        httpclient.execute(httpGet, new FutureCallback<HttpResponse>() {
            @Override
            public void completed(final HttpResponse endpointResponse) {
                try {
                    handleResponse(fullRequest, ctx, endpointResponse);
                } catch (final Exception e) {
                    e.printStackTrace();
                } finally {

                }
            }

            @Override
            public void failed(final Exception ex) {
                httpGet.abort();
                ex.printStackTrace();
            }

            @Override
            public void cancelled() {
                httpGet.abort();
            }
        });
    }

    private void handleResponse(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx, final HttpResponse endpointResponse) {
        FullHttpResponse response = null;

        try {
            final String value = null;

            final byte[] body = EntityUtils.toByteArray(endpointResponse.getEntity());

            response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(body));

            response.headers().set("Content-Type", "application/json");
            response.headers().setInt("Content-Length", Integer.parseInt(endpointResponse.getFirstHeader("Content-Length").getValue()));

            filter.filter(response);

        } catch (final Exception e) {
            System.out.println("error message:" + e.getMessage());
            response = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
            exceptionCaught(ctx, e);
        } finally {
            if (fullRequest != null) {
                if (!HttpUtil.isKeepAlive(fullRequest)) {
                    ctx.write(response).addListener(ChannelFutureListener.CLOSE);
                } else {
                    ctx.write(response);
                }
            }
            ctx.flush();
        }
    }

    public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
