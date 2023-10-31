package com.star.utils;

import cn.dev33.satoken.secure.SaSecureUtil;

/**
 * 密码加密
 **/
public class SecurityUtils {

    /**
     * sha256加密
     *
     * @param password 密码
     * @return 加密后的密码
     */
    public static String sha256Encrypt(String password) {
        return SaSecureUtil.sha256(password);
    }
}
