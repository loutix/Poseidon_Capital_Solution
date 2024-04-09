package org.ocr.poseidon.services;

import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.ocr.poseidon.domain.User;
import org.ocr.poseidon.dto.UserCreationDTO;
import org.ocr.poseidon.enums.UserRole;
import org.ocr.poseidon.exceptions.ItemNotFoundException;
import org.ocr.poseidon.repositories.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JpaRepository<User, Integer> repository;

    @BeforeEach
    public void setUp() {
        userService = new UserServiceImpl(repository, userRepository, passwordEncoder);
    }


    @Test
    @DisplayName("save user")
    void saveUser() {
        //GIVEN
        UserCreationDTO user = new UserCreationDTO();
        user.setUsername("Johnnn");
        user.setFullname("testeur");
        user.setRole((UserRole.USER));
        user.setPassword(passwordEncoder.encode("password"));

        //WHEN
        when(userRepository.existsByUsername(anyString())).thenReturn(false);

        userService.saveUser(user);

        //THEN
        verify(userRepository, times(1)).existsByUsername(anyString());
    }


    @Test
    @DisplayName("save user throw error")
    void saveUser_throw_error() {
        //GIVEN
        UserCreationDTO user = new UserCreationDTO();
        user.setUsername("Johnnn");
        user.setFullname("testeur");
        user.setRole((UserRole.USER));
        user.setPassword(passwordEncoder.encode("password"));

        //WHEN
        when(userRepository.existsByUsername(anyString())).thenReturn(true);

        //THEN
        assertThrows(ValidationException.class, () -> userService.saveUser(user));
        verify(userRepository, times(1)).existsByUsername(anyString());
    }

    @Test
    @DisplayName("controlUser")
    void controlUser() {
        //GIVEN
        Integer id = 1;


        User userNew = new User();
        userNew.setId(id);
        userNew.setUsername("JohnNewName");
        userNew.setFullname("testeur");
        userNew.setRole(String.valueOf((UserRole.USER)));
        userNew.setPassword(passwordEncoder.encode("password"));

        User userToUpdated = new User();
        userToUpdated.setId(id);
        userToUpdated.setUsername("JohnOldName");
        userToUpdated.setFullname("testeur");
        userToUpdated.setRole(String.valueOf((UserRole.USER)));
        userToUpdated.setPassword(passwordEncoder.encode("password"));

        //WHEN
        when(repository.existsById(id)).thenReturn(true);
        when(userRepository.findById(id)).thenReturn(Optional.of(userToUpdated));
        when(userRepository.existsByUsername(userNew.getUsername())).thenReturn(false);


        User result = userService.controlUser(userNew);

        //THEN
        verify(repository, times(1)).existsById(id);
        verify(userRepository, times(1)).findById(id);
        verify(userRepository, times(1)).existsByUsername(userNew.getUsername());

        assertEquals("JohnNewName", result.getUsername());
    }

    @Test
    @DisplayName("controlUser throw")
    void controlUser_throw() {
        //GIVEN
        Integer id = 1;

        User userNew = new User();
        userNew.setId(id);
        userNew.setUsername("JohnNewName");
        userNew.setFullname("testeur");
        userNew.setRole(String.valueOf((UserRole.USER)));
        userNew.setPassword(passwordEncoder.encode("password"));

        User userToUpdated = new User();
        userToUpdated.setId(id);
        userToUpdated.setUsername("JohnOldName");
        userToUpdated.setFullname("testeur");
        userToUpdated.setRole(String.valueOf((UserRole.USER)));
        userToUpdated.setPassword(passwordEncoder.encode("password"));

        //WHEN
        when(repository.existsById(id)).thenReturn(false);

        //THEN
        assertThrows(ItemNotFoundException.class, () -> userService.controlUser(userNew));
        verify(repository, times(1)).existsById(id);

    }

    @Test
    @DisplayName("controlUser throw username already in use")
    void controlUser_throw_username() {
        //GIVEN
        Integer id = 1;

        User userNew = new User();
        userNew.setId(id);
        userNew.setUsername("JohnNewName");
        userNew.setFullname("testeur");
        userNew.setRole(String.valueOf((UserRole.USER)));
        userNew.setPassword(passwordEncoder.encode("password"));

        User userToUpdated = new User();
        userToUpdated.setId(id);
        userToUpdated.setUsername("JohnOldName");
        userToUpdated.setFullname("testeur");
        userToUpdated.setRole(String.valueOf((UserRole.USER)));
        userToUpdated.setPassword(passwordEncoder.encode("password"));

        //WHEN
        when(repository.existsById(id)).thenReturn(true);
        when(userRepository.findById(id)).thenReturn(Optional.of(userToUpdated));
        when(userRepository.existsByUsername(userNew.getUsername())).thenReturn(true);

        //THEN
        assertThrows(ValidationException.class, () -> userService.controlUser(userNew));
        verify(repository, times(1)).existsById(id);

    }
}