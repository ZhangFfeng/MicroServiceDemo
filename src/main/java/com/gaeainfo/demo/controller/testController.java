package com.gaeainfo.demo.controller;

import com.gaeainfo.demo.dto.response.BaseResponseDTO;
import com.gaeainfo.demo.pojo.UsersDO;
import com.gaeainfo.demo.service.OrganzationService;
import com.gaeainfo.demo.dto.request.BaseRequestDTO;
import com.gaeainfo.demo.service.TestService;
import com.gaeainfo.demo.util.RedisLock;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * 测试接口.
 *
 * @author 张丰.
 * @version v1.0
 */
@SuppressWarnings("ALL")
@RestController
public class testController {

    private static final Logger LOGGER = LoggerFactory.getLogger(testController.class);

    @Autowired
    private TestService testService;


    @Autowired
    private OrganzationService organzationService;

    @Autowired
    private RedisLock redisLock;

    /**
     * 多数据源操作测试
     *
     * @return
     */
    @ApiOperation(value = "右侧标题测试", notes = "notes多数据源测试")
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public BaseResponseDTO test() {
        BaseResponseDTO baseResponseDTO = new BaseResponseDTO();

        try {
            /*测试数据*/
            testService.test();
        } catch (Exception e) {
            LOGGER.error("右侧标题测试异常{}", e.getMessage());
            e.printStackTrace();
        }
        baseResponseDTO.setData("SUCCESS");
        return baseResponseDTO;
    }

    /**
     * 更新数据
     */
    @ApiOperation(value = "更新测试", notes = "notes更新测试")
    @RequestMapping(value = "/testUpdate", method = RequestMethod.GET)
    public void testUpdate() {
        testService.testUpdate();
    }

    /**
     * 删除数据
     */
    @ApiOperation(value = "删除", notes = "notes删除测试")
    @RequestMapping(value = "/testDelete", method = RequestMethod.GET)
    public void testDelete() {
        testService.testDelete();
    }


    /**
     * redis缓存测试
     *
     * @param id
     */
    @ApiOperation(value = "查询测试redis", notes = "redis缓存测试")
    @RequestMapping(value = "/testRedis", method = RequestMethod.GET)
    public void testRedis(@RequestParam Integer id) {
        UsersDO user = testService.testRedis(id);
        System.out.println(user.toString());
    }

    /**
     * 测试hystrix
     */
    @ApiOperation(value = "断路器测试", notes = "断路器测试")
    @RequestMapping(value = "/testHystrix", method = RequestMethod.GET)
    public void testHystrix() {
        Long l1 = System.currentTimeMillis();
        testService.testHystrix();
        LOGGER.info("断路器测试耗时{}ms", System.currentTimeMillis() - l1);
    }

    /**
     * 测试hystrix父子线程隔离模式传值
     */
    @ApiOperation(value = "舱壁模式 父子线程", notes = "舱壁模式")
    @RequestMapping(value = "/testHystrix2", method = RequestMethod.GET)
    public void testHystrix2() {
        Long l1 = System.currentTimeMillis();
        testService.testHystrix("1");
        LOGGER.info("舱壁模式 父子线程{}ms", System.currentTimeMillis() - l1);
    }

    /**
     * 测试feign客户端调用
     */
    @ApiOperation(value = "feign客户端调用", notes = "feign客户端调用")
    @RequestMapping(value = "/testFeign", method = RequestMethod.GET)
    public void testFeign() {
        Long l1 = System.currentTimeMillis();
        try {

            String result = organzationService.sayHello("mutiDatasource");
            System.out.println(result);

        } catch (Exception e) {
            e.printStackTrace();
        }
        LOGGER.info(" testFeign{}ms", System.currentTimeMillis() - l1);
    }

    /**
     * 测试redis并发锁
     *
     * @param baseRequestDTO 接收类
     * @return
     */
    @ApiOperation(value = "redislock", notes = "redislock")
    @RequestMapping(value = "/redislock")
    public BaseResponseDTO<UsersDO> redislock(BaseRequestDTO<UsersDO> baseRequestDTO) {

        boolean q = redisLock.tryLock("lockKey", "123456789", 30, TimeUnit.SECONDS);
        String b = redisLock.get("lockKey");
        redisLock.releaseLock("lockKey", b);
        return null;
    }


}
