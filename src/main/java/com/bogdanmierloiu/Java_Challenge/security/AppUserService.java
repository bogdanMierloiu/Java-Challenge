package com.bogdanmierloiu.Java_Challenge.security;

import jakarta.annotation.PostConstruct;
import lombok.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Value
public class AppUserService implements UserDetailsService {
    PasswordEncoder passwordEncoder;

    Map<String, AppUser> users = new HashMap<>();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return users.get(username);
    }

    @PostConstruct
    private void createHardcodedUsers(){

        var bogdan = AppUser.builder()
                .username("bogdan")
                .password(passwordEncoder.encode("1234"))
                .authorities(List.of(new SimpleGrantedAuthority("read")))
                .build();
        var admin = AppUser.builder()
                .username("admin")
                .password(passwordEncoder.encode("1234"))
                .authorities(List.of(new SimpleGrantedAuthority("read")))
                .build();

        createUser(bogdan);
        createUser(admin);

    }

    private void createUser(AppUser user) {
        users.putIfAbsent(user.getUsername(), user);
    }


    OAuth2UserService<OidcUserRequest, OidcUser> oidcLoginHandler() {
        return userRequest -> {
            OidcUserService delegate = new OidcUserService();
            OidcUser oidcUser = delegate.loadUser(userRequest);
            return AppUser
                    .builder()
                    .provider(LoginProvider.APP)
                    .username(oidcUser.getEmail())
                    .name(oidcUser.getFullName())
                    .email(oidcUser.getEmail())
                    .userId(oidcUser.getName())
                    .imageUrl(oidcUser.getAttribute("picture"))
                    .password(passwordEncoder.encode(UUID.randomUUID().toString()))
                    .attributes(oidcUser.getAttributes())
                    .authorities(oidcUser.getAuthorities())
                    .build();
        };
    }
    OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2LoginHandler() {
        return userRequest -> {
            String clientId = userRequest.getClientRegistration().getClientId().toUpperCase();
            LoginProvider provider = null;
            if (clientId.contains("GOOGLEUSERCONTENT")) {
                provider = LoginProvider.GOOGLE;
            } else if (clientId.contains("D17E29B35FAB10516986")) {
                provider = LoginProvider.GITHUB;
            }
            DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
            OAuth2User oAuth2User = delegate.loadUser(userRequest);
            return AppUser
                    .builder()
                    .provider(provider)
                    .username(oAuth2User.getAttribute("login"))
                    .name(oAuth2User.getAttribute("login"))
                    .password(passwordEncoder.encode(UUID.randomUUID().toString()))
                    .userId(oAuth2User.getName())
                    .userId(oAuth2User.getAttribute("avatar_url"))
                    .attributes(oAuth2User.getAttributes())
                    .authorities(oAuth2User.getAuthorities())
                    .build();
        };
    }




}