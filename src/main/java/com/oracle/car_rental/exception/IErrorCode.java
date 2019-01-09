package com.oracle.car_rental.exception;

/**
 * 定义一个获取异常码和异常信息的接口，提升系统可拓展性
 * @author wys
 * created in 23:47 2019/1/5
 */
public interface IErrorCode {
    Integer getCode();
    String getMsg();
}
