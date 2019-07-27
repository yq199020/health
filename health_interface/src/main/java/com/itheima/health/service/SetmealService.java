package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.Setmeal;

import java.util.List;

public interface SetmealService {

    void add(Setmeal setmeal, Integer[] checkgroupIds);

    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

    List<Setmeal> findAll();

    Setmeal findById(Integer id);
}
