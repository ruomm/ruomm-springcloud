package com.ruomm.springcloud.authserver.config;

import com.ruomm.javax.basex.TokenHelper;

/**
 * @author 牛牛-研发部-www.ruomm.com
 * @version 1.0
 * @copyright 像衍科技-idr.ai
 * @create 2024/2/1 0:14
 */
public class AppConfig {
    public final static TokenHelper TOKEN_HELPER = new TokenHelper(TokenHelper.TOKEN_NUMBER+TokenHelper.TOKEN_LETTER_LOWER_CASE,16);
    public final static String PASSWORD_SLAT_FE = "xiaoniu@ruomm.com";
    public final static String PASSWORD_SLAT_BE = "niuniu@ruomm.com";

    public final static String DATE_FORMAT_MESSAGE = "yyyy-MM-dd HH:mm:ss";
    public final static String DATE_PATTERN_SECOND = "yyyy-MM-dd HH:mm:ss";
    public final static String DATE_PATTERN_SECOND_NOSPACE = "yyyyMMddHHmmss";
    public final static String DATE_PATTERN_MILLI_SECOND = "yyyy-MM-dd HH:mm:ss.SSS";
    public final static String DATE_PATTERN_MILLI_SECOND_NOSPACE = "yyyyMMddHHmmssSSS";
}
