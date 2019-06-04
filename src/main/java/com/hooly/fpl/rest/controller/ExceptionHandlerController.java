package com.hooly.fpl.rest.controller;

import com.hooly.fpl.rest.dto.generic.ApiResponseWrapper;
import com.hooly.fpl.rest.dto.generic.ErrorDTO;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@ControllerAdvice(annotations = RestController.class)
public class ExceptionHandlerController {

    private final String VALIDATION_ERROR = "VALIDATION_ERROR";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponseWrapper validationError(MethodArgumentNotValidException ex){
        List<String> errors = new ArrayList<>();
        BindingResult bindingResult = ex.getBindingResult();
        bindingResult.getFieldErrors().forEach(fieldError -> errors.add(fieldError.getField()));
        return new ApiResponseWrapper<>(new ErrorDTO(VALIDATION_ERROR,errors));
    }
}