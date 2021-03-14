package encin.shardingsphere.xa.xademo;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.apache.shardingsphere.driver.api.yaml.YamlShardingSphereDataSourceFactory;
import org.apache.shardingsphere.transaction.core.TransactionType;
import org.apache.shardingsphere.transaction.core.TransactionTypeHolder;

/**
 * @author Encin.Li
 * @create 2021-03-14
 */
public class XaConfig {

    private final DataSource dataSource;

    XaConfig(final String configFile) throws IOException, SQLException {
        final File file = new File(XaConfig.class.getResource(configFile).getFile());
        dataSource = YamlShardingSphereDataSourceFactory.createDataSource(file);
    }

    void insertSuccessed() throws SQLException {
        TransactionTypeHolder.set(TransactionType.XA);
        try (final Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            final PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO t_order (user_id, description) VALUES (?, ?)");
            for (int i = 0; i < 10; i++) {
                preparedStatement.setObject(1, i);
                preparedStatement.setObject(2, "test-success-"+i);
                preparedStatement.executeUpdate();
            }
            connection.commit();
        } finally {
            TransactionTypeHolder.clear();
        }
    }

    void insertFailed() throws SQLException {
        TransactionTypeHolder.set(TransactionType.XA);
        try (final Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            final PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO t_order (user_id, description) VALUES (?, ?)");
            for (int i = 0; i < 10; i++) {
                preparedStatement.setObject(1, i);
                preparedStatement.setObject(2, "test-fail-"+i);
                preparedStatement.executeUpdate();
            }
            connection.rollback();
        } finally {
            TransactionTypeHolder.clear();
        }
    }

    int selectAll() throws SQLException {
        int result = 0;
        try (final Connection connection = dataSource.getConnection()) {
            final Statement statement = connection.createStatement();
            statement.executeQuery("SELECT COUNT(1) AS count FROM t_order");
            final ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                result = resultSet.getInt(1);
            }
        }
        return result;
    }
}
