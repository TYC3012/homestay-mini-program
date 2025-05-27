package com.homestay.project.business.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.homestay.project.business.domain.UserView;
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
import com.homestay.project.business.domain.UserCollect;
import com.homestay.project.business.service.IUserCollectService;
import com.homestay.framework.web.controller.BaseController;
import com.homestay.framework.web.domain.AjaxResult;
import com.homestay.common.utils.poi.ExcelUtil;
import com.homestay.framework.web.page.TableDataInfo;

/**
 * 用户收藏信息Controller
 * 
 * @author 0
 * @date 2024-03-01
 */
@RestController
@RequestMapping("/business/collect")
public class UserCollectController extends BaseController
{
    @Autowired
    private IUserCollectService userCollectService;

    /**
     * 查询用户收藏信息列表
     */
    @PreAuthorize("@ss.hasPermi('business:collect:list')")
    @GetMapping("/list")
    public TableDataInfo list(UserCollect userCollect)
    {
        startPage();
        List<UserCollect> list = userCollectService.selectUserCollectList(userCollect);
        return getDataTable(list);
    }

    /**
     * 导出用户收藏信息列表
     */
    @PreAuthorize("@ss.hasPermi('business:collect:export')")
    @Log(title = "用户收藏信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, UserCollect userCollect)
    {
        List<UserCollect> list = userCollectService.selectUserCollectList(userCollect);
        ExcelUtil<UserCollect> util = new ExcelUtil<UserCollect>(UserCollect.class);
        util.exportExcel(response, list, "用户收藏信息数据");
    }

    /**
     * 获取用户收藏信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:collect:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(userCollectService.selectUserCollectById(id));
    }

    /**
     * 新增用户收藏信息
     */
    @PreAuthorize("@ss.hasPermi('business:collect:add')")
    @Log(title = "用户收藏信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody UserCollect userCollect)
    {
        return toAjax(userCollectService.insertUserCollect(userCollect));
    }

    /**
     * 修改用户收藏信息
     */
    @PreAuthorize("@ss.hasPermi('business:collect:edit')")
    @Log(title = "用户收藏信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody UserCollect userCollect)
    {
        return toAjax(userCollectService.updateUserCollect(userCollect));
    }

    /**
     * 删除用户收藏信息
     */
    @PreAuthorize("@ss.hasPermi('business:collect:remove')")
    @Log(title = "用户收藏信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(userCollectService.deleteUserCollectByIds(ids));
    }


    /**
     * 新增用户收藏
     */
    @PostMapping("/api/save")
    public AjaxResult apiSave(@RequestBody UserCollect userCollect){
        return toAjax(userCollectService.apiSave(userCollect));
    }
}
