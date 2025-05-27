package com.homestay.project.business.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.homestay.framework.aspectj.lang.annotation.Excel;
import com.homestay.framework.web.domain.BaseEntity;
import lombok.Data;

/**
 * 酒店信息对象 business_hotel
 * 
 * @author y
 * @date 2024-02-13
 */
@Data
@TableName("business_hotel")
public class Hotel extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 店主id */
    @Excel(name = "店主id")
    private Long bossId;

    /** 民宿名字 */
    @Excel(name = "民宿名字")
    private String homeName;

    /** 民宿图片 */
    @Excel(name = "民宿图片")
    private String homeFile;

    /** 所在省份 */
    @Excel(name = "所在省份")
    private String province;

    /** 所在城市 */
    @Excel(name = "所在城市")
    private String city;

    /** 名宿地址 */
    @Excel(name = "名宿地址")
    private String address;

    /** 是否营业，0：否，1：是 */
    @Excel(name = "是否营业，0：否，1：是")
    private Integer asTrade;

    /** 删除标志：0 未删除，1已删除 */
    @Excel(name = "删除标志：0 未删除，1已删除")
    private Integer isDelete;


}
