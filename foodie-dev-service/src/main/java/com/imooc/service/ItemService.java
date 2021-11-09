package com.imooc.service;

import com.imooc.pojo.*;
import com.imooc.pojo.vo.CategoryVO;
import com.imooc.pojo.vo.CommentLevelCountsVo;
import com.imooc.pojo.vo.ItemCommentVO;
import com.imooc.pojo.vo.NewItemsVO;
import com.imooc.utils.PagedGridResult;

import java.util.List;

/**
 * @author chn
 */
public interface ItemService {
    /**
     * 根据商品ID查询详情
     * @param itemId
     * @return
     */
    public Items queryItemById(String itemId);


    /**
     * 根据商品id查询图片列表
     * @param itemId
     * @return
     */
    public List<ItemsImg> queryItemImgList(String itemId);


    /**
     * 根据商品id查询商品规格
     * @param itemId
     * @return
     */
    public List<ItemsSpec> queryItemSpecList(String itemId);


    /**
     *  根据商品id查询商品详情
     * @param itemId
     * @return
     */
    public ItemsParam queryItemParam(String itemId);


    /**
     * 根据商品id查询商品的评论等级数量
     * @param itemId
     */
    public CommentLevelCountsVo queryCommentCounts(String itemId);


    /**
     * 根据商品id查询商品的评价（分页）
     * @param itemId
     * @param level
     * @return
     */
    public PagedGridResult queryPageComments(String itemId, Integer level, Integer page , Integer pageSize);


}
