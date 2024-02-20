package com.ruomm.springcloud.authserver.config;

import com.ruomm.javax.basex.TokenHelper;

import java.text.SimpleDateFormat;

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

    public final static SimpleDateFormat DATE_FORMAT_MESSAGE = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public final static SimpleDateFormat DATE_PATTERN_SECOND = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public final static SimpleDateFormat DATE_PATTERN_SECOND_NOSPACE = new SimpleDateFormat("yyyyMMddHHmmss");
    public final static SimpleDateFormat DATE_PATTERN_MILLI_SECOND = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    public final static SimpleDateFormat DATE_PATTERN_MILLI_SECOND_NOSPACE = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    public final static TokenHelper TOKEN_HELPER_MSG = new TokenHelper(TokenHelper.TOKEN_NUMBER,6);
    public final static int DB_STATUS_OK = 1;

    // 邮箱地址
    public final static String REGEX_PHONE = "(^((1)\\d{10})$|^((\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1})|(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1}))$)";
    public final static String REGEX_EMAIL = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
    public final static String REGEX_MOBILE = "^(1)\\d{10}$";
    public final static String REGEX_USER_NAME = "^[a-zA-Z][a-zA-Z0-9_-]{3,15}$";
    public final static String REGEX_PASSWORD = "^[a-fA-F0-9]{32,64}$";
    public final static String REGEX_QQ = "^[1-9]([0-9]{4,10})$";
    public final static String REGEX_WEIXIN = "";

}
