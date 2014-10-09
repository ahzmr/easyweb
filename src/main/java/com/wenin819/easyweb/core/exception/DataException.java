package com.wenin819.easyweb.core.exception;

/**
 * 数据异常类
 * Created by wenin819@gmail.com on 2014-10-09.
 */
public class DataException extends BaseException {

    public DataException() {
        super();
    }

    public DataException(String message) {
        super(message);
    }

    public DataException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataException(Throwable cause) {
        super(cause);
    }

}
