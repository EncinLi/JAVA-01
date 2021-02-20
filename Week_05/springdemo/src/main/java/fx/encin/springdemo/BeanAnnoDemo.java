package fx.encin.springdemo;

import javax.annotation.Resource;

import lombok.Data;

/**
 * @author Encin.Li
 * @create 2021-02-20
 */
@Data
public class BeanAnnoDemo {

    @Resource(name = "xml222")
    Student xml2;

    public void print() {
        System.out.println("Base name anno" + xml2.toString());
    }
}
