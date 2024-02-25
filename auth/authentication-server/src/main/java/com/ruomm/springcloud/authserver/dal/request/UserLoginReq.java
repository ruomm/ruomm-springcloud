package com.ruomm.springcloud.authserver.dal.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

/**
 * @author 牛牛-wanruome@126.com
 * @version 1.0
 * @copyright www.ruomm.com
 * @create 2024/2/25 9:46
 */
@Getter
@Setter
@ToString
public class UserLoginReq {
    @NotEmpty(message = "用户名必须填写，且必须为4-16位")
    @Length(min = 4,max = 16,message = "用户名必须填写，且必须为4-16位")
    private String userName;
    @NotEmpty(message = "密码必须填写，且必须为8-32位")
    @Length(min = 8,max = 32,message = "密码必须填写，且必须为8-32位")
    private String password;
    @NotEmpty(message = "验证码必须填写")
    @Length(min = 4,max = 8,message = "验证码格式不正确")
    private String verifyCode;
}
