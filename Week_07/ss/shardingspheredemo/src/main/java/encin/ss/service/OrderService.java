package encin.ss.service;

import javax.sql.DataSource;

import encin.ss.entity.Account;

/**
 * @author Encin.Li
 * @create 2021-03-07
 */
public interface OrderService {

    public Boolean addOrder(DataSource dataSource, Account account);

    public Account getOrderById(DataSource dataSource, String orderId);
}
