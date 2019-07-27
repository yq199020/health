package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.dao.MenuDao;
import com.itheima.health.dao.UserDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.Menu;
import com.itheima.health.pojo.Role;
import com.itheima.health.pojo.User;
import com.itheima.health.service.MenuService;
import com.itheima.health.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName CheckItemServiceImpl
 * @Description TODO
 * @Author ly
 * @Company 深圳黑马程序员
 * @Date 2019/6/26 15:44
 * @Version V1.0
 */
@Service(interfaceClass = MenuService.class)
@Transactional
public class MenuServiceImpl implements MenuService {

    @Autowired
    MenuDao menuDao;

    @Autowired
    UserDao userDao;


    @Override
    public List<Menu> getmenu(String username) {
        List<Menu> menuList = new ArrayList<>();
        List<Menu> menuList4 = new ArrayList<>();
        List<Integer> listId = new ArrayList<>();
        List<Integer> listIdF = new ArrayList<>();
        User user = userDao.findUserByUsername(username);
        Set<Role> roles = user.getRoles();
        for (Role role : roles) {
            Integer id = role.getId();
            menuList= menuDao.findzMenubById(id);
        }
        for (Menu menu : menuList) {
            if (menu.getParentMenuId()!=null) {
                //子菜单id
                Integer id1 = menu.getId();
                listId.add(id1);
            }
            if (menu.getParentMenuId()==null) {
                //父菜单id
                Integer id = menu.getId();
                listIdF.add(id);
            }
        }
        //子菜单
        List<Menu> menuList1 = new ArrayList<>();
        List<Menu> menuList2 = new ArrayList<>();
        for (Integer id : listId) {
            Menu menu = menuDao.findfMenuId(id);
            menuList1.add(menu);
        }

        for (Integer id : listIdF) {
            Menu menu2 = menuDao.findfMenuId(id);
            menuList2.add(menu2);
        }
        for (Menu menu : menuList2) {
            Integer id = menu.getId();
            List<Menu> menuList3 = new ArrayList<>();
            for (Menu menu1 : menuList1) {
                Integer parentMenuId = menu1.getParentMenuId();
                if (id.equals(parentMenuId)) {
                    menuList3.add(menu1);
                }
            }
            menu.setChildren(menuList3);
            menuList4.add(menu);
        }
        System.out.println(menuList4);
        return menuList4;
    }

    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage, pageSize);
        Page<Menu> page = menuDao.findPage(queryString);
        return new PageResult(page.getTotal(),page.getResult());

    }

    @Override
    public List<Menu> findPrentMenuName() {
        return menuDao.findPrentMenuName();
    }

    @Override
    public void add(Menu menu) {
        menuDao.add(menu);
    }

    @Override
    public void deleteById(Integer id) {
        Integer count = menuDao.findRoleMenuById(id);

        if (count>0) {
            throw new RuntimeException(MessageConstant.DELETE_MENU_RELATION);
        }

        menuDao.deleteById(id);
    }

    @Override
    public Menu findById(Integer id) {
        return menuDao.findById(id);
    }

    @Override
    public void edit(Menu menu) {
        menuDao.edit(menu);
    }


}
