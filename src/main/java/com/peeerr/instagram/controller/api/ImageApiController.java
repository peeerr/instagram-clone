package com.peeerr.instagram.controller.api;

import com.peeerr.instagram.config.auth.PrincipalDetails;
import com.peeerr.instagram.domain.image.Image;
import com.peeerr.instagram.dto.CMResponse;
import com.peeerr.instagram.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class ImageApiController {

    private final ImageService imageService;

    @GetMapping("image")
    public ResponseEntity<?> imageStory(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                        @PageableDefault(size = 3) Pageable pageable) {
        Page<Image> stories = imageService.getImageStories(principalDetails.getUser().getId(), pageable);

        return ResponseEntity.ok()
                .body(new CMResponse<>(1, "구독유저 스토리 가져오기 성공", stories));
    }

}
