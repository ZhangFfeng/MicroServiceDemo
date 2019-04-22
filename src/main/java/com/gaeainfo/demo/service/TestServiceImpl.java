package com.gaeainfo.demo.service;

import com.gaeainfo.demo.dao.dao1.UsersDao1;
import com.gaeainfo.demo.config.hystrix.UserContextHolder;
import com.gaeainfo.demo.pojo.UsersDO;
import com.gaeainfo.demo.pojo.UsersDO2;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.*;

@SuppressWarnings("ALL")
@Service
public class TestServiceImpl implements TestService {
    @Autowired
    private ApplicationContext context;

    private TestService testService;

    //本地调用缓存---通过代理
    @PostConstruct
    public void initContext() {
        this.testService = context.getBean(TestService.class);
    }


    @Autowired
    private UsersDao1 UsersDao1;

    @Autowired
    private com.gaeainfo.demo.dao.dao2.UsersDao2 UsersDao2;

    /**
     * @resource 、@autowired---一个是手动注入，一个是通过别名来寻找
     * @Resource(name = "primaryJdbcTemplate")
     * private JdbcTemplate primaryJdbcTemplate;
     * @Resource(name = "secondaryJdbcTemplate")
     * private JdbcTemplate secondaryJdbcTemplate;
     * @Resource(name = "redisTemplate1")
     * private RedisTemplate<String, Object> redisTemplate2;
     */

    @Autowired
    private JdbcTemplate primaryJdbcTemplate;

    @Autowired
    private JdbcTemplate secondaryJdbcTemplate;


    @Autowired
    private OrganzationService organzationService;

//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public void test() {
//        try {
//            List<Map<String, Object>> list1 = primaryJdbcTemplate.queryForList("select * from users");
//            List<Map<String, Object>> list2 = secondaryJdbcTemplate.queryForList("select * from users");
//            System.out.println(list1);
//            System.out.println(list2);
//            secondaryJdbcTemplate.execute("insert into users(id,age,name)  VALUES (2,2,2)");
//            System.out.println(100 / 0);
//            primaryJdbcTemplate.execute("insert into users(id,age,name)  VALUES (1,1,1)");
//            List<Map<String, Object>> list11 = primaryJdbcTemplate.queryForList("select * from users");
//            List<Map<String, Object>> list22 = secondaryJdbcTemplate.queryForList("select * from users");
//            System.out.println(list11.size());
//            System.out.println(list22.size());
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            // 手动回滚
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//        }
//
//
//    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void test() {
//        List<Users> list2 = UsersDao2.findAll();
//        List<Users> list1 = UsersDao1.findAll();
//        System.out.println(list1.size());
//        System.out.println(list2.size());
//        System.out.println("----------------------------------");
//
//        Users users = new Users();
//        users.setName("test" + new Date());
//        users.setId(2);
//        users.setAge(2);
//        UsersDao2.addOne(users);
////        System.out.println(100 / 0);
//
//        UsersDao1.addOne(users);
//        List<Users> list22 = UsersDao2.findAll();
//        List<Users> list11 = UsersDao1.findAll();
//        System.out.println(list11.size());
//        System.out.println(list22.size());
//        System.out.println("----------------------------------");
//        String result = organzationService.sayHello("mutiDatasource");


        UsersDO2 usersDO = new UsersDO2();
        usersDO.setName("李四");
        usersDO.setAge(25);
        usersDO.setLid(UUID.randomUUID().toString().replace("-", ""));
        System.out.println("pid=before=" + usersDO.getPid());
        UsersDao1.addOne2(usersDO);
        System.out.println("pid=after=" + usersDO.getPid());
    }

    @Override
    public void testUpdate() {
        UsersDO usersDO = new UsersDO();
        usersDO.setId(2);
        usersDO.setName("update 更新");
        UsersDao1.updateOne(usersDO);
    }

    @Override
    public void testDelete() {
        UsersDao1.testDelete(2);
    }

    @Override
    @Cacheable(value = "cacheTest", key = "#id")
    public UsersDO testEhcache(Integer id) {
        System.out.println("service--testehcache");
        UsersDO usersDO = UsersDao1.findById(id);
        System.out.println(usersDO.toString());
        return usersDO;
    }

    @Autowired
    private RedisTemplate redisTemplate;


    @Autowired
    private RedisTemplate<String, Object> redisTemplate1;

    @Override
    public UsersDO testRedis(Integer id) {

        redisTemplate1.opsForValue().set("users1", "你好");
        return new UsersDO();
    }

    /**
     * 几率睡眠
     */
    public void randomlyRunlog() {
        Random random = new Random();
        int randomNum = random.nextInt(3) + 1;
        if (randomNum == 3) {
            sleep();
        }

    }

    private void sleep() {
        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 断路器注解测试
    @HystrixCommand(commandProperties = {
            @HystrixProperty(
                    name = "execution.isolation.thread.timeoutInMilliseconds",
                    value = "2000")
    }, fallbackMethod = "buildFallbackMethod")
    @Override
    public void testHystrix() {
        System.out.println(100 / 0);
        System.out.println("test Hystrix");
    }

    // 舱壁模式处理测试~~单独线程获取不到父线程的thread中的值
    @HystrixCommand(fallbackMethod = "buildFallbackMethod2",
            threadPoolKey = "licenseByOrgThreadPool",
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "30"),
                    @HystrixProperty(name = "maxQueueSize", value = "10")
            })
    @Override
    public void testHystrix(String id) {
//        System.out.println(100 / 0);
        System.out.println("****service**" + UserContextHolder.getContext().getCorrelationId());
    }

    //后备处理方法必须与源方法参数保持一致--降级处理
    public void buildFallbackMethod() {
        System.out.println("buildFallbackMethods断路器---后备测试");
    }

    //后备处理方法必须与源方法参数保持一致--降级处理
    public void buildFallbackMethod2(String id) {
        System.out.println("buildFallbackMethods舱壁模式");
    }


}
