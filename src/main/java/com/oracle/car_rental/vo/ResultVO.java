package com.oracle.car_rental.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.oracle.car_rental.exception.IErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author wys
 * created in 22:50 2019/1/5
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultVO<T> {

    private Integer code;
    private Integer count;
    private String msg;
    private T data;

    public ResultVO(IErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.msg = errorCode.getMsg();
    }

}
