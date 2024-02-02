package com.ruomm.springcloud.authserver.config;

import com.ruomm.springcloud.authserver.dal.CommonResponse;
import com.ruomm.springcloud.authserver.exception.WebAppException;
import com.ruomm.javax.corex.StringUtils;
import com.ruomm.javax.jsonx.XJSON;
import com.ruomm.springcloud.authserver.utils.AppHttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author 牛牛-研发部-www.ruomm.com
 * @version 1.0
 * @copyright wanruome@163.com
 * @create 2022/4/12 15:23
 */
@RestControllerAdvice
@Slf4j
public class InterfaceExceptionHandler {
    @ResponseBody
    @ExceptionHandler(WebAppException.class)
    public String businessInterfaceException(WebAppException e) {
        int code = e.getCode();
        String message = StringUtils.isEmpty(e.getMessage()) ? "业务处理错误" : e.getMessage();
        if (null == e.getCause()) {
            log.error("WebAppException Handler，错误码:" +code + ",错误信息:" + message);
        } else {
            log.error("WebAppException Handler,错误码:" + code  + ",错误信息:" + message, e.getCause());
        }
        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setCode(code);
        commonResponse.setMessage(message);
        return XJSON.toJSONString(commonResponse);
    }

    @ResponseBody
    @ExceptionHandler(RuntimeException.class)
    public String runtimeException(RuntimeException e) {
        int code = AppHttpUtils.ERROR_EXCEPT;
        String message = "业务处理异常";
        log.error("RuntimeException Handler,错误码:" + code + ",错误信息:" + message, e);
        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setCode(code);
        commonResponse.setMessage(message);
        return XJSON.toJSONString(commonResponse);
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public String exception(Exception e) {
        int code = AppHttpUtils.ERROR_SYSTEM;
        String message = "系统运行错误";
        log.error("Exception Handler,错误码:" + code + ",错误信息:" + message, e);
        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setCode(code);
        commonResponse.setMessage(message);
        return XJSON.toJSONString(commonResponse);
    }
}
