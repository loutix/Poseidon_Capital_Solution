package org.ocr.poseidon.controllers;

import jakarta.validation.ValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.ocr.poseidon.dto.UserCreationDTO;
import org.ocr.poseidon.security.OAuth2UserService;
import org.ocr.poseidon.security.SecurityConfig;
import org.ocr.poseidon.services.UserServiceImpl;
import org.ocr.poseidon.util.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UserController.class)
@ContextConfiguration(classes = {SecurityConfig.class, UserController.class})
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OAuth2UserService oAuth2UserService;

    @MockBean
    private AuthUtils authUtils;

    @MockBean
    private UserServiceImpl userService;


    @Test
    @DisplayName("Get the register page")
    void showRegistrationForm() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("registration/register"))
                .andExpect(model().attributeExists("userCreationDTO"))
                .andExpect(model().attribute("userCreationDTO", new UserCreationDTO()));
    }

    @Test
    @DisplayName("Post new user")
    void saveUser() throws Exception {
        //GIVEN
        UserCreationDTO userCreationDTO = new UserCreationDTO();
        userCreationDTO.setUsername("Bernard");
        userCreationDTO.setFullname("Bianci");
        userCreationDTO.setPassword("}a5p5i5Q-N");

        //WHEN
        doNothing().when(userService).saveUser(userCreationDTO);

        mockMvc.perform(post("/register/save")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("username", userCreationDTO.getUsername())
                        .param("fullname", userCreationDTO.getFullname())
                        .param("password", userCreationDTO.getPassword()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?success"));

        //THEN
        verify(userService, times(1)).saveUser(userCreationDTO);
    }


    @Test
    @DisplayName("Post non unique user email")
    void saveUserError() throws Exception {
        //GIVEN
        UserCreationDTO userCreationDTO = new UserCreationDTO();
        userCreationDTO.setUsername("Bernard123");
        userCreationDTO.setFullname("Bianci");
        userCreationDTO.setPassword("}a5p5i5Q-N");

        //WHEN
        doThrow(ValidationException.class).when(userService).saveUser(userCreationDTO);

        mockMvc.perform(post("/register/save")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("username", userCreationDTO.getUsername())
                        .param("fullname", userCreationDTO.getFullname())
                        .param("password", userCreationDTO.getPassword()))
                .andExpect(status().isOk())
                .andExpect(view().name("registration/register"))
                .andExpect(model().attributeExists("usernameNotUnique"))
                .andExpect(model().attribute("usernameNotUnique", true));
        ;

        //THEN
        verify(userService, times(1)).saveUser(userCreationDTO);
    }

    @Test
    @DisplayName("Post error form")
    void saveUserErrorForm() throws Exception {

        //GIVEN
        UserCreationDTO userCreationDTO = new UserCreationDTO();
        userCreationDTO.setUsername("");
        userCreationDTO.setFullname("");
        userCreationDTO.setPassword("");

        //WHEN
        mockMvc.perform(post("/register/save")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("username", userCreationDTO.getUsername())
                        .param("fullname", userCreationDTO.getFullname())
                        .param("password", userCreationDTO.getPassword()))
                .andExpect(status().isOk())
                .andExpect(view().name("registration/register"));

        //THEN
        verify(userService, times(0)).saveUser(userCreationDTO);
    }

    @Test
    @DisplayName("Get the login page")
    void login() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("registration/login"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    @DisplayName("Get user/list ADMIN ROLE")
    void home() throws Exception {

        mockMvc.perform(get("/user/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/list"))
                .andExpect(model().attributeExists("users"));


        verify(userService, times(1)).getAll();
    }

    @Test
    void addUser() {
    }

    @Test
    void validate() {
    }

    @Test
    void showUpdateForm() {
    }

    @Test
    void updateUser() {
    }

    @Test
    void deleteUser() {
    }
}