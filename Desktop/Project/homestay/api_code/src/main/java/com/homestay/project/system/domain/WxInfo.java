package com.homestay.project.system.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WxInfo {

    /**
     * 临时码
     */
    private String code;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 头像
     */
    private String avatarUrl;

}
