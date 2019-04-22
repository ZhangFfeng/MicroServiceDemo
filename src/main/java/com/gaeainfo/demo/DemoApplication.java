package com.gaeainfo.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 启动类入口
 *
 * @author 张丰
 * @version v1.0
 */

@SuppressWarnings("ALL")
@SpringBootApplication
@EnableCaching/*启动缓存*/
@EnableEurekaClient/*将服务实例注册至注册服务中心*/
@EnableCircuitBreaker/*告诉springcloud将要为服务使用hystrix*/
@EnableFeignClients/*服务发现客户端查找服务负载均衡*/
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }


}
