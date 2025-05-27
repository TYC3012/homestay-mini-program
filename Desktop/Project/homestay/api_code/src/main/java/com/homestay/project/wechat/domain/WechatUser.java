package com.homestay.project.wechat.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.homestay.framework.aspectj.lang.annotation.Excel;
import com.homestay.framework.web.domain.BaseEntity;
import lombok.Data;

/**
 * 微信用户对象 wechat_user
* @date 2024-02-26
 */
@Data
public class WechatUser extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 自增主键 */
    private Long id;

    /** 用户的唯一标识 */
    @Excel(name = "用户的唯一标识")
    private String openid;

    /** 昵称 */
    @Excel(name = "昵称")
    private String nickName;

    /** 真实姓名 */
    @Excel(name = "真实姓名")
    private String name;

    /** 手机号码 */
    @Excel(name = "手机号码")
    private String phone;

    /** 性别，0-未知 1-男性，2-女性 */
    @Excel(name = "性别，0-未知 1-男性，2-女性")
    private Integer gender;

    /** 地区 */
    @Excel(name = "地区")
    private String region;

    /** 用户头像URL */
    @Excel(name = "用户头像URL")
    private String avatar;

    /** 是否订阅公众号 0-否 1-是 */
    @Excel(name = "是否老板 0-否 1-是")
    private Integer isBoss;

    /** 删除标志（0代表存在 2代表删除） */
    private Integer delFlag;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Integer isDelete;

    /** 微信会话秘钥 */
    @TableField(exist = false)
    private String sessionKey;


}
