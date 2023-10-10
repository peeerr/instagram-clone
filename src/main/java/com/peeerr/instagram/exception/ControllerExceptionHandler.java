package com.peeerr.instagram.exception;

import com.peeerr.instagram.dto.CMResponse;
import com.peeerr.instagram.exception.ex.CustomApiException;
import com.peeerr.instagram.exception.ex.CustomException;
import com.peeerr.instagram.exception.ex.CustomValidationApiException;
import com.peeerr.instagram.exception.ex.CustomValidationException;
import com.peeerr.instagram.util.Script;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(CustomValidationException.class)
    public String validationException(CustomValidationException e) {
        if (e.getErrorMap() == null) {
            return Script.back(e.getMessage());
        }

        return Script.back(e.getErrorMap().toString());
    }

    @ExceptionHandler(CustomException.class)
    public String Exception(CustomException e) {
        return Script.back(e.getMessage());
    }

    @ExceptionHandler(CustomValidationApiException.class)
    public ResponseEntity<CMResponse<?>> validationApiException(CustomValidationApiException e) {
        return ResponseEntity.badRequest()
                .body(new CMResponse<>(-1, e.getMessage(), e.getErrorMap()));
    }

    @ExceptionHandler(CustomApiException.class)
    public ResponseEntity<CMResponse<?>> apiException(CustomApiException e) {
        return ResponseEntity.badRequest()
                .body(new CMResponse<>(-1, e.getMessage(), null));
    }

}
