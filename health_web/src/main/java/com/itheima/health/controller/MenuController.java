package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Menu;
import com.itheima.health.service.MenuService;
import com.itheima.health.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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
@RequestMapping(value = "/menu")
public class MenuController {

    @Reference
    MenuService menuService;

    @RequestMapping(value = "/getmenu")
    public Result getmenu(String username){
        try {
            List<Menu> menuList = menuService.getmenu(username);


            System.out.println(menuList);
            return new Result(true, MessageConstant.GET_USERNAME_SUCCESS, menuList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_USERNAME_FAIL);
        }
    }

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult pageResult = menuService.findPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize(),queryPageBean.getQueryString());
        return pageResult;
    }

    @RequestMapping("/add")
    public Result add(@RequestBody Menu menu){
        try {
            menuService.add(menu);
            return new Result(true, MessageConstant.ADD_MENU_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_MENU_FAIL);
        }
    }

    @RequestMapping("/delete")
    public Result delete(Integer id) {
        try {
            menuService.deleteById(id);
            return new Result(true,MessageConstant.DELETE_MENU_SUCCESS);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new Result(false,e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_MENU_FAIL);
        }
    }

    @RequestMapping("/findById")
    public Result findById(Integer id){
        try {
            Menu menu = menuService.findById(id);
            return new Result(true, MessageConstant.QUERY_MENU_SUCCESS,menu);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_MENU_FAIL);
        }
    }

    @RequestMapping("/edit")
    public Result edit(@RequestBody Menu menu) {
        try {
            menuService.edit(menu);
            return new Result(true, MessageConstant.EDIT_MENU_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_MENU_FAIL);
        }
    }

}
