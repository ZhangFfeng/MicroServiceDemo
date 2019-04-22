package com.gaeainfo.demo.config;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import oracle.jdbc.xa.client.OracleXADataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * 1号数据源配置文件
 *
 * @Author: 张丰
 * @Version 1.0
 */
@SuppressWarnings("ALL")
@Configuration
// basePackages 最好分开配置 如果放在同一个文件夹可能会报错
@MapperScan(basePackages = "com.gaeainfo.demo.mapper.mapper1", sqlSessionTemplateRef = "primarySqlSessionTemplate")
public class PrimaryMyBatisConfig {


    /**
     * 配置1号数据源
     *
     * @param primaryDataSourceProperties 1号数据源配置类
     * @return 数据源
     * @throws SQLException
     */
    @Primary
    @Bean(name = "primaryDataSource")
    @DependsOn({"transactionManager"})
    public DataSource primaryDataSource(PrimaryDataSourceProperties primaryDataSourceProperties) throws SQLException {
        OracleXADataSource oracleXADataSource = new OracleXADataSource();
        oracleXADataSource.setURL(primaryDataSourceProperties.getUrl());
        oracleXADataSource.setUser(primaryDataSourceProperties.getUserName());
        oracleXADataSource.setPassword(primaryDataSourceProperties.getPassword());
        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaDataSource(oracleXADataSource);
        xaDataSource.setUniqueResourceName("primaryDataSource");
        xaDataSource.setTestQuery(primaryDataSourceProperties.getTestQuery());
        xaDataSource.setMinPoolSize(primaryDataSourceProperties.getMinPoolSize());
        xaDataSource.setMaxPoolSize(primaryDataSourceProperties.getMaxPoolSize());
        xaDataSource.setMaxLifetime(primaryDataSourceProperties.getMaxLifetime());
        xaDataSource.setBorrowConnectionTimeout(primaryDataSourceProperties.getBorrowConnectionTimeout());
        xaDataSource.setLoginTimeout(primaryDataSourceProperties.getLoginTimeout());
        xaDataSource.setMaintenanceInterval(primaryDataSourceProperties.getMaintenanceInterval());
        xaDataSource.setMaxIdleTime(primaryDataSourceProperties.getMaxIdleTime());
        return xaDataSource;
    }

    /**
     * 创建SqlSession实例的工厂
     *
     * @param dataSource 数据源
     * @return
     * @throws Exception
     */
    @Bean(name = "primarySqlSessionFactory")
    public SqlSessionFactory primarySqlSessionFactory(@Qualifier("primaryDataSource") DataSource dataSource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper1/*.xml"));
        return bean.getObject();
    }

    /**
     * jdbc访问模板化的工具----普通jdbc访问数据库模板工具
     *
     * @param dataSource
     * @return 模板
     */
    @Bean(name = "primaryJdbcTemplate")
    public JdbcTemplate primaryJdbcTemplate(@Qualifier("primaryDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    /**
     * 持久层访问模板化的工具 --替代默认的MyBatis实现的DefaultSqlSession
     *
     * @param sqlSessionFactory 实例工厂
     * @return 模板
     * @throws Exception
     */
    @Bean(name = "primarySqlSessionTemplate")
    public SqlSessionTemplate primarySqlSessionTemplate(
            @Qualifier("primarySqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}