package com.imooc.service;

import com.imooc.pojo.Carousel;
import com.imooc.pojo.Category;
import com.imooc.pojo.vo.CategoryVO;
import com.imooc.pojo.vo.NewItemsVO;

import java.util.List;

/**
 * @author chn
 */
public interface CategoryService {
    /**
     * 查询所有一级分类
     * @param
     * @return 所有一级分类的数据
     */
    public List<Category> queryAllRootLevleCat();


    /**
     *  根据一级分类id查询子分类信息
     */
    public List<CategoryVO> getSubCatList(Integer rootCatId);


    /**
     * 查询首页每个一级分类下六条的最新商品相关信息
     * @return
     */
    public List<NewItemsVO> getSixNewItemsLazy(Integer rootCatId);
}
