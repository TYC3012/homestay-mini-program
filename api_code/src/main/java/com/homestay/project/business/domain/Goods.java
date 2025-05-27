package com.homestay.project.business.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.homestay.framework.aspectj.lang.annotation.Excel;
import com.homestay.framework.web.domain.BaseEntity;
import lombok.Data;


/**
 * 商品对象 goods
 * 
 * @author ruoyi
 * @date 2023-11-14
 */
@Data
public class Goods extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /** 商品名字 */
    @Excel(name = "商品名字")
    private String goodsName;

    /** 商品名字 */
    @Excel(name = "商品图片")
    private String goodsFile;

    /** 商品是否上架，0：下架，1：上架 */
    @Excel(name = "商品是否上架，0：下架，1：上架")
    private Integer putWayFlag;


}
