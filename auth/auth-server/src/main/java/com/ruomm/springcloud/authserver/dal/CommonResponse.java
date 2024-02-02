package com.ruomm.springcloud.authserver.dal;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author 牛牛-研发部-www.ruomm.com
 * @version 1.0
 * @copyright wanruome@163.com
 * @create 2022/4/11 15:14
 */
@Getter
@Setter
@ToString
public class CommonResponse <T>{
    private int code;
    private String message;
    private List<CommonFieldError> errors;
    private T data;
}
