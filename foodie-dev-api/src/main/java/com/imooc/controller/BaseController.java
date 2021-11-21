package com.imooc.controller;

import org.springframework.stereotype.Controller;

import java.io.File;

@Controller
public class BaseController {
    public static final Integer COMMENR_PAGE_SIZE = 10;
    public static final Integer PAGE_SIZE = 20 ;
    public static final String FOODIE_SHOPCART = "shopcart";

    // 支付中心的调用地址
    String paymentUrl = "http://payment.t.mukewang.com/foodie-payment/payment/createMerchantOrder";

    //微信支付成功 -> ！-(商户后台系统    支付中心 -> 天天吃货平台）-！
    String payReturnUrl = "http://localhost:8088/orders/notifyMerchantOrderPaid";


    //用户上传头像的地址
    //
    public static  final String IMAGE_USER_FACE_LOCATION = "D:"+
            File.separator + "WorkSpace"+
            File.separator + "images" +
            File.separator + "foodie"+
            File.separator + "faces";
}
