package com.peeerr.instagram.controller;

import com.peeerr.instagram.config.auth.PrincipalDetails;
import com.peeerr.instagram.domain.user.User;
import com.peeerr.instagram.dto.user.UserProfileRequest;
import com.peeerr.instagram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    @GetMapping("/user/{pageUserId}")
    public String profile(@PathVariable Long pageUserId,
                          @AuthenticationPrincipal PrincipalDetails principalDetails,
                          Model model) {
        UserProfileRequest dto = userService.getProfile(pageUserId, principalDetails.getUser().getId());
        model.addAttribute("dto", dto);

        return "user/profile";
    }

    @GetMapping("/user/{id}/update")
    public String update(@PathVariable Long id) {
        return "user/update";
    }

}
