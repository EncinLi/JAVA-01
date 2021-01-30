package encin.io.gateway.inbound;

import java.util.List;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author Encin.Li
 * @create 2021-01-30
 */
public class HttpInboundInitializer extends ChannelInitializer<SocketChannel> {

    private final List<String> proxyServer;

    public HttpInboundInitializer(final List<String> proxyServer) {
        this.proxyServer = proxyServer;
    }

    @Override
    protected void initChannel(final SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline()
                     .addLast(new HttpServerCodec())
                     .addLast(new HttpObjectAggregator(1024 * 1024))
                     .addLast(new HttpInboundHandler(proxyServer));

    }
}
