package com.homestay.project.business.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.homestay.framework.aspectj.lang.annotation.Excel;
import com.homestay.framework.web.domain.BaseEntity;
import lombok.Data;

/**
 * 个人订单信息对象 business_order
 * 
 * @author 0
 * @date 2024-03-03
 */
@Data
@TableName("business_order")
public class Order extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 用户id */
    @Excel(name = "用户id")
    private Long userId;

    /** 用户姓名 */
    @Excel(name = "订单编号")
    private String sn;

    /** 用户姓名 */
    @Excel(name = "用户姓名")
    private String userName;

    /** 房间id */
    @Excel(name = "房间id")
    private Long roomId;

    /** 房间名称 */
    @Excel(name = "房间名称")
    private String roomName;

    /** 订单金额 */
    @Excel(name = "订单金额")
    private BigDecimal amount;

    /** 订单状态 */
    @Excel(name = "订单状态")
    private Integer status;

    /** 下单时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "下单时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date orderTime;

    /** 下单时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "入住时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date checkInTime;

    /** 下单时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "退房时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date checkOutTime;

    /** 订单状态 */
    @Excel(name = "入住天数")
    private Integer days;



    /** 删除标志：0 未删除，1已删除 */
    @Excel(name = "删除标志：0 未删除，1已删除")
    private Integer isDelete;

    /** 房间id */
    @Excel(name = "民宿id")
    private Long hotelId;





}
