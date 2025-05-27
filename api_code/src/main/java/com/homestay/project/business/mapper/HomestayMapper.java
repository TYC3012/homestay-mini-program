package com.homestay.project.business.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.homestay.project.business.domain.Goods;
import com.homestay.project.business.domain.Homestay;
import org.apache.ibatis.annotations.Mapper;

/**
 * 名宿信息Mapper接口
 * 
 * @author y
 * @date 2024-02-13
 */
@Mapper
public interface HomestayMapper extends BaseMapper<Homestay>
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
     * 删除名宿信息
     * 
     * @param id 名宿信息主键
     * @return 结果
     */
    public int deleteHomestayById(Long id);

    /**
     * 批量删除名宿信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteHomestayByIds(Long[] ids);
}
