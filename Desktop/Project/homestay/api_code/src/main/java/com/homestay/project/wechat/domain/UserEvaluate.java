package com.homestay.project.wechat.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.homestay.framework.aspectj.lang.annotation.Excel;
import com.homestay.framework.web.domain.BaseEntity;
import lombok.Data;

/**
 * 评价信息对象 business_user_evaluate
 * 
 * @author 陶
 * @date 2024-04-06
 */
@Data
@TableName("business_user_evaluate")
public class UserEvaluate extends BaseEntity {
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

    /** 房间id */
    @Excel(name = "订单id")
    private Long orderId;

    /** 房间名字 */
    @Excel(name = "房间名字")
    private String roomName;

    /** 得分星级，0：否，1：是 */
    @Excel(name = "得分星级，0：否，1：是")
    private Long star;

    /** 得分 */
    @Excel(name = "得分")
    private Long score;

    /** 评价描述 */
    @Excel(name = "评价描述")
    private String evaluateDesc;

    /** 删除标志：0 未删除，1已删除 */
    @Excel(name = "删除标志：0 未删除，1已删除")
    private Integer isDelete;


}
