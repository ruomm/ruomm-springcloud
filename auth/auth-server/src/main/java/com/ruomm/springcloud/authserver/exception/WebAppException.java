package com.ruomm.springcloud.authserver.exception;

/**
 * @author 牛牛-研发部-www.ruomm.com
 * @version 1.0
 * @copyright wanruome@163.com
 * @create 2022/4/11 16:08
 */
public class WebAppException extends RuntimeException {
    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public WebAppException() {
    }

    public WebAppException(String message) {
        super(message);
    }

    public WebAppException(String message, int code) {
        super(message);
        this.code = code;
    }

    public WebAppException(String message, Throwable cause) {
        super(message, cause);
    }

    public WebAppException( int code, String message,Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public WebAppException(Throwable cause) {
        super(cause);
    }

    public WebAppException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public WebAppException(int code,String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
    }
}
