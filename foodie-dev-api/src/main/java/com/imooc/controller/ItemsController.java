package com.imooc.controller;


import com.imooc.pojo.Items;
import com.imooc.pojo.ItemsImg;
import com.imooc.pojo.ItemsParam;
import com.imooc.pojo.ItemsSpec;
import com.imooc.pojo.vo.CommentLevelCountsVo;
import com.imooc.pojo.vo.ItemInfoVO;
import com.imooc.pojo.vo.ShopcartVO;
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




    @ApiOperation(value = "查询商品评价详情", notes = "查询商品评价等级" , httpMethod = "GET")
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



    @ApiOperation(value = "搜索商品列表", notes = "搜索商品列表" , httpMethod = "GET")
    @GetMapping("/search")
    public IMOOCJSONResult search(
            @ApiParam(name = "keywords", value = "关键字"  , required = true)
            @RequestParam String keywords,
            @ApiParam(name = "sort", value = "排序"  , required = false)
            @RequestParam String sort,
            @ApiParam(name = "page", value = "查询下一页的第几页"  , required = false)
            @RequestParam Integer page,
            @ApiParam(name = "pageSize", value = "分页的每一页现实的条数"  , required = false)
            @RequestParam Integer pageSize){
        if (StringUtils.isBlank(keywords)){
            return IMOOCJSONResult.errorMsg(null);
        }
        if (page == null ){
            page = 1;
        }
        if (pageSize == null){
            pageSize = PAGE_SIZE;
        }
        PagedGridResult grid = itemService.searhItems(keywords,
                                                                sort,
                                                                page,
                                                                pageSize);

        return IMOOCJSONResult.ok(grid);

    }


    @ApiOperation(value = "通过分类ID搜索商品列表", notes = "通过分类ID搜索商品列表" , httpMethod = "GET")
    @GetMapping("/catItems")
    public IMOOCJSONResult catItems(
            @ApiParam(name = "catId", value = "三级分类id"  , required = true)
            @RequestParam Integer catId,
            @ApiParam(name = "sort", value = "排序"  , required = false)
            @RequestParam String sort,
            @ApiParam(name = "page", value = "查询下一页的第几页"  , required = false)
            @RequestParam Integer page,
            @ApiParam(name = "pageSize", value = "分页的每一页现实的条数"  , required = false)
            @RequestParam Integer pageSize){
        if (catId == null){
            return IMOOCJSONResult.errorMsg(null);
        }
        if (page == null ){
            page = 1;
        }
        if (pageSize == null){
            pageSize = PAGE_SIZE;
        }
        PagedGridResult grid = itemService.searhItems(catId,
                sort,
                page,
                pageSize);


        return IMOOCJSONResult.ok(grid);

    }


    /**用于用户长时间为登录网站，刷新购物车中的数据(主要是商品数据)
    */
    @ApiOperation(value = "根据商品规格ids查找最新的商品数据", notes = "根据商品规格ids查找最新的商品数据" , httpMethod = "GET")
    @GetMapping("/refresh")
    public IMOOCJSONResult refresh(
            @ApiParam(name = "itemSpecIds", value = "拼接的规格ids"  , required = true,example = "1001,1005")
            @RequestParam String itemSpecIds){
        if (StringUtils.isBlank(itemSpecIds)){
            return IMOOCJSONResult.ok();
        }
        List<ShopcartVO> shopcartVOS = itemService.queryItemsBySpecIds(itemSpecIds);

        return IMOOCJSONResult.ok(shopcartVOS);

    }

}
