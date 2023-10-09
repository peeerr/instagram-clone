package com.peeerr.instagram.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class CMResponse<T> {

    private int code;
    private String message;
    private T data;

}
