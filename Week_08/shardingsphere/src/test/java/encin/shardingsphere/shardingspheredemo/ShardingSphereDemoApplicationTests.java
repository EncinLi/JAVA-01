package encin.shardingsphere.shardingspheredemo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = ShardingSphereDemoApplication.class)
public class ShardingSphereDemoApplicationTests {

    @Autowired
    private DataSource dataSource;

//    @Test
//    void contextLoads() {
//    }

    @Test
    public void insertSql() throws  SQLException {
        final Connection connection = dataSource.getConnection();
        final String sql = "insert into t_order(user_id, order_id, description) values(?, ?, ?)";
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

    @Test
    public void selectSql() throws SQLException {
        final Connection connection = dataSource.getConnection();
        final String sql = "select * from t_order where user_id = 5 order by order_id";
        final PreparedStatement ps = connection.prepareStatement(sql);
        final ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()) {
            System.out.println("user_id:" + resultSet.getInt(1) + " - order_id:" + resultSet.getInt(2) + " - description:" + resultSet.getString(3));
        }
        ps.close();
        connection.close();
    }

    @Test
    public void updateSql() throws SQLException {
        final Connection connection = dataSource.getConnection();
        final String sql = "update t_order set description = 'update-1-2' where user_id = 1";
        final PreparedStatement ps = connection.prepareStatement(sql);
        ps.executeUpdate();
        ps.close();
        connection.close();
    }

    @Test
    public void deleteSql() throws SQLException {
        final Connection connection = dataSource.getConnection();
        final String sql = "delete from t_order where user_id = 3 ";
        final PreparedStatement ps = connection.prepareStatement(sql);
        ps.executeUpdate();
        ps.close();
        connection.close();
    }
}
