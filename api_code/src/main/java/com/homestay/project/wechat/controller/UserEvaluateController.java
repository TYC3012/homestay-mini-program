package com.homestay.project.wechat.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.homestay.project.business.domain.Order;
import com.homestay.project.business.vo.OrderVo;
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
import com.homestay.project.wechat.domain.UserEvaluate;
import com.homestay.project.wechat.service.IUserEvaluateService;
import com.homestay.framework.web.controller.BaseController;
import com.homestay.framework.web.domain.AjaxResult;
import com.homestay.common.utils.poi.ExcelUtil;
import com.homestay.framework.web.page.TableDataInfo;

/**
 * 评价信息Controller
 * 
 * @author 陶
 * @date 2024-04-06
 */
@RestController
@RequestMapping("/wechat/evaluate")
public class UserEvaluateController extends BaseController
{
    @Autowired
    private IUserEvaluateService userEvaluateService;

    /**
     * 查询评价信息列表
     */
    @PreAuthorize("@ss.hasPermi('wechat:evaluate:list')")
    @GetMapping("/list")
    public TableDataInfo list(UserEvaluate userEvaluate)
    {
        startPage();
        List<UserEvaluate> list = userEvaluateService.selectUserEvaluateList(userEvaluate);
        return getDataTable(list);
    }

    /**
     * 导出评价信息列表
     */
    @PreAuthorize("@ss.hasPermi('wechat:evaluate:export')")
    @Log(title = "评价信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, UserEvaluate userEvaluate)
    {
        List<UserEvaluate> list = userEvaluateService.selectUserEvaluateList(userEvaluate);
        ExcelUtil<UserEvaluate> util = new ExcelUtil<UserEvaluate>(UserEvaluate.class);
        util.exportExcel(response, list, "评价信息数据");
    }

    /**
     * 获取评价信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('wechat:evaluate:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(userEvaluateService.selectUserEvaluateById(id));
    }

    /**
     * 新增评价信息
     */
    @PreAuthorize("@ss.hasPermi('wechat:evaluate:add')")
    @Log(title = "评价信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody UserEvaluate userEvaluate)
    {
        return toAjax(userEvaluateService.insertUserEvaluate(userEvaluate));
    }

    /**
     * 修改评价信息
     */
    @PreAuthorize("@ss.hasPermi('wechat:evaluate:edit')")
    @Log(title = "评价信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody UserEvaluate userEvaluate)
    {
        return toAjax(userEvaluateService.updateUserEvaluate(userEvaluate));
    }

    /**
     * 删除评价信息
     */
    @PreAuthorize("@ss.hasPermi('wechat:evaluate:remove')")
    @Log(title = "评价信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(userEvaluateService.deleteUserEvaluateByIds(ids));
    }

    /**
     * 新增用户评价
     */
    @PostMapping("/api/save")
    public AjaxResult apiSave(@RequestBody UserEvaluate userEvaluate){
        return toAjax(userEvaluateService.apiSave(userEvaluate));
    }

    /**
     * 小程序查询评价详情信息
     */
    @GetMapping("/api/getUserEvaluate")
    public AjaxResult getUserEvaluate(UserEvaluate userEvaluate) {
        return success(userEvaluateService.getUserEvaluate(userEvaluate));
    }
}
