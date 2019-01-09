package com.oracle.car_rental.exception;

/**
 * @author wys
 * created in 18:03 2019/1/6
 */
public class ReadRuntimeException extends Exception implements IFrameException {

    private IErrorCode errorCode;

    public ReadRuntimeException(IErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public IErrorCode getIErrorCode() {
        return errorCode;
    }
}
