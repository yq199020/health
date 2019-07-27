package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserDao {

    // 使用登录名查询用户
    User findUserByUsername(String username);

    Integer checkUserByRoleId(Integer id);

    Page<User> findPage(String queryString);

    void add(User user);

    void addRoleAndUser(Map<String, Object> map);

    User findById(Integer id);

    List<Integer> findRoleIdsByUserId(Integer id);

    long findCountByUserId(Integer id);


    void editUser(User user);

    void deleteUserAndRoleByUserId(Integer id);

    void deleteById(Integer id);
}

