package com.ruomm.springcloud.authserver.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruomm.javax.corex.StringUtils;
import com.ruomm.javax.objvalidatorx.ObjValidResult;
import com.ruomm.javax.objvalidatorx.ObjValidUtil;
import com.ruomm.springcloud.authserver.dal.CommonResponse;
import com.ruomm.springcloud.authserver.exception.WebAppException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 牛牛-研发部-www.ruomm.com
 * @version 1.0
 * @copyright wanruome@163.com
 * @create 2022/4/11 15:59
 */
public class AppUtils {
    public final static int CODE_OK = 200;
    //创建成功，通常用在异步操作时，表示请求已接受，但是还没有处理完成
    public final static int CODE_OK_AJAX = 202;
    // 参数错误，通常用在表单参数错误
    public final static int ERROR_PARAM = 400;
    //授权错误，通常用在 Token 缺失或失效，注意 401 会触发前端跳转到登录页
    public final static int ERROR_TOKEN = 401;
    //操作被拒绝，通常发生在权限不足时，注意此时务必带上详细错误信息
    public final static int ERROR_REFUSE = 403;
    //查找的资源不存在，通常发生在使用错误的 id 查询详情
    public final static int ERROR_NOT_EXIST = 404;
    //业务处理错误
    public final static int ERROR_CORE = 410;
    //资源处理错误，通常是数据库读写错误
    public final static int ERROR_DB_CORE = 411;
    //文件读写错误
    public final static int ERROR_FILE_CORE = 412;
    //第三方授权失败
    public final static int ERROR_THRID_GATEWAY = 420;
    //服务器内部错误
    public final static int ERROR_INTERNAL_ERROR = 500;
    //未定义错误
    public final static int ERROR_UNDEFINED = 900;
    // 业务处理异常
    public final static int ERROR_EXCEPT = 990;
    //系统运行错误
    public final static int ERROR_SYSTEM = 999;

    public final static Map<Integer,String> CODE_MAP = new HashMap<>();
    static {
        CODE_MAP.put(CODE_OK,"success");
        CODE_MAP.put(CODE_OK_AJAX,"request success, waiting process");
        CODE_MAP.put(ERROR_PARAM,"请求参数错误");
        CODE_MAP.put(ERROR_TOKEN,"用户鉴权失败，请重新登陆");
        CODE_MAP.put(ERROR_REFUSE,"操作被拒绝，可能权限不足");
        CODE_MAP.put(ERROR_NOT_EXIST,"查找的资源不存在");
        CODE_MAP.put(ERROR_CORE,"业务处理错误");
        CODE_MAP.put(ERROR_DB_CORE,"资源处理错误");
        CODE_MAP.put(ERROR_FILE_CORE,"文件读写错误");
        CODE_MAP.put(ERROR_THRID_GATEWAY,"第三方授权失败");
        CODE_MAP.put(ERROR_INTERNAL_ERROR,"服务器内部错误");
        CODE_MAP.put(ERROR_UNDEFINED,"未定义错误");
        CODE_MAP.put(ERROR_EXCEPT,"业务处理异常");
        CODE_MAP.put(ERROR_SYSTEM,"系统运行错误");
    }


    public final static String[] JOB_SERVER_NONE_SERIALIZE_KEYS = new String[]{"recordStartTime", "recordEndTime", "recordActionDelay"};

    public static JSONObject toAckJson(Object respnseObj, String... noneSerializeKeys) {
        JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(respnseObj));
        if (null != noneSerializeKeys && noneSerializeKeys.length > 0) {
            for (String key : noneSerializeKeys) {
                if (!StringUtils.isEmpty(key)) {
                    jsonObject.remove(key);
                }
            }
        }
        jsonObject.put("code", CODE_OK);
        jsonObject.put("message", CODE_MAP.get(CODE_OK));
        return jsonObject;
    }
    public static CommonResponse toAck() {
        return toCommonResponse(CODE_OK,null);
    }
    public static CommonResponse toAck(String message) {
        return toCommonResponse(CODE_OK,message);
    }
    public static <T> CommonResponse<T> toAckT(T t) {
        return toCommonResponse(CODE_OK,null,t);
    }
    public static <T> CommonResponse<T> toAck(String message,T t) {
        return toCommonResponse(CODE_OK,message,t);
    }

    public static void validToException(Object requestObj) {
        ObjValidResult objValidResult = ObjValidUtil.verify(requestObj, ERROR_PARAM +"");
        if (!objValidResult.isValid()) {
            throw new WebAppException( ERROR_PARAM,objValidResult.getErrMsg());
        }
    }

    public static CommonResponse validToResponse(Object requestObj) {
        ObjValidResult objValidResult = ObjValidUtil.verify(requestObj, ERROR_PARAM +"");
        if (!objValidResult.isValid()) {
            return toCommonResponse(ERROR_PARAM,objValidResult.getErrMsg());
        } else {
            return null;
        }
    }
    public static CommonResponse toNackParam() {
        return toCommonResponse(ERROR_PARAM,null);
    }
    public static CommonResponse toNackParam(String message) {
        return toCommonResponse(ERROR_PARAM,message);
    }
    public static <T> CommonResponse<T> toNackParamT(T t) {
        return toCommonResponse(ERROR_PARAM,null,t);
    }
    public static <T> CommonResponse<T> toNackParam(String message,T t) {
        return toCommonResponse(ERROR_PARAM,message,t);
    }
    public static CommonResponse toNackCore() {
        return toCommonResponse(ERROR_CORE,null);
    }
    public static CommonResponse toNackCore(String message) {
        return toCommonResponse(ERROR_CORE,message);
    }
    public static <T> CommonResponse<T> toNackCoreT(T t) {
        return toCommonResponse(ERROR_CORE,null,t);
    }
    public static <T> CommonResponse<T> toNackCore(String message,T t) {
        return toCommonResponse(ERROR_CORE,message,t);
    }

    public static CommonResponse toNack(int code) {
        return toCommonResponse(code,null);
    }

    public static CommonResponse toNack(int code,String message) {
        return toCommonResponse(code,message);
    }
    public static <T> CommonResponse<T> toNackT(int code,T t) {
        return toCommonResponse(code,null,t);
    }
    public static <T> CommonResponse<T> toNack(int code,String message,T t) {
        return toCommonResponse(code,message,t);
    }



    private static CommonResponse toCommonResponse(int code,String message) {
        CommonResponse commonResponse=new CommonResponse();
        commonResponse.setCode(code);
        if (StringUtils.isEmpty(message)) {
            String resultMessage = CODE_MAP.get(code);
            if (StringUtils.isEmpty(resultMessage)){
                commonResponse.setMessage("未定义错误");
            } else {
                commonResponse.setMessage(resultMessage);
            }
        } else {
            commonResponse.setMessage(message);
        }
        return commonResponse;
    }
    private static <T> CommonResponse<T> toCommonResponse(int code,String message,T t) {
        CommonResponse<T> commonResponse=new CommonResponse<>();
        commonResponse.setCode(code);
        if (StringUtils.isEmpty(message)) {
            String resultMessage = CODE_MAP.get(code);
            if (StringUtils.isEmpty(resultMessage)){
                commonResponse.setMessage("未定义错误");
            } else {
                commonResponse.setMessage(resultMessage);
            }
        } else {
            commonResponse.setMessage(message);
        }
        if (null!=t){
            commonResponse.setData(t);
        }
        return commonResponse;
    }

}
