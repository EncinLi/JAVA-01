package encin.datasource.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author Encin.Li
 * @create 2021-03-07
 */
public class RoutingDataSource extends AbstractRoutingDataSource {
    
    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicSwitchDBTypeUtil.get();
    }
}
