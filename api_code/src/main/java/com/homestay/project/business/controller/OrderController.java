package com.homestay.project.business.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.homestay.project.business.domain.Room;
import com.homestay.project.business.domain.UserCollect;
import com.homestay.project.business.domain.UserView;
import com.homestay.project.business.vo.OrderVo;
import com.homestay.project.business.vo.RoomVO;
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
import com.homestay.project.business.domain.Order;
import com.homestay.project.business.service.IOrderService;
import com.homestay.framework.web.controller.BaseController;
import com.homestay.framework.web.domain.AjaxResult;
import com.homestay.common.utils.poi.ExcelUtil;
import com.homestay.framework.web.page.TableDataInfo;

/**
 * 个人订单信息Controller
 * 
 * @author 0
 * @date 2024-03-03
 */
@RestController
@RequestMapping("/business/order")
public class OrderController extends BaseController {
    @Autowired
    private IOrderService orderService;

    /**
     * 查询个人订单信息列表
     */
    @PreAuthorize("@ss.hasPermi('business:order:list')")
    @GetMapping("/list")
    public TableDataInfo list(Order order)
    {
        startPage();
        List<OrderVo> list = orderService.selectOrderList(order);
        return getDataTable(list);
    }

    /**
     * 导出个人订单信息列表
     */
    @PreAuthorize("@ss.hasPermi('business:order:export')")
    @Log(title = "个人订单信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Order order)
    {
        List<OrderVo> list = orderService.selectOrderList(order);
        ExcelUtil<OrderVo> util = new ExcelUtil<OrderVo>(OrderVo.class);
        util.exportExcel(response, list, "个人订单信息数据");
    }

    /**
     * 获取个人订单信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:order:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(orderService.selectOrderById(id));
    }

    /**
     * 新增个人订单信息
     */
    @PreAuthorize("@ss.hasPermi('business:order:add')")
    @Log(title = "个人订单信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Order order)
    {
        return toAjax(orderService.insertOrder(order));
    }

    /**
     * 修改个人订单信息
     */
    @PreAuthorize("@ss.hasPermi('business:order:edit')")
    @Log(title = "个人订单信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Order order)
    {
        return toAjax(orderService.updateOrder(order));
    }

    /**
     * 删除个人订单信息
     */
    @PreAuthorize("@ss.hasPermi('business:order:remove')")
    @Log(title = "个人订单信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(orderService.deleteOrderByIds(ids));
    }

    /**
     * 新增用户收藏
     */
    @PostMapping("/api/save")
    public AjaxResult apiSave(@RequestBody OrderVo orderVo){
        return toAjax(orderService.apiSave(orderVo));
    }

    /**
     * 小程序查询订单列表
     */
    @GetMapping("/api/list")
    public TableDataInfo apiList(Order order) {
        startPage();
        List<OrderVo> list = orderService.selectOrderList(order);
        return getDataTable(list);
    }

    /**
     * 小程序查询订单详情信息
     */
    @GetMapping("/api/getOrderById")
    public AjaxResult getOrderById(Order order) {
        return success(orderService.selectOrderById(order.getId()));
    }

    /**
     * 删除
     */
    @PostMapping("/api/delete")
    public AjaxResult apiDelete(@RequestBody OrderVo orderVo){
        return toAjax(orderService.apiDelete(orderVo));
    }

    /**
     * 删除
     */
    @PostMapping("/api/update")
    public AjaxResult apiUpdate(@RequestBody OrderVo orderVo){
        return toAjax(orderService.apiUpdate(orderVo));
    }
}
