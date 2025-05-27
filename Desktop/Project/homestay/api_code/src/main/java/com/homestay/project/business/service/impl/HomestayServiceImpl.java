package com.homestay.project.business.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.homestay.common.utils.DateUtils;
import com.homestay.project.business.domain.Goods;
import com.homestay.project.business.mapper.GoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.homestay.project.business.mapper.HomestayMapper;
import com.homestay.project.business.domain.Homestay;
import com.homestay.project.business.service.IHomestayService;

/**
 * 名宿信息Service业务层处理
 * 
 * @author y
 * @date 2024-02-13
 */
@Service
public class HomestayServiceImpl extends ServiceImpl<HomestayMapper, Homestay> implements IHomestayService
{
    @Autowired
    private HomestayMapper homestayMapper;

    /**
     * 查询名宿信息
     * 
     * @param id 名宿信息主键
     * @return 名宿信息
     */
    @Override
    public Homestay selectHomestayById(Long id)
    {
        return homestayMapper.selectHomestayById(id);
    }

    /**
     * 查询名宿信息列表
     * 
     * @param homestay 名宿信息
     * @return 名宿信息
     */
    @Override
    public List<Homestay> selectHomestayList(Homestay homestay)
    {
        return homestayMapper.selectHomestayList(homestay);
    }

    /**
     * 新增名宿信息
     * 
     * @param homestay 名宿信息
     * @return 结果
     */
    @Override
    public int insertHomestay(Homestay homestay)
    {
        homestay.setCreateTime(DateUtils.getNowDate());
        return homestayMapper.insertHomestay(homestay);
    }

    /**
     * 修改名宿信息
     * 
     * @param homestay 名宿信息
     * @return 结果
     */
    @Override
    public int updateHomestay(Homestay homestay)
    {
        homestay.setUpdateTime(DateUtils.getNowDate());
        return homestayMapper.updateHomestay(homestay);
    }

    /**
     * 批量删除名宿信息
     * 
     * @param ids 需要删除的名宿信息主键
     * @return 结果
     */
    @Override
    public int deleteHomestayByIds(Long[] ids)
    {
        return homestayMapper.deleteHomestayByIds(ids);
    }

    /**
     * 删除名宿信息信息
     * 
     * @param id 名宿信息主键
     * @return 结果
     */
    @Override
    public int deleteHomestayById(Long id)
    {
        return homestayMapper.deleteHomestayById(id);
    }
}
