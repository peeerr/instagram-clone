package com.peeerr.instagram.controller.api;

import com.peeerr.instagram.config.auth.PrincipalDetails;
import com.peeerr.instagram.domain.image.Image;
import com.peeerr.instagram.dto.CMResponse;
import com.peeerr.instagram.exception.ex.CustomApiException;
import com.peeerr.instagram.service.ImageService;
import com.peeerr.instagram.service.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class ImageApiController {

    private final ImageService imageService;
    private final LikesService likesService;

    @GetMapping("/image")
    public ResponseEntity<?> imageStory(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                        @PageableDefault(size = 3) Pageable pageable) {
        Page<Image> stories = imageService.getImageStories(principalDetails.getUser().getId(), pageable);

        return ResponseEntity.ok()
                .body(new CMResponse<>(1, "구독유저 스토리 가져오기 성공", stories));
    }

    @PostMapping("/image/{imageId}/likes")
    public ResponseEntity<?> like(@PathVariable Long imageId,
                                  @AuthenticationPrincipal PrincipalDetails principalDetails) {
        try {
            likesService.like(principalDetails.getUser().getId(), imageId);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new CMResponse<>(1, "좋아요 성공", null));
        } catch (Exception e) {
            throw new CustomApiException("좋아요 오류");
        }
    }

    @DeleteMapping("/image/{imageId}/likes")
    public ResponseEntity<?> unlike(@PathVariable Long imageId,
                                  @AuthenticationPrincipal PrincipalDetails principalDetails) {
        likesService.unlike(principalDetails.getUser().getId(), imageId);

        return ResponseEntity.ok()
                .body(new CMResponse<>(1, "좋아요 취소 성공", null));
    }

}
