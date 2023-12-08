package com.peeerr.instagram.domain.likes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.peeerr.instagram.domain.image.Image;
import com.peeerr.instagram.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "likes_uk",
                        columnNames = {"image_id", "user_id"}
                )
        }
)
@Entity
public class Likes {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @JsonIgnoreProperties({"images"})
    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;

    @JoinColumn(name = "image_id")
    @ManyToOne
    private Image image;

    private LocalDateTime createDate;

    @PrePersist
    public void setCreateDate() {
        this.createDate = LocalDateTime.now();
    }

}
