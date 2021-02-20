package jdbc.encin.jdbcdemo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * @author Encin.Li
 * @create 2021-02-20
 */
public class JdbcUtil {
    public static String url = "jdbc:mysql://localhost:3306/test";
    public static String username = "root";
    public static String password = "123456";

    public Connection getConn() throws SQLException {
        Connection conn = null;
        final Properties connectionProps = new Properties();
        connectionProps.put("user", username);
        connectionProps.put("password", password);
        conn = DriverManager.getConnection(url, connectionProps);
        return conn;
    }

    public HikariDataSource getHikariDS() throws SQLException {
        final HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.setMaximumPoolSize(4);
        return new HikariDataSource(config);
    }

}
