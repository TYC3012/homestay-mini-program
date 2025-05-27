package com.homestay.common.wx;

import lombok.Data;

@Data
public class WxLoginBody {

    /**
     * 临时登录凭证
     */
    private String code;
    /**
     * 偏移量
     */
    private String encryptedIv;

    /**
     * 加密数据
     */
    private String encryptedData;

    private String sessionKey;

    private String mobile;


    private String jscode;

    private String iv;
    private String phonenumber;




}
