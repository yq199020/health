package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.constant.RedisConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Setmeal;
import com.itheima.health.service.SetmealService;
import com.itheima.health.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.util.UUID;

/**
 * @ClassName CheckItemController
 * @Description TODO
 * @Author ly
 * @Company 深圳黑马程序员
 * @Date 2019/6/26 15:45
 * @Version V1.0
 */
@RestController
@RequestMapping(value = "/setmeal")
public class SetmealController {

    @Autowired
    JedisPool jedisPool;

    @Reference
    SetmealService setmealService;

    // 在新增套餐页面中，完成图片上传（上传到七牛云）
    @RequestMapping(value = "/upload")
    public Result upload(@RequestParam(value = "imgFile") MultipartFile imgFile){
        try {
            // 获取文件名(01.jpg)
            String name = imgFile.getOriginalFilename();
            // 获取文件的后缀
            String perfix = name.substring(name.lastIndexOf("."));
            // ADFASDFSDF_DAFDSADFSA_DSSADFSDF.jpg
            String fileName = UUID.randomUUID().toString().toUpperCase()+perfix;
            // 上传七牛云的时候，封装了QiniuUtils
            QiniuUtils.upload2Qiniu(imgFile.getBytes(),fileName);

            // 将数据存放到redis
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES,fileName);

            // 返回结果
            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS,fileName);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
    }

    // 保存套餐功能
    @RequestMapping(value = "/add")
    public Result add(@RequestBody Setmeal setmeal,Integer [] checkgroupIds){
        try {
            setmealService.add(setmeal,checkgroupIds);
            // 返回结果
            return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_SETMEAL_FAIL);
        }
    }

    // 分页查询套餐列表
    @RequestMapping(value = "/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = setmealService.findPage(queryPageBean.getCurrentPage(),
                queryPageBean.getPageSize(),
                queryPageBean.getQueryString());
        // 返回结果
        return pageResult;

    }

}
