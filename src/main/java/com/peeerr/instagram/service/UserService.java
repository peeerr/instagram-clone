package com.peeerr.instagram.service;

import com.peeerr.instagram.domain.user.User;
import com.peeerr.instagram.domain.user.UserRepository;
import com.peeerr.instagram.exception.ex.CustomValidationApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User update(Long id, User user) {
        // 영속화
        User userEntity = userRepository.findById(id).orElseThrow(() -> new CustomValidationApiException("해당 유저를 찾을 수 없습니다."));

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
