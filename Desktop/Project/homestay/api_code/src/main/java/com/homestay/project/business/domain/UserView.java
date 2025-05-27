package com.homestay.project.business.domain;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.homestay.framework.aspectj.lang.annotation.Excel;
import com.homestay.framework.web.domain.BaseEntity;
import lombok.Data;

/**
 * 客户浏览信息，用户计算推荐对象 business_user_view
 * 
 * @author ruoyi
 * @date 2024-02-29
 */
@Data
@TableName("business_user_view")
public class UserView extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 用户id */
    @Excel(name = "用户id")
    private Long userId;

    /** 用户姓名 */
    @Excel(name = "用户姓名")
    private String userName;

    /** 房间id */
    private Long roomId;

    /** 房间名字 */
    @Excel(name = "房间名字")
    private String roomName;

    /** 是否预订，0：否，1：是 */
    @Excel(name = "是否预订，0：否，1：是")
    private Integer isOrder;

    /** 浏览时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Excel(name = "浏览时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date viewTime;

    /** 浏览时长 */
    @Excel(name = "浏览时长")
    private Integer viewDuration;

    /** 浏览次数 */
    @Excel(name = "浏览次数")
    private Integer timeNumber;

    /** 删除标志：0 未删除，1已删除 */
    @Excel(name = "删除标志：0 未删除，1已删除")
    private Integer isDelete;


}
