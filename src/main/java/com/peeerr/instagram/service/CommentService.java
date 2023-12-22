package com.peeerr.instagram.service;

import com.peeerr.instagram.domain.comment.Comment;
import com.peeerr.instagram.domain.comment.CommentRepository;
import com.peeerr.instagram.domain.image.Image;
import com.peeerr.instagram.domain.user.User;
import com.peeerr.instagram.domain.user.UserRepository;
import com.peeerr.instagram.exception.ex.CustomApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Transactional
    public Comment writeComment(String content, Long imageId, Long principalId) {
        Image image = new Image(imageId);

        User user = userRepository.findById(principalId).orElseThrow(() -> {
            throw new CustomApiException("해당 유저를 찾을 수 없습니다.");
        });

        Comment comment = Comment.builder()
                .content(content)
                .image(image)
                .user(user)
                .build();

        return commentRepository.save(comment);
    }

    @Transactional
    public void deleteComment(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> {
                    throw new CustomApiException("해당 댓글을 찾을 수 없습니다.");
                });

        commentRepository.delete(comment);
    }

}
