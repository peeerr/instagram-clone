package com.peeerr.instagram.service;

import com.peeerr.instagram.domain.user.User;
import com.peeerr.instagram.domain.user.UserRepository;
import com.peeerr.instagram.dto.user.UserProfileRequest;
import com.peeerr.instagram.exception.ex.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public UserProfileRequest getProfile(Long pageUserId, Long principalId) {
        UserProfileRequest dto = new UserProfileRequest();

        User user = userRepository.findById(pageUserId).orElseThrow(() -> new CustomException("해당 프로필 페이지는 존재하지 않습니다."));

        dto.setUser(user);
        dto.setImageCounter(user.getImages().size());
        dto.setPageOwnerState(pageUserId == principalId);

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

}
