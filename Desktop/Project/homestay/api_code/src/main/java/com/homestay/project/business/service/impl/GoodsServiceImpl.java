package com.homestay.project.business.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.homestay.common.utils.DateUtils;
import com.homestay.common.utils.StringUtils;
import com.homestay.project.business.domain.Goods;
import com.homestay.project.business.mapper.GoodsMapper;
import com.homestay.project.business.service.IGoodsService;
import com.homestay.project.business.vo.GoodsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 商品Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-11-14
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements IGoodsService
{
    @Autowired
    private GoodsMapper goodsMapper;

    /**
     * 查询商品
     * 
     * @param id 商品主键
     * @return 商品
     */
    @Override
    public Goods selectGoodsById(Long id)
    {
        return goodsMapper.selectById(id);//mybatis_plus的使用方法，不需要写sql
        //return goodsMapper.selectGoodsById(id);//普通的使用方法，需要写sql
    }

    /**
     * 查询商品列表
     * 
     * @param goods 商品
     * @return 商品
     */
    @Override
    public List<Goods> selectGoodsList(GoodsVO goods)
    {
        LambdaQueryWrapper<Goods> lambdaQuery = Wrappers.lambdaQuery(Goods.class);
        //lambdaQuery.eq(Goods::getId, goods.getId());
        //一些通用的方法，使用枚举方法，不要到处自己定义
        //lambdaQuery.eq(Goods::getPutWayFlag, WhereEnum.YES.getValue() );
        if(StringUtils.isNotEmpty(goods.getGoodsName())){
            lambdaQuery.like(Goods::getGoodsName, goods.getGoodsName());
        }

        //mybatis_plus的使用方法，不需要写sql
        List<Goods> goodsList=goodsMapper.selectList(lambdaQuery);
        //该方法将goodsList集合按照id转成了map，处理逻辑时可以按照id去匹配数据
        Map<Long, Goods> goodsMap = goodsList.stream().collect(Collectors.toMap(Goods::getId, g -> g));
        System.out.println("转换后的商品：goodsMap"+goodsMap);
        goodsList.stream().forEach(a->{
            //stream的流失表达式，使用stream流出来list效率会高得多
            //可在此做些逻辑处理
        });

        return goodsList;
    }

    /**
     * 新增商品
     * 
     * @param goods 商品
     * @return 结果
     */
    @Override
    public int insertGoods(Goods goods)
    {
        goods.setCreateTime(DateUtils.getNowDate());
        return goodsMapper.insert(goods);
        //return goodsMapper.insertGoods(goods);
    }

    /**
     * 修改商品
     * 
     * @param goods 商品
     * @return 结果
     */
    @Override
    public int updateGoods(Goods goods)
    {
        goods.setUpdateTime(DateUtils.getNowDate());
        return goodsMapper.updateById(goods);
        //return goodsMapper.updateGoods(goods);
    }

    /**
     * 批量删除商品
     * 
     * @param ids 需要删除的商品主键
     * @return 结果
     */
    @Override
    public int deleteGoodsByIds(Long[] ids)
    {
        return goodsMapper.deleteBatchIds(Arrays.asList(ids));
        //return goodsMapper.deleteGoodsByIds(ids);
    }

    /**
     * 删除商品信息
     * 
     * @param id 商品主键
     * @return 结果
     */
    @Override
    public int deleteGoodsById(Long id)
    {
        return goodsMapper.deleteById(id);
        //return goodsMapper.deleteGoodsById(id);
    }
}
