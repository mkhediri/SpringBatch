package com.batchtraining.mk.springbatch.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.*;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(
        entityManagerFactoryRef = "universityEntityManagerFactory",
        transactionManagerRef = "universityTransactionManager"
)
public class JpaConfig {

    @Bean(name = "universityEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean universityEntityManagerFactory(
            @Qualifier("universitydatasource") DataSource dataSource) {

        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.batchtraining.mk.springbatch.entitie"); // adapte
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect"); // ou MySQL5
        em.setJpaPropertyMap(properties);

        return em;
    }

    @Bean(name = "universityTransactionManager")
    public JpaTransactionManager universityTransactionManager(
            @Qualifier("universityEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
