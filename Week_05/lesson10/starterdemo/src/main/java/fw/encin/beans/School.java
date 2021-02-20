package fw.encin.beans;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Encin.Li
 * @create 2021-02-20
 */
@Data
@AllArgsConstructor
@Component
public class School {

    @Autowired
    Klass class1;

    @Resource(name = "student100")
    Student student100;

    public void ding() {
        System.out.println("Class1 have " + class1.getStudents().size() + " students and one is " + student100);
    }
}
