package com.imooc.service.center;

import com.imooc.pojo.OrderItems;
import com.imooc.pojo.Orders;
import com.imooc.pojo.Users;
import com.imooc.pojo.bo.center.OrderItemsCommentBO;
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


    /**
     * 保存用户的评论
     * @param orderId
     * @param userId
     * @param commentList
     */
    public void saveComments(String orderId, String userId,
                             List<OrderItemsCommentBO> commentList);


    /**
     * 我的评价查询 分页
     * @param uesrId
     * @param page
     * @param pageSize
     * @return
     */
    public PagedGridResult queryMyComments(String uesrId,Integer page, Integer pageSize);
}
