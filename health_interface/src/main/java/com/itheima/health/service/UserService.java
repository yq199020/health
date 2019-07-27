package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.User;

import java.util.List;

public interface UserService {

    User findUserByUsername(String username);

    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

    void add(User user, Integer[] userIds);

    User findById(Integer id);

    List<Integer> findRoleIdsByUserId(Integer id);

    void deleteById(Integer id);

    void edit(User user, Integer[] roleIds);
}
