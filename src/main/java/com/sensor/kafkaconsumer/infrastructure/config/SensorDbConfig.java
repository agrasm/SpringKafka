package com.sensor.kafkaconsumer.infrastructure.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
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

import java.util.HashMap;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "sensorEntityManagerFactory",
        transactionManagerRef = "sensorTransactionManager",
        basePackages = {"com.sensor.kafkaconsumer.infrastructure.persistence.sensor.repository"}
)
public class SensorDbConfig {

    @Primary
    @Bean(name = "sensorProperties")
    @ConfigurationProperties("spring.datasource.sensor")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean(name = "sensorDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.sensor")
    public DataSource dataSource(@Qualifier("sensorProperties") DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().build();
    }

    @Primary
    @Bean(name = "sensorEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder builder, @Qualifier("sensorDataSource") DataSource dataSource) {
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");

        return builder
                .dataSource(dataSource)
                .packages("com.sensor.kafkaconsumer.infrastructure.persistence.sensor.entity")
                .persistenceUnit("sensor")
                .properties(properties)
                .build();
    }

    @Primary
    @Bean(name = "sensorTransactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("sensorEntityManagerFactory") LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory.getObject());
    }
}