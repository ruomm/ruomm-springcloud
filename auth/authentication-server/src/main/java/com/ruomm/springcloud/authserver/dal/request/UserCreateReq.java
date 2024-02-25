package com.ruomm.springcloud.authserver.dal.request;

import com.ruomm.springcloud.authserver.config.AppConfig;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * @author 牛牛-研发部-www.ruomm.com
 * @version 1.0
 * @copyright 像衍科技-idr.ai
 * @create 2024/1/30 0:20
 */
@Data
public class UserCreateReq {
    @NotEmpty(message = "用户名必须填写，且必须为4-16位")
    @Length(min = 4,max = 16,message = "用户名必须填写，且必须为4-16位")
    private String userName;
    @NotEmpty(message = "昵称必须填写，且必须为2-32位")
    @Length(min = 2,max = 32,message = "昵称必须填写，且必须为2-32位")
    private String nickName;
    @NotEmpty(message = "密码必须填写，且必须为8-32位")
    @Length(min = 8,max = 32,message = "密码必须填写，且必须为8-32位")
    private String password;
    @NotEmpty(message = "绑定手机号必须填写")
    @Pattern(regexp = AppConfig.REGEX_MOBILE,message = "绑定手机号格式不正确")
    private String bindPhone;
    @NotEmpty(message = "验证码必须填写")
    @Length(min = 4,max = 8,message = "验证码格式不正确")
    private String verifyCode;
}
