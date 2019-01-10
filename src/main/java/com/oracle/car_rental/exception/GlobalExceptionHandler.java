package com.oracle.car_rental.exception;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.oracle.car_rental.utils.ResultUtil;
import com.oracle.car_rental.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultVO<?> defaultExceptionHandler(HttpServletRequest request, Exception e) {
        IErrorCode errorCode = getErrorCode(e);
        // 将异常信息转换成json样式，更加直观
        String message = e.getMessage();
        Object errMsgObj = message;

        if (JSONUtil.isJson(message)) {
            errMsgObj = JSONUtil.parseObj(message);
        }

        JSONObject errLogInfo = Objects.requireNonNull(JSONUtil.createObj()
                .put("reqUri", request.getRequestURI()))
                .put("errMsg", errMsgObj);
        log.warn(Objects.requireNonNull(errLogInfo).toString(), e);

        return ResultUtil.failure(errorCode);
    }

    private IErrorCode getErrorCode(Exception e) {
        IErrorCode errorCode = FrameErrorCodeEnums.SERVER_INTERNAL_ERROR;
        if (e instanceof IFrameException) {
            IFrameException platformException = (IFrameException) e;
            if (platformException.getErrorCode() != null) {
                errorCode = platformException.getErrorCode();
            }
        } else if (e instanceof BindException) {
            BindException bindException = (BindException) e;
            // bindException.getFieldError()
            errorCode = FrameErrorCodeEnums.BIND_ARGS_ERROR.setMsg(Objects.requireNonNull(bindException.getFieldError()).getDefaultMessage());
        }


        return errorCode;
    }
}
