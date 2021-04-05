package encin.cache.demo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import lombok.SneakyThrows;

@SpringBootApplication
@EnableCaching
public class RedisDemoApplication {

    @SneakyThrows
    public static void main(String[] args) {
        //        SpringApplication.run(RedisDemoApplication.class, args);
        // 必做(1
        //        final LockDemo lockDemo = new LockDemo();
        //        lockDemo.lockDemo();

        // 必做(2
        final DecreaseCounter decreaseCounter = new DecreaseCounter();
        decreaseCounter.createGoods(1);
        ExecutorService executorService = Executors.newFixedThreadPool(16);

        for (int i = 0; i < 100; i++) {
            int finalI = i;
            executorService.execute(() -> {
                try {
                    System.out.println("商品充足");
                    decreaseCounter.reduceGoods(finalI);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("商品数量不足");
                }
            });
        }
        executorService.shutdown();
        System.out.println(100);
        try {
            executorService.awaitTermination(Integer.MAX_VALUE, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        decreaseCounter.shutDown();
    }

}
