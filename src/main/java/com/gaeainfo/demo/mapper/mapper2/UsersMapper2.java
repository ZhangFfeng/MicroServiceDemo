package com.gaeainfo.demo.mapper.mapper2;

import com.gaeainfo.demo.pojo.UsersDO;
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
public interface UsersMapper2 {
    /**
     * 查询所有用户
     * @return 所有用户 list集合
     */
    List<UsersDO> findAll();

    /**
     * 添加一名用户
     * @param usersDO
     */
    void addOne(UsersDO usersDO);


}