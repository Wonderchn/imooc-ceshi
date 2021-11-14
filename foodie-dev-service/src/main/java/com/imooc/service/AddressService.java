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
    public void updateUserAddress(AddressBO addressBO);


    /**
     * 根据用户id和地址id，删除对应的地址信息
     * @param userId
     * @param addressId
     */
    public void deleteUserAddress(String userId,String addressId);


    /**
     * 根据用户id和地址id，更新对应的地址信息
     * @param userId
     * @param addressId
     */
    public void updateUserAddressToBeDefault(String userId,String addressId);

}
