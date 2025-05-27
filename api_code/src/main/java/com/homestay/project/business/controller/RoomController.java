package com.homestay.project.business.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.homestay.project.business.domain.Hotel;
import com.homestay.project.business.domain.Room;
import com.homestay.project.business.domain.UserView;
import com.homestay.project.business.service.IRoomService;
import com.homestay.project.business.service.IUserViewService;
import com.homestay.project.business.vo.RoomVO;
import com.homestay.project.wechat.domain.WechatUser;
import com.homestay.project.wechat.vo.WeChatUserVo;
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
 * 房间信息Controller
 * 
 * @author ruoyi
 * @date 2024-02-23
 */
@RestController
@RequestMapping("/business/room")
public class RoomController extends BaseController {
    @Autowired
    private IRoomService roomService;
    @Autowired
    private IUserViewService userViewService;

    /**
     * 查询房间信息列表
     */
    @PreAuthorize("@ss.hasPermi('business:room:list')")
    @GetMapping("/list")
    public TableDataInfo list(RoomVO room)
    {
        startPage();
        List<RoomVO> list = roomService.selectRoomList(room);
        return getDataTable(list);
    }

    /**
     * 导出房间信息列表
     */
    @PreAuthorize("@ss.hasPermi('business:room:export')")
    @Log(title = "房间信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, RoomVO Room)
    {
        List<RoomVO> list = roomService.selectRoomList(Room);
        ExcelUtil<RoomVO> util = new ExcelUtil<RoomVO>(RoomVO.class);
        util.exportExcel(response, list, "房间信息数据");
    }

    /**
     * 获取房间信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:room:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(roomService.selectRoomById(id));
    }

    /**
     * 新增房间信息
     */
    @PreAuthorize("@ss.hasPermi('business:room:add')")
    @Log(title = "房间信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RoomVO roomVO)
    {
        return toAjax(roomService.insertRoom(roomVO));
    }

    /**
     * 修改房间信息
     */
    @PreAuthorize("@ss.hasPermi('business:room:edit')")
    @Log(title = "房间信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Room room)
    {
        return toAjax(roomService.updateRoom(room));
    }

    /**
     * 删除房间信息
     */
    @PreAuthorize("@ss.hasPermi('business:room:remove')")
    @Log(title = "房间信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(roomService.deleteRoomByIds(ids));
    }

    /**
     * 小程序查询房间信息列表
     */
    @GetMapping("/api/list")
    public TableDataInfo apiList(RoomVO room) {
        startPage();
        List<RoomVO> list = roomService.selectRoomList(room);
        return getDataTable(list);
    }

    /**
     * 小程序查询房间信息列表
     */
    @GetMapping("/api/getRoomById")
    public AjaxResult getRoomById(RoomVO roomVO) {
        if("view".equals(roomVO.getQueryType())){
            UserView userView=new UserView();
            userView.setRoomId(roomVO.getId());
            userView.setUserId(roomVO.getUserId());
            userView.setUserName(roomVO.getUserName());
            userViewService.apiAdd(userView);
        }
        return success(roomService.selectRoomById(roomVO.getId()));
    }

    /**
     * 房间推荐信息列表，使用算法来实现
     */
    @GetMapping("/api/recommend")
    public TableDataInfo recommend(RoomVO roomVO) {
        startPage();
        List<RoomVO> list = roomService.recommend(roomVO);
        return getDataTable(list);
    }

}
