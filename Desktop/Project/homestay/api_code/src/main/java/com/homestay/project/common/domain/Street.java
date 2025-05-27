package com.homestay.project.common.domain;

import com.homestay.framework.aspectj.lang.annotation.Excel;
import com.homestay.framework.web.domain.BaseEntity;
import lombok.Data;
import nonapi.io.github.classgraph.json.Id;

/**
 * 街道对象 street
 * @date 2024-02-22
 */
@Data
public class Street extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @Id
    private Integer id;

    /** 街道名称 */
    @Excel(name = "街道名称")
    private String name;

    /** 区域id */
    @Excel(name = "区域id")
    private Integer pid;


}
