package com.homestay.project.business.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.homestay.project.business.domain.Goods;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 商品Mapper接口
 * 
 * @author ruoyi
 * @date 2023-11-14
 */
@Mapper
public interface GoodsMapper extends BaseMapper<Goods>
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
    public List<Goods> selectGoodsList(Goods goods);

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
     * 删除商品
     * 
     * @param id 商品主键
     * @return 结果
     */
    public int deleteGoodsById(Long id);

    /**
     * 批量删除商品
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteGoodsByIds(Long[] ids);
}
