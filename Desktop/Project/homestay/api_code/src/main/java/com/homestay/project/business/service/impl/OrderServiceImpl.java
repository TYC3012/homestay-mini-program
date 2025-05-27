package com.homestay.project.business.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.homestay.common.enums.WhereEnum;
import com.homestay.common.utils.DateUtils;
import com.homestay.common.utils.bean.BeanUtils;
import com.homestay.project.business.domain.Room;
import com.homestay.project.business.domain.UserView;
import com.homestay.project.business.mapper.RoomMapper;
import com.homestay.project.business.mapper.UserViewMapper;
import com.homestay.project.business.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.homestay.project.business.mapper.OrderMapper;
import com.homestay.project.business.domain.Order;
import com.homestay.project.business.service.IOrderService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 个人订单信息Service业务层处理
 * 
 * @author 0
 * @date 2024-03-03
 */
@Service
public class OrderServiceImpl implements IOrderService 
{
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private UserViewMapper userViewMapper;
    @Autowired
    private RoomMapper roomMapper;

    /**
     * 查询个人订单信息
     * 
     * @param id 个人订单信息主键
     * @return 个人订单信息
     */
    @Override
    public OrderVo selectOrderById(Long id) {
        Order order=orderMapper.selectOrderById(id);
        OrderVo orderVo=new OrderVo();
        if(Objects.nonNull(order)){
            orderVo=BeanUtil.copyProperties(order,OrderVo.class);
            Long roomId=order.getRoomId();
            Room room=roomMapper.selectById(roomId);
            orderVo.setRoom(room);
        }
        return orderVo;
    }

    /**
     * 查询个人订单信息列表
     * 
     * @param order 个人订单信息
     * @return 个人订单信息
     */
    @Override
    public List<OrderVo> selectOrderList(Order order) {

        List<Order> orders=orderMapper.selectOrderList(order);
        List<Long> roomIds=orders.stream().map(Order::getRoomId).collect(Collectors.toList());
        List<OrderVo> orderVoList=BeanUtil.copyToList(orders,OrderVo.class);
        if(roomIds.size()>0){
            List<Room> roomList=roomMapper.selectBatchIds(roomIds);
            Map<Long,Room> roomMap=roomList.stream().collect(Collectors.toMap(Room::getId,Room->Room));
            orderVoList.stream().forEach(a->{
                a.setRoom(roomMap.getOrDefault(a.getRoomId(),new Room()));
            });
        }

        return orderVoList;
    }

    /**
     * 新增个人订单信息
     * 
     * @param order 个人订单信息
     * @return 结果
     */
    @Override
    public int insertOrder(Order order)
    {
        order.setCreateTime(DateUtils.getNowDate());
        return orderMapper.insertOrder(order);
    }

    /**
     * 修改个人订单信息
     * 
     * @param order 个人订单信息
     * @return 结果
     */
    @Override
    public int updateOrder(Order order)
    {
        order.setUpdateTime(DateUtils.getNowDate());
        return orderMapper.updateOrder(order);
    }

    /**
     * 批量删除个人订单信息
     * 
     * @param ids 需要删除的个人订单信息主键
     * @return 结果
     */
    @Override
    public int deleteOrderByIds(Long[] ids)
    {
        return orderMapper.deleteOrderByIds(ids);
    }

    /**
     * 删除个人订单信息信息
     * 
     * @param id 个人订单信息主键
     * @return 结果
     */
    @Override
    public int deleteOrderById(Long id)
    {
        return orderMapper.deleteOrderById(id);
    }

    @Override
    @Transactional
    public int apiSave(OrderVo orderVo) {
        LambdaQueryWrapper<UserView> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserView::getUserId,orderVo.getUserId());
        lambdaQueryWrapper.eq(UserView::getRoomId,orderVo.getRoomId());
        lambdaQueryWrapper.eq(UserView::getIsDelete, WhereEnum.NO.getValue());
        //判断用户是否浏览过该房间
        UserView userViewExist=userViewMapper.selectOne(lambdaQueryWrapper);
        if(Objects.nonNull(userViewExist)){
            userViewExist.setIsOrder(WhereEnum.YES.getValue());
            userViewMapper.updateById(userViewExist);
        }
        //修改浏览记录为已下单
        if(Objects.nonNull(orderVo.getId())){
            return orderMapper.updateById(orderVo);
        }
        Long id = IdUtil.getSnowflake(10, 5).nextId();
        String sn="SN"+IdUtil.getSnowflake(8, 5).nextId();
        orderVo.setId(id);
        orderVo.setSn(sn);
        return orderMapper.insert(orderVo);
    }

    @Override
    public int apiDelete(OrderVo orderVo) {
        Order order=orderMapper.selectById(orderVo.getId());
        if(Objects.nonNull(order)){
            return orderMapper.deleteById(order);
        }
        return 0;
    }

    @Override
    public int apiUpdate(OrderVo orderVo) {
        Order order=orderMapper.selectById(orderVo.getId());
        if(Objects.nonNull(order)){
            orderVo.setId(order.getId());
            return orderMapper.updateById(orderVo);
        }
        return 0;
    }
}
