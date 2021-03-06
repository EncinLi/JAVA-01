package encin.datasource.switchdemo;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import encin.datasource.model.Account;
import encin.datasource.service.AccountService;

@SpringBootTest
@RunWith(SpringRunner.class)
class SwitchdemoApplicationTests {

    @Autowired
    private AccountService accountService;

    @Test
    public void addMasterTest() {
        final Account account = new Account();
        account.setName("master-王五");
        account.setMoney(BigDecimal.valueOf(12.34));
        account.setPhone("12222");
        accountService.addMaster(account);
    }

    @Test
    public void findAllMasterTest() {
        final List<Account> all = accountService.findAllMaster();
        System.out.println(all.toString());
    }

    @Test
    public void addSlaveTest() {
        final Account account = new Account();
        account.setName("slave-王五");
        account.setMoney(BigDecimal.valueOf(12.34));
        account.setPhone("12222");
        accountService.addSlave(account);
    }

    @Test
    public void findAllSlaveTest() {
        final List<Account> all = accountService.findAllSlave();
        System.out.println(all.toString());
    }
}
