package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.Role;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface RoleDao {

    // 使用用户id，查询用户id所具有的角色集合
    Set<Role> findRolesByUserId(Integer userId);

    Page<Role> findPageByString(String queryString);

    Role findById(Integer id);

    void deleteById(Integer id);

    void add(Role role);

    void editRole(Role role);

    List<Role> findAll();
//    使用登录名查询用户
}
