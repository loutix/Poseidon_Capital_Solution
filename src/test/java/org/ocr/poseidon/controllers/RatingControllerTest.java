package org.ocr.poseidon.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.ocr.poseidon.domain.Rating;
import org.ocr.poseidon.dto.RatingCreateRequestDTO;
import org.ocr.poseidon.dto.RatingUpdateRequestDTO;
import org.ocr.poseidon.security.OAuth2UserService;
import org.ocr.poseidon.security.SecurityConfig;
import org.ocr.poseidon.services.RatingServiceImpl;
import org.ocr.poseidon.util.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = Rating.class)
@ContextConfiguration(classes = {SecurityConfig.class, RatingController.class})
class RatingControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private OAuth2UserService oAuth2UserService;

    @MockBean
    private RatingServiceImpl ratingService;

    @MockBean
    private AuthUtils authUtils;
    //GIVEN

    @Test
    @WithMockUser
    @DisplayName("Get /rating/list")
    void home() throws Exception {

        //GIVEN
        List<Rating> ratings = new ArrayList<>(List.of(new Rating()));

        //WHEN
        when(authUtils.isAdmin()).thenReturn(true);
        when(ratingService.getAll()).thenReturn(ratings);

        mockMvc.perform(get("/rating/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/list"))
                .andExpect(model().attributeExists("ratings"))
                .andExpect(model().attribute("ratings", ratings))
                .andExpect(model().attributeExists("isAdmin"))
                .andExpect(model().attribute("isAdmin", true));

        //THEN
        verify(ratingService, times(1)).getAll();
        verify(authUtils, times(1)).isAdmin();
    }


    @Test
    @WithMockUser
    @DisplayName("Get /rating/list - USER")
    void homeUser() throws Exception {

        //GIVEN
        List<Rating> ratings = new ArrayList<>(List.of(new Rating()));

        //WHEN
        when(authUtils.isAdmin()).thenReturn(false);
        when(ratingService.getAll()).thenReturn(ratings);

        mockMvc.perform(get("/rating/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/list"))
                .andExpect(model().attributeExists("ratings"))
                .andExpect(model().attribute("ratings", ratings));

        //THEN
        verify(ratingService, times(1)).getAll();
        verify(authUtils, times(1)).isAdmin();
    }

    @Test
    @WithMockUser
    @DisplayName("Get /rating/add")
    void addRatingForm() throws Exception {
        //GIVEN

        //WHEN
        mockMvc.perform(get("/rating/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"))
                .andExpect(model().attributeExists("ratingCreateRequestDTO"))
                .andExpect(model().attribute("ratingCreateRequestDTO", new RatingCreateRequestDTO()));
        //THEN

    }

    @Test
    @WithMockUser
    @DisplayName("POST /rating/validate")
    void validate() throws Exception {
        //GIVEN
        RatingCreateRequestDTO ratingCreateRequestDTO = new RatingCreateRequestDTO();
        ratingCreateRequestDTO.setMoodysRating("bb");
        ratingCreateRequestDTO.setSandPRating("cc");
        ratingCreateRequestDTO.setFitchRating("aa");
        ratingCreateRequestDTO.setOrderNumber(25);

        Rating rating = new Rating();

        Rating ratingConverted = ratingCreateRequestDTO.convertToRating();
        //WHEN
        when(ratingService.save(rating)).thenReturn(rating);

        mockMvc.perform(post("/rating/validate")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("moodysRating", ratingCreateRequestDTO.getMoodysRating())
                        .param("sandPRating", ratingCreateRequestDTO.getSandPRating())
                        .param("fitchRating", ratingCreateRequestDTO.getFitchRating())
                        .param("orderNumber", String.valueOf(ratingCreateRequestDTO.getOrderNumber())))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));

        //THEN
        verify(ratingService, times(1)).save(ratingConverted);
    }

    @Test
    @WithMockUser
    @DisplayName("GET /rating/update/{id}")
    void showUpdateForm() throws Exception {
        //GIVEN
        int id = 1;
        Rating rating = new Rating();
        rating.setId(id);
        RatingUpdateRequestDTO dto = new RatingUpdateRequestDTO(rating);

        when(ratingService.getById(id)).thenReturn(rating);

        //WHEN
        mockMvc.perform(get("/rating/update/{id}", id))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"))
                .andExpect(model().attributeExists("ratingList"))
                .andExpect(model().attribute("ratingList", dto));
        //THEN
        verify(ratingService, times(1)).getById(id);
    }

    @Test
    @WithMockUser
    @DisplayName("POST /rating/update/{id}")
    void updateRating() throws Exception {

        //GIVEN
        int id = 1;
        RatingUpdateRequestDTO ratingUpdateRequestDTO = new RatingUpdateRequestDTO();
        ratingUpdateRequestDTO.setMoodysRating("bb");
        ratingUpdateRequestDTO.setSandPRating("cc");
        ratingUpdateRequestDTO.setFitchRating("aa");
        ratingUpdateRequestDTO.setOrderNumber(25);
        ratingUpdateRequestDTO.setId(1);


        Rating ratingConverted = ratingUpdateRequestDTO.convertToRating();

        //WHEN
        doNothing().when(ratingService).update(ratingConverted);
        mockMvc.perform(post("/rating/update/{id}", id)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("moodysRating", ratingUpdateRequestDTO.getMoodysRating())
                        .param("sandPRating", ratingUpdateRequestDTO.getSandPRating())
                        .param("fitchRating", ratingUpdateRequestDTO.getFitchRating())
                        .param("orderNumber", String.valueOf(ratingUpdateRequestDTO.getOrderNumber())))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));
        //THEN
        verify(ratingService, times(1)).update(ratingConverted);
    }

    @Test
    @WithMockUser
    @DisplayName("GET /rating/delete/{id}")
    void deleteRating() throws Exception {
        //GIVEN
        Integer id = 1;

        //WHEN
        doNothing().when(ratingService).delete(id);

        mockMvc.perform(get("/rating/delete/{id}", id)
                        .param("id", String.valueOf(id)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));

        //THEN
        verify(ratingService, times(1)).delete(id);
    }
}