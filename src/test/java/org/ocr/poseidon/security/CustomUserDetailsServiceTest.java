package org.ocr.poseidon.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.ocr.poseidon.domain.User;
import org.ocr.poseidon.enums.UserRole;
import org.ocr.poseidon.repositories.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    CustomUserDetailsService customUserDetailsService;

    @Mock
    UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        customUserDetailsService = new CustomUserDetailsService(userRepository);
    }

    @Test
    void loadUserByUsername() {
        //GIVEN
        String username = "loic";

        User user = new User();
        user.setUsername(username);
        user.setFullname(username);
        user.setPassword("password");
        user.setUsername(username);
        user.setRole(String.valueOf(UserRole.ADMIN));
        user.setId(1);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        //WHEN
        UserDetails result = customUserDetailsService.loadUserByUsername(username);

        //THEN
        verify(userRepository, times(1)).findByUsername(anyString());
        assertNotNull(result);
        assertEquals(username, result.getUsername());
        assertTrue(result.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")));
    }
}