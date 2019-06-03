package com.hooly.fpl.rest.dto.generic;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ApiResponseWrapper<T> {

    private T payload;
    private ErrorDTO errorDTO;

    public ApiResponseWrapper(T payload){
        this.payload = payload;
    }

    public ApiResponseWrapper(ErrorDTO errorDTO){
        this.errorDTO = errorDTO;
    }
}
