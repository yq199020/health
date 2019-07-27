package com.itheima.health.dao;


import com.github.pagehelper.Page;
import com.itheima.health.pojo.Menu;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;


@Repository
public interface MenuDao {
    List<Menu> findzMenubById(Integer id);

    Menu findfMenuId(Integer id);

    LinkedHashSet<Menu> findMenuList();

    Integer checkMenuByRoleId(Integer id);

    List<Integer> findMenuIdByRoleId(Integer id);

    void addRoleAndMenu(Map<String, Object> map);

    void deleteMenuById(Integer id);

    List<Menu> findPrentMenuName();

    Page<Menu> findPage(String queryString);

    Integer findRoleMenuById(Integer id);

    void deleteById(Integer id);

    void add(Menu menu);

    Menu findById(Integer id);

    void edit(Menu menu);
}

