package com.itheima.health.service;


import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.Permission;

import java.util.List;

public interface PermissionService {



    PageResult findPermissions(Integer currentPage, Integer pageSize, String queryString);

    void add(Permission permission);

    void deleteById(Integer id);

    Permission findById(Integer id);

    void edit(Permission permission);
}
