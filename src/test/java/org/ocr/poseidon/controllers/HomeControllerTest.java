package org.ocr.poseidon.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.ocr.poseidon.security.OAuth2UserService;
import org.ocr.poseidon.security.SecurityConfig;
import org.ocr.poseidon.util.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = HomeController.class)
@ContextConfiguration(classes = {SecurityConfig.class, HomeController.class})
class HomeControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OAuth2UserService oAuth2UserService;

    @MockBean
    private AuthUtils authUtils;

    @Test
    @WithMockUser
    @DisplayName("display home page")
    void home() throws Exception {
        //GIVEN
        when(authUtils.isUserAnonymous()).thenReturn(false);
        when(authUtils.isAdmin()).thenReturn(true);

        //WHEN
        mockMvc.perform(get("/home"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(model().attributeExists("isUserAnonymous"))
                .andExpect(model().attribute("isUserAnonymous", false))
                .andExpect(model().attributeExists("isAdmin"))
                .andExpect(model().attribute("isAdmin", true));
        //THEN
        verify(authUtils, times(1)).isUserAnonymous();
        verify(authUtils, times(1)).isAdmin();
    }

}