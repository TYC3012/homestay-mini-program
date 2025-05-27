package com.homestay.common.wx;

import lombok.Data;

@Data
public class WxAccessTokenDTo {
    private String access_token;

    private Integer expires_in;

    private Integer errcode;

    private String errmsg;
}
