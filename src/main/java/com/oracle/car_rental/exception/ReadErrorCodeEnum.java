package com.oracle.car_rental.exception;

/**
 * @author wys
 * created in 0:07 2019/1/6
 */
public enum ReadErrorCodeEnum implements IErrorCode {

    /**
     * 返回值
     */
    SUCCESS(0, "成功"),
    SERVER_INTERNAL_ERROR(-1, "服务器内部错误"),
    ARGS_ERROR(420, "参数错误"),
    DB_ERROR(-100, "数据库错误");

    private Integer code;
    private String msg;

    ReadErrorCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
