package com.nuanyang.nursing.vo;

import lombok.Data;

/**
 * LoginVO
 * @author byte-love
 */
@Data
public class LoginVo {

    /**
     * JWT token
     */
    private String token;

    /**
     * 昵称
     */
    private String nickName;
}