package com.homestay.project.common.domain;

import com.homestay.framework.aspectj.lang.annotation.Excel;
import com.homestay.framework.web.domain.BaseEntity;
import lombok.Data;

/**
 * 省份对象 province
 * @date 2024-02-22
 */
@Data
public class Province extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    /** 省份名称 */
    @Excel(name = "省份名称")
    private String name;


}
