package com.peeerr.instagram.dto.image;

import com.peeerr.instagram.domain.image.Image;
import com.peeerr.instagram.domain.user.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
public class ImageUploadRequest {

    private String caption;
    private MultipartFile file;

    public Image toEntity(String postImageUrl, User user) {
        return Image.builder()
                .caption(caption)
                .postImageUrl(postImageUrl)
                .user(user)
                .build();
    }

}
