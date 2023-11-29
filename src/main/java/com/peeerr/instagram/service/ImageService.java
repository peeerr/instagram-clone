package com.peeerr.instagram.service;

import com.peeerr.instagram.config.auth.PrincipalDetails;
import com.peeerr.instagram.domain.image.Image;
import com.peeerr.instagram.domain.image.ImageRepository;
import com.peeerr.instagram.dto.image.ImageUploadRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ImageService {

    private final ImageRepository imageRepository;

    @Value("${file.path}")
    private String uploadFolder;

    @Transactional
    public void imageUpload(ImageUploadRequest imageUploadRequest, PrincipalDetails principalDetails) {
        UUID uuid = UUID.randomUUID();
        String imageFileName = uuid + "_" + imageUploadRequest.getFile().getOriginalFilename();
        Path imageFilePath = Paths.get(uploadFolder + imageFileName);
        
        // 이미지 파일 저장
        try {
            Files.write(imageFilePath, imageUploadRequest.getFile().getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 이미지 DB에 저장
        Image image = imageUploadRequest.toEntity(imageFileName, principalDetails.getUser());
        imageRepository.save(image);
    }

    @Transactional(readOnly = true)  // 더티체킹, flush 를 안해서 성능 더 좋아짐
    public Page<Image> getImageStories(Long principalId, Pageable pageable) {
        return imageRepository.getStories(principalId, pageable);
    }

}
