package com.peeerr.instagram.controller.api;

import com.peeerr.instagram.config.auth.PrincipalDetails;
import com.peeerr.instagram.domain.user.User;
import com.peeerr.instagram.dto.CMResponse;
import com.peeerr.instagram.dto.subscribe.SubscribeInfoResponse;
import com.peeerr.instagram.dto.user.UserUpdateRequest;
import com.peeerr.instagram.exception.ex.CustomValidationApiException;
import com.peeerr.instagram.service.SubscribeService;
import com.peeerr.instagram.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class UserApiController {

    private final UserService userService;
    private final SubscribeService subscribeService;

    @GetMapping("/user/{pageUserId}/subscribe")
    public ResponseEntity<?> subscribeInfo(@PathVariable Long pageUserId,
                                           @AuthenticationPrincipal PrincipalDetails principalDetails) {
        List<SubscribeInfoResponse> dto = subscribeService.getSubscribes(principalDetails.getUser().getId(), pageUserId);

        return ResponseEntity.ok()
                .body(new CMResponse<>(1, "구독 정보 불러오기 성공", dto));
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<CMResponse<?>> update(@PathVariable Long id,
                                               @Valid UserUpdateRequest userUpdateRequest,
                                               BindingResult bindingResult,
                                               @AuthenticationPrincipal PrincipalDetails principalDetails) {
        User user = userService.update(id, userUpdateRequest.toEntity());

        principalDetails.setUser(user);  // 세션 정보 업데이트 (회원정보 업데이트 창 반영)

        return ResponseEntity.ok()
                .body(new CMResponse<>(1, "회원정보 변경 성공", user));
    }

    @PutMapping("/user/{principalId}/profileImageUrl")
    public ResponseEntity<?> profileImageUrlUpdate(@PathVariable Long principalId,
                                                   MultipartFile profileImageFile,
                                                   @AuthenticationPrincipal PrincipalDetails principalDetails) {
        System.out.println("principalId = " + principalId);
        System.out.println("profileImageFile = " + profileImageFile);
        System.out.println("principalDetails = " + principalDetails);

        User user = userService.profileUpdate(principalId, profileImageFile);
        principalDetails.setUser(user);

        return ResponseEntity.ok()
                .body(new CMResponse<>(1, "프로필 사진 변경 성공", null));
    }

}
