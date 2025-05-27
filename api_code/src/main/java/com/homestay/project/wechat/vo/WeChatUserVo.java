package com.homestay.project.wechat.vo;

import com.homestay.project.business.domain.Hotel;
import com.homestay.project.business.domain.Movie;
import com.homestay.project.business.domain.Room;
import com.homestay.project.business.vo.RoomVO;
import com.homestay.project.wechat.domain.WechatUser;
import lombok.Data;

import java.util.List;

@Data
public class WeChatUserVo extends WechatUser {

    private static final long serialVersionUID = 1L;

    private String code;

    private Long userId;

    private List<String> like;
    private double seem;//相似度


    //推荐的房间信息
    private List<RoomVO> roomList;


    public RoomVO find(Long roomId) {
        for (RoomVO roomVO : roomList) {
            if (roomVO.getId().equals(roomId)) {
                return roomVO;
            }
        }
        return null;
    }

    //民宿id，用以绑定店主
    private Long hotelId;

    //店主的民宿信息
    private Hotel hotel;

}
