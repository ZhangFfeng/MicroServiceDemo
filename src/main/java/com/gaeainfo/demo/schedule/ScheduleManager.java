package com.gaeainfo.demo.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 定时器
 *
 * @author 张丰
 * @version v1.0
 */
@SuppressWarnings("ALL")
@Configuration
@EnableScheduling
@EnableAsync
public class ScheduleManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleManager.class);
    private final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 测试
     */
    @Scheduled(cron = "0/1 * * * * ?") // 每10分钟执行一次
    public void test1() {
        i++;
        LOGGER.info("test1==========,i=={}", i);

    }

    private static int i = 0;

    /**
     * 测试
     */
    @Async/*异步调用*/
    @Scheduled(cron = "0/10 * * * * ?") /* 每10分钟执行一次*/
    public void test2() {
        try {
            TimeUnit.SECONDS.sleep(30);
            i++;
            LOGGER.info("test2**********,i=={}", i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }


}
