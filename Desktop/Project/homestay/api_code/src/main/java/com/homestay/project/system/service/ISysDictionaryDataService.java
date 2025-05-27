package com.homestay.project.system.service;

import java.util.List;
import com.homestay.project.system.domain.SysDictionaryData;

/**
 * 字典数据Service接口
 * 
 * @author yulian qiang
 * @date 2024-02-03
 */
public interface ISysDictionaryDataService 
{
    /**
     * 查询字典数据
     * 
     * @param dictCode 字典数据主键
     * @return 字典数据
     */
    public SysDictionaryData selectSysDictionaryDataByDictCode(Long dictCode);

    /**
     * 查询字典数据列表
     * 
     * @param sysDictionaryData 字典数据
     * @return 字典数据集合
     */
    public List<SysDictionaryData> selectSysDictionaryDataList(SysDictionaryData sysDictionaryData);

    /**
     * 新增字典数据
     * 
     * @param sysDictionaryData 字典数据
     * @return 结果
     */
    public int insertSysDictionaryData(SysDictionaryData sysDictionaryData);

    /**
     * 修改字典数据
     * 
     * @param sysDictionaryData 字典数据
     * @return 结果
     */
    public int updateSysDictionaryData(SysDictionaryData sysDictionaryData);

    /**
     * 批量删除字典数据
     * 
     * @param dictCodes 需要删除的字典数据主键集合
     * @return 结果
     */
    public int deleteSysDictionaryDataByDictCodes(Long[] dictCodes);

    /**
     * 删除字典数据信息
     * 
     * @param dictCode 字典数据主键
     * @return 结果
     */
    public int deleteSysDictionaryDataByDictCode(Long dictCode);
}
