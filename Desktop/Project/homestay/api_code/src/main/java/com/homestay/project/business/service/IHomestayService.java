package com.homestay.project.business.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.homestay.project.business.domain.Goods;
import com.homestay.project.business.domain.Homestay;

/**
 * 名宿信息Service接口
 * 
 * @author y
 * @date 2024-02-13
 */
public interface IHomestayService extends IService<Homestay>
{
    /**
     * 查询名宿信息
     * 
     * @param id 名宿信息主键
     * @return 名宿信息
     */
    public Homestay selectHomestayById(Long id);

    /**
     * 查询名宿信息列表
     * 
     * @param homestay 名宿信息
     * @return 名宿信息集合
     */
    public List<Homestay> selectHomestayList(Homestay homestay);

    /**
     * 新增名宿信息
     * 
     * @param homestay 名宿信息
     * @return 结果
     */
    public int insertHomestay(Homestay homestay);

    /**
     * 修改名宿信息
     * 
     * @param homestay 名宿信息
     * @return 结果
     */
    public int updateHomestay(Homestay homestay);

    /**
     * 批量删除名宿信息
     * 
     * @param ids 需要删除的名宿信息主键集合
     * @return 结果
     */
    public int deleteHomestayByIds(Long[] ids);

    /**
     * 删除名宿信息信息
     * 
     * @param id 名宿信息主键
     * @return 结果
     */
    public int deleteHomestayById(Long id);
}
