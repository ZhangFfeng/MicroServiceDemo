package com.gaeainfo.demo.service;

/**
 * 普通接口实现类
 *
 * @author 张丰
 * @version v1.0
 */
@SuppressWarnings("ALL")
public class OrganzationServiceImpl implements OrganzationService {
    /**
     * @param name
     * @return
     */
    @Override
    public String sayHello(String name) {
        System.out.println("say hello!");
        return null;
    }

    @Override
    public String saySorry(String name) {
        System.out.println("say sorry!");
        return null;
    }
}
