package com.wenin819.easyweb.core.exception;

/**
 * 基本异常类
 * Created by wenin819@gmail.com on 2014-10-08.
 */
public class BaseException extends RuntimeException {

    public BaseException() {
        super();
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

}
