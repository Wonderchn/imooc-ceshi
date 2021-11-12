package com.imooc.service;

import com.imooc.pojo.Carousel;
import com.imooc.pojo.UserAddress;
import com.imooc.pojo.bo.AddressBO;

import java.util.List;

/**
 * @author chn
 */
public interface AddressService {
    /**
     * 根据用户id查询用户的收货地址
     * @param userId
     * @return
     */
    public List<UserAddress> queryAll(String userId);


    /**
     * 用户地址新增
     * @param addressBO
     */
    public void addNewUserAddress(AddressBO addressBO);

    /**
     * 用户地址修改
     */




}
