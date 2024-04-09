package org.ocr.poseidon.util;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.ocr.poseidon.enums.UserRole;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@SpringBootTest
class AuthUtilsTest {

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private AuthUtils authUtils;

    @Test
    void isAdmin() {
        // Créez un UserDetails représentant un utilisateur avec le rôle "ADMIN"
//        UserDetails userDetails = org.springframework.security.core.userdetails.User
//                .withUsername("hello")
//                .password("password")
//                .roles(String.valueOf(UserRole.ADMIN))
//                .build();

        UserDetails userDetails = new User("hello", "password", AuthorityUtils.createAuthorityList("ROLE_ADMIN"));

        //Objet Authentication basé sur UserDetails
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());

        //Mettre l'objet Authent dans le SecurityContextHolder
        SecurityContextHolder.getContext().setAuthentication(authentication);

        assertTrue(authUtils.isAdmin());
    }

    @Test
    void isNotAdmin() {
        // Créez un UserDetails représentant un utilisateur avec le rôle "ADMIN"
        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername("hello")
                .password("password")
                .roles(String.valueOf(UserRole.USER))
                .build();

        //Objet Authentication basé sur UserDetails
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());

        //Mettre l'objet Authent dans le SecurityContextHolder
        SecurityContextHolder.getContext().setAuthentication(authentication);

        assertFalse(authUtils.isAdmin());
    }

    @Test
    void isAdminAuthNull() {
        SecurityContextHolder.getContext().setAuthentication(null);
        assertFalse(authUtils.isAdmin());
    }

    @Test
    void isUserAnonymousNull() {
        SecurityContextHolder.getContext().setAuthentication(null);
        assertFalse(authUtils.isUserAnonymous());
    }

    @Test
    void isUserAnonymous() {

        Object principal = "anonymousUser";

        Authentication authentication = mock(Authentication.class);

        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn(principal);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        assertTrue(authUtils.isUserAnonymous());
    }
}