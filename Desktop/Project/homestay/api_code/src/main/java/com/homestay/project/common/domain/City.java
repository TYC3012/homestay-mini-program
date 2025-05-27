package com.homestay.project.common.domain;

import com.homestay.framework.aspectj.lang.annotation.Excel;
import com.homestay.framework.web.domain.BaseEntity;
import lombok.Data;

/**
 * 城市对象 city
 * @date 2024-02-22
 */
@Data
public class City extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    /** 城市名称 */
    @Excel(name = "城市名称")
    private String name;

    /** 省份id */
    @Excel(name = "省份id")
    private Integer pid;


}
