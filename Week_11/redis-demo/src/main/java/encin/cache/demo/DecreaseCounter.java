package encin.cache.demo;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * @author Encin.Li
 * @create 2021-04-06
 */
public class DecreaseCounter {
    private RedissonClient client;
    private RLock rLock;
    private String goodsKey = "goods:";
    private int MAX_COUNT;

    public DecreaseCounter() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://localhost:6379");
        client = Redisson.create(config);
        rLock = client.getLock("reduceLock");
        MAX_COUNT = 100;
    }

    private String getKey(int i) {
        return goodsKey + i;
    }

    public void createGoods(int i) throws Exception {
        String key = getKey(i);

        if (client.getAtomicLong(key).isExists()) {
            System.out.println("exist");
            // throw new Exception("had existed");
        }

        client.getAtomicLong(key).set(MAX_COUNT);
    }

    public void reduceGoods(int i) throws Exception {
        String key = getKey(i);
        try {
            rLock.lock();
            if (client.getAtomicLong(key).get() > 0) {
                client.getAtomicLong(key).decrementAndGet();
            } else {
                System.out.println("商品不足");
                throw new Exception("商品不足");
            }
        } finally {
            rLock.unlock();
        }
    }

    public void shutDown() {
        client.shutdown();
    }
}
