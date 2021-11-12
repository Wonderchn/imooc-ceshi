package com.imooc.controller;


import com.imooc.pojo.UserAddress;
import com.imooc.pojo.bo.AddressBO;
import com.imooc.service.AddressService;
import com.imooc.utils.IMOOCJSONResult;
import com.imooc.utils.MobileEmailUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Api(value = "首页",tags = {"地址相关的API"})
@RequestMapping("address")
@RestController
public class AddressController {
    /**
     * 用户在确认订单页面，可以针对收货地址做如下操作：
     * 1.查询用户的所有用户地址列表
     * 2.新增收货地址
     * 3.删除收货地址
     * 4.修改收货地址
     * 5.设置默认地址
     *
     */
    @Autowired
    AddressService addressService;

    @ApiOperation(value = "根据用户id查询收货地址", notes = "根据用户id查询收货地址", httpMethod = "POST")
    @PostMapping("/list")
    public IMOOCJSONResult list(
            @RequestParam String userId
            ) {

        if (StringUtils.isBlank(userId)) {
            return IMOOCJSONResult.errorMsg("分类不存在");
        }

        List<UserAddress> userAddresses = addressService.queryAll(userId);
        return IMOOCJSONResult.ok(userAddresses);
    }


    @ApiOperation(value = "添加收货地址", notes = "添加收货地址", httpMethod = "POST")
    @PostMapping("/add")
    public IMOOCJSONResult add(
            @RequestBody AddressBO addressBO
    ) {

        IMOOCJSONResult checkRes = checkAddress(addressBO);
        if (checkRes.getStatus() != 200){
            return checkRes;
        }
        addressService.addNewUserAddress(addressBO);
        return IMOOCJSONResult.ok();
    }


    private IMOOCJSONResult checkAddress(AddressBO addressBO) {
        String receiver = addressBO.getReceiver();
        if (StringUtils.isBlank(receiver)) {
            return IMOOCJSONResult.errorMsg("收货人不能为空");
        }
        if (receiver.length() > 12) {
            return IMOOCJSONResult.errorMsg("收货人姓名不能太长");
        }

        String mobile = addressBO.getMobile();
        if (StringUtils.isBlank(mobile)) {
            return IMOOCJSONResult.errorMsg("收货人手机号不能为空");
        }
        if (mobile.length() != 11) {
            return IMOOCJSONResult.errorMsg("收货人手机号长度不正确");
        }
        boolean isMobileOk = MobileEmailUtils.checkMobileIsOk(mobile);
        if (!isMobileOk) {
            return IMOOCJSONResult.errorMsg("收货人手机号格式不正确");
        }

        String province = addressBO.getProvince();
        String city = addressBO.getCity();
        String district = addressBO.getDistrict();
        String detail = addressBO.getDetail();
        if (StringUtils.isBlank(province) ||
                StringUtils.isBlank(city) ||
                StringUtils.isBlank(district) ||
                StringUtils.isBlank(detail)) {
            return IMOOCJSONResult.errorMsg("收货地址信息不能为空");
        }

        return IMOOCJSONResult.ok();
    }


}
