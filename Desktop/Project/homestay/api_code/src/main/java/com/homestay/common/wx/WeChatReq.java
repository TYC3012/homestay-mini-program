package com.homestay.common.wx;

import lombok.Data;

@Data
public class WeChatReq {
    private String code;
    private String jscode;
    private String encryptedData;
    private String iv;
    private String phonenumber;
    private String sessionKey;

}
