package com.peeerr.instagram.dto.user;

import com.peeerr.instagram.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class UserProfileRequest {

    private boolean pageOwnerState;
    private int imageCounter;
    private User user;

}
