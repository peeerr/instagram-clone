package com.peeerr.instagram.service;

import com.peeerr.instagram.domain.subscribe.SubscribeRepository;
import com.peeerr.instagram.dto.subscribe.SubscribeInfoResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SubscribeService {

    private final SubscribeRepository subscribeRepository;
    private final EntityManager em;

    @Transactional
    public void subscribe(Long fromUserId, Long toUserId) {
        subscribeRepository.subscribe(fromUserId, toUserId);
    }

    @Transactional
    public void unsubscribe(Long fromUserId, Long toUserId) {
        subscribeRepository.unsubscribe(fromUserId, toUserId);
    }

    @Transactional(readOnly = true)
    public List<SubscribeInfoResponse> getSubscribes(Long principleId, Long pageUserId) {
        JpaResultMapper mapper = new JpaResultMapper();
        StringBuffer sb = new StringBuffer();

        sb.append("SELECT u.id, u.username, u.profile_image_url AS profileImageUrl, ");
        sb.append("IF((SELECT 1 FROM subscribe s WHERE from_user_id = ? AND to_user_id = u.id), 1, 0) AS subscribeState, ");
        sb.append("IF((u.id = ?), 1, 0) AS equalUserState ");
        sb.append("FROM `user` u INNER JOIN subscribe s ON u.id = s.to_user_id ");
        sb.append("WHERE s.from_user_id = ?");

        Query query = em.createNativeQuery(sb.toString())
                .setParameter(1, principleId)
                .setParameter(2, principleId)
                .setParameter(3, pageUserId);

        return mapper.list(query, SubscribeInfoResponse.class);
    }

}
