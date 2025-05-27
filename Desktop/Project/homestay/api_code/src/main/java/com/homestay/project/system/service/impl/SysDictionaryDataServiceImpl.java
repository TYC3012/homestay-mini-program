package com.homestay.project.system.service.impl;

import java.util.List;
import com.homestay.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.homestay.project.system.mapper.SysDictionaryDataMapper;
import com.homestay.project.system.domain.SysDictionaryData;
import com.homestay.project.system.service.ISysDictionaryDataService;

/**
 * 字典数据Service业务层处理
 * 
 * @author yulian qiang
 * @date 2024-02-03
 */
@Service
public class SysDictionaryDataServiceImpl implements ISysDictionaryDataService 
{
    @Autowired
    private SysDictionaryDataMapper sysDictionaryDataMapper;

    /**
     * 查询字典数据
     * 
     * @param dictCode 字典数据主键
     * @return 字典数据
     */
    @Override
    public SysDictionaryData selectSysDictionaryDataByDictCode(Long dictCode)
    {
        return sysDictionaryDataMapper.selectSysDictionaryDataByDictCode(dictCode);
    }

    /**
     * 查询字典数据列表
     * 
     * @param sysDictionaryData 字典数据
     * @return 字典数据
     */
    @Override
    public List<SysDictionaryData> selectSysDictionaryDataList(SysDictionaryData sysDictionaryData)
    {
        return sysDictionaryDataMapper.selectSysDictionaryDataList(sysDictionaryData);
    }

    /**
     * 新增字典数据
     * 
     * @param sysDictionaryData 字典数据
     * @return 结果
     */
    @Override
    public int insertSysDictionaryData(SysDictionaryData sysDictionaryData)
    {
        sysDictionaryData.setCreateTime(DateUtils.getNowDate());
        return sysDictionaryDataMapper.insertSysDictionaryData(sysDictionaryData);
    }

    /**
     * 修改字典数据
     * 
     * @param sysDictionaryData 字典数据
     * @return 结果
     */
    @Override
    public int updateSysDictionaryData(SysDictionaryData sysDictionaryData)
    {
        sysDictionaryData.setUpdateTime(DateUtils.getNowDate());
        return sysDictionaryDataMapper.updateSysDictionaryData(sysDictionaryData);
    }

    /**
     * 批量删除字典数据
     * 
     * @param dictCodes 需要删除的字典数据主键
     * @return 结果
     */
    @Override
    public int deleteSysDictionaryDataByDictCodes(Long[] dictCodes)
    {
        return sysDictionaryDataMapper.deleteSysDictionaryDataByDictCodes(dictCodes);
    }

    /**
     * 删除字典数据信息
     * 
     * @param dictCode 字典数据主键
     * @return 结果
     */
    @Override
    public int deleteSysDictionaryDataByDictCode(Long dictCode)
    {
        return sysDictionaryDataMapper.deleteSysDictionaryDataByDictCode(dictCode);
    }
}
