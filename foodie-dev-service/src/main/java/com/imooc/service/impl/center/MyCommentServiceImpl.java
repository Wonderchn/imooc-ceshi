package com.imooc.service.impl.center;

import com.imooc.mapper.OrderItemsMapper;
import com.imooc.pojo.OrderItems;
import com.imooc.service.center.MyCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MyCommentServiceImpl implements MyCommentService {

    @Autowired
    public OrderItemsMapper orderItemsMapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<OrderItems> queryPendingComment(String orderId) {
        OrderItems query = new OrderItems();
        query.setOrderId(orderId);


        return orderItemsMapper.select(query);
    }
}
