package com.xingling.aspect;

import com.xingling.common.WrapMapper;
import com.xingling.common.Wrapper;
import com.xingling.exception.ArgumentInvalidResult;
import com.xingling.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>Title:	  GlobalExceptionHandler. </p>
 * <p>Description 添加全局异常处理流程，根据需要设置需要处理的异常 </p>
 * <p>Company:    http://www.xinglinglove.com </p>
 *
 * @Author <a href="190332447@qq.com"/>杨文生</a>
 * @CreateDate 2017 /8/18 13:47
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * Method argument not valid handler object.
     *
     * @param exception the exception
     * @return the object
     * @throws Exception the exception
     */
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public Wrapper MethodArgumentNotValidHandler(MethodArgumentNotValidException exception) throws Exception {
        //按需重新封装需要返回的错误信息
        List<ArgumentInvalidResult> invalidArguments = new ArrayList<>();
        for (FieldError error : exception.getBindingResult().getFieldErrors()) {  //解析原错误信息，封装后返回，此处返回非法的字段名称，原始值，错误信息
            ArgumentInvalidResult invalidArgument = new ArgumentInvalidResult();
            invalidArgument.setDefaultMessage(error.getDefaultMessage());
            invalidArgument.setField(error.getField());
            invalidArgument.setRejectedValue(error.getRejectedValue());
            invalidArguments.add(invalidArgument);
        }
        return WrapMapper.wrap(Wrapper.ERROR_CODE, exception.getMessage(),invalidArguments);
    }

    /**
     * Illegal argument exception wrapper.
     *
     * @param exception the exception
     * @return the wrapper
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Wrapper IllegalArgumentException(IllegalArgumentException exception) {
        log.error("参数非法异常:", exception.getMessage(), exception);
        return WrapMapper.wrap(Wrapper.ERROR_CODE, exception.getMessage());
    }

    /**
     * Not fount wrapper.
     *
     * @param exception the exception
     * @return the wrapper
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.OK)
    public Wrapper businessException(BusinessException exception) {
        log.error("业务异常:", exception.getMessage(), exception);
        return WrapMapper.wrap(Wrapper.ERROR_CODE, exception.getMessage());
    }
}