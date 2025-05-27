package com.homestay.project.business.vo;

import com.homestay.project.business.domain.Order;
import com.homestay.project.business.domain.Room;
import lombok.Data;
@Data
public class OrderVo extends Order {

    private Room room;//房间信息

}
