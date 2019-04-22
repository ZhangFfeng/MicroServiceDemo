package com.gaeainfo.demo.config;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;

/**
 * jta事务管理配置
 * MyBatis自动参与到spring事务管理中，无需额外配置，
 * 只要org.mybatis.spring.SqlSessionFactoryBean
 * 引用的数据源与DataSourceTransactionManager引用的数据源一致即可，否则事务管理会不起作用。
 *
 * @author 张丰
 * @version v1.0
 */

/**
 * 自定义事务
 */
@SuppressWarnings("ALL")
@Configuration
@ComponentScan
@EnableTransactionManagement
public class TransactionManagerConfig {

    /**
     * 事务管理器
     *
     * @return
     * @throws Throwable
     */
    @Bean(name = "userTransaction")
    public UserTransaction userTransaction() throws Throwable {
        UserTransactionImp userTransactionImp = new UserTransactionImp();
        userTransactionImp.setTransactionTimeout(3);//3秒
        return userTransactionImp;
    }

    /**
     * 事务管理器
     *
     * @return
     * @throws Throwable
     */
    @Bean(name = "atomikosTransactionManager", initMethod = "init", destroyMethod = "close")
    public TransactionManager atomikosTransactionManager() throws Throwable {
        UserTransactionManager userTransactionManager = new UserTransactionManager();
        // when close is called, should we force transactions to terminate or not?
        userTransactionManager.setForceShutdown(false);
        return userTransactionManager;
    }

    /**
     * 事务调用接口
     *
     * @return
     * @throws Throwable
     */
    @Bean(name = "transactionManager")
    @DependsOn({"userTransaction", "atomikosTransactionManager"})
    public PlatformTransactionManager transactionManager() throws Throwable {
        UserTransaction userTransaction = userTransaction();
        JtaTransactionManager manager = new JtaTransactionManager(userTransaction, atomikosTransactionManager());
        return manager;
    }
}