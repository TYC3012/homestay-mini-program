package com.homestay.project.business.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.homestay.project.business.domain.Homestay;
import com.homestay.project.business.domain.Room;
import com.homestay.project.business.vo.RoomVO;
import com.homestay.project.wechat.domain.WechatUser;
import com.homestay.project.wechat.vo.WeChatUserVo;

import java.util.List;

/**
 * 房间信息Service接口
 * 
 * @author ruoyi
 * @date 2024-02-23
 */
public interface IRoomService extends IService<Room> {
    /**
     * 查询房间信息
     * 
     * @param id 房间信息主键
     * @return 房间信息
     */
    public Room selectRoomById(Long id);

    /**
     * 查询房间信息列表
     * 
     * @param roomVO 房间信息
     * @return 房间信息集合
     */
    public List<RoomVO> selectRoomList(RoomVO roomVO);

    /**
     * 新增房间信息
     * 
     * @param roomVO 房间信息
     * @return 结果
     */
    public int insertRoom(RoomVO roomVO);

    /**
     * 修改房间信息
     * 
     * @param room 房间信息
     * @return 结果
     */
    public int updateRoom(Room room);

    /**
     * 批量删除房间信息
     * 
     * @param ids 需要删除的房间信息主键集合
     * @return 结果
     */
    public int deleteRoomByIds(Long[] ids);

    /**
     * 删除房间信息信息
     * 
     * @param id 房间信息主键
     * @return 结果
     */
    public int deleteRoomById(Long id);

    /**
     * 根据用户信息推荐房间
     * @param roomVO
     * @return
     */
    public List<RoomVO> recommend(RoomVO roomVO);
}
