package com.imooc.service;

import com.imooc.pojo.*;
import com.imooc.pojo.vo.*;
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


    /**
     * 搜索商品列表
     * @param keywords
     * @param sort
     * @param page
     * @param pageSize
     * @return
     */
    public PagedGridResult searhItems(String keywords , String sort,
                                              Integer page , Integer pageSize
                                              );


    /**
     * 根据分类ID查看商品
     * @param catId
     * @param sort
     * @param page
     * @param pageSize
     * @return
     */
    public PagedGridResult searhItems(Integer catId , String sort,
                                      Integer page , Integer pageSize
    );


    /**
     * 根据规格 specIds 查询最新的购物车中商品数据，（用于渲染购物车）
     * @param specIds
     * @return
     */
    public List<ShopcartVO> queryItemsBySpecIds(String specIds);


    /**
     * 根据商品规格id获取规格对象的具体信息
     * @param specId
     * @return
     */
    public ItemsSpec queryItemsSpecById(String specId);


    /**
     * 根据商品Id,获取商品图片主图url
     * @param itemId
     * @return
     */
    public String queryItemMainImgById(String itemId);


    /**
     * 根据规格ID和商品id减少商品库存
     * @param specId
     * @param buyCounts
     */
    public void decreaseItemSpecStock(String specId,int buyCounts);
}
