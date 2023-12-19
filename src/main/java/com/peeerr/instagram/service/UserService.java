package com.peeerr.instagram.service;

import com.peeerr.instagram.domain.subscribe.SubscribeRepository;
import com.peeerr.instagram.domain.user.User;
import com.peeerr.instagram.domain.user.UserRepository;
import com.peeerr.instagram.dto.user.UserProfileRequest;
import com.peeerr.instagram.exception.ex.CustomApiException;
import com.peeerr.instagram.exception.ex.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final SubscribeRepository subscribeRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${file.path}")
    private String uploadFolder;

    @Transactional(readOnly = true)
    public UserProfileRequest getProfile(Long pageUserId, Long principalId) {
        UserProfileRequest dto = new UserProfileRequest();

        User user = userRepository.findById(pageUserId).orElseThrow(() -> new CustomException("해당 프로필 페이지는 존재하지 않습니다."));

        // 프로필 페이지 좋아요 카운트
        user.getImages().forEach(image -> {
            image.setLikeCount(image.getLikes().size());
        });

        dto.setUser(user);
        dto.setImageCounter(user.getImages().size());
        dto.setPageOwnerState(pageUserId == principalId);
        dto.setSubscribeState(subscribeRepository.subscribeState(principalId, pageUserId) == 1);
        dto.setSubscribeCount(subscribeRepository.subscribeCount(pageUserId));

        return dto;
    }

    @Transactional
    public User update(Long id, User user) {
        // 영속화
        User userEntity = userRepository.findById(id).orElseThrow(() -> new CustomException("해당 유저를 찾을 수 없습니다."));

        // 영속화 된 오브젝트 더티체킹
        userEntity.setName(user.getName());
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        userEntity.setBio(user.getBio());
        userEntity.setWebsite(user.getWebsite());
        userEntity.setPhone(user.getPhone());
        userEntity.setGender(user.getGender());

        return userEntity;
    }

    @Transactional
    public User profileUpdate(Long principalId, MultipartFile profileImageFile) {
        UUID uuid = UUID.randomUUID();
        String imageFileName = uuid + "_" + profileImageFile.getOriginalFilename();

        Path path = Paths.get(uploadFolder + imageFileName);

        try {
            Files.write(path, profileImageFile.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        User user = userRepository.findById(principalId).orElseThrow(() -> {
            throw new CustomApiException("유저를 찾을 수 없습니다.");
        });

        user.setProfileImageUrl(imageFileName);

        return user;
    }
}
