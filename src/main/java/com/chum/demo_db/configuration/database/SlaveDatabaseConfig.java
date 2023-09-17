package com.chum.demo_db.configuration.database;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.chum.demo_db.repositories.slave",
        entityManagerFactoryRef = "slaveEntityManagerFactory",
        transactionManagerRef = "slaveTransactionManager"
)
public class SlaveDatabaseConfig {
    @Primary
    @Bean(name = "slaveDataSourceProperties")
    @ConfigurationProperties(prefix = "datasource.slave")
    public DataSourceProperties slaveDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean(name = "slaveDataSource")
    @ConfigurationProperties(prefix = "datasource.slave.hikari")
    public HikariDataSource slaveDataSource() {
        return slaveDataSourceProperties()
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Primary
    @Bean(name = "slaveEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("slaveDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("com.chum.demo_db.domains.entities")
                .persistenceUnit("slave")
                .build();
    }

    @Primary
    @Bean(name = "slaveTransactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("slaveEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean(name = "slaveLiquibaseProperties")
    @ConfigurationProperties(prefix = "datasource.slave.liquibase")
    public LiquibaseProperties slaveLiquibaseProperties() {
        return new LiquibaseProperties();
    }


    @Bean(name = "slaveLiquibase")
    public SpringLiquibase slaveLiquibase (
            @Qualifier("slaveDataSource") DataSource dataSource,
            @Qualifier("slaveLiquibaseProperties") LiquibaseProperties properties) {
        SpringLiquibase liquibase = new SpringLiquibase();

        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog(properties.getChangeLog());
        liquibase.setContexts(properties.getContexts());
        liquibase.setDefaultSchema(properties.getDefaultSchema());
        liquibase.setDropFirst(properties.isDropFirst());
        liquibase.setShouldRun(properties.isEnabled());
        liquibase.setLabelFilter(properties.getLabelFilter());
        liquibase.setChangeLogParameters(properties.getParameters());
        liquibase.setRollbackFile(properties.getRollbackFile());
        return liquibase;
    }
}
