package com.homestay.project.business.domain;

import com.homestay.framework.aspectj.lang.annotation.Excel;
import com.homestay.framework.web.domain.BaseEntity;
import lombok.Data;

/**
 * 房间详细图片对象 business_room_details_img
 * @date 2024-02-23
 */
@Data
public class RoomDetailsImg extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 房间id */
    @Excel(name = "房间id")
    private Long roomId;

    /** 文件名称 */
    @Excel(name = "文件名称")
    private String imgName;

    /** 文件路径 */
    @Excel(name = "文件路径")
    private String imgUrl;

    /** 删除标志：0 未删除，1已删除 */
    @Excel(name = "删除标志：0 未删除，1已删除")
    private Long isDelete;


}
