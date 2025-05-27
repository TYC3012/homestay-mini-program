package com.homestay.project.business.vo;

import com.homestay.project.business.domain.Room;
import com.homestay.project.business.domain.RoomDetailsImg;
import com.homestay.project.wechat.domain.WechatUser;
import lombok.Data;

import java.util.List;

/**
 * 民宿房间拓展实体
 */
@Data
public class RoomVO extends Room {

    //房间图片集合
    private List<RoomDetailsImg> detailsImgList;

    //查询类别
    private String queryType;

    //微信用户信息ID
    private Long userId;;

    //微信用户昵称
    private String userName;

    //房间是否下单
    private Integer isOrder;

    //房间浏览时长
    private Integer viewDuration;

    //房间浏览次数
    private Integer timeNumber;

    //房间下单次数
    private Integer orderNum;

    //搜索字段
    private String searchText;


}
