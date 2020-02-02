package com.github.yohohaha.flink.exception;

/**
 * created at 2020/01/16 14:58:03
 *
 * @author Yohohaha
 */
public class EnvHandlerException extends RuntimeException {
    public EnvHandlerException() {
        super();
    }

    public EnvHandlerException(String message) {
        super(message);
    }

    public EnvHandlerException(String message, Throwable cause) {
        super(message, cause);
    }

    public EnvHandlerException(Throwable cause) {
        super(cause);
    }

    protected EnvHandlerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
