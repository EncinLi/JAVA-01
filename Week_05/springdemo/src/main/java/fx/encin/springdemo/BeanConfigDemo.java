package fx.encin.springdemo;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Encin.Li
 * @create 2021-02-20
 */
public class BeanConfigDemo {
    public static void main(final String[] args) {
        //Xml config
        final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        final Student xml111 = (Student) context.getBean("xml111");
        System.out.println(xml111.toString());
        final BeanAnnoDemo beanAnnoDemo = context.getBean(BeanAnnoDemo.class);
        beanAnnoDemo.print();

        //Annotations Bean config
        final AnnotationConfigApplicationContext acontext = new AnnotationConfigApplicationContext("fx.encin.springdemo");
        final Student student111 = (Student) acontext.getBean("student111");
        System.out.println(student111.toString());
    }
}
