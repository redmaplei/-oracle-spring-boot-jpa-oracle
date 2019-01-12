package com.oracle.car_rental.exception;

/**
 * 异常枚举
 */
public enum FrameErrorCodeEnums implements IErrorCode {
    /**
     * 汽车
     */
    SUCCESS(0, "处理成功"),
    SERVER_INTERNAL_ERROR(-1, "服务器内部错误"),
    UNKNOWN_ERROR(-100, "未知错误"),
    DB_ERROR(-101, "数据库错误"),
    OBJECT_ERROR(-103, "空对象"),


    ARGS_EMPTY_ERROR(599, "有参数为空"),
    BIND_ARGS_ERROR(600, "参数绑定异常"),
    CARORCUSTOMER_EMPTY_ERROR(601, "汽车或者出租人不存在"),
    CAR_EXIST(602, "汽车已出租"),
    COMPANYS_EXIST(603, "没有这个业务员"),
    RENTINFO_EMPTY(604, "没有这次租车"),
    ADMIN_EMPTY(605, "没有这个管理员"),
    AIDORIDNUMBER_EMPTY_EORROR(606, "出租人的账号或者身份证为空"),
    RENT_EMPTY_EORROR(607, "现在没租有车"),
    // 用户部分
    WRONG_A_ACCOUNT(403, "已存在账号"),

    WRONG_ACCOUNT(404, "不存在账号");

    private Integer code;
    private String msg;

    FrameErrorCodeEnums(Integer code, String msg) {
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

    public IErrorCode setMsg(String msg) {
        this.msg = msg;
        return this;
    }
}
