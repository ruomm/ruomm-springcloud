package com.ruomm.springcloud.authserver.dal.request;

import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "用户名必须填写，且必须为4-16位")
    @Length(min = 4,max = 16,message = "用户名必须填写，且必须为4-16位")
    private String userName;
    @NotNull(message = "昵称必须填写，且必须为2-32位")
    @Length(min = 2,max = 32,message = "昵称必须填写，且必须为2-32位")
    private String nickName;
    @NotNull(message = "密码必须填写，且必须为8-32位")
    @Length(min = 8,max = 32,message = "密码必须填写，且必须为8-32位")
    private String password;
}
