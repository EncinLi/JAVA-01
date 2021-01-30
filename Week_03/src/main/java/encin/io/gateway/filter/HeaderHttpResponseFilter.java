package encin.io.gateway.filter;

import io.netty.handler.codec.http.FullHttpResponse;

public class HeaderHttpResponseFilter implements HttpResponseFilter {
    @Override
    public void filter(final FullHttpResponse response) {
        response.headers().set("encin", "work-3-nio");
    }
}
