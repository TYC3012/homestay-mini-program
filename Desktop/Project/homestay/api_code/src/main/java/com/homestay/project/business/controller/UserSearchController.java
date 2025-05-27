package com.homestay.project.business.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.homestay.framework.security.LoginUser;
import com.homestay.project.business.vo.UserSearchVo;
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
import com.homestay.project.business.domain.UserSearch;
import com.homestay.project.business.service.IUserSearchService;
import com.homestay.framework.web.controller.BaseController;
import com.homestay.framework.web.domain.AjaxResult;
import com.homestay.common.utils.poi.ExcelUtil;
import com.homestay.framework.web.page.TableDataInfo;

/**
 * 用户搜索记录Controller
 * 
 * @author 0
 * @date 2024-02-29
 */
@RestController
@RequestMapping("/business/search")
public class UserSearchController extends BaseController
{
    @Autowired
    private IUserSearchService userSearchService;

    /**
     * 查询用户搜索记录列表
     */
    @PreAuthorize("@ss.hasPermi('business:search:list')")
    @GetMapping("/list")
    public TableDataInfo list(UserSearchVo userSearch) {
        startPage();
        List<UserSearchVo> list = userSearchService.selectUserSearchList(userSearch);
        return getDataTable(list);
    }

    /**
     * 导出用户搜索记录列表
     */
    @PreAuthorize("@ss.hasPermi('business:search:export')")
    @Log(title = "用户搜索记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, UserSearchVo businessUserSearch)
    {
        List<UserSearchVo> list = userSearchService.selectUserSearchList(businessUserSearch);
        ExcelUtil<UserSearchVo> util = new ExcelUtil<UserSearchVo>(UserSearchVo.class);
        util.exportExcel(response, list, "用户搜索记录数据");
    }

    /**
     * 获取用户搜索记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:search:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(userSearchService.selectBusinessUserSearchById(id));
    }

    /**
     * 新增用户搜索记录
     */
    @PreAuthorize("@ss.hasPermi('business:search:add')")
    @Log(title = "用户搜索记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody UserSearch businessUserSearch)
    {
        return toAjax(userSearchService.insertBusinessUserSearch(businessUserSearch));
    }

    /**
     * 修改用户搜索记录
     */
    @PreAuthorize("@ss.hasPermi('business:search:edit')")
    @Log(title = "用户搜索记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody UserSearch businessUserSearch)
    {
        return toAjax(userSearchService.updateBusinessUserSearch(businessUserSearch));
    }

    /**
     * 删除用户搜索记录
     */
    @PreAuthorize("@ss.hasPermi('business:search:remove')")
    @Log(title = "用户搜索记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(userSearchService.deleteBusinessUserSearchByIds(ids));
    }


    /**
     * 新增用户搜索记录
     */
    @Log(title = "小程序端新增用户搜索记录", businessType = BusinessType.INSERT)
    @PostMapping("/api/add")
    public AjaxResult apiAdd(@RequestBody UserSearch userSearch){
        String userName=getLoginUser().getWechatUser().getNickName();
        userSearch.setCreateBy(userName);
        return toAjax(userSearchService.apiAdd(userSearch));
    }

    /**
     * 小程序查询用户搜索记录列表
     */
    @GetMapping("/api/list")
    public TableDataInfo apiList(UserSearchVo userSearch) {
        startPage();
        List<UserSearchVo> list = userSearchService.selectUserSearchList(userSearch);
        return getDataTable(list);
    }
}
