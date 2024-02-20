package com.ruomm.springcloud.authserver.utils;

import com.ruomm.javax.corex.StringUtils;
import com.ruomm.javax.corex.TimeUtils;
import com.ruomm.javax.encryptx.DigestUtil;
import com.ruomm.springcloud.authserver.config.AppConfig;
import com.ruomm.springcloud.authserver.config.ConfigProperties;
import com.ruomm.springcloud.authserver.entry.MsgTemplateEntity;

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

    public static String parseUserStatus(Integer userStatus){
        if (null == userStatus) {
            return "未知";
        }
        int tmpStatus = userStatus.intValue();
        if (tmpStatus == 0){
            return "等待审核";
        } else if (tmpStatus == 1){
            return "正常";
        }  else if (tmpStatus == 2){
            return "密码过期";
        } else if (tmpStatus == 3){
            return "登录限制";
        } else if (tmpStatus == 4){
            return "冻结";
        } else if (tmpStatus == 9){
            return "停用";
        } else {
            return "未知";
        }
    }
    public static String parseMsgType(String msgType){
        if (StringUtils.isEmpty(msgType)) {
            return "手机号";
        } else if (StringUtils.isEquals(msgType,"mobile")){
            return "手机号";
        } else if (StringUtils.isEquals(msgType,"email")){
            return "邮箱";
        } else if (StringUtils.isEquals(msgType,"weixin")){
            return "微信号";
        } else if (StringUtils.isEquals(msgType,"qq")){
            return "QQ号";
        } else {
            return "手机号";
        }
    }

    public static int parseMsgValidTime(MsgTemplateEntity msgTemplate, ConfigProperties configProperties){
        int validTime = null==msgTemplate.getValidTime()?0:msgTemplate.getValidTime().intValue();
        if (validTime<=0){
            Long validTimeLong = TimeUtils.parseStrToTimeSeconds(configProperties.getVerifyCodeConfig().getValidTime(),0L,true);
            if (null == validTimeLong){
                validTime = 0;
            } else {
                validTime = validTimeLong.intValue();
            }
        }
        if (validTime<=0 || validTime >3600*24*30){
            validTime = 900;
        }
        return validTime;
    }

    public static String formatMsgValidTime(int validTime){
        if (validTime<=0){
            return "0";
        } else if (validTime<120){
            return validTime+"秒";
        } else if (validTime<3600){
            int minute = validTime /60;
            int second =validTime%60;
            StringBuilder sb = new StringBuilder();
            sb.append(minute+"分钟");
            if (second>0){
                sb.append(second+"秒");
            }
            return sb.toString();
        } else {
            int hour = validTime/3600;
            int secondVal = validTime%3600;
            int minute = secondVal /60;
            int second =secondVal%60;
            StringBuilder sb = new StringBuilder();
            sb.append(hour+"小时");
            if (minute>0){
                sb.append(minute+"分钟");
            }
            if (second>0){
                sb.append(second+"秒");
            }
            return sb.toString();
        }
    }
}
