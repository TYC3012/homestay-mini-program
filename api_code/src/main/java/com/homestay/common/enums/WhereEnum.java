package com.homestay.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WhereEnum {
    /**
     * 否
     */
    NO(0),
    /**
     * 是
     */
    YES(1);


    private Integer value;
}
