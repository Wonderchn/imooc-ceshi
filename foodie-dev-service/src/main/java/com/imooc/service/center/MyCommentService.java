package com.imooc.service.center;

import com.imooc.pojo.OrderItems;
import com.imooc.pojo.Orders;
import com.imooc.pojo.Users;
import com.imooc.utils.PagedGridResult;

import java.util.List;

public interface MyCommentService {
    /**
     * 根据订单id 查询相关商品
     *
     * @param orderId
     * @return
     */
    public List<OrderItems> queryPendingComment(String orderId);
}
