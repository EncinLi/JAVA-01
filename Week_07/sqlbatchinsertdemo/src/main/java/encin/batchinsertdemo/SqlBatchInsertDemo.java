package encin.batchinsertdemo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.zaxxer.hikari.HikariDataSource;

/**
 * @author Encin.Li
 * @create 2021-03-06
 */
public class SqlBatchInsertDemo {

    public static void main(final String[] args) throws SQLException {

        final JdbcUtil jdbcUtil = new JdbcUtil();
        final HikariDataSource hikariDS = jdbcUtil.getHikariDS();
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = hikariDS.getConnection();
            connection.setAutoCommit(false);

            final String createSql = "insert into j_orders values(?,?,?,?,?)";
            ps = connection.prepareStatement(createSql);
            final Long start = System.currentTimeMillis();
            int k = 0;

            for (Integer i = 1; i <= 1000000; i++) {
                ps.setInt(1, i);
                ps.setInt(2, (i % 20));
                ps.setInt(3, (i % 2));
                ps.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
                ps.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
                ps.addBatch();
                if (i % 10000 == 0) {

                    ps.executeBatch();
                    connection.commit();
                    ps.clearBatch();

                    System.out.println("execute-----" + k++);
                }
            }
            ps.executeBatch();
            connection.commit();

            final Long end = System.currentTimeMillis();
            System.out.println("耗时(ms)：" + (end - start));//11 sec
        } catch (final Exception e) {
            e.printStackTrace();
        } finally {
            ps.close();
            connection.close();
        }
    }
}
