package com.homestay.project.system.domain;

import com.homestay.framework.aspectj.lang.annotation.Excel;
import com.homestay.framework.web.domain.BaseEntity;
import lombok.Data;

@Data
public class BusUser extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 用户id */
    private String userId;

    /** 父级id */
    @Excel(name = "父级id")
    private String parentId;

    /** 姓名 */
    @Excel(name = "姓名")
    private String noticeContent;

    /** 登录名 */
    @Excel(name = "登录名")
    private String userName;

    /** 微信昵称 */
    @Excel(name = "微信昵称")
    private String nickName;

    /** 用户类型 */
    @Excel(name = "用户类型")
    private String userType;

    /** 邮箱 */
    @Excel(name = "邮箱")
    private String email;

    /** 电话号码 */
    @Excel(name = "电话号码")
    private String phoneNumber;

    /** 性别 */
    @Excel(name = "性别")
    private String sex;

    /** 头像 */
    @Excel(name = "头像")
    private String avatar;

    /** 密码 */
    @Excel(name = "密码")
    private String password;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 登录IP */
    @Excel(name = "登录IP")
    private String loginIp;

    /** 登录日期 */
    @Excel(name = "登录日期")
    private String loginDate;

    /** 设备品牌 */
    @Excel(name = "设备品牌")
    private String wxBrand;

    /** 设备型号 */
    @Excel(name = "设备型号")
    private String wxModel;

    /** 微信语言 */
    @Excel(name = "微信语言")
    private String wxLanguage;

    /** 微信版本 */
    @Excel(name = "微信版本")
    private String wxVersion;

    /** 操作系统 */
    @Excel(name = "操作系统")
    private String wxPlatform;

    /** 系统版本 */
    @Excel(name = "系统版本")
    private String wxSystem;

    /** 基础库版本 */
    @Excel(name = "基础库版本")
    private String wxSdkVersion;

    /** 位置权限 */
    @Excel(name = "位置权限")
    private String wxLocationEnabled;

    /** wifi权限 */
    @Excel(name = "wifi权限")
    private String wxWifiEnabled;

    /** 微信唯一id */
    @Excel(name = "微信唯一id")
    private String openId;

    /** 小程序唯一id */
    @Excel(name = "小程序唯一id")
    private String unionId;

    /** 删除标志 */
    private String delFlag;

    /** 行政区划code */
    @Excel(name = "行政区划code")
    private String areaCode;

    /** 分润比例 */
    @Excel(name = "分润比例")
    private int scale;

}
