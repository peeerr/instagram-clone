package com.peeerr.instagram.domain.subscribe;

import com.peeerr.instagram.domain.user.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "subscribe_uk",
                        columnNames = {"from_user_id", "to_user_id"}
                )
        }
)
@Entity
public class Subscribe {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @ManyToOne
    private User fromUser;
    @ManyToOne
    private User toUser;

    private LocalDateTime createDate;

    @PrePersist
    public void setCreateDate() {
        this.createDate = LocalDateTime.now();
    }

}
