package demo.configuration;

import java.util.Properties;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.eclipse.persistence.jpa.PersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaDialect;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;



@Configuration
@EnableTransactionManagement
@PropertySource("classpath:app.properties")
public class AppConfig {

	private static final String PROPERTY_NAME_DATABASE_DRIVER = "db.driver";
	private static final String PROPERTY_NAME_DATABASE_PASSWORD = "db.password";
	private static final String PROPERTY_NAME_DATABASE_URL = "db.url";
	private static final String PROPERTY_NAME_DATABASE_USERNAME = "db.username";

	private static final String PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN = "entitymanager.packages.to.scan";
	
	@Resource
	private Environment env;
	
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getRequiredProperty(PROPERTY_NAME_DATABASE_DRIVER));
		dataSource.setUrl(env.getRequiredProperty(PROPERTY_NAME_DATABASE_URL));
		dataSource.setUsername(env.getRequiredProperty(PROPERTY_NAME_DATABASE_USERNAME));
		dataSource.setPassword(env.getRequiredProperty(PROPERTY_NAME_DATABASE_PASSWORD));
		return dataSource;
	}
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		//entityManagerFactoryBean.setDataSource(dataSource());
		entityManagerFactoryBean.setPersistenceProviderClass(PersistenceProvider.class);
		entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter());
		entityManagerFactoryBean.setPackagesToScan(env.getRequiredProperty(PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN));
		entityManagerFactoryBean.setJpaProperties(getProperties());
		
		return entityManagerFactoryBean;
	}
	
	 @Bean
	    public JpaVendorAdapter jpaVendorAdapter() {
	         EclipseLinkJpaVendorAdapter adapter = new EclipseLinkJpaVendorAdapter();
	         adapter.setShowSql(false);
	         adapter.setDatabase(Database.MYSQL);
	        return adapter;
	    }
	

	private Properties getProperties() {
		Properties properties = new Properties();
		
		properties.put("javax.persistence.jdbc.driver",env.getRequiredProperty(PROPERTY_NAME_DATABASE_DRIVER));
		properties.put("javax.persistence.jdbc.url",env.getRequiredProperty(PROPERTY_NAME_DATABASE_URL));
		properties.put("javax.persistence.jdbc.user",env.getRequiredProperty(PROPERTY_NAME_DATABASE_USERNAME));
		properties.put("javax.persistence.jdbc.password",env.getRequiredProperty(PROPERTY_NAME_DATABASE_PASSWORD));
		
		properties.put("eclipselink.connection-pool.default.initial","1");
		properties.put("eclipselink.connection-pool.default.min","64");
		properties.put("eclipselink.connection-pool.default.max","64");
		
		
		//properties.put("eclipselink.connection-pool.node2.url",env.getRequiredProperty(PROPERTY_NAME_DATABASE_URL));
		//properties.put("eclipselink.connection-pool.node2.user",env.getRequiredProperty(PROPERTY_NAME_DATABASE_USERNAME));
		//properties.put("eclipselink.connection-pool.node2.password",env.getRequiredProperty(PROPERTY_NAME_DATABASE_PASSWORD));
		//properties.put("eclipselink.connection-pool.node2.initial","1");
		//properties.put("eclipselink.connection-pool.node2.min","64");
		//properties.put("eclipselink.connection-pool.node2.max","64");
		//properties.put("eclipselink.partitioning","Replicate");
		
		properties.put("eclipselink.weaving","false");
		
		
		return properties;
	}

	@Bean
	public JpaTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
		return transactionManager;
	}

	
}
