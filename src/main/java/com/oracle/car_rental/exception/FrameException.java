package com.oracle.car_rental.exception;

import cn.hutool.core.util.StrUtil;

/**
 * 框架异常，此异常为检查异常
 */
public class FrameException extends Exception implements IFrameException {
    private static final long serialVersionUID = -8325396926857034510L;
    private IErrorCode errorCode;


    public FrameException() {
        super();
    }

    public FrameException(IErrorCode errorCode) {
        this(errorCode, "");
    }

    public FrameException(IErrorCode errorCode, String message) {
        this(null, errorCode, message);
    }

    public FrameException(Throwable e, IErrorCode errorCode) {
        this(e, errorCode, "");
    }

    public FrameException(Throwable e, IErrorCode errorCode, String message) {
        super(StrUtil.format("{\"code\": {}, \"msg\": \"{}\", \"extMsg\": \"{}\"}",
                errorCode.getCode(), errorCode.getMsg(), message), e);
        this.errorCode = errorCode;
    }

    public FrameException(String message) {
        super(message);
    }

    public FrameException(String message, Throwable cause) {
        super(message, cause);
    }

    public FrameException(Throwable cause) {
        super(cause);
    }

    protected FrameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public IErrorCode getErrorCode() {
        return errorCode;
    }
}
