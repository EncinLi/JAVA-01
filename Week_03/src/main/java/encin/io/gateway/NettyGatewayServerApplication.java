package encin.io.gateway;

import java.util.Arrays;

import encin.io.gateway.inbound.HttpInboundServer;

/**
 * @author Encin.Li
 * @create 2021-01-30
 */
public class NettyGatewayServerApplication {

    public final static String GATEWAY_NAME = "NIOGateway";
    public final static String GATEWAY_VERSION = "3.0.0";

    public static void main(final String[] args) {

        final String proxyPort = System.getProperty("proxyPort", "8889");

        final String proxyServers = System.getProperty("proxyServers", "http://localhost:8801,http://localhost:8802,http://localhost:8803");
        final int port = Integer.parseInt(proxyPort);
        System.out.println(GATEWAY_NAME + " " + GATEWAY_VERSION + " starting...");
        final HttpInboundServer server = new HttpInboundServer(port, Arrays.asList(proxyServers.split(",")));
        System.out.println(GATEWAY_NAME + " " + GATEWAY_VERSION + " started at http://localhost:" + port + " for server:" + server.toString());
        try {
            server.run();
        } catch (final Exception ex) {
            ex.printStackTrace();
        }
    }
}
