package com.homestay.project.system.service.impl;

import java.util.List;


import com.homestay.project.system.domain.SysFileInfo;
import com.homestay.project.system.mapper.SysFileInfoMapper;
import com.homestay.project.system.service.ISysFileInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 文件信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-08-03
 */
@Service
public class SysFileInfoServiceImpl implements ISysFileInfoService
{
    @Autowired
    private SysFileInfoMapper sysFileInfoMapper;

    /**
     * 查询文件信息
     * 
     * @param fileId 文件信息主键
     * @return 文件信息
     */
    @Override
    public SysFileInfo selectSysFileInfoByFileId(Long fileId)
    {
        return sysFileInfoMapper.selectSysFileInfoByFileId(fileId);
    }

    /**
     * 查询文件信息列表
     * 
     * @param sysFileInfo 文件信息
     * @return 文件信息
     */
    @Override
    public List<SysFileInfo> selectSysFileInfoList(SysFileInfo sysFileInfo)
    {
        return sysFileInfoMapper.selectSysFileInfoList(sysFileInfo);
    }

    /**
     * 新增文件信息
     * 
     * @param sysFileInfo 文件信息
     * @return 结果
     */
    @Override
    public int insertSysFileInfo(SysFileInfo sysFileInfo)
    {
        return sysFileInfoMapper.insertSysFileInfo(sysFileInfo);
    }

    /**
     * 修改文件信息
     * 
     * @param sysFileInfo 文件信息
     * @return 结果
     */
    @Override
    public int updateSysFileInfo(SysFileInfo sysFileInfo)
    {
        return sysFileInfoMapper.updateSysFileInfo(sysFileInfo);
    }

    /**
     * 批量删除文件信息
     * 
     * @param fileIds 需要删除的文件信息主键
     * @return 结果
     */
    @Override
    public int deleteSysFileInfoByFileIds(Long[] fileIds)
    {
        return sysFileInfoMapper.deleteSysFileInfoByFileIds(fileIds);
    }

    /**
     * 删除文件信息信息
     * 
     * @param fileId 文件信息主键
     * @return 结果
     */
    @Override
    public int deleteSysFileInfoByFileId(Long fileId)
    {
        return sysFileInfoMapper.deleteSysFileInfoByFileId(fileId);
    }
}
