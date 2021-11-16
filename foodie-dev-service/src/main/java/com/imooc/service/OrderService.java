package com.imooc.service;

import com.imooc.pojo.Carousel;
import com.imooc.pojo.Category;
import com.imooc.pojo.bo.SubmitOrderBO;
import com.imooc.pojo.vo.CategoryVO;
import com.imooc.pojo.vo.NewItemsVO;
import com.imooc.pojo.vo.OrderVO;

import java.util.List;

/**
 * @author chn
 */
public interface OrderService {

    /**
     * 用于创建订单相关信息
     * @param submitOrderBO
     * @return
     */
     public OrderVO createOrder(SubmitOrderBO submitOrderBO);


    /**
     * 修改订单状态
     * @param orderId
     * @param orderStatus
     */
     public void updateOrderStatus(String orderId,Integer orderStatus);

}
