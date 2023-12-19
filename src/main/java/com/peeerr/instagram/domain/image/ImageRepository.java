package com.peeerr.instagram.domain.image;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {

    @Query(value = "SELECT * FROM image WHERE user_id IN (SELECT to_user_id FROM subscribe WHERE from_user_id = :principalId) ORDER BY id DESC;", nativeQuery = true)
    Page<Image> getStories(Long principalId, Pageable pageable);

    @Query(value = "SELECT i.* FROM image i INNER JOIN (SELECT image_id, COUNT(image_id) like_count FROM likes l  GROUP BY image_id) c ON i.id = c.image_id ORDER BY like_count DESC", nativeQuery = true)
    List<Image> getPopulars();

}
