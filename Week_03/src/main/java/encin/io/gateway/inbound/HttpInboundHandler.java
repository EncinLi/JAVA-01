package encin.io.gateway.inbound;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import encin.io.gateway.filter.HeaderHttpRequestFilter;
import encin.io.gateway.filter.HttpRequestFilter;
import encin.io.gateway.outbound.HttpOutboundHandler;
import encin.io.gateway.outbound.OkHttpOutboundHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * @author Encin.Li
 * @create 2021-01-30
 */
public class HttpInboundHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(HttpInboundHandler.class);
    private final List<String> proxyServer;
    private final HttpOutboundHandler handler;
    private final OkHttpOutboundHandler okHandler;
    private final HttpRequestFilter filter = new HeaderHttpRequestFilter();

    public HttpInboundHandler(final List<String> proxyServer) {
        this.proxyServer = proxyServer;
        handler = new HttpOutboundHandler(this.proxyServer);
        okHandler = new OkHttpOutboundHandler(this.proxyServer);
    }

    @Override
    public void channelReadComplete(final ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void channelRead(final ChannelHandlerContext ctx, final Object msg) throws Exception {
        final FullHttpRequest fullRequest = (FullHttpRequest) msg;
        //        handler.handle(fullRequest, ctx, filter);
        okHandler.handle(fullRequest, ctx, filter);
    }
}
