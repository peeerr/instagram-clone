package com.peeerr.instagram.controller.api;

import com.peeerr.instagram.config.auth.PrincipalDetails;
import com.peeerr.instagram.domain.user.User;
import com.peeerr.instagram.dto.CMResponse;
import com.peeerr.instagram.dto.user.UserUpdateRequest;
import com.peeerr.instagram.exception.ex.CustomValidationApiException;
import com.peeerr.instagram.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class UserApiController {

    private final UserService userService;

    @PutMapping("/user/{id}")
    public ResponseEntity<CMResponse<?>> update(@PathVariable Long id,
                                               @Valid UserUpdateRequest userUpdateRequest,
                                               BindingResult bindingResult,
                                               @AuthenticationPrincipal PrincipalDetails principalDetails) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            
            throw new CustomValidationApiException("유효성 검사 오류", errors);
        } else {
            User user = userService.update(id, userUpdateRequest.toEntity());

            principalDetails.setUser(user);  // 세션 정보 업데이트 (회원정보 업데이트 창 반영)

            return ResponseEntity.ok()
                    .body(new CMResponse<>(1, "회원정보 변경 성공", user));
        }
    }

}
