package com.peeerr.instagram.controller;

import com.peeerr.instagram.config.auth.PrincipalDetails;
import com.peeerr.instagram.dto.image.ImageUploadRequest;
import com.peeerr.instagram.exception.ex.CustomValidationException;
import com.peeerr.instagram.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class ImageController {

    private final ImageService imageService;

    @GetMapping({"/", "/image/story"})
    public String story() {
        return "image/story";
    }

    @GetMapping("/image/popular")
    public String popular() {
        return "image/popular";
    }

    @GetMapping("/image/upload")
    public String upload() {
        return "image/upload";
    }

    @PostMapping("/image")
    public String imageUpload(ImageUploadRequest imageUploadRequest,
                              @AuthenticationPrincipal PrincipalDetails principalDetails) {
        if (imageUploadRequest.getFile().isEmpty()) {
            throw new CustomValidationException("이미지가 첨부되지 않았습니다.", null);
        }

        imageService.imageUpload(imageUploadRequest, principalDetails);

        return "redirect:/user/" + principalDetails.getUser().getId();
    }

}
