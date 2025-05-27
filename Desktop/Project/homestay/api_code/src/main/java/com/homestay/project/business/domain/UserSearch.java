package com.homestay.project.business.domain;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.homestay.framework.aspectj.lang.annotation.Excel;
import com.homestay.framework.web.domain.BaseEntity;
import lombok.Data;

/**
 * 用户搜索记录对象 business_user_search
 * 
 * @author 0
 * @date 2024-02-29
 */
@Data
@TableName("business_user_search")
public class UserSearch extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 用户id */
    @Excel(name = "用户id")
    private Long userId;

    /** 用户姓名 */
    @Excel(name = "用户姓名")
    private String userName;

    /** 搜素时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Excel(name = "搜素时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date searchTime;

    /** 搜索内容 */
    @Excel(name = "搜索内容")
    private String searchText;

    /** 删除标志：0 未删除，1已删除 */
    @Excel(name = "删除标志：0 未删除，1已删除")
    private Integer isDelete;


}
