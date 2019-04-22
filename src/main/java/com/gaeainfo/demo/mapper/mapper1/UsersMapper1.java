package com.gaeainfo.demo.mapper.mapper1;

import com.gaeainfo.demo.pojo.UsersDO;
import com.gaeainfo.demo.pojo.UsersDO2;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 张丰
 * @version v1.0
 */
@SuppressWarnings("ALL")
@Service
@Mapper
public interface UsersMapper1 {
    /**
     * 查询所有用户
     * @return 所有用户 list集合
     */
    List<UsersDO> findAll();

    /**
     * 添加一个用户
     * @param usersDO
     */
    void addOne(UsersDO usersDO);

    /**
     * 添加一个用户
     * @param usersDO
     */
    void addOne2(UsersDO2 usersDO);

    /**
     * 更新用户信息
     * @param usersDO
     */
    void updateOne(UsersDO usersDO);

    /**
     * 删除用户
     * @param id
     */
    void deleteOne(Integer id);

    /**
     * 查询用户
     * @param id
     * @return 用户具体信息
     */
    UsersDO findOne(Integer id);

}