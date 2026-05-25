package com.nuanyang.common.exception.user;

import com.nuanyang.common.exception.base.BaseException;

/**
 * 用户信息异常类
 * 
 * @author byte-love
 */
public class UserException extends BaseException
{
    private static final long serialVersionUID = 1L;

    public UserException(String code, Object[] args)
    {
        super("user", code, args, null);
    }
}
