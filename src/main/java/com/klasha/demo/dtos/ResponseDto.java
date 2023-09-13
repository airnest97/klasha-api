package com.klasha.demo.dtos;

import com.klasha.demo.enums.ResponseStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ResponseDto<T> {
    private ResponseStatus status;
    private T data;
    private String message;

    @JsonIgnore
    private Object[] messageArgs;

    public static <T> ResponseDto<T> wrapSuccessResult(T data, String message) {
        ResponseDto<T> response = new ResponseDto<>();
        response.setData(data);
        response.setMessage(message);
        response.setStatus(ResponseStatus.SUCCESS);
        return response;
    }

    public static <T> ResponseDto<T> wrapErrorResult(String message) {
        ResponseDto<T> response = new ResponseDto<>();
        response.setStatus(ResponseStatus.ERROR);
        response.setMessage(message);
        return response;
    }
}
