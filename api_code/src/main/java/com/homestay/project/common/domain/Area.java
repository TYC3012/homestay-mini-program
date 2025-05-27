package com.homestay.project.common.domain;

import com.homestay.framework.aspectj.lang.annotation.Excel;
import com.homestay.framework.web.domain.BaseEntity;
import lombok.Data;

/**
 * 区域对象 area
 * @date 2024-02-22
 */
@Data
public class Area extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    /** 区域名称 */
    @Excel(name = "区域名称")
    private String name;

    /** 城市id */
    @Excel(name = "城市id")
    private Integer pid;


}
