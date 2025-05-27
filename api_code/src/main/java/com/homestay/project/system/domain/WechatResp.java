package com.homestay.project.system.domain;

import lombok.Data;

@Data
public class WechatResp {

    private String code;
    private String jscode;
    private String openId;
    private String unionId;
    private String sessionKey;
    private String accessToken;

}
