package com.example.ptms.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ① @Valid 校验失败 → 400
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<Void> handleValid(MethodArgumentNotValidException e) {
        String msg = e.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        return Result.fail(400, msg);
    }

    // ② 自己抛的业务异常 → 状态码跟着异常走（比如 409）
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Result<Void>> handleBusiness(BusinessException e) {
        return ResponseEntity.status(e.getCode())
                .body(Result.fail(e.getCode(), e.getMessage()));
    }

    // ③ Spring 自己的 HTTP 异常（405 方法不支持、404 路径不存在、400 JSON 格式错等）
    //    保留它原本的状态码，不要被下面的兜底吞成 500
    @ExceptionHandler({
            HttpRequestMethodNotSupportedException.class,
            NoResourceFoundException.class,
            HttpMessageNotReadableException.class,
            MissingServletRequestParameterException.class
    })
    public ResponseEntity<Result<Void>> handleSpringError(ErrorResponse e) {
        int status = e.getStatusCode().value();
        String msg = e.getBody().getDetail() != null
                ? e.getBody().getDetail()
                : e.getBody().getTitle();
        return ResponseEntity.status(status).body(Result.fail(status, msg));
    }

    // ④ 兜底：其他所有异常 → 500
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<Void> handleOther(Exception e) {
        e.printStackTrace();
        return Result.fail(500, "服务器开小差了，请稍后再试");
    }
}
