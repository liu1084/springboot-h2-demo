/*
 * © Copyright Process Asset Integration and Management Limited 2013 - 2019.
 *  All rights reserved.
 */

package com.jim.db.h2.config;

import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * @project: proaim-trinity-demo
 * @packageName: com.proaimltd.web.pub.config
 * @author: liu10
 * @date: 2019/6/18 11:17
 * @description：数据库读写分离配置
 */
@Slf4j
@Setter
@Getter
@Configuration
@MapperScan(value = {"com.jim.db.h2.service.mapper"}, sqlSessionFactoryRef = "sqlSessionFactory")
@EnableTransactionManagement
@Order(-1)
public class DatasourceConfig implements TransactionManagementConfigurer {

    private static final String MAPPER_LOCATION = "classpath*:mappers/**/*Mapper.xml";

	@Value("${spring.datasource.url}")
	private String dataSourceUrl;

	@Value("${spring.datasource.driverClassName}")
	private String driverClassName;

	@Value("${spring.datasource.username}")
	private String dataSourceUsername;

	@Value("${spring.datasource.password}")
	private String dataSourcePassword;

    @Bean(name = "dataSource")
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(dataSourceUrl);
        dataSource.setUsername(dataSourceUsername);
        dataSource.setPassword(dataSourcePassword);
        dataSource.setDriverClassName(driverClassName);
        return dataSource;
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory getSqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws IOException {

        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);

        // 添加XML目录
          ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
          try {
            factoryBean.setMapperLocations(resolver.getResources(MAPPER_LOCATION));
            SqlSessionFactory factory = factoryBean.getObject();

            factory.getConfiguration().setMapUnderscoreToCamelCase(true);
            return factoryBean.getObject();
        } catch (Exception e) {
            log.warn("getSqlSessionFactory failed, errorMessage:{}", e);
            throw new RuntimeException(e);
        }
    }

    @Bean(name = "sqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean(name = "annotationDrivenTransactionManager")
    @Override
    public DataSourceTransactionManager annotationDrivenTransactionManager() {
        try {
            DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
            transactionManager.setDataSource(dataSource());
            return transactionManager;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
