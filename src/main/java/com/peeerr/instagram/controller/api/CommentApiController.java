package com.peeerr.instagram.controller.api;

import com.peeerr.instagram.config.auth.PrincipalDetails;
import com.peeerr.instagram.domain.comment.Comment;
import com.peeerr.instagram.dto.CMResponse;
import com.peeerr.instagram.dto.comment.CommentRequest;
import com.peeerr.instagram.exception.ex.CustomValidationApiException;
import com.peeerr.instagram.exception.ex.CustomValidationException;
import com.peeerr.instagram.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class CommentApiController {

    private final CommentService commentService;

    @PostMapping("/comment")
    public ResponseEntity<?> saveComment(@Valid @RequestBody CommentRequest commentRequest,
                                         BindingResult bindingResult,
                                         @AuthenticationPrincipal PrincipalDetails principalDetails) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();

            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
            }

            throw new CustomValidationApiException("유효성 검사 실패", errorMap);
        }

        String content = commentRequest.getContent();
        Long imageId = commentRequest.getImageId();
        Long principalId = principalDetails.getUser().getId();

        Comment comment = commentService.writeComment(content, imageId, principalId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CMResponse<>(1, "댓글 추가 성공", comment));
    }

    @DeleteMapping("/comment/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);

        return ResponseEntity.ok()
                .body(new CMResponse<>(1, "댓글 삭제 성공", null));
    }

}
