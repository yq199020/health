package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Setmeal;
import com.itheima.health.service.SetmealService;
import com.itheima.health.utils.SerializeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.List;

/**
 * @ClassName SetmealMobileController
 * @Description TODO
 * @Author ly
 * @Company 深圳黑马程序员
 * @Date 2019/7/2 15:57
 * @Version V1.0
 */
@RestController
@RequestMapping(value = "/setmeal")
public class SetmealMobileController {
    @Reference
    SetmealService setmealService;

    @Autowired
    private JedisPool jedisPool;

    @RequestMapping(value = "/getSetmeal")
    public Result findAll(){
        try {
            byte[] bytes = jedisPool.getResource().get("setmealList".getBytes());
            List<Setmeal> setmealList = SerializeUtil.unserializeForList(bytes);
            if (setmealList==null){
                setmealList = setmealService.findAll();
                System.out.println("套餐数据来自数据库");
                jedisPool.getResource().set("setmealList".getBytes(),SerializeUtil.serialize(setmealList));
                return new Result(true, MessageConstant.GET_SETMEAL_LIST_SUCCESS,setmealList);
            }
            jedisPool.getResource().expire("setmealList".getBytes(),60*1);
            System.out.println("套餐数据来自redis");
            return new Result(true, MessageConstant.GET_SETMEAL_LIST_SUCCESS,setmealList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_SETMEAL_LIST_FAIL);
        }
    }
    @RequestMapping(value = "/findById")
    public Result findById(Integer id){
        // 使用套餐id的主键，查询套餐id的信息
        try {
            String setmealId = id+"";
            //1.先查询缓存是否有数据，如果没有，则查询数据库
            byte[] bytes = jedisPool.getResource().get(setmealId.getBytes());
            Setmeal setmeal = (Setmeal) SerializeUtil.unserialize(bytes);
            if (setmeal == null){
                setmeal = setmealService.findById(id);
                System.out.println("查询的数据库");
                //将从数据库中查找到的数据列表存入redis
                jedisPool.getResource().set(setmealId.getBytes(), SerializeUtil.serialize(setmeal));
                return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
            }
            jedisPool.getResource().expire(setmealId.getBytes(),60*3);
            System.out.println("查询的redis");
            return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }
}
