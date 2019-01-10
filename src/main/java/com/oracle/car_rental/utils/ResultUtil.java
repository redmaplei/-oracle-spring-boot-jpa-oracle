package com.oracle.car_rental.utils;


import com.oracle.car_rental.exception.FrameErrorCodeEnums;
import com.oracle.car_rental.exception.IErrorCode;
import com.oracle.car_rental.vo.ResultVO;

public class ResultUtil {

    public static <T> ResultVO<T> success() {
        return success(null, null, null);
    }

    public static <T> ResultVO<T> success(String msg) {
        return success(null, msg, null);
    }

    public static <T> ResultVO<T> success(T data) {
        return success(null, null, data);
    }

    public static <T> ResultVO<T> success(Integer count) {
        return success(count, null, null);
    }

    public static <T> ResultVO<T> success(Integer count, T data) {
        return success(count, null, data);
    }

    public static <T> ResultVO<T> success(Integer count, String msg, T data) {
        ResultVO<T> resultVO = new ResultVO<>(FrameErrorCodeEnums.SUCCESS);
        resultVO.setMsg(msg);
        resultVO.setCount(count);
        resultVO.setData(data);
        return resultVO;
    }

    public static <T> ResultVO<T> failure(String msg) {
        return failure(null, msg);
    }

    public static <T> ResultVO<T> failure(IErrorCode errorCode) {
        ResultVO<T> resultVO = new ResultVO<>(errorCode);
//        resultVO.setMsg(msg);
        return resultVO;
    }

    public static <T> ResultVO<T> failure(IErrorCode errorCode, String msg) {
        ResultVO<T> resultVO = new ResultVO<>(errorCode);
        resultVO.setMsg(msg);
        return resultVO;
    }

}
