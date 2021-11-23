package com.imooc.controller.center;

import com.imooc.controller.BaseController;
import com.imooc.pojo.Orders;
import com.imooc.service.center.MyOrdersService;
import com.imooc.utils.IMOOCJSONResult;
import com.imooc.utils.PagedGridResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "用户中心我的订单", tags = {"用户中心我的订单相关接口"})
@RequestMapping("mycomments")
public class MyCommentsController extends BaseController {
    @Autowired
    private MyOrdersService myOrdersService;



    @ApiOperation(value = "查询订单列表", notes = "查询订单列表" , httpMethod = "POST")
    @PostMapping("/query")
    public IMOOCJSONResult catItems(
            @ApiParam(name = "userId", value = "用户Id"  , required = true)
            @RequestParam String userId,
            @ApiParam(name = "orderStatus", value = "订单状态"  , required = false)
            @RequestParam Integer orderStatus,
            @ApiParam(name = "page", value = "查询下一页的第几页"  , required = false)
            @RequestParam Integer page,
            @ApiParam(name = "pageSize", value = "分页的每一页现实的条数"  , required = false)
            @RequestParam Integer pageSize){
        if (StringUtils.isBlank(userId)){
            return IMOOCJSONResult.errorMsg(null);
        }
        if (page == null ){
            page = 1;
        }
        if (pageSize == null){
            pageSize = COMMENR_PAGE_SIZE;
        }
        PagedGridResult grid = myOrdersService.queryMyOrders(userId,
                orderStatus,
                page,
                pageSize);



        return IMOOCJSONResult.ok(grid);

    }


    @ApiOperation(value = "商家发货", notes = "商家发货" , httpMethod = "GET")
    @GetMapping("/deliver")
    public IMOOCJSONResult deliver(@ApiParam(name = "orderId",value = "订单id",required = true)
                                       @RequestParam String orderId) throws Exception{
        if (StringUtils.isBlank(orderId)){
            return IMOOCJSONResult.errorMsg("订单ID不能为空");
        }

        myOrdersService.updateDeliverOrderStatus(orderId);
        return IMOOCJSONResult.ok();
    }


    @ApiOperation(value = "用户确认收货", notes = "商家发货" , httpMethod = "POST")
    @PostMapping("/confirmReceive")
    public IMOOCJSONResult confirmReceive(@ApiParam(name = "orderId",value = "订单id",required = true)
                                   @RequestParam String orderId,
                                          @ApiParam(name = "userId",value = "用户id",required = true)
                                          @RequestParam String userId ) throws Exception{

        IMOOCJSONResult checkResult = checkUserOrder(userId, orderId);
        if (checkResult.getStatus() != HttpStatus.OK.value()){
            return checkResult;
        }

       boolean res = myOrdersService.updateReceiveOrderStatus(orderId);
        if (!res){
            return IMOOCJSONResult.errorMsg("订单确认收获失败");
        }
        return IMOOCJSONResult.ok();
    }



    @ApiOperation(value = "用户删除订单", notes = "用户删除订单" , httpMethod = "POST")
    @PostMapping("/delete")
    public IMOOCJSONResult delete(@ApiParam(name = "orderId",value = "订单id",required = true)
                                          @RequestParam String orderId,
                                          @ApiParam(name = "userId",value = "用户id",required = true)
                                          @RequestParam String userId ) throws Exception{

        IMOOCJSONResult checkResult = checkUserOrder(userId, orderId);
        if (checkResult.getStatus() != HttpStatus.OK.value()){
            return checkResult;
        }

        boolean res = myOrdersService.deleteOrder(userId, orderId);
        if (!res){
            return  IMOOCJSONResult.errorMsg("订单删除失败");
        }
        //判断用户Id是否与订单id是捆绑在一起的

        return IMOOCJSONResult.ok();
    }



    /**
     * 用于验证用户和订单是否有关联关系，避免非法用户调用
     * @return
     */
    private IMOOCJSONResult checkUserOrder(String userId, String orderId){
        Orders order = myOrdersService.queryMyOrder(userId, orderId);
        if (order == null) {
            return IMOOCJSONResult.errorMsg("订单不存在");
        }
        return IMOOCJSONResult.ok();
    }

}
