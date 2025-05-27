package com.homestay.project.business.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.homestay.framework.aspectj.lang.annotation.Excel;
import com.homestay.framework.web.domain.BaseEntity;
import lombok.Data;

/**
 * 用户收藏信息对象 business_user_collect
 * 
 * @author 0
 * @date 2024-03-01
 */
@Data
@TableName("business_user_collect")
public class UserCollect extends BaseEntity {
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
    @Excel(name = "房间id")
    private Long roomId;

    /** 房间名字 */
    @Excel(name = "房间名字")
    private String roomName;

    /** 是否收藏，0：否，1：是 */
    @Excel(name = "是否收藏，0：否，1：是")
    private Integer isCollect;

    /** 删除标志：0 未删除，1已删除 */
    @Excel(name = "删除标志：0 未删除，1已删除")
    private Integer isDelete;


}
