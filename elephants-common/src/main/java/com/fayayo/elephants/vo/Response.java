package com.fayayo.elephants.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author dalizu on 2019/2/20.
 * @version v1.0
 * @desc
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response<T> implements Serializable{


    private Integer code;

    private String message;

    private T data;

    public Response(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
