package encin.io.gateway.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

public class HeaderHttpRequestFilter implements HttpRequestFilter {

    @Override
    public void filter(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx) {
        fullRequest.headers().set("mao", "soul");
    }
}
