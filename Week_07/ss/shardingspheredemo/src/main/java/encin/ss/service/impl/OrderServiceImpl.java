package encin.ss.service.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.stereotype.Service;

import encin.ss.entity.Account;
import encin.ss.service.OrderService;

/**
 * @author Encin.Li
 * @create 2021-03-07
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Override
    public Boolean addOrder(final DataSource dataSource, final Account account) {
        final String sql = String.format("insert into account (id,name)values(%d,%d);", account.getId(), account.getName());
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            connection.prepareStatement(sql).execute();
        } catch (final SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                connection.close();
            } catch (final SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    @Override
    public Account getOrderById(final DataSource dataSource, final String orderId) {
        Connection connection = null;
        final String sql = "select * from j_order where id = '" + orderId + "';";

        try {
            connection = dataSource.getConnection();
            final ResultSet resultSet = connection.prepareStatement(sql).executeQuery();
            int id = 0;
            while (resultSet.next()) {
                id = resultSet.getInt("id");
            }
            return new Account(id, "test", "21111111", new BigDecimal("0.52"));
        } catch (final SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                connection.close();
            } catch (final SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
