package com.gaeainfo.demo.config;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.mysql.cj.jdbc.MysqlXADataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * 2号数据源配置文件
 *
 * @Author: 张丰
 * @Version 1.0
 */
@SuppressWarnings("ALL")
@Configuration
@MapperScan(basePackages = "com.gaeainfo.demo.mapper.mapper2", sqlSessionTemplateRef = "secondarySqlSessionTemplate")
public class secondaryMyBatisConfig {
    /**
     * 配置2号数据源
     *
     * @param primaryDataSourceProperties 2号数据源配置类
     * @return 数据源
     * @throws SQLException
     */
    @Bean(name = "secondaryDataSource")
    public DataSource secondaryDataSource(SecondaryDataSourceProperties secondaryDataSourceProperties) throws SQLException {
        MysqlXADataSource mysqlXADataSource = new MysqlXADataSource();
        mysqlXADataSource.setURL(secondaryDataSourceProperties.getUrl());
        mysqlXADataSource.setPinGlobalTxToPhysicalConnection(true);
        mysqlXADataSource.setUser(secondaryDataSourceProperties.getUserName());
        mysqlXADataSource.setPassword(secondaryDataSourceProperties.getPassword());
        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaDataSource(mysqlXADataSource);
        xaDataSource.setUniqueResourceName("secondaryDataSource");
        xaDataSource.setTestQuery(secondaryDataSourceProperties.getTestQuery());
        xaDataSource.setMinPoolSize(secondaryDataSourceProperties.getMinPoolSize());
        xaDataSource.setMaxPoolSize(secondaryDataSourceProperties.getMaxPoolSize());
        xaDataSource.setMaxLifetime(secondaryDataSourceProperties.getMaxLifetime());
        xaDataSource.setBorrowConnectionTimeout(secondaryDataSourceProperties.getBorrowConnectionTimeout());
        xaDataSource.setLoginTimeout(secondaryDataSourceProperties.getLoginTimeout());
        xaDataSource.setMaintenanceInterval(secondaryDataSourceProperties.getMaintenanceInterval());
        xaDataSource.setMaxIdleTime(secondaryDataSourceProperties.getMaxIdleTime());
        return xaDataSource;
    }

    /**
     * 创建SqlSession实例的工厂
     *
     * @param dataSource 数据源
     * @return
     * @throws Exception
     */
    @Bean(name = "secondarySqlSessionFactory")
    public SqlSessionFactory secondarySqlSessionFactory(@Qualifier("secondaryDataSource") DataSource dataSource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper2/*.xml"));
        return bean.getObject();
    }

    /**
     * jdbc访问模板化的工具---普通jdbc访问数据库模板工具
     *
     * @param dataSource
     * @return 模板
     */
    @Bean(name = "secondaryJdbcTemplate")
    public JdbcTemplate secondaryJdbcTemplate(@Qualifier("secondaryDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    /**
     * 持久层访问模板化的工具 --替代默认的MyBatis实现的DefaultSqlSession
     *
     * @param sqlSessionFactory 实例工厂
     * @return 模板
     * @throws Exception
     */
    @Bean(name = "secondarySqlSessionTemplate")
    public SqlSessionTemplate secondarySqlSessionTemplate(
            @Qualifier("secondarySqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}