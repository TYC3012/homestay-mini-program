package com.homestay.project.system.controller;


import com.homestay.common.utils.file.FileUploadUtils;

import com.homestay.common.utils.poi.ExcelUtil;
import com.homestay.framework.aspectj.lang.annotation.Log;
import com.homestay.framework.aspectj.lang.enums.BusinessType;
import com.homestay.framework.config.RuoYiConfig;
import com.homestay.framework.web.controller.BaseController;
import com.homestay.framework.web.domain.AjaxResult;
import com.homestay.framework.web.page.TableDataInfo;

import com.homestay.project.system.domain.SysFileInfo;
import com.homestay.project.system.service.ISysFileInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 文件信息Controller
 * 
 * @author ruoyi
 * @date 2023-08-03
 */
@RestController
@RequestMapping("/system/info")
public class SysFileInfoController extends BaseController {
    @Autowired
    private ISysFileInfoService sysFileInfoService;

    /**
     * 查询文件信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:info:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysFileInfo sysFileInfo)
    {
        startPage();
        List<SysFileInfo> list = sysFileInfoService.selectSysFileInfoList(sysFileInfo);
        return getDataTable(list);
    }

    /**
     * 导出文件信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:info:export')")
    @Log(title = "文件信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysFileInfo sysFileInfo)
    {
        List<SysFileInfo> list = sysFileInfoService.selectSysFileInfoList(sysFileInfo);
        ExcelUtil<SysFileInfo> util = new ExcelUtil<SysFileInfo>(SysFileInfo.class);
        util.exportExcel(response, list, "文件信息数据");
    }

    /**
     * 获取文件信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:info:query')")
    @GetMapping(value = "/{fileId}")
    public AjaxResult getInfo(@PathVariable("fileId") Long fileId)
    {
        return success(sysFileInfoService.selectSysFileInfoByFileId(fileId));
    }

    /**
     * 新增文件信息
     */
    @PreAuthorize("@ss.hasPermi('system:info:add')")
    @Log(title = "文件信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysFileInfo sysFileInfo)
    {
        return toAjax(sysFileInfoService.insertSysFileInfo(sysFileInfo));
    }

    /**
     * 修改文件信息
     */
    @PreAuthorize("@ss.hasPermi('system:info:edit')")
    @Log(title = "文件信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysFileInfo sysFileInfo)
    {
        return toAjax(sysFileInfoService.updateSysFileInfo(sysFileInfo));
    }

    /**
     * 删除文件信息
     */
    @PreAuthorize("@ss.hasPermi('system:info:remove')")
    @Log(title = "文件信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{fileIds}")
    public AjaxResult remove(@PathVariable Long[] fileIds)
    {
        return toAjax(sysFileInfoService.deleteSysFileInfoByFileIds(fileIds));
    }
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@RequestParam("file") MultipartFile file, SysFileInfo fileInfo) throws IOException
    {
        // 上传文件路径
        String filePath = RuoYiConfig.getUploadPath();
        // 上传并返回新文件名称
        String fileName = FileUploadUtils.upload(filePath, file);
        fileInfo.setFilePath(fileName);
        return toAjax(sysFileInfoService.insertSysFileInfo(fileInfo));
    }
}
