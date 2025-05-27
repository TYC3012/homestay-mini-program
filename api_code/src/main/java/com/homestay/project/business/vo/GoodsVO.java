package com.homestay.project.business.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.homestay.project.business.domain.Goods;
import lombok.Data;

/**
 * 该实体为拓展的实体，为了和前端做交互
 */
@Data
public class GoodsVO extends Goods {

    /** 搜索值 */
    @JsonIgnore
    private String searchValue;
}
