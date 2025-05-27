package com.homestay.project.business.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.homestay.project.business.domain.UserSearch;
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
import com.homestay.project.business.domain.UserView;
import com.homestay.project.business.service.IUserViewService;
import com.homestay.framework.web.controller.BaseController;
import com.homestay.framework.web.domain.AjaxResult;
import com.homestay.common.utils.poi.ExcelUtil;
import com.homestay.framework.web.page.TableDataInfo;

/**
 * 客户浏览信息，用户计算推荐Controller
 * 
 * @author ruoyi
 * @date 2024-02-29
 */
@RestController
@RequestMapping("/business/view")
public class UserViewController extends BaseController
{
    @Autowired
    private IUserViewService userViewService;

    /**
     * 查询客户浏览信息，用户计算推荐列表
     */
    @PreAuthorize("@ss.hasPermi('business:view:list')")
    @GetMapping("/list")
    public TableDataInfo list(UserView businessUserView)
    {
        startPage();
        List<UserView> list = userViewService.selectBusinessUserViewList(businessUserView);
        return getDataTable(list);
    }

    /**
     * 导出客户浏览信息，用户计算推荐列表
     */
    @PreAuthorize("@ss.hasPermi('business:view:export')")
    @Log(title = "客户浏览信息，用户计算推荐", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, UserView businessUserView)
    {
        List<UserView> list = userViewService.selectBusinessUserViewList(businessUserView);
        ExcelUtil<UserView> util = new ExcelUtil<UserView>(UserView.class);
        util.exportExcel(response, list, "客户浏览信息，用户计算推荐数据");
    }

    /**
     * 获取客户浏览信息，用户计算推荐详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:view:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(userViewService.selectBusinessUserViewById(id));
    }

    /**
     * 新增客户浏览信息，用户计算推荐
     */
    @PreAuthorize("@ss.hasPermi('business:view:add')")
    @Log(title = "客户浏览信息，用户计算推荐", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody UserView businessUserView)
    {
        return toAjax(userViewService.insertBusinessUserView(businessUserView));
    }

    /**
     * 修改客户浏览信息，用户计算推荐
     */
    @PreAuthorize("@ss.hasPermi('business:view:edit')")
    @Log(title = "客户浏览信息，用户计算推荐", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody UserView businessUserView)
    {
        return toAjax(userViewService.updateBusinessUserView(businessUserView));
    }

    /**
     * 删除客户浏览信息，用户计算推荐
     */
    @PreAuthorize("@ss.hasPermi('business:view:remove')")
    @Log(title = "客户浏览信息，用户计算推荐", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(userViewService.deleteBusinessUserViewByIds(ids));
    }

    /**
     * 新增用户搜索记录
     */
    @PostMapping("/api/add")
    public AjaxResult apiAdd(@RequestBody UserView userView){
        return toAjax(userViewService.apiAdd(userView));
    }

    /**
     * 用户离开
     */
    @PostMapping("/api/leave")
    public AjaxResult apiLeave(@RequestBody UserView userView){
        return toAjax(userViewService.apiLeave(userView));
    }
}
