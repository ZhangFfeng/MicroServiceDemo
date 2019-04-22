package com.gaeainfo.demo.service;

import com.gaeainfo.demo.pojo.UsersDO;

@SuppressWarnings("ALL")
public interface TestService {
    public void test() ;

    public void testUpdate();

    public void testDelete();

    public UsersDO testEhcache(Integer id);

    public UsersDO testRedis(Integer id);

    public void testHystrix();

    public void testHystrix(String id);
}
