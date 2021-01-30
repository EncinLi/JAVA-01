package encin.io.gateway.outbound;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.jetbrains.annotations.NotNull;

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
import okhttp3.Dispatcher;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @author Encin.Li
 * @create 2021-01-30
 */
public class OkHttpOutboundHandler {

    private final List<String> backendUrls;
    private final ExecutorService proxyService;
    HttpResponseFilter filter = new HeaderHttpResponseFilter();
    HttpEndpointRouter router = new RandomHttpEndpointRouter();
    private OkHttpClient client = null;

    public OkHttpClient getClient() {
        return client;
    }

    public OkHttpOutboundHandler(final List<String> backends) {
        backendUrls = backends.stream().map(this::formatUrl).collect(Collectors.toList());

        final int cores = Runtime.getRuntime().availableProcessors();
        final long keepAliveTime = 1000;
        final int queueSize = 2048;
        final RejectedExecutionHandler handler = new ThreadPoolExecutor.CallerRunsPolicy();//.DiscardPolicy();
        proxyService = new ThreadPoolExecutor(cores, cores,
                                              keepAliveTime, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(queueSize),
                                              new NamedThreadFactory("proxyService"), handler);
        client = initClient();
    }

    private String formatUrl(final String url) {
        return url.endsWith("/") ? url.substring(0, url.length() - 1) : url;
    }

    private OkHttpClient initClient() {
        final Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequests(40);
        dispatcher.setMaxRequestsPerHost(8);
        return new OkHttpClient().newBuilder().dispatcher(dispatcher).build();
    }

    public void handle(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx, final HttpRequestFilter filter) {
        final String backendUrl = router.route(backendUrls);
        final String url = backendUrl + fullRequest.uri();
        filter.filter(fullRequest, ctx);
        proxyService.submit(() -> fetchGet(fullRequest, ctx, url));
    }

    private void fetchGet(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx, final String url) {
        final Request request = getRequest(url);

        try {
            client.newBuilder().addInterceptor(new Interceptor() {
                @NotNull
                @Override
                public Response intercept(@NotNull final Chain chain) throws IOException {
                    final Request request1 = chain.request();
                    final Response response = chain.proceed(request1);
                    handlerResponse(fullRequest, ctx, response);
                    return response;
                }
            }).build().newCall(request).execute();

        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    private void handlerResponse(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx, final Response headerResponse) {
        FullHttpResponse response = null;

        try {
            final String value = headerResponse.body().string();

            response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(value.getBytes("UTF-8")));

            response.headers().set("Content-Type", "application/json");
            response.headers().setInt("Content-Length", response.content().readableBytes());

            filter.filter(response);

        } catch (final Exception e) {
            e.printStackTrace();
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

    private Request getRequest(final String url) {
        final Request request = new Request.Builder().url(url).build();
        return request;
    }

    public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
