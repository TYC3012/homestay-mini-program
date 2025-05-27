package com.homestay.project.business.vo;

import com.homestay.project.business.domain.Room;
import com.homestay.project.business.domain.UserSearch;
import lombok.Data;

import java.util.List;

@Data
public class UserSearchVo extends UserSearch {

    //搜索字段
    private String searchText;

    //房间信息
    private List<Room> roomList;

}
