package com.itheima.health.dao;


import com.github.pagehelper.Page;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.pojo.Permission;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;


@Repository
public interface PermissionDao {

    Set<Permission> findPermissionSetByRoleId(Integer id);


    Page<Permission> findPermissions(String queryString);

    void add(Permission permission);

    Integer checkRoleByPermissionId(Integer id);

    void deleteById(Integer id);

    Permission findById(Integer id);

    void edit(Permission permission);

    List<Permission> findPermissionList();

    Set<Permission> findRolePermissionByRoleId(Integer id);

    Integer checkPermissionByRoleId(Integer id);

    void addRoleAndPermission(Map<String, Object> map);

    List<Integer> findPermissionIdByRoleId(Integer id);

    void deletePermissionById(Integer id);
}
