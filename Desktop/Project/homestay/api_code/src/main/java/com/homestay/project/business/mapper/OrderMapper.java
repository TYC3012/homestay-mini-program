package com.homestay.project.business.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.homestay.project.business.domain.Order;
import com.homestay.project.business.vo.RoomVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 个人订单信息Mapper接口
 * 
 * @author 0
 * @date 2024-03-03
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {
    /**
     * 查询个人订单信息
     * 
     * @param id 个人订单信息主键
     * @return 个人订单信息
     */
    public Order selectOrderById(Long id);

    /**
     * 查询个人订单信息列表
     * 
     * @param order 个人订单信息
     * @return 个人订单信息集合
     */
    public List<Order> selectOrderList(Order order);

    /**
     * 新增个人订单信息
     * 
     * @param order 个人订单信息
     * @return 结果
     */
    public int insertOrder(Order order);

    /**
     * 修改个人订单信息
     * 
     * @param order 个人订单信息
     * @return 结果
     */
    public int updateOrder(Order order);

    /**
     * 删除个人订单信息
     * 
     * @param id 个人订单信息主键
     * @return 结果
     */
    public int deleteOrderById(Long id);

    /**
     * 批量删除个人订单信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOrderByIds(Long[] ids);

    /**
     * 查询个人房间订单信息
     * @param userId 个人订单信息
     * @return
     */
    public List<RoomVO> userOrderList(@Param("userId")Long userId);
}
