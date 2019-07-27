package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.dao.UserDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.User;
import com.itheima.health.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

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
@Service(interfaceClass = UserService.class)
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    // 使用登录用户名查询唯一的用户信息
    @Override
    public User findUserByUsername(String username) {
        return userDao.findUserByUsername(username);
    }

    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        /**使用Mybatis的分页插件PageHelper*/
        PageHelper.startPage(currentPage, pageSize); // 底层用mysql计算分页
        Page<User> page = userDao.findPage(queryString);
        return new PageResult(page.getTotal(), page.getResult());
    }

    // 新增用户
    @Override
    public void add(User user, Integer[] roleIds) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encode);

        // 1：保存用户
        userDao.add(user);
        // 2：向用户和角色的中间表保存数据
        setRoleAndUser(user.getId(), roleIds);
    }


    @Override
    public User findById(Integer id) {
        return userDao.findById(id);
    }

    @Override
    public List<Integer> findRoleIdsByUserId(Integer id) {
        return userDao.findRoleIdsByUserId(id);
    }

    @Override
    public void deleteById(Integer id) {
        // 1：使用检查项id，查询t_user_role，判断是否存在数据，如果存在数据，则不能删除
        long count = userDao.findCountByUserId(id);
        // 有数据
        if (count > 0) {
            throw new RuntimeException(MessageConstant.DELETE_USER_RELATION);
        }
        // 2：删除检查项
        userDao.deleteById(id);
    }

    @Override
    public void edit(User user, Integer[] roleIds) {
        // 1：更新用户的信息（update）
        userDao.editUser(user);
        // 2：更新用户和角色的中间表数据（中间表）
        //（1）先使用用户的id，删除之前的数据
        userDao.deleteUserAndRoleByUserId(user.getId());
        //（2）再使用检查组的id，和检查项id，新增数据
        setRoleAndUser(user.getId(),roleIds);

    }

    // 向用户和角色的中间表保存数据（Map指定参数）
    private void setRoleAndUser(Integer userid, Integer[] roleIds) {
        if (roleIds != null && roleIds.length > 0) {
            for (Integer roleId : roleIds) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("user_id", userid);
                map.put("role_id", roleId);
                userDao.addRoleAndUser(map);
            }
        }
    }
}
