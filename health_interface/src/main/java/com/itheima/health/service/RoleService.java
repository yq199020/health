package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.Menu;
import com.itheima.health.pojo.Role;

import java.util.List;
import java.util.Map;

public interface RoleService {


    Map<String, Object>  findPermissionAndMenu();


    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

    Role findById(Integer id);

    void deleteById(Integer id);

    void add(Role role, Integer[] menuIds, Integer[] permissionIds);

    Map<String, Object> findMenuIdsAndPermissionIdsByRoleIds(Integer id);

    void edit(Role role, Integer[] menuIds, Integer[] permissionIds);

    List<Role> findAll();
}
