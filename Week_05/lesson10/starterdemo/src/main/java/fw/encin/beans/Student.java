package fw.encin.beans;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Encin.Li
 * @create 2021-02-20
 */
@Data
@AllArgsConstructor
public class Student implements Serializable {
    private int id;
    private String name;
}

