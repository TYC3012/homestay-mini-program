package com.homestay.project.system.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.homestay.framework.aspectj.lang.annotation.Log;
import com.homestay.framework.aspectj.lang.enums.BusinessType;
import com.homestay.project.system.domain.SysDictionaryData;
import com.homestay.project.system.service.ISysDictionaryDataService;
import com.homestay.framework.web.controller.BaseController;
import com.homestay.framework.web.domain.AjaxResult;
import com.homestay.common.utils.poi.ExcelUtil;
import com.homestay.framework.web.page.TableDataInfo;

/**
 * 字典数据Controller
 * 
 * @author yulian qiang
 * @date 2024-02-03
 */
@RestController
@RequestMapping("/system/dictionaryData")
public class SysDictionaryDataController extends BaseController
{
    @Autowired
    private ISysDictionaryDataService sysDictionaryDataService;

    /**
     * 查询字典数据列表
     */
    @PreAuthorize("@ss.hasPermi('system:dictionaryData:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysDictionaryData sysDictionaryData)
    {
        startPage();
        List<SysDictionaryData> list = sysDictionaryDataService.selectSysDictionaryDataList(sysDictionaryData);
        return getDataTable(list);
    }

    /**
     * 导出字典数据列表
     */
    @PreAuthorize("@ss.hasPermi('system:dictionaryData:export')")
    @Log(title = "字典数据", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysDictionaryData sysDictionaryData)
    {
        List<SysDictionaryData> list = sysDictionaryDataService.selectSysDictionaryDataList(sysDictionaryData);
        ExcelUtil<SysDictionaryData> util = new ExcelUtil<SysDictionaryData>(SysDictionaryData.class);
        util.exportExcel(response, list, "字典数据数据");
    }

    /**
     * 获取字典数据详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:dictionaryData:query')")
    @GetMapping(value = "/{dictCode}")
    public AjaxResult getInfo(@PathVariable("dictCode") Long dictCode)
    {
        return success(sysDictionaryDataService.selectSysDictionaryDataByDictCode(dictCode));
    }

    /**
     * 新增字典数据
     */
    @PreAuthorize("@ss.hasPermi('system:dictionaryData:add')")
    @Log(title = "字典数据", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysDictionaryData sysDictionaryData)
    {
        return toAjax(sysDictionaryDataService.insertSysDictionaryData(sysDictionaryData));
    }

    /**
     * 修改字典数据
     */
    @PreAuthorize("@ss.hasPermi('system:dictionaryData:edit')")
    @Log(title = "字典数据", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysDictionaryData sysDictionaryData)
    {
        return toAjax(sysDictionaryDataService.updateSysDictionaryData(sysDictionaryData));
    }

    /**
     * 删除字典数据
     */
    @PreAuthorize("@ss.hasPermi('system:dictionaryData:remove')")
    @Log(title = "字典数据", businessType = BusinessType.DELETE)
	@DeleteMapping("/{dictCodes}")
    public AjaxResult remove(@PathVariable Long[] dictCodes)
    {
        return toAjax(sysDictionaryDataService.deleteSysDictionaryDataByDictCodes(dictCodes));
    }
}
