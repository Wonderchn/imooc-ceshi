package com.imooc.controller;


import com.imooc.enums.YesOrNo;
import com.imooc.pojo.*;
import com.imooc.pojo.vo.CategoryVO;
import com.imooc.pojo.vo.ItemInfoVO;
import com.imooc.pojo.vo.NewItemsVO;
import com.imooc.service.CarouselService;
import com.imooc.service.CategoryService;
import com.imooc.service.ItemService;
import com.imooc.utils.IMOOCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "查询商品详情",tags = {"查询商品详情"})
@RequestMapping("items")
@RestController
public class ItemsController {
    @Autowired
    private ItemService itemService;

    @ApiOperation(value = "查询商品详情", notes = "查询商品详情" , httpMethod = "GET")
    @GetMapping("/info/{itemId}")
    public IMOOCJSONResult subCat(
            @ApiParam(name = "itemId", value = "商品id"  , required = true)
            @PathVariable String itemId){
       if (StringUtils.isBlank(itemId)){
           return IMOOCJSONResult.errorMsg(null);
       }

        Items item = itemService.queryItemById(itemId);
        List<ItemsImg> itemsImgs = itemService.queryItemImgList(itemId);
        ItemsParam itemsParam = itemService.queryItemParam(itemId);
        List<ItemsSpec> itemsSpecs = itemService.queryItemSpecList(itemId);
        ItemInfoVO itemInfoVO = new ItemInfoVO();
        itemInfoVO.setItem(item);
        itemInfoVO.setItemImgList(itemsImgs);
        itemInfoVO.setItemParams(itemsParam);
        itemInfoVO.setItemSpecList(itemsSpecs);
        return IMOOCJSONResult.ok(itemInfoVO);
    }





}
