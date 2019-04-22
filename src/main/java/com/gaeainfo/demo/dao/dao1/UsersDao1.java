package com.gaeainfo.demo.dao.dao1;

import com.gaeainfo.demo.mapper.mapper1.UsersMapper1;
import com.gaeainfo.demo.pojo.UsersDO;

import com.gaeainfo.demo.pojo.UsersDO2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * dao层 实现类
 *
 * @author 张丰
 * @version v1.0
 */
@SuppressWarnings("ALL")
@Component
public class UsersDao1 {
    @Autowired
    private UsersMapper1 usersMapper1;

    /**
     * 查询所有用户
     *
     * @return 所有用户 list集合
     */
    public List<UsersDO> findAll() {
        return usersMapper1.findAll();
    }

    /**
     * 添加一个用户
     *
     * @param usersDO
     */
    public void addOne(UsersDO usersDO) {
        usersMapper1.addOne(usersDO);
    }

    /**
     * 添加一个用户
     *
     * @param usersDO
     */
    public void addOne2(UsersDO2 usersDO) {
        usersMapper1.addOne2(usersDO);
    }

    /**
     * 更新用户信息
     *
     * @param usersDO
     */
    public void updateOne(UsersDO usersDO) {
        usersMapper1.updateOne(usersDO);
    }

    /**
     * 删除用户
     *
     * @param id
     */
    public void testDelete(Integer id) {
        usersMapper1.deleteOne(id);
    }

    /**
     * 查询用户
     *
     * @param id
     * @return 用户具体信息
     */
    public UsersDO findById(Integer id) {
        System.out.println("dao---");
        return usersMapper1.findOne(id);

    }

}
