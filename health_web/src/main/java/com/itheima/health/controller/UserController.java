package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName CheckItemController
 * @Description TODO
 * @Author ly
 * @Company 深圳黑马程序员
 * @Date 2019/6/26 15:45
 * @Version V1.0
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Reference
    UserService userService;

    // 获取当前登录用户的用户信息（SpringSecurity）
    @RequestMapping(value = "/getUsername")
    public Result upload(){
        try {
            // 不使用SpringSecurity，从Session中获取（登录成功需要放置到Session）
            // 使用SpringSecurity（UserDetails user = new User(username,password,list); ）
            User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();//Principal() == User()
            return new Result(true, MessageConstant.GET_USERNAME_SUCCESS,user.getUsername());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_USERNAME_FAIL);
        }
    }

    // 分页查询检查项（条件）
    @RequestMapping(value = "/findPage")
    //@PreAuthorize(value = "hasAuthority('CHECKITEM_QUERY')")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = userService.findPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize(),queryPageBean.getQueryString());
        return pageResult;
    }

    // 新增用户
    @RequestMapping(value = "/add")
    public Result add(@RequestBody com.itheima.health.pojo.User user, Integer [] roleIds){
        try {
            userService.add(user,roleIds);
            return new Result(true, MessageConstant.ADD_USER_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_USER_FAIL);
        }
    }

    // 编辑检查项（表单回显）
    @RequestMapping(value = "/findById")
    public Result findById(Integer id){
        try {
            com.itheima.health.pojo.User user = userService.findById(id);
            return new Result(true,MessageConstant.QUERY_USER_SUCCESS,user);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_USER_FAIL);
        }
    }

    // 使用检查组id，查询检查项和检查组的中间表，让检查项信息中的checkbox进行回显
    @RequestMapping(value = "/findRoleIdsByUserId")
    public List<Integer> findRoleIdsByUserId(Integer id){
        List<Integer> list = userService.findRoleIdsByUserId(id);
        return list;
    }

    // 用户删除
    @RequestMapping(value = "/delete")
    public Result delete(Integer id){
        try {
            userService.deleteById(id);
            return new Result(true,MessageConstant.DELETE_USER_SUCCESS);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new Result(false,e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_USER_FAIL);
        }
    }

    @RequestMapping(value = "/edit")
    public Result edit(@RequestBody com.itheima.health.pojo.User user, Integer [] roleIds){
        try {
            userService.edit(user,roleIds);
            return new Result(true, MessageConstant.EDIT_USER_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_USER_FAIL);
        }
    }

}
