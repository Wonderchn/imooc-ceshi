package com.imooc.controller;


import com.imooc.enums.YesOrNo;
import com.imooc.pojo.*;
import com.imooc.pojo.vo.*;
import com.imooc.service.CarouselService;
import com.imooc.service.CategoryService;
import com.imooc.service.ItemService;
import com.imooc.utils.IMOOCJSONResult;
import com.imooc.utils.PagedGridResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "查询商品详情",tags = {"查询商品详情"})
@RequestMapping("items")
@RestController
public class ItemsController extends BaseController{
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



    @ApiOperation(value = "查询商品评价等级", notes = "查询商品评价等级" , httpMethod = "GET")
    @GetMapping("/commentLevel")
    public IMOOCJSONResult commentLevel(
            @ApiParam(name = "itemId", value = "商品id"  , required = true)
            @RequestParam String itemId){
        if (StringUtils.isBlank(itemId)){
            return IMOOCJSONResult.errorMsg(null);
        }

        CommentLevelCountsVo commentLevelCountsVo = itemService.queryCommentCounts(itemId);


        return IMOOCJSONResult.ok(commentLevelCountsVo);

    }




    @ApiOperation(value = "查询商品评价等级", notes = "查询商品评价等级" , httpMethod = "GET")
    @GetMapping("/comments")
    public IMOOCJSONResult comments(
            @ApiParam(name = "itemId", value = "商品id"  , required = true)
            @RequestParam String itemId,
            @ApiParam(name = "level", value = "评价等级"  , required = false)
            @RequestParam Integer level,
            @ApiParam(name = "page", value = "查询下一页的第几页"  , required = false)
            @RequestParam Integer page,
            @ApiParam(name = "pageSize", value = "分页的每一页现实的条数"  , required = false)
            @RequestParam Integer pageSize){
        if (StringUtils.isBlank(itemId)){
            return IMOOCJSONResult.errorMsg(null);
        }
        if (page == null ){
            page = 1;
        }
        if (pageSize == null){
            pageSize = COMMENR_PAGE_SIZE;
        }
        PagedGridResult pagedGridResult = itemService.queryPageComments(itemId,
                level, page, pageSize);



        return IMOOCJSONResult.ok(pagedGridResult);

    }



}
