package com.springmvc.configuration;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

/**
 * @author Khyati Desai
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.springmvc.domain.repo",entityManagerFactoryRef="wfEM", transactionManagerRef="wfTM")
public class PersistenceContext {


    

    private static final String PROPERTY_NAME_HIBERNATE_DIALECT 		= "hibernate.dialect";
    private static final String PROPERTY_NAME_HIBERNATE_FORMAT_SQL 		= "hibernate.format_sql";
    private static final String PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO 	= "hibernate.hbm2ddl.auto";
    private static final String PROPERTY_NAME_HIBERNATE_NAMING_STRATEGY = "hibernate.ejb.naming_strategy";
    private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL 		= "hibernate.show_sql";
    private static final String PROPERTY_NAME_HIBERNATE_LAZY_LOAD 		= "hibernate.enable_lazy_load_no_trans";

    private static final String PROPERTY_PACKAGES_TO_SCAN = "com.springmvc.domain";

    @Autowired
    private Environment environment;

    @Bean(name="wfDatastore")
    @Primary
    public DataSource dataSource() {
    	HikariDataSource dataSource = new HikariDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/neml_register");
		dataSource.setUsername("root");
		dataSource.setPassword("root");
		dataSource.setConnectionTestQuery("SELECT 1");
		dataSource.setMaximumPoolSize(10);
		dataSource.setMaxLifetime(30000);
		dataSource.setConnectionTimeout(60000);
		return dataSource;
    	
    }

    @Bean(name="wfTM")
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();

        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());

        return transactionManager;
    }
    
    @Bean(name="wfEM")
    @DependsOn("wfDatastore")
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();

        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setPackagesToScan(PROPERTY_PACKAGES_TO_SCAN);

        Properties jpaProperties = new Properties();
        jpaProperties.put(PROPERTY_NAME_HIBERNATE_DIALECT, "org.hibernate.dialect.MySQL5Dialect");
        jpaProperties.put(PROPERTY_NAME_HIBERNATE_FORMAT_SQL, true);
        jpaProperties.put(PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO, "update");
        jpaProperties.put(PROPERTY_NAME_HIBERNATE_NAMING_STRATEGY, "org.hibernate.cfg.ImprovedNamingStrategy");
        jpaProperties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL, false);
        jpaProperties.put(PROPERTY_NAME_HIBERNATE_LAZY_LOAD, true);

        entityManagerFactoryBean.setJpaProperties(jpaProperties);
        return entityManagerFactoryBean;
    }
    
    

}
