package com.peeerr.instagram.service;

import com.peeerr.instagram.domain.subscribe.SubscribeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class SubscribeService {

    private final SubscribeRepository subscribeRepository;

    @Transactional
    public void subscribe(Long fromUserId, Long toUserId) {
        subscribeRepository.subscribe(fromUserId, toUserId);
    }

    @Transactional
    public void unsubscribe(Long fromUserId, Long toUserId) {
        subscribeRepository.unsubscribe(fromUserId, toUserId);
    }

}
