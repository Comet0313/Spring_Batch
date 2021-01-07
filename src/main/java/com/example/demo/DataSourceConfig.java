package com.example.demo;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariDataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.batch.BatchDataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

/**
  *使用Postgres管理数据的配置
  */
@Configuration
@PropertySource({"classpath:dataSource.properties"})
@MapperScan(basePackages = "com.example.demo.db") 
public class DataSourceConfig {

	@Value("${spring.datasource.driverClassName}")
	private String driverClassName;

	@Value("${spring.datasource.url}")
	private String driverUrl;

	@Value("${spring.datasource.username}")
	private String driverUsername;

	@Value("${spring.datasource.password}")
	private String driverPassword;

	@Value("${spring.datasource.defaultAutoCommit}")
	private boolean defaultAutoCommit;

	/**
	* Spring Batch用于状态管理的数据
	*
	* <p>
	*对于不需要状态管理的批次，请使用此样本中所示的H2内存数据库。 执行批处理后，将删除保存的内容。
	*
	* @return SpringBatch状态管理数据源
	*/
    @Bean(name="springBatchDs")
    @BatchDataSource
    public DataSource springBatchDs() {
        return DataSourceBuilder.create().url("jdbc:h2:mem:instantdb").username("sa").password("").build();
	}
	
	@Primary
	@Bean(name="postgresqlDataSource")
	public DataSource postgresqlDataSource() {
		HikariDataSource dataSource = new HikariDataSource();
		dataSource.setDriverClassName(driverClassName);
		dataSource.setJdbcUrl(driverUrl);
		dataSource.setUsername(driverUsername);
		dataSource.setPassword(driverPassword);
		dataSource.setAutoCommit(defaultAutoCommit);
		return dataSource;
	}

	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception {
	  SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
	  factoryBean.setDataSource(postgresqlDataSource());
	  return factoryBean.getObject();
	}

	@Bean
	public SqlSessionTemplate sqlSession() throws Exception {
	  return new SqlSessionTemplate(sqlSessionFactory());
	}

	@Bean
	public DataSourceTransactionManager postgresTransactionManager() {
	  return new DataSourceTransactionManager(this.postgresqlDataSource());
	}

}
