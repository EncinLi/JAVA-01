package encin.datasource.switchdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("encin.datasource.mapper")
public class SwitchdemoApplication {

    public static void main(final String[] args) {
        SpringApplication.run(SwitchdemoApplication.class, args);
    }

}
