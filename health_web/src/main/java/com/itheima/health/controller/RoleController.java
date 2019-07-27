package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Menu;
import com.itheima.health.pojo.Role;
import com.itheima.health.service.MenuService;
import com.itheima.health.service.RoleService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName CheckItemController
 * @Description TODO
 * @Author ly
 * @Company 深圳黑马程序员
 * @Date 2019/6/26 15:45
 * @Version V1.0
 */
@RestController
@RequestMapping(value = "/role")
public class RoleController {

    @Reference
    RoleService roleService;

    @RequestMapping("/findPermissionAndMenu")
    public Result findPermissionAndMenu(){
        try {
            Map<String, Object> map = roleService.findPermissionAndMenu();
            return new Result(true, MessageConstant.QUERY_PERMISSIONANDMENU_FAIL, map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_PERMISSIONANDMENU_FAIL);
        }
    }

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult pageResult = roleService.findPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize(), queryPageBean.getQueryString());
        return pageResult;
    }

    @RequestMapping("/findById")
    public Result findById(Integer id){
        try {
            Role role = roleService.findById(id);
            return new Result(true,MessageConstant.QUERY_PERMISSION_SUCCESS,role);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_PERMISSION_FAIL);
        }
    }

    @RequestMapping("/delete")
    public Result delete(Integer id){
        try {
            roleService.deleteById(id);
            return new Result(true,MessageConstant.DELETE_ROLE_SUCCESS);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new Result(false,e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_ROLE_FAIL);
        }
    }

    @RequestMapping("/add")
    public Result add(@RequestBody Role role,Integer[] menuIds,Integer[] permissionIds){
        try {
            roleService.add(role,menuIds,permissionIds);
            return new Result(true,MessageConstant.ADD_ROLE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_ROLE_FAIL);
        }
    }

    @RequestMapping("/findMenuIdsAndPermissionIdsByRoleIds")
    public Result findMenuIdsAndPermissionIdsByRoleIds(Integer id){
        try {
            Map<String, Object> map = roleService.findMenuIdsAndPermissionIdsByRoleIds(id);
            return new Result(true, MessageConstant.QUERY_PERMISSIONANDMENU_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_PERMISSIONANDMENU_FAIL);

        }

    }

    @RequestMapping("/edit")
    public Result edit(@RequestBody Role role,Integer[] menuIds,Integer[] permissionIds) {
        try {
            roleService.edit(role,menuIds,permissionIds);
            return new Result(true,MessageConstant.EDIT_ROLE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_ROLE_FAIL);
        }
    }


    // 查询所有
    @RequestMapping(value = "/findAll")
    public Result findAll(){
        try {
            List<Role> list = roleService.findAll();
            return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }
}

