package com.oracle.car_rental.exception;

import cn.hutool.core.util.StrUtil;

/**
 * 框架运行时异常
 *
 */
public class FrameRuntimeException extends RuntimeException implements IFrameException {
    private static final long serialVersionUID = -3636525914152169100L;
    private IErrorCode errorCode;

    public FrameRuntimeException() {
        super();
    }

    /**
     * 直接使用IErrorCode构造异常，异常的msg将使用errorCode.getMsg();
     * 如果errorCode为Null，底层则会自己抛出空指针异常
     *
     * @param errorCode 错误码
     */
    public FrameRuntimeException(IErrorCode errorCode) {
        this(errorCode, "");
    }

    public FrameRuntimeException(IErrorCode errorCode, String message) {
        this(null, errorCode, message);
    }


    public FrameRuntimeException(Throwable e, IErrorCode errorCode) {
        this(e, errorCode, "");
    }
    public FrameRuntimeException(Throwable e, IErrorCode errorCode, String message) {
        super(StrUtil.format("{\"code\": {}, \"msg\": \"{}\", \"extMsg\": \"{}\"}",
                errorCode.getCode(), errorCode.getMsg(), message), e);
        this.errorCode = errorCode;
    }

    public FrameRuntimeException(String message) {
        super(message);
    }

    public FrameRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public FrameRuntimeException(Throwable cause) {
        super(cause);
    }

    protected FrameRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public IErrorCode getErrorCode() {
        return errorCode;
    }
}
