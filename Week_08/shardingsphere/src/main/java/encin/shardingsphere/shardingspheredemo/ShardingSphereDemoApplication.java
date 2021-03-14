package encin.shardingsphere.shardingspheredemo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import encin.shardingsphere.shardingspheredemo.sharding.ShardingSphereConfig;

@SpringBootApplication
public class ShardingSphereDemoApplication {



    public static void main(final String[] args) throws SQLException {
        SpringApplication.run(ShardingSphereDemoApplication.class, args);
        //go to unit test run
//        insertSql();
//        selectSql();
    }

    public static void insertSql() throws  SQLException {
        final Connection connection = getDataSource().getConnection();
        final String sql = "insert into t_order (user_id, order_id, desc) values(?, ?, ?);";
        final PreparedStatement ps = connection.prepareStatement(sql);
        for (int i = 0; i < 256; i++) {
            for (int j = 0; j < 256; j++) {
                ps.setInt(1,i);
                ps.setInt(2,j);
                ps.setString(3,"test-" + i +"-"+ j);
                ps.addBatch();
            }
            ps.executeBatch();
        }
        connection.commit();
        ps.close();
        connection.close();
    }

    public static void selectSql() throws SQLException {
        final Connection connection = getDataSource().getConnection();
        final String sql = "select * from t_order where user_id = 5 order by order_id";
        final ResultSet resultSet = connection.prepareStatement(sql).executeQuery();
        while (resultSet.next()) {
            System.out.println("userid:" + resultSet.getInt(1) + " - order_id:" + resultSet.getInt(2) + " - description:" + resultSet.getString(3));
        }
        connection.close();
    }

    private static DataSource getDataSource() throws SQLException {
        final ShardingSphereConfig shardingSphereConfig = new ShardingSphereConfig();
        final DataSource dataSource = shardingSphereConfig.shardingSphereDb();
        return dataSource;
    }
}
