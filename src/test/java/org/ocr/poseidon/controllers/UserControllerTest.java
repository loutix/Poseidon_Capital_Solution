package org.ocr.poseidon.controllers;

import jakarta.validation.ValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.ocr.poseidon.domain.User;
import org.ocr.poseidon.dto.UserCreationDTO;
import org.ocr.poseidon.dto.UserUpdateDTO;
import org.ocr.poseidon.enums.UserRole;
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
    @WithMockUser(username = "admin", roles = "ADMIN")
    @DisplayName("Get form add user")
    void addUser() throws Exception {
        //GIVEN

        //WHEN
        mockMvc.perform(get("/user/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"))
                .andExpect(model().attributeExists("userCreationDto"))
                .andExpect(model().attribute("userCreationDto", new UserCreationDTO()));
        //THEN
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    @DisplayName("Post valide new user")
    void validate() throws Exception {
        //GIVEN
        UserCreationDTO userCreationDTO = new UserCreationDTO();
        userCreationDTO.setUsername("Johne");
        userCreationDTO.setFullname("Jeypaul");
        userCreationDTO.setPassword("}a5p5i5Q-N");

        //WHEN
        doNothing().when(userService).saveUser(userCreationDTO);

        mockMvc.perform(post("/user/validate")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("username", userCreationDTO.getUsername())
                        .param("fullname", userCreationDTO.getFullname())
                        .param("password", userCreationDTO.getPassword()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));

        //THEN
        verify(userService, times(1)).saveUser(userCreationDTO);
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    @DisplayName("Post valide new user Error valid form")
    void validateErrorForm() throws Exception {
        //GIVEN
        UserCreationDTO userCreationDTO = new UserCreationDTO();
        userCreationDTO.setUsername("");
        userCreationDTO.setFullname("");
        userCreationDTO.setPassword("");

        //WHEN
        doNothing().when(userService).saveUser(userCreationDTO);

        mockMvc.perform(post("/user/validate")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("username", userCreationDTO.getUsername())
                        .param("fullname", userCreationDTO.getFullname())
                        .param("password", userCreationDTO.getPassword()))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"))
                .andExpect(model().attributeHasFieldErrors("userCreationDto", "fullname", "username", "password"));

        //THEN
        verify(userService, times(0)).saveUser(userCreationDTO);

    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    @DisplayName("Post valide new user Error usernameNotUnique")
    void validateErrorUsernameNotUnique() throws Exception {
        //GIVEN
        UserCreationDTO userCreationDTO = new UserCreationDTO();
        userCreationDTO.setUsername("Johne");
        userCreationDTO.setFullname("Jeypaul");
        userCreationDTO.setPassword("}a5p5i5Q-N");
        //WHEN
        doThrow(ValidationException.class).when(userService).saveUser(userCreationDTO);

        mockMvc.perform(post("/user/validate")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("username", userCreationDTO.getUsername())
                        .param("fullname", userCreationDTO.getFullname())
                        .param("password", userCreationDTO.getPassword()))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"))
                .andExpect(model().attributeExists("usernameNotUnique"))
                .andExpect(model().attribute("usernameNotUnique", true));

        //THEN
        verify(userService, times(1)).saveUser(userCreationDTO);

    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    @DisplayName("Get update user form")
    void showUpdateForm() throws Exception {
        //GIVEN
        int id = 1;

        User user = new User();
        user.setId(1);
        user.setUsername("Johnnnn");
        user.setFullname("Johnnnn");
        user.setPassword("}a5p5i5Q-N");
        user.setRole("USER");

        // WHEN
        when(userService.getById(id)).thenReturn(user);

        mockMvc.perform(get("/user/update/{id}", id))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("userUpdateDTO"))
                .andExpect(model().attribute("userUpdateDTO", new UserUpdateDTO(user)));

        //THEN
        verify(userService, times(1)).getById(id);
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    @DisplayName("Post form update user")
    void updateUser() throws Exception {
        //GIVEN
        int id = 1;
        UserUpdateDTO userUpdateDTO = new UserUpdateDTO();
        userUpdateDTO.setUsername("Johne");
        userUpdateDTO.setFullname("Jeypaul");
        userUpdateDTO.setPassword("}a5p5i5Q-N");
        userUpdateDTO.setRole(UserRole.USER);
        userUpdateDTO.setId(1);

        // WHEN
        mockMvc.perform(post("/user/update/{id}", id)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id", String.valueOf(userUpdateDTO.getId()))
                        .param("role", String.valueOf(userUpdateDTO.getRole()))
                        .param("username", userUpdateDTO.getUsername())
                        .param("fullname", userUpdateDTO.getFullname())
                        .param("password", userUpdateDTO.getPassword()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));

        //THEN
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    @DisplayName("Post form update user error form")
    void updateUserErrorForm() throws Exception {
        //GIVEN
        int id = 1;
        UserUpdateDTO userUpdateDTO = new UserUpdateDTO();
        userUpdateDTO.setUsername("");
        userUpdateDTO.setFullname("");
        userUpdateDTO.setPassword("}-N");
        userUpdateDTO.setRole(UserRole.USER);
        userUpdateDTO.setId(1);

        // WHEN
        mockMvc.perform(post("/user/update/{id}", id)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id", String.valueOf(userUpdateDTO.getId()))
                        .param("role", String.valueOf(userUpdateDTO.getRole()))
                        .param("username", userUpdateDTO.getUsername())
                        .param("fullname", userUpdateDTO.getFullname())
                        .param("password", userUpdateDTO.getPassword()))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("userUpdateDTO", "username", "fullname", "password"))
                .andExpect(view().name("user/update"));
        //THEN
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    @DisplayName("Post form update user usernameNotUnique")
    void updateUserErrorUsernameNotUnique() throws Exception {
        //GIVEN
        int id = 1;
        UserUpdateDTO userUpdateDTO = new UserUpdateDTO();
        userUpdateDTO.setUsername("Johne");
        userUpdateDTO.setFullname("Jeypaul");
        userUpdateDTO.setPassword("}a5p5i5Q-N");
        userUpdateDTO.setRole(UserRole.USER);
        userUpdateDTO.setId(1);


        //WHEN
        doThrow(ValidationException.class).when(userService).controlUser(any(User.class));

        mockMvc.perform(post("/user/update/{id}", id)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id", String.valueOf(userUpdateDTO.getId()))
                        .param("role", String.valueOf(userUpdateDTO.getRole()))
                        .param("username", userUpdateDTO.getUsername())
                        .param("fullname", userUpdateDTO.getFullname())
                        .param("password", userUpdateDTO.getPassword()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("usernameNotUnique"))
                .andExpect(model().attribute("usernameNotUnique", true))
                .andExpect(view().name("user/update"));
        //THEN
        verify(userService, times(1)).controlUser(any(User.class));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    @DisplayName("Delete User")
    void deleteUser() throws Exception {
        //GIVEN
        int id = 1;
        // WHEN
        mockMvc.perform(get("/user/delete/{id}", id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));

        //THEN
        verify(userService, times(1)).delete(id);

    }
}