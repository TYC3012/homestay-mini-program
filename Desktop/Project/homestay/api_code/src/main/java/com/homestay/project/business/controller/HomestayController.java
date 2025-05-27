package com.homestay.project.business.controller;

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
import com.homestay.project.business.domain.Homestay;
import com.homestay.project.business.service.IHomestayService;
import com.homestay.framework.web.controller.BaseController;
import com.homestay.framework.web.domain.AjaxResult;
import com.homestay.common.utils.poi.ExcelUtil;
import com.homestay.framework.web.page.TableDataInfo;

/**
 * 名宿信息Controller
 * 
 * @author y
 * @date 2024-02-13
 */
@RestController
@RequestMapping("/business/homestay")
public class HomestayController extends BaseController
{
    @Autowired
    private IHomestayService homestayService;

    /**
     * 查询名宿信息列表
     */
    @PreAuthorize("@ss.hasPermi('business:homestay:list')")
    @GetMapping("/list")
    public TableDataInfo list(Homestay homestay)
    {
        startPage();
        List<Homestay> list = homestayService.selectHomestayList(homestay);
        return getDataTable(list);
    }

    /**
     * 导出名宿信息列表
     */
    @PreAuthorize("@ss.hasPermi('business:homestay:export')")
    @Log(title = "名宿信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Homestay homestay)
    {
        List<Homestay> list = homestayService.selectHomestayList(homestay);
        ExcelUtil<Homestay> util = new ExcelUtil<Homestay>(Homestay.class);
        util.exportExcel(response, list, "名宿信息数据");
    }

    /**
     * 获取名宿信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:homestay:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(homestayService.selectHomestayById(id));
    }

    /**
     * 新增名宿信息
     */
    @PreAuthorize("@ss.hasPermi('business:homestay:add')")
    @Log(title = "名宿信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Homestay homestay)
    {
        return toAjax(homestayService.insertHomestay(homestay));
    }

    /**
     * 修改名宿信息
     */
    @PreAuthorize("@ss.hasPermi('business:homestay:edit')")
    @Log(title = "名宿信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Homestay homestay)
    {
        return toAjax(homestayService.updateHomestay(homestay));
    }

    /**
     * 删除名宿信息
     */
    @PreAuthorize("@ss.hasPermi('business:homestay:remove')")
    @Log(title = "名宿信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(homestayService.deleteHomestayByIds(ids));
    }
}
