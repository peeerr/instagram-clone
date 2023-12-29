package com.peeerr.instagram.config;

import com.peeerr.instagram.config.oauth.OAuth2DetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final OAuth2DetailsService oAuth2DetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(c -> c.disable())
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(new AntPathRequestMatcher("/")).authenticated()
                        .requestMatchers(new AntPathRequestMatcher("/user/**")).authenticated()
                        .requestMatchers(new AntPathRequestMatcher("/image/**")).authenticated()
                        .requestMatchers(new AntPathRequestMatcher("/subscribe/**")).authenticated()
                        .requestMatchers(new AntPathRequestMatcher("/comment/**")).authenticated()
                        .requestMatchers(new AntPathRequestMatcher("/api/**")).authenticated()
                        .anyRequest().permitAll())
                .formLogin(log -> log
                        .loginPage("/auth/signin")
                        .loginProcessingUrl("/auth/signin")
                        .defaultSuccessUrl("/"))
                .oauth2Login(oauth -> oauth
                        .userInfoEndpoint(info -> info.userService(oAuth2DetailsService)));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
