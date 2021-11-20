package com.imooc.config;

import com.imooc.service.OrderService;
import com.imooc.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static com.imooc.utils.DateUtil.DATETIME_PATTERN;

/**
 * @author a1073
 */
@Component
public class OrderJob {
    @Autowired
    private OrderService orderService;

    /**
     * 使用定时任务关闭超期未支付订单，会存在的弊端：
     * 1.会有时间差，程序不严谨
     *   10.39下单，11：00检查不足一小时，12:00检查超过多余1小时多余39分钟
     * 2.不支持集群
     *      单机没毛病，使用集群后，就会有多个定时任务
     * 3.会对数据库进行全表搜索，及其影响数据库性能  select * from order where
     *  定时任务，仅仅只适用于小型轻量级项目，传统项目
     *
     *  后续课程会涉及到消息队列: MQ-> RabbitMQ, RocketMQ,Kafka ,ZeroMq...
     *      延时任务(队列)
     *      10:12下单的，未付款(10)状态
     *
     */
    @Scheduled(cron ="0 0 0-2 * * ?")
    public void autoCloseOrder(){
        orderService.closeOrder();
        System.out.println("执行当前任务，当前任务为：" + DateUtil.getCurrentDateString(DATETIME_PATTERN));
    }


}
