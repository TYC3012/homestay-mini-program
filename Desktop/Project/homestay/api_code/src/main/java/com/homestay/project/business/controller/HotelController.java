package com.homestay.project.business.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.homestay.framework.security.LoginUser;
import com.homestay.project.business.domain.Hotel;
import com.homestay.project.business.service.IHotelService;
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

import com.homestay.framework.web.controller.BaseController;
import com.homestay.framework.web.domain.AjaxResult;
import com.homestay.common.utils.poi.ExcelUtil;
import com.homestay.framework.web.page.TableDataInfo;

/**
 * 酒店信息Controller
 * 
 * @author y
 * @date 2024-02-13
 */
@RestController
@RequestMapping("/business/hotel")
public class HotelController extends BaseController
{
    @Autowired
    private IHotelService hotelService;

    /**
     * 查询酒店信息列表
     */
    @PreAuthorize("@ss.hasPermi('business:hotel:list')")
    @GetMapping("/list")
    public TableDataInfo list(Hotel hotel)
    {
        startPage();
        LoginUser loginUser= getLoginUser();
        //System.out.println("当前登陆者信息：："+loginUser);
        String userName=loginUser.getUsername();
        if(!"admin".equals(userName)){
            hotel.setCreateBy(userName);
        }
        List<Hotel> list = hotelService.selectHotelList(hotel);
        return getDataTable(list);
    }

    /**
     * 导出酒店信息列表
     */
    @PreAuthorize("@ss.hasPermi('business:hotel:export')")
    @Log(title = "酒店信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Hotel hotel)
    {
        List<Hotel> list = hotelService.selectHotelList(hotel);
        ExcelUtil<Hotel> util = new ExcelUtil<Hotel>(Hotel.class);
        util.exportExcel(response, list, "酒店信息数据");
    }

    /**
     * 获取酒店信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:hotel:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(hotelService.selectHotelById(id));
    }

    /**
     * 新增酒店信息
     */
    @PreAuthorize("@ss.hasPermi('business:hotel:add')")
    @Log(title = "酒店信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Hotel hotel)
    {
        hotel.setCreateBy(getUsername());
        return toAjax(hotelService.insertHotel(hotel));
    }

    /**
     * 修改酒店信息
     */
    @PreAuthorize("@ss.hasPermi('business:hotel:edit')")
    @Log(title = "酒店信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Hotel hotel)
    {
        hotel.setUpdateBy(getUsername());
        return toAjax(hotelService.updateHotel(hotel));
    }

    /**
     * 删除酒店信息
     */
    @PreAuthorize("@ss.hasPermi('business:hotel:remove')")
    @Log(title = "酒店信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(hotelService.deleteHotelByIds(ids));
    }

    /**
     * 查询所有的酒店信息列表
     */
    @GetMapping("/allList")
    public AjaxResult allList() {
        Hotel hotel=new Hotel();
        LoginUser loginUser= getLoginUser();
        //System.out.println("当前登陆者信息：："+loginUser);
        String userName=loginUser.getUsername();
        if(!"admin".equals(userName)){
            hotel.setCreateBy(userName);
        }
        List<Hotel> list = hotelService.selectHotelList(hotel);
        return success(list);
    }

    /**
     * 查询所有的酒店信息列表
     */
    @GetMapping("/api/list")
    public TableDataInfo apiList(Hotel hotel) {
        startPage();
        List<Hotel> list = hotelService.selectHotelList(hotel);
        return getDataTable(list);
    }
}
