package encin.datasource.config;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author Encin.Li
 * @create 2021-03-07
 */
public class DynamicSwitchDBTypeUtil {

    @Value("${spring.datasource.slavecnt}")
    private static int slaveCnt;

    private static final ThreadLocal<DbEnum> CONTEXT_HAND = new ThreadLocal<>();

    public static void set(final DbEnum dbEnum) {
        CONTEXT_HAND.set(dbEnum);
        //        log.info("切换数据源：" + dbEnum);
    }

    public static void master() {
        set(DbEnum.MASTER);
    }

    public static void slave() {
        //随机切换
        if (System.currentTimeMillis() % 2 == 0) {
            set(DbEnum.SLAVE1);
        } else {
            set(DbEnum.SLAVE2);
        }

    }

    public static DbEnum get() {
        return CONTEXT_HAND.get();
    }
}
