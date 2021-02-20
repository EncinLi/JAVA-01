package fw.encin.starterdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StarterDemoApplication {

    public static void main(final String[] args) {
        System.out.println("==== runing =====");
        SpringApplication.run(StarterDemoApplication.class, args);
    }

}
