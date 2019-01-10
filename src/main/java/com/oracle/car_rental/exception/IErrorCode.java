package com.oracle.car_rental.exception;

/**
 * 定义一个获取异常码和异常信息的接口
 */
public interface IErrorCode {
    Integer getCode();
    String getMsg();
}
