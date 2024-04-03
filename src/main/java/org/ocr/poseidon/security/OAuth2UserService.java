package org.ocr.poseidon.security;


import lombok.extern.slf4j.Slf4j;
import org.ocr.poseidon.domain.User;
import org.ocr.poseidon.enums.UserRole;
import org.ocr.poseidon.repositories.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class OAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    public OAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {

        log.info("Load user {}", oAuth2UserRequest);
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);
        return processOAuth2User(oAuth2UserRequest, oAuth2User);


    }

    private DefaultOAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {

        String username = oAuth2User.getAttributes().get("login").toString();

        Optional<User> existingUser = userRepository.findByUsername(username);

        if (existingUser.isPresent()) {
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            return new DefaultOAuth2User(authorities, oAuth2User.getAttributes(), "login");

        } else {
            User newUserSSO = new User();
            newUserSSO.setFullname(oAuth2User.getAttributes().get("login").toString());
            newUserSSO.setUsername(oAuth2User.getAttributes().get("login").toString());
            newUserSSO.setRole(String.valueOf(UserRole.USER));

            userRepository.save(newUserSSO);

            log.trace("new SSO user created" + newUserSSO);

            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            return new DefaultOAuth2User(authorities, oAuth2User.getAttributes(), "login");
        }
    }
}
