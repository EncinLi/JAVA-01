package encin.shardingsphere.xa.xademo;

import java.io.IOException;
import java.sql.SQLException;

public class XaDemoApplication {

    public static void main(final String[] args) throws IOException, SQLException {
        final XaConfig orderService = new XaConfig("/sharding-db-xa.yaml");
        orderService.insertSuccessed();
        orderService.insertFailed();
    }

}
