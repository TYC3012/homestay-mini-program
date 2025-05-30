package com.homestay.project.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
/**
 * 微信用户实体
 */
public class WxUser {
    private static final long serialVersionUID = 1L;

    /** 微信唯一标识 */
    private String openId;

    /** 微信会话秘钥 */
    private String sessionKey;

    public WxUser(String openId, String sessionKey)
    {
        super();
        this.openId = openId;
        this.sessionKey = sessionKey;
    }

    public String getOpenId()
    {
        return openId;
    }

    public void setOpenId(String openId)
    {
        this.openId = openId;
    }

    public String getSessionKey()
    {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey)
    {
        this.sessionKey = sessionKey;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("openId", getOpenId())
                .append("sessionKey", getSessionKey())
                .toString();
    }
}
