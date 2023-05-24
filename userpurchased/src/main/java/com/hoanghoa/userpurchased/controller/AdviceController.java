package com.hoanghoa.userpurchased.controller;

import com.hoanghoa.userpurchased.model.dto.response.BaseResponse;
import com.hoanghoa.userpurchased.util.StringUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AdviceController {

    /**
     * Handle request param validation
     * @param ex MethodArgumentNotValidException
     * @return Error response
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResponse invalidArgument(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();

        return new BaseResponse(message, true);
    }
}
