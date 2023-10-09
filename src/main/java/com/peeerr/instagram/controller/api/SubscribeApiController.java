package com.peeerr.instagram.controller.api;

import com.peeerr.instagram.config.auth.PrincipalDetails;
import com.peeerr.instagram.dto.CMResponse;
import com.peeerr.instagram.exception.ex.CustomApiException;
import com.peeerr.instagram.service.SubscribeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class SubscribeApiController {

    private final SubscribeService subscribeService;

    @PostMapping("/subscribe/{toUserId}")
    public ResponseEntity<?> subscribe(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                       @PathVariable Long toUserId) {
        try {
            subscribeService.subscribe(principalDetails.getUser().getId(), toUserId);

            return ResponseEntity.ok()
                    .body(new CMResponse(1, "구독 성공", null));
        } catch (Exception e) {
            throw new CustomApiException("이미 구독한 유저입니다.");
        }
    }

    @DeleteMapping("/subscribe/{toUserId}")
    public ResponseEntity<?> unsubscribe(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                       @PathVariable Long toUserId) {
        subscribeService.unsubscribe(principalDetails.getUser().getId(), toUserId);

        return ResponseEntity.ok()
                .body(new CMResponse(1, "구독취소 성공", null));
    }

}
