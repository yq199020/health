package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Permission;
import com.itheima.health.service.PermissionService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/manage")
public class PermissionManageController {

    @Reference
    PermissionService permissionService;

    @RequestMapping("/findPermission")
    public PageResult findPermission(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = permissionService.findPermissions(queryPageBean.getCurrentPage(),queryPageBean.getPageSize(),queryPageBean.getQueryString());
        return pageResult;
    }

    @RequestMapping("/add")
    public Result add(@RequestBody Permission permission){
        try {
            permissionService.add(permission);
            return new Result(true, MessageConstant.ADD_PERMISSION_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_PERMISSION_FAIL);
        }
    }

    @RequestMapping("/delete")
    public Result delete(Integer id){
        try {
            permissionService.deleteById(id);
            return new Result(true,MessageConstant.DELETE_PERMISSION_SUCCESS);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new Result(false,e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_PERMISSION_FAIL);
        }
    }

    @RequestMapping("/findById")
    public Result findById(Integer id){
        try {
            Permission permission = permissionService.findById(id);
            return new Result(true,MessageConstant.QUERY_PERMISSION_SUCCESS,permission);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_PERMISSION_FAIL);
        }
    }

    @RequestMapping("/edit")
    public Result edit(@RequestBody Permission permission){
        try {
            permissionService.edit(permission);
            return new Result(true,MessageConstant.EDIT_PERMISSION_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,MessageConstant.EDIT_PERMISSION_FAIL);
        }
    }
}
