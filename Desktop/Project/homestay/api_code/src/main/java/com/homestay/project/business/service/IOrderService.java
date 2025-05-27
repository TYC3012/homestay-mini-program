package com.homestay.project.business.service;

import java.util.List;
import com.homestay.project.business.domain.Order;
import com.homestay.project.business.vo.OrderVo;

/**
 * 个人订单信息Service接口
 * 
 * @author 0
 * @date 2024-03-03
 */
public interface IOrderService 
{
    /**
     * 查询个人订单信息
     * 
     * @param id 个人订单信息主键
     * @return 个人订单信息
     */
    public OrderVo selectOrderById(Long id);

    /**
     * 查询个人订单信息列表
     * 
     * @param order 个人订单信息
     * @return 个人订单信息集合
     */
    public List<OrderVo> selectOrderList(Order order);

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
     * 批量删除个人订单信息
     * 
     * @param ids 需要删除的个人订单信息主键集合
     * @return 结果
     */
    public int deleteOrderByIds(Long[] ids);

    /**
     * 删除个人订单信息信息
     * 
     * @param id 个人订单信息主键
     * @return 结果
     */
    public int deleteOrderById(Long id);

    /**
     * 删除个人订单信息信息
     *
     * @param orderVo 个人订单信息主键
     * @return 结果
     */
    public int apiSave(OrderVo orderVo);

    /**
     * 删除个人订单信息信息
     *
     * @param orderVo 个人订单信息主键
     * @return 结果
     */
    public int apiDelete(OrderVo orderVo);

    /**
     * 删除个人订单信息信息
     *
     * @param orderVo 个人订单信息主键
     * @return 结果
     */
    public int apiUpdate(OrderVo orderVo);











}
