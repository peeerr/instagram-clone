package com.peeerr.instagram.config.oauth;

import com.peeerr.instagram.config.auth.PrincipalDetails;
import com.peeerr.instagram.domain.user.User;
import com.peeerr.instagram.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OAuth2DetailsService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();

        String id = (String) attributes.get("id");
        id = "facebook_" + id;
        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");

        Optional<User> userEntity = userRepository.findUserByUsername(id);

        if (!userEntity.isPresent()) {
            User user = User.builder()
                    .username(id)
                    .role("ROLE_USER")
                    .password(new BCryptPasswordEncoder().encode(UUID.randomUUID().toString()))
                    .email(email)
                    .name(name)
                    .build();

            return new PrincipalDetails(userRepository.save(user), attributes);
        }

        return new PrincipalDetails(userEntity.get(), attributes);
    }

}
