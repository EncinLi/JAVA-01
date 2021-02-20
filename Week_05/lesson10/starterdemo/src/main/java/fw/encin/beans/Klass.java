package fw.encin.beans;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Encin.Li
 * @create 2021-02-20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Klass {
    List<Student> students;

    public void dong() {
        for (final Student student : students) {
            System.out.println(student.toString());
        }
    }
}
