package com.ruomm.springcloud.authserver.utils;

import com.ruomm.javax.corex.StringUtils;
import com.ruomm.javax.encryptx.DigestUtil;
import com.ruomm.springcloud.authserver.config.AppConfig;

/**
 * @author 牛牛-研发部-www.ruomm.com
 * @version 1.0
 * @copyright 像衍科技-idr.ai
 * @create 2024/2/1 0:11
 */
public class WebUtils {
    public static String passwordByClear(String clearText,String slatBe){
        String passwordSlatBe = StringUtils.isEmpty(slatBe)? AppConfig.PASSWORD_SLAT_BE:slatBe;
        String pwdByFe = DigestUtil.encodingMD5(clearText+AppConfig.PASSWORD_SLAT_FE);
        String pwdByBe = DigestUtil.encodingMD5(pwdByFe+passwordSlatBe);
        return pwdByBe;
    }
    public static String passwordByFE(String pwdByFe,String slatBe){
        String passwordSlatBe = StringUtils.isEmpty(slatBe)? AppConfig.PASSWORD_SLAT_BE:slatBe;
        String pwdByBe = DigestUtil.encodingMD5(pwdByFe+passwordSlatBe);
        return pwdByBe;
    }
}
