package encin.datasource.config;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author Encin.Li
 * @create 2021-03-07
 */
@Aspect
@Component
public class DataSourceAOP {
    @Pointcut("@annotation(encin.datasource.config.Slave)")
    public void readPointcut() {
    }

    @Pointcut("@annotation(encin.datasource.config.Master)")
    public void writePointcut() {
    }

    @Before("readPointcut()")
    public void readAdvise() {
        DynamicSwitchDBTypeUtil.slave();
    }

    @Before("writePointcut()")
    public void writeAdvise() {
        DynamicSwitchDBTypeUtil.master();
    }
}
