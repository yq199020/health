package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.dao.MemberDao;
import com.itheima.health.dao.OrderDao;
import com.itheima.health.dao.OrderSettingDao;
import com.itheima.health.dao.PermissionDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.*;
import com.itheima.health.service.OrderService;
import com.itheima.health.service.PermissionService;
import com.itheima.health.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName CheckItemServiceImpl
 * @Description TODO
 * @Author ly
 * @Company 深圳黑马程序员
 * @Date 2019/6/26 15:44
 * @Version V1.0
 */
@Service(interfaceClass = PermissionService.class)
@Transactional
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    PermissionDao permissionDao;


    @Override
    public PageResult findPermissions(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage,pageSize);
        Page<Permission> page = permissionDao.findPermissions(queryString);
        PageResult pageResult = new PageResult(page.getTotal(), page.getResult());
        return pageResult;
    }

    @Override
    public void add(Permission permission) {
        permissionDao.add(permission);
    }

    @Override
    public void deleteById(Integer id) {
        //查询这个权限有没有被管理
        Integer count = permissionDao.checkRoleByPermissionId(id);

        if (count>0) {
            throw new RuntimeException(MessageConstant.DELETE_PERMISSION_RELATION);
        }

        permissionDao.deleteById(id);
    }

    @Override
    public Permission findById(Integer id) {
        return permissionDao.findById(id);
    }

    @Override
    public void edit(Permission permission) {
        permissionDao.edit(permission);
    }
}
