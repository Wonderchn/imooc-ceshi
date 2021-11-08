package com.imooc.service;

import com.imooc.pojo.*;
import com.imooc.pojo.vo.CategoryVO;
import com.imooc.pojo.vo.NewItemsVO;

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
}
