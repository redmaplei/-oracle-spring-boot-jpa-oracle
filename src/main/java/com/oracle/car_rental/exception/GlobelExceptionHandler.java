package com.oracle.car_rental.exception;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.oracle.car_rental.utils.ResultUtil;
import com.oracle.car_rental.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.net.BindException;
import java.util.Objects;

/**
 * @author wys
 * created in 21:26 2019/1/6
 */
@Slf4j
@ControllerAdvice
public class GlobelExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultVO<?> defaultExceptionHandler(HttpServletRequest request, Exception e) {
        IErrorCode errorCode = getErrorCode(e);
        String message = e.getMessage();
        Object errMsgObj = message;

        if (JSONUtil.isJson(message)) {
            errMsgObj = JSONUtil.parseObj(message);
        }

        JSONObject errLogInfo = Objects.requireNonNull(JSONUtil.createObj())
                .put("reqUri", request.getRequestURI())
                .put("errMsg", errMsgObj);
        log.warn(Objects.requireNonNull(errLogInfo).toString(), e);

        return ResultUtil.failure(errorCode);

    }

    public IErrorCode getErrorCode(Exception e) {
        IErrorCode errorCode = ReadErrorCodeEnum.SERVER_INTERNAL_ERROR;
        if (e instanceof IFrameException) {
            IFrameException platformException = (IFrameException) e;
            if (platformException != null) {
                errorCode = platformException.getIErrorCode();
            }
        } else if (e instanceof BindException) {
            // 参数异常的 暂时没加有  后期可拓展
            BindException bindException = (BindException) e;
            errorCode = ReadErrorCodeEnum.ARGS_ERROR;
        }

        return errorCode;
    }


}
