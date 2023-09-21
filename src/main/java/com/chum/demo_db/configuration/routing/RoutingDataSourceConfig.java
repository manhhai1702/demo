package com.chum.demo_db.configuration.routing;

import com.chum.demo_db.configuration.context.DatabaseContextHolder;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class RoutingDataSourceConfig extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return DatabaseContextHolder.getDbType();
    }
}
