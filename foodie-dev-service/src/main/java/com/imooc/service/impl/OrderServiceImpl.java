package com.imooc.service.impl;

import com.imooc.enums.OrderStatusEnum;
import com.imooc.enums.YesOrNo;
import com.imooc.mapper.OrderItemsMapper;
import com.imooc.mapper.OrderStatusMapper;
import com.imooc.mapper.OrdersMapper;
import com.imooc.pojo.*;
import com.imooc.pojo.bo.SubmitOrderBO;
import com.imooc.service.AddressService;
import com.imooc.service.ItemService;
import com.imooc.service.OrderService;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author chn
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private AddressService addressService;

    @Autowired
    public OrderItemsMapper orderItemsMapper;

    @Autowired
    public OrderStatusMapper orderStatusMapper;


    @Autowired
    private ItemService itemService;

    @Autowired
    private Sid sid;


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void createOrder(SubmitOrderBO submitOrderBO) {
        String userId = submitOrderBO.getUserId();
        String addressId = submitOrderBO.getAddressId();
        String itemSpecIds = submitOrderBO.getItemSpecIds();
        Integer payMethod = submitOrderBO.getPayMethod();
        String leftMsg = submitOrderBO.getLeftMsg();
        Integer postAmount = 0;

        //订单主键
        String orderId = sid.nextShort();

        UserAddress address = addressService.queryUserAddress(userId, addressId);


        // 1.新订单数据保存
        Orders newOrder = new Orders();
        newOrder.setId(orderId);
        newOrder.setUserId(userId);


        newOrder.setReceiverName(address.getReceiver());
        newOrder.setReceiverMobile(address.getMobile());
        newOrder.setReceiverAddress(address.getProvince() + " " + address.getCity() + " " +
                address.getDistrict() + address.getDetail()
        );

        //我们需要根据 itemSpecIds分割出来的数组进行循环遍历


        newOrder.setPostAmount(postAmount);

        newOrder.setPayMethod(payMethod);
        newOrder.setLeftMsg(leftMsg);
        newOrder.setIsComment(YesOrNo.NO.type);
        newOrder.setIsDelete(YesOrNo.NO.type);

        newOrder.setCreatedTime(new Date());
        newOrder.setUpdatedTime(new Date());

        // 2.循环根据itemSpecIds保存订单商品信息表
        String[] itemSpecIdArr = itemSpecIds.split(",");
        //商品原价累计
        Integer totalAmount = 0;
        //优惠后的实际支付价格累计
        Integer realPayAmount = 0;

        for (String itemSpecId : itemSpecIdArr) {
            //2.1根据规格id，查询规格的详细信息，
            ItemsSpec itemsSpec = itemService.queryItemsSpecById(itemSpecId);
            //TODO 整合redis后，商品购买的数量重新从redis的购物车获取
            //此处buyCounts的值是写死了的
            int buyCounts = 1;
            totalAmount += itemsSpec.getPriceNormal() * buyCounts;
            realPayAmount += itemsSpec.getPriceDiscount() * buyCounts;

            //2.2 根据商品id，获得商品信息以及商品图片
            String itemId = itemsSpec.getItemId();
            Items item = itemService.queryItemById(itemId);
            String imgUrl = itemService.queryItemMainImgById(itemId);

            //2.3 循环保存此订单到数据库
            String subOrderId = sid.nextShort();
            OrderItems subOrderItem = new OrderItems();
            subOrderItem.setId(subOrderId);
            subOrderItem.setOrderId(orderId);
            subOrderItem.setItemSpecId(itemSpecId);
            subOrderItem.setItemId(itemId);
            subOrderItem.setItemName(item.getItemName());
            subOrderItem.setItemImg(imgUrl);
            subOrderItem.setBuyCounts(buyCounts);
            subOrderItem.setItemSpecName(itemsSpec.getName());
            subOrderItem.setPrice(itemsSpec.getPriceDiscount());
            orderItemsMapper.insert(subOrderItem);

            //2.4 在用户提交订单后，规格表中扣除库存
            itemService.decreaseItemSpecStock(itemSpecId, buyCounts);


        }
        newOrder.setTotalAmount(totalAmount);
        newOrder.setRealPayAmount(realPayAmount);
        ordersMapper.insert(newOrder);

        //  3.保存订单状态表
        OrderStatus waitPayOderStatus = new OrderStatus();
        waitPayOderStatus.setOrderId(orderId);
        waitPayOderStatus.setOrderStatus(OrderStatusEnum.WAIT_PAY.type);
        waitPayOderStatus.setCreatedTime(new Date());
        orderStatusMapper.insert(waitPayOderStatus);

    }
}
