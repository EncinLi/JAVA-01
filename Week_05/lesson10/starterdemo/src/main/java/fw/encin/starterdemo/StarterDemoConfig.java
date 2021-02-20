package fw.encin.starterdemo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

import fw.encin.beans.Klass;
import fw.encin.beans.School;
import fw.encin.beans.Student;

/**
 * 通过使用配置文件 spring.factories 去配置或者用注解@Configuration都可以实现装配达到效果
 *
 * @author Encin.Li
 * @create 2021-02-20
 */
//@Configuration
//@ConditionalOnClass(StarterDemoApplication.class)
public class StarterDemoConfig {

    @ConditionalOnMissingBean(StarterDemoApplication.class)
    @Bean
    public StarterDemoApplication startRun() {
        return new StarterDemoApplication();
    }

    @ConditionalOnMissingBean(Student.class)
    @Bean(name = "student100")
    public Student initStudent() {
        System.out.println("===========Student========");
        final Student student = new Student(100, "student100-1");
        System.out.println(student.toString());
        return student;
    }

    @ConditionalOnMissingBean(Klass.class)
    @Bean
    public Klass initClass01() {
        System.out.println("===========Klass========");
        final List<Student> students = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            students.add(new Student(i, "student #" + i));
        }
        final Klass klass = new Klass(students);
        klass.dong();
        return klass;
    }

    @ConditionalOnMissingBean(School.class)
    @Bean
    public School initSchool() {
        System.out.println("===========initSchool========");
        final List<Student> students = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            students.add(new Student(i, "student #" + i));
        }
        final Klass klass = new Klass(students);
        final Student student = new Student(100, "student100-1");
        final School school = new School(klass, student);
        school.ding();
        return school;
    }
}
