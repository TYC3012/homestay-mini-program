package com.homestay.project.system.domain.vo;

import com.homestay.framework.security.LoginBody;
import lombok.Data;

@Data
public class RegisterBody extends LoginBody {
    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;
}
