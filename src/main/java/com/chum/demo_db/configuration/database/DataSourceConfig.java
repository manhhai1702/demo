package com.chum.demo_db.configuration.database;

import com.chum.demo_db.configuration.context.DbType;
import com.chum.demo_db.configuration.routing.RoutingDataSourceConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
class DataSourceConfig {
    @Bean
    public DataSource routingDataSource(
            @Qualifier("masterDataSource") DataSource masterDataSource,
            @Qualifier("slaveDataSource") DataSource slaveDataSource) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DbType.MASTER, masterDataSource);
        targetDataSources.put(DbType.SLAVE, slaveDataSource);

        RoutingDataSourceConfig routingDataSource = new RoutingDataSourceConfig();
        routingDataSource.setTargetDataSources(targetDataSources);

        return routingDataSource;
    }

}
