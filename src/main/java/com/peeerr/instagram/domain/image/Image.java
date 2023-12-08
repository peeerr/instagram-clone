package com.peeerr.instagram.domain.image;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.peeerr.instagram.domain.likes.Likes;
import com.peeerr.instagram.domain.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
public class Image {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String caption;
    private String postImageUrl;

    @JsonIgnoreProperties({"images"})
    @ManyToOne
    private User user;

    @JsonIgnoreProperties({"image"})  // 무한참조 오류 해결
    @OneToMany(mappedBy = "image")
    private List<Likes> likes;

    private LocalDateTime createDate;

    @Setter
    @Transient  // DB에 삽입 안됨
    private boolean likeState;

    @Setter
    @Transient
    private int likeCount;

    @PrePersist
    public void setCreateDate() {
        this.createDate = LocalDateTime.now();
    }

    @Builder
    public Image(String caption, String postImageUrl, User user) {
        this.caption = caption;
        this.postImageUrl = postImageUrl;
        this.user = user;
    }

}
