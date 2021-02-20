package fx.encin.springdemo;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author Encin.Li
 * @create 2021-02-20
 */
@Component
public class AnnotationsBean {

    @Bean
    public Student student111() {
        return new Student(111, "Annotations");
    }

}
