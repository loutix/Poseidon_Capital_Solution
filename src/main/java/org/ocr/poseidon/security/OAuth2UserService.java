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


/**
 * Custom implementation of OAuth2UserService to handle OAuth2 user authentication.
 * Extends DefaultOAuth2UserService provided by Spring Security.
 */
@Slf4j
@Service
public class OAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    public OAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * This method load user information from the OAuth2 provider.
     *
     * @param oAuth2UserRequest The OAuth2 user request.
     * @return OAuth2User containing user information.
     * @throws OAuth2AuthenticationException If an error occurs during authentication.
     */


    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);
        return processOAuth2User(oAuth2User);

    }

    /**
     * Processes the OAuth2 user information obtained from the provider.
     *
     * @param oAuth2User The OAuth2 user information.
     * @return DefaultOAuth2User containing processed user details.
     */

    private DefaultOAuth2User processOAuth2User(OAuth2User oAuth2User) {

        String username = oAuth2User.getAttributes().get("login").toString();
        String id = oAuth2User.getAttributes().get("id").toString();

        String username_id = username + id;

        boolean existingUser = userRepository.findByUsername(username + id).stream().anyMatch(user -> user.getSso().equals(true));

        if (!existingUser) {
            User newUserSSO = new User();
            newUserSSO.setUsername(username_id);
            newUserSSO.setFullname(oAuth2User.getAttributes().get("login").toString());
            newUserSSO.setRole(String.valueOf(UserRole.USER));
            newUserSSO.setSso(true);

            userRepository.save(newUserSSO);
        }

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        return new DefaultOAuth2User(authorities, oAuth2User.getAttributes(), "login");
    }
}
