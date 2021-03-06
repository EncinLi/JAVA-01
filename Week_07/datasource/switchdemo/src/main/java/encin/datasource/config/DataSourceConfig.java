package encin.datasource.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Encin.Li
 * @create 2021-03-07
 */
@Configuration
public class DataSourceConfig {
    @Bean
    @ConfigurationProperties("spring.datasource.master")
    public DataSource masterDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.slave1")
    public DataSource slaveDataSource1() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.slave2")
    public DataSource slaveDataSource2() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public DataSource targetDataSource(@Qualifier("masterDataSource") final DataSource masterDataSource,
            @Qualifier("slaveDataSource1") final DataSource slaveDataSource1,
            @Qualifier("slaveDataSource2") final DataSource slaveDataSource2) {

        final Map<Object, Object> targetDataSource = new HashMap<>();
        targetDataSource.put(DbEnum.MASTER, masterDataSource);
        targetDataSource.put(DbEnum.SLAVE1, slaveDataSource1);
        targetDataSource.put(DbEnum.SLAVE2, slaveDataSource2);

        final RoutingDataSource routingDataSource = new RoutingDataSource();
        //绑定所有数据源
        routingDataSource.setTargetDataSources(targetDataSource);
        //设置默认数据源
        routingDataSource.setDefaultTargetDataSource(masterDataSource);

        return routingDataSource;
    }
}
