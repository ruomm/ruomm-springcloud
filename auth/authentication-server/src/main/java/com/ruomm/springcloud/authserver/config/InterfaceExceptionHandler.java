package com.ruomm.springcloud.authserver.config;

import com.ruomm.javax.corex.ListUtils;
import com.ruomm.javax.corex.StringUtils;
import com.ruomm.javax.jsonx.XJSON;
import com.ruomm.springcloud.authserver.dal.CommonFieldError;
import com.ruomm.springcloud.authserver.dal.CommonResponse;
import com.ruomm.springcloud.authserver.exception.WebAppException;
import com.ruomm.springcloud.authserver.utils.AppUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

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
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String commmonArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        List<FieldError> listFieldErrors = e.getFieldErrors();
        CommonResponse commonResponse= AppUtils.toNack(AppUtils.ERROR_PARAM,"请求参数错误");
        log.error("MethodArgumentNotValidException Handler,错误码:" + AppUtils.ERROR_PARAM + ",错误信息:" + "请求参数错误", e);
        if (ListUtils.isEmpty(listFieldErrors)){
            return XJSON.toJSONString(commonResponse);
        } else {
            List<CommonFieldError> listCommonFieldErrors = new ArrayList<>();
            listFieldErrors.forEach(fieldError -> {
                CommonFieldError commonFieldError = new CommonFieldError();
                commonFieldError.setField(fieldError.getField());
                commonFieldError.setMessage(fieldError.getDefaultMessage());
                listCommonFieldErrors.add(commonFieldError);
                log.error("MethodArgumentNotValidException Handler，field name:"+commonFieldError.getField()+",message:"+commonFieldError.getMessage());
            });
            commonResponse.setErrors(listCommonFieldErrors);
            return XJSON.toJSONString(commonResponse);
        }
    }
    @ResponseBody
    @ExceptionHandler(WebAppException.class)
    public String commmonWebAppExceptionHandler(WebAppException e) {
        int code = e.getCode();
        String message = StringUtils.isEmpty(e.getMessage()) ? "业务处理错误" : e.getMessage();
        if (null == e.getCause()) {
            log.error("WebAppException Handler，错误码:" +code + ",错误信息:" + message);
        } else {
            log.error("WebAppException Handler,错误码:" + code  + ",错误信息:" + message, e.getCause());
        }
        CommonResponse commonResponse = AppUtils.toNack(code,message);
        return XJSON.toJSONString(commonResponse);
    }

    @ResponseBody
    @ExceptionHandler(RuntimeException.class)
    public String commmonRuntimeExceptionHandler(RuntimeException e) {
        int code = AppUtils.ERROR_EXCEPT;
        String message = "业务处理异常";
        log.error("RuntimeException Handler,错误码:" + code + ",错误信息:" + message, e);
        CommonResponse commonResponse = AppUtils.toNack(code,message);
        return XJSON.toJSONString(commonResponse);
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public String commmonExceptionHandler(Exception e) {
        int code = AppUtils.ERROR_SYSTEM;
        String message = "系统运行错误";
        log.error("Exception Handler,错误码:" + code + ",错误信息:" + message, e);
        CommonResponse commonResponse = AppUtils.toNack(code,message);
        return XJSON.toJSONString(commonResponse);
    }
}
