package com.peeerr.instagram.exception;

import com.peeerr.instagram.dto.CMResponse;
import com.peeerr.instagram.exception.ex.CustomApiException;
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
        return Script.back(e.getErrorMap().toString());
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
