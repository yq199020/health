package com.itheima.health.service;


import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.Menu;

import java.util.List;

public interface MenuService {

    List<Menu> getmenu(String username);
    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

    List<Menu> findPrentMenuName();

    void add(Menu menu);

    void deleteById(Integer id);

    Menu findById(Integer id);

    void edit(Menu menu);

}
