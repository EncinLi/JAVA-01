package encin.cache.demo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.stereotype.Component;


/**
 * @author Encin.Li
 * @create 2021-04-04
 */
@Component
public class LockDemo {


    public void lockDemo() throws InterruptedException {
        Config config = new Config();
        config.useSingleServer()
              .setAddress("redis://localhost:6379");

        RedissonClient client = Redisson.create(config);
        RMap<String, String> rmap = client.getMap("map1");
        RLock rLock = client.getLock("lock");

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            executorService.execute(() -> {
                rLock.lock();
                try {
                    rmap.put("rkey:"+ finalI, "rvalue:"+ finalI);
                    System.out.println("successful");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("fail");
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread() + " - " + System.currentTimeMillis());
                rLock.unlock();
            });
        }
    }


}
