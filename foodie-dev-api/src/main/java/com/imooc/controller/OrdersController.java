package com.imooc.controller;

import com.imooc.enums.OrderStatusEnum;
import com.imooc.enums.PayMethod;
import com.imooc.pojo.OrderStatus;
import com.imooc.pojo.bo.SubmitOrderBO;
import com.imooc.pojo.vo.MerchantOrdersVO;
import com.imooc.pojo.vo.OrderVO;
import com.imooc.service.OrderService;
import com.imooc.utils.IMOOCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(value = "订单相关",tags = {"订单相关的api接口"})
@RequestMapping("orders")
@RestController
public class OrdersController  extends  BaseController{

    @Autowired
    private OrderService orderService;

    @Autowired
    private RestTemplate restTemplate;

    @ApiOperation(value = "用户下单",notes = "用户下单", httpMethod = "POST")
    @PostMapping("/create")
    public IMOOCJSONResult add(@RequestBody SubmitOrderBO submitOrderBO,
                               HttpServletRequest request,
                               HttpServletResponse response){
//        System.out.println(submitOrderBO.toString());
        if (!submitOrderBO.getPayMethod().equals(PayMethod.WEIXIN.type) &&
                !submitOrderBO.getPayMethod().equals(PayMethod.ALIPAY.type)
        ){
            return  IMOOCJSONResult.errorMsg("支付方式不支持");
        }
        //1.创建订单
        OrderVO orderVO =  orderService.createOrder(submitOrderBO);
        String orderId = orderVO.getOrderId();
        MerchantOrdersVO merchantOrdersVO = orderVO.getMerchantOrdersVO();
        merchantOrdersVO.setReturnUrl(payReturnUrl);

        //2.创建订单后，移除购物车中已结算（已提交）的商品
        /**
         * 1001
         * 2002 ->用户购买
         * 3003 ->用户购买  去剔除
         * 4004
         */
        //TODO 整合redis后，完善购物车中的已结算商品清除,并且同步到前端cookie
        //前端购物车清空
//        CookieUtils.setCookie(request, response, FOODIE_SHOPCART, "", true);

        //3.向支付中心发送当前订单，用于保存支付中心的订单数据

        // 为了方便测试购买，所以所有的支付金额都统一改为1分钱
        merchantOrdersVO.setAmount(1);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("imoocUserId", "imooc");
        headers.add("password", "imooc");
        HttpEntity<MerchantOrdersVO> entity = new HttpEntity<>(merchantOrdersVO,headers);


        ResponseEntity<IMOOCJSONResult> responseEntity = restTemplate.postForEntity(paymentUrl, entity, IMOOCJSONResult.class);
        IMOOCJSONResult paymentResult = responseEntity.getBody();
        if (paymentResult.getStatus() != 200){
            return IMOOCJSONResult.errorMsg("支付中心订单创建失败，请联系管理员");
        }


        return  IMOOCJSONResult.ok(orderId);
    }


    @PostMapping("/notifyMerchantOrderPaid")
    public Integer notifyMerchantOrderPaid(String orderId){
        orderService.updateOrderStatus(orderId, OrderStatusEnum.WAIT_DELIVER.type);
        return HttpStatus.OK.value();


    }


    @PostMapping("getPaidOrderInfo")
    public IMOOCJSONResult getPaidOrderInfo(String orderId){
        OrderStatus orderStatus = orderService.queryOrderStatusInfo(orderId);
        return IMOOCJSONResult.ok(orderStatus);


    }

}
