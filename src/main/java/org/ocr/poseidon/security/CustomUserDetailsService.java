package org.ocr.poseidon.security;

import lombok.extern.slf4j.Slf4j;
import org.ocr.poseidon.domain.User;
import org.ocr.poseidon.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Load user-specific data based on the provided username.
     *
     * @param username The username of the user to load.
     * @return UserDetails containing the user's details.
     * @throws UsernameNotFoundException If the user with the given username is not found.
     */

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User userDomain = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return org.springframework.security.core.userdetails.User
                .withUsername(userDomain.getUsername())
                .password(userDomain.getPassword())
                .roles(String.valueOf(userDomain.getRole()))
                .build();
    }


}
