package jdbc.encin.jdbcdemo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.zaxxer.hikari.HikariDataSource;

@SpringBootApplication
public class JdbcDemoApplication {

    static String createSql = "create table if not exists student (id int not null primary key,name varchar(255) null,age int null)";
    static String dropSql = "drop table if exists student";

    public static void main(final String[] args) throws SQLException {
        System.out.println("==== runing =====");
        JdbcTest();
        PreparedTest();
        HiKariTest();
    }

    private static void HiKariTest() throws SQLException {
        final JdbcUtil jdbcUtil = new JdbcUtil();
        final HikariDataSource hikariDs = jdbcUtil.getHikariDS();
        final Connection hikariConn = hikariDs.getConnection();
        final Statement statement = hikariConn.createStatement();

        try {
            System.out.println("===create table person");
            statement.execute(createSql);
            final long start = System.currentTimeMillis();
            for (int i = 0; i < 1000; i++) {
                System.out.println("connections:" + i);
                //测压
                final String sql = String.format("INSERT INTO STUDENT(ID, NAME, AGE) " +
                                                         "SELECT  %d,CONCAT('student',%d), %d FROM DUAL " +
                                                         "WHERE NOT EXISTS ( SELECT ID FROM STUDENT WHERE ID = %d )", i, i, i, i);
                statement.execute(sql);
            }
            System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
        } catch (final Exception e) {
            hikariConn.rollback();
            e.printStackTrace();
        } finally {
            statement.execute(dropSql);
            System.out.println("====drop table successful====");
            statement.close();
            hikariConn.close();
        }
    }

    private static void PreparedTest() throws SQLException {
        final JdbcUtil jdbcUtil = new JdbcUtil();
        final Connection conn = jdbcUtil.getConn();
        final Statement statement = conn.createStatement();
        try {
            System.out.println("===create table person");
            statement.execute(createSql);

            System.out.println("===execute insert new record===");
            final PreparedStatement preparedStatement = conn.prepareStatement(
                    "INSERT INTO STUDENT(ID, NAME, AGE) SELECT ?,?,? FROM DUAL WHERE NOT EXISTS ( SELECT ID FROM STUDENT WHERE ID = ? )");

            preparedStatement.setInt(1, 1);
            preparedStatement.setString(2, "student1");
            preparedStatement.setInt(3, 1);
            preparedStatement.setInt(4, 1);

            preparedStatement.addBatch();

            preparedStatement.setInt(1, 2);
            preparedStatement.setString(2, "student2");
            preparedStatement.setInt(3, 2);
            preparedStatement.setInt(4, 2);
            preparedStatement.addBatch();
            final int[] ints = preparedStatement.executeBatch();
            Arrays.stream(ints).forEach(System.out::println);

            System.out.println("===execute select ===");
            final String query = "select * from student ";
            final ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                System.out.println("result:" + rs.getString("id") + " # " + rs.getString("name") + " # " + rs.getString("age"));
            }

        } catch (final Exception e) {
            conn.rollback();
            e.printStackTrace();
        } finally {
            statement.execute(dropSql);
            System.out.println("====drop table successful====");
            statement.close();
            conn.close();
        }
    }

    private static void JdbcTest() throws SQLException {
        final JdbcUtil jdbcUtil = new JdbcUtil();
        final Connection conn = jdbcUtil.getConn();
        final Statement statement = conn.createStatement();
        try {
            System.out.println("===create table person");
            statement.execute(createSql);

            System.out.println("===execute insert new record===");
            statement.execute("INSERT INTO STUDENT(ID, NAME, AGE) SELECT 1,'student1',1 FROM DUAL WHERE NOT EXISTS ( SELECT ID FROM STUDENT WHERE ID = 1 )");
            statement.execute("INSERT INTO STUDENT(ID, NAME, AGE) SELECT 2,'student2',2 FROM DUAL WHERE NOT EXISTS ( SELECT ID FROM STUDENT WHERE ID = 2 )");
            statement.execute("INSERT INTO STUDENT(ID, NAME, AGE) SELECT 3,'student3',3 FROM DUAL WHERE NOT EXISTS ( SELECT ID FROM STUDENT WHERE ID = 3 )");
            System.out.println("===execute insert finish===");

            System.out.println("===execute select ===");
            final String query = "select * from student ";
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                System.out.println("result:" + rs.getString("id") + " # " + rs.getString("name") + " # " + rs.getString("age"));
            }

            System.out.println("===execute update sql ===");
            statement.executeUpdate("update student set name='student update' where id='1'");
            System.out.println("===after update ===");
            rs = statement.executeQuery(query);
            while (rs.next()) {
                System.out.println("result:" + rs.getString("id") + " # " + rs.getString("name") + " # " + rs.getString("age"));
            }

            System.out.println("===execute delete ===");
            statement.executeUpdate("delete from student where id='1'");
            System.out.println("===after delete ===");
            rs = statement.executeQuery(query);
            while (rs.next()) {
                System.out.println("result:" + rs.getString("id") + " # " + rs.getString("name") + " # " + rs.getString("age"));
            }
        } catch (final Exception e) {
            conn.rollback();
            e.printStackTrace();
        } finally {
            statement.execute(dropSql);
            System.out.println("====drop table successful====");
            statement.close();
            conn.close();
        }

    }

}
