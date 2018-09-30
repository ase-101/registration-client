package org.mosip.registration.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.mosip.kernel.auditmanager.config.AuditConfig;
import org.mosip.kernel.dataaccess.config.impl.HibernateDaoConfig;
import org.mosip.kernel.dataaccess.repository.impl.HibernateRepositoryImpl;
import org.mosip.kernel.logger.appenders.MosipRollingFileAppender;
import org.springframework.web.client.RestTemplate;

/**
 * Spring Configuration class for Registration-Processor Module
 * 
 * @author Balaji Sridharan
 * @since 1.0.0
 *
 */
@Configuration
@Import({HibernateDaoConfig.class, AuditConfig.class})
@EnableJpaRepositories(basePackages = "org.mosip.registration.", repositoryBaseClass = HibernateRepositoryImpl.class)
@ComponentScan("org.mosip.registration.")
@PropertySource({"application.properties", "config.properties"})
public class AppConfig {
	
	@Autowired
	private Environment environment;
	
	@Bean(name = "mosipRollingFileAppender")
	public MosipRollingFileAppender getFileAppender() {
		MosipRollingFileAppender mosipRollingFileAppender = new MosipRollingFileAppender();
		mosipRollingFileAppender.setAppenderName(environment.getProperty("log4j.appender.Appender"));
		mosipRollingFileAppender.setFileName(environment.getProperty("log4j.appender.Appender.file"));
		mosipRollingFileAppender.setFileNamePattern(environment.getProperty("log4j.appender.Appender.filePattern"));
		mosipRollingFileAppender.setMaxFileSize(environment.getProperty("log4j.appender.Appender.maxFileSize"));
		mosipRollingFileAppender.setTotalCap(environment.getProperty("log4j.appender.Appender.totalCap"));
		mosipRollingFileAppender.setMaxHistory(10);
		mosipRollingFileAppender.setImmediateFlush(true);
		mosipRollingFileAppender.setPrudent(true);

		return mosipRollingFileAppender;

	}
	
	@Bean
	public RestTemplate getRestTemplate(){
		return new RestTemplate();
	}
	
}
