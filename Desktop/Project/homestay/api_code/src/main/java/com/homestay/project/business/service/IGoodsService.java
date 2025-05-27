package com.homestay.project.business.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.homestay.project.business.domain.Goods;
import com.homestay.project.business.vo.GoodsVO;

import java.util.List;

/**
 * 商品Service接口
 * 
 * @author ruoyi
 * @date 2023-11-14
 */
public interface IGoodsService extends IService<Goods>
{
    /**
     * 查询商品
     * 
     * @param id 商品主键
     * @return 商品
     */
    public Goods selectGoodsById(Long id);

    /**
     * 查询商品列表
     * 
     * @param goods 商品
     * @return 商品集合
     */
    public List<Goods> selectGoodsList(GoodsVO goods);

    /**
     * 新增商品
     * 
     * @param goods 商品
     * @return 结果
     */
    public int insertGoods(Goods goods);

    /**
     * 修改商品
     * 
     * @param goods 商品
     * @return 结果
     */
    public int updateGoods(Goods goods);

    /**
     * 批量删除商品
     * 
     * @param ids 需要删除的商品主键集合
     * @return 结果
     */
    public int deleteGoodsByIds(Long[] ids);

    /**
     * 删除商品信息
     * 
     * @param id 商品主键
     * @return 结果
     */
    public int deleteGoodsById(Long id);
}
