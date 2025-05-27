package com.homestay.project.business.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.homestay.project.business.domain.Goods;
import com.homestay.project.business.domain.Order;
import com.homestay.project.business.domain.Room;
import com.homestay.project.business.vo.RoomVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 房间信息Mapper接口
 * 
 * @author ruoyi
 * @date 2024-02-23
 */
@Mapper
public interface RoomMapper extends BaseMapper<Room> {

    /**
     * 批量删除房间信息
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteRoomByIds(Long[] ids);


    /**
     * 查询房间的浏览信息
     *
     * @param roomVO 个人订单信息
     * @return 个人订单信息集合
     */
    public List<RoomVO> getViewRoomList(RoomVO roomVO);

    /**
     * 查询房间的浏览信息
     *
     * @param room 个人订单信息
     * @return 个人订单信息集合
     */
    public List<RoomVO> getRoomList(RoomVO room);

}
