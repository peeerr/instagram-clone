package com.peeerr.instagram.service;

import com.peeerr.instagram.domain.likes.LikesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class LikesService {

    private final LikesRepository likesRepository;

    @Transactional
    public void like(Long userId, Long imageId) {
        likesRepository.like(userId, imageId);
    }

    @Transactional
    public void unlike(Long userId, Long imageId) {
        likesRepository.unlike(userId, imageId);
    }

}
