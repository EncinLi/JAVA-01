package encin.datasource.model;

import javax.persistence.Table;

import lombok.Data;
import lombok.ToString;

/**
 * @author Encin.Li
 * @create 2021-03-07
 */
@Data
@Table(name = "account")
@ToString
public class Account {

    private Integer id;

    private String name;

}
