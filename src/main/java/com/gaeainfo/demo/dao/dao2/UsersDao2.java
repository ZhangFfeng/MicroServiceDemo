package com.gaeainfo.demo.dao.dao2;

import com.gaeainfo.demo.mapper.mapper2.UsersMapper2;
import com.gaeainfo.demo.pojo.UsersDO;
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
public class UsersDao2 {
    @Autowired
    private UsersMapper2 usersMapper2;

    /**
     * 查询所有用户
     *
     * @return 所有用户 list集合
     */
    public List<UsersDO> findAll() {

        return usersMapper2.findAll();
    }

    /**
     * 添加一名用户
     *
     * @param usersDO
     */
    public void addOne(UsersDO usersDO) {
        usersMapper2.addOne(usersDO);
    }
}
