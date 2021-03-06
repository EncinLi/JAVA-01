package encin.datasource.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import encin.datasource.config.Master;
import encin.datasource.config.Slave;
import encin.datasource.mapper.AccountMapper;
import encin.datasource.model.Account;

/**
 * @author Encin.Li
 * @create 2021-03-07
 */
@Service
public class AccountService {
    @Resource
    private AccountMapper accountMapper;

    @Master
    public void addMaster(final Account account) {
        final int i = accountMapper.insert(account);
        System.out.println("新增成功：" + i + "条");
    }

    @Master
    public List<Account> findAllMaster() {
        return accountMapper.selectAll();
    }

    @Slave
    public void addSlave(final Account account) {
        final int i = accountMapper.insert(account);
        System.out.println("新增成功：" + i + "条");
    }

    @Slave
    public List<Account> findAllSlave() {
        return accountMapper.selectAll();
    }
}
