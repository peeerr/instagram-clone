package com.peeerr.instagram.domain.likes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface LikesRepository extends JpaRepository<Likes, Long> {

    @Modifying
    @Query(value = "INSERT INTO likes(user_id, image_id, create_date) VALUES (:userId, :imageId, now())", nativeQuery = true)
    void like(Long userId, Long imageId);

    @Modifying
    @Query(value = "DELETE FROM likes WHERE user_id = :userId AND image_id = :imageId", nativeQuery = true)
    void unlike(Long userId, Long imageId);

}
