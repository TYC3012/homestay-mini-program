package com.homestay.project.business.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.homestay.framework.aspectj.lang.annotation.Excel;
import com.homestay.framework.web.domain.BaseEntity;
import lombok.Data;

/**
 * 房间信息对象 business_room
 * @date 2024-02-23
 */
@Data
@TableName("business_room")
public class Room extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 关联的民宿id */
    @Excel(name = "关联的民宿id")
    private Long hotelId;

    /** 房间名字 */
    @Excel(name = "民宿名字")
    private String hotelName;

    /** 房间名字 */
    @Excel(name = "房间名字")
    private String roomName;

    /** 房间号 */
    @Excel(name = "房间号")
    private String roomNumber;

    /** 房间主图 */
    @Excel(name = "房间主图")
    private String mainImg;

    /** 房间详情图 */
    @Excel(name = "房间详情图")
    private String detailsImg;

    /** 价格 */
    @Excel(name = "价格")
    private Long price;

    /** 房间区域 */
    @Excel(name = "房间区域")
    private String area;

    /** 可入住人数 */
    @Excel(name = "可入住人数")
    private Long occupancy;

    /** 床型 */
    @Excel(name = "床型")
    private String bedType;

    /** 楼层 */
    @Excel(name = "楼层")
    private String floor;

    /** 删除标志：0 未删除，1已删除 */
    @Excel(name = "删除标志：0 未删除，1已删除")
    private Integer isDelete;


}
