package com.gaeainfo.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 2号数据源属性
 *
 * @Author: 张丰
 * @Version 1.0
 */
@SuppressWarnings("ALL")
@Configuration
@ConfigurationProperties(prefix = "spring.datasource.test2")
public class SecondaryDataSourceProperties {
    // 数据库驱动类
    private String driverClassName;
    // 数据库jdbc 路径
    private String url;
    // 用户名
    private String userName;
    // 密码
    private String password;
    // 最小连接数
    private Integer minPoolSize;
    // 最大连接数
    private Integer maxPoolSize;
    // 连接最大存活时间
    private Integer maxLifetime;
    // 获取连接失败重新获等待最大时间，在这个时间内如果有可用连接，将返回
    private Integer borrowConnectionTimeout;
    // java数据库连接池，最大可等待获取datasouce的时间
    private Integer loginTimeout;
    // 连接回收时间
    private Integer maintenanceInterval;
    //  最大闲置时间，超过最小连接池连接的连接将将关闭
    private Integer maxIdleTime;
    // 测试sql
    private String testQuery;


    public Integer getMinPoolSize() {
        return minPoolSize;
    }

    public void setMinPoolSize(Integer minPoolSize) {
        this.minPoolSize = minPoolSize;
    }

    public Integer getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(Integer maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public Integer getMaxLifetime() {
        return maxLifetime;
    }

    public void setMaxLifetime(Integer maxLifetime) {
        this.maxLifetime = maxLifetime;
    }

    public Integer getBorrowConnectionTimeout() {
        return borrowConnectionTimeout;
    }

    public void setBorrowConnectionTimeout(Integer borrowConnectionTimeout) {
        this.borrowConnectionTimeout = borrowConnectionTimeout;
    }

    public Integer getLoginTimeout() {
        return loginTimeout;
    }

    public void setLoginTimeout(Integer loginTimeout) {
        this.loginTimeout = loginTimeout;
    }

    public Integer getMaintenanceInterval() {
        return maintenanceInterval;
    }

    public void setMaintenanceInterval(Integer maintenanceInterval) {
        this.maintenanceInterval = maintenanceInterval;
    }

    public Integer getMaxIdleTime() {
        return maxIdleTime;
    }

    public void setMaxIdleTime(Integer maxIdleTime) {
        this.maxIdleTime = maxIdleTime;
    }



    public String getTestQuery() {
        return testQuery;
    }

    public void setTestQuery(String testQuery) {
        this.testQuery = testQuery;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}