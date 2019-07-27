package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.constant.RedisConstant;
import com.itheima.health.dao.SetmealDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.Setmeal;
import com.itheima.health.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

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
@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    SetmealDao setmealDao;

    @Autowired
    JedisPool jedisPool;

    //新增套餐
    @Override
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        // 1：先保存套餐，往t_setmeal表中 插入数据，返回setmeal_id
        setmealDao.add(setmeal);
        // 2：使用setmealid和checkgroupids数组，向t_setmeal_checkgroup表中插入多条数据
        setSetmealAndCheckGroup(setmeal.getId(),checkgroupIds);
        // 3：如果新增保存成功，将图片名称存到redis中
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,setmeal.getImg());

        //新增套餐，需要删除原来页面缓存的套餐
        jedisPool.getResource().del("setmealList".getBytes());
    }

    // 分页查询套餐列表
    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage,pageSize);
        Page<Setmeal> page = setmealDao.findPage(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public List<Setmeal> findAll() {
        return setmealDao.findAll();
    }

    @Override
    public Setmeal findById(Integer id) {
        return setmealDao.findById(id);
    }

    // 使用setmealid和checkgroupids数组，向t_setmeal_checkgroup表中插入多条数据
    private void setSetmealAndCheckGroup(Integer setmeal_id, Integer[] checkgroupIds) {
        if(checkgroupIds!=null && checkgroupIds.length>0){
            for (Integer checkgroup_id : checkgroupIds) {
                Map<String,Object> map = new HashMap<String,Object>();
                map.put("setmeal_id",setmeal_id);
                map.put("checkgroup_id",checkgroup_id);
                setmealDao.setSetmealAndCheckGroup(map);
                //修改套餐，需要删除原来页面缓存的套餐
                jedisPool.getResource().del("setmealList".getBytes());
            }
        }
    }
}
