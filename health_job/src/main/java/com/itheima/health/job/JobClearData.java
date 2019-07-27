package com.itheima.health.job;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.service.OrderSettingService;

/**
 * @ClassName JobImg
 * @Description TODO
 * @Author ly
 * @Company 深圳黑马程序员
 * @Date 2019/6/29 18:23
 * @Version V1.0
 */
public class JobClearData {

    @Reference
    private OrderSettingService orderSettingService;

    /**
     *删除当月1号之前的数据，成功返回true，失败返回false
     */
    public void deleteOrderSetting() {
        boolean flag = false;//标识删除成功，成功返回true，失败返回false
        flag= orderSettingService.deleteOrderSetting();
        if(flag){
            System.out.println("SUCCESS ");
        }else {
            System.out.println("Fail");
        }

    }

}
