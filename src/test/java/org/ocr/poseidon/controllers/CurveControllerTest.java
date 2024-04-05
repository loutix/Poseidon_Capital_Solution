package org.ocr.poseidon.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.ocr.poseidon.domain.CurvePoint;
import org.ocr.poseidon.dto.CurverCreateRequestDTO;
import org.ocr.poseidon.dto.CurverUpdateRequestDTO;
import org.ocr.poseidon.security.OAuth2UserService;
import org.ocr.poseidon.security.SecurityConfig;
import org.ocr.poseidon.services.CurvePointServiceImpl;
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


@WebMvcTest(controllers = CurvePoint.class)
@ContextConfiguration(classes = {SecurityConfig.class, CurveController.class})
class CurveControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OAuth2UserService oAuth2UserService;

    @MockBean
    private CurvePointServiceImpl curvePointService;

    @MockBean
    private AuthUtils authUtils;

    @Test
    @WithMockUser
    @DisplayName("Get /curvePoint/list isAdmin")
    void home_Admin() throws Exception {
        //GIVEN
        List<CurvePoint> curvePointList = new ArrayList<>(List.of(new CurvePoint()));

        //WHEN
        when(authUtils.isAdmin()).thenReturn(true);
        when(curvePointService.getAll()).thenReturn(curvePointList);


        mockMvc.perform(get("/curvePoint/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/list"))
                .andExpect(model().attributeExists("curvePoints"))
                .andExpect(model().attribute("curvePoints", curvePointList))
                .andExpect(model().attributeExists("isAdmin"))
                .andExpect(model().attribute("isAdmin", true));

        //THEN
        verify(curvePointService, times(1)).getAll();
        verify(authUtils, times(1)).isAdmin();
    }


    @Test
    @WithMockUser
    @DisplayName("Get /curvePoint/list User")
    void home_User() throws Exception {
        //GIVEN
        List<CurvePoint> curvePointList = new ArrayList<>(List.of(new CurvePoint()));

        //WHEN
        when(authUtils.isAdmin()).thenReturn(false);
        when(curvePointService.getAll()).thenReturn(curvePointList);


        mockMvc.perform(get("/curvePoint/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/list"))
                .andExpect(model().attributeExists("curvePoints"))
                .andExpect(model().attribute("curvePoints", curvePointList));

        //THEN
        verify(curvePointService, times(1)).getAll();
        verify(authUtils, times(1)).isAdmin();
    }

    @Test
    @WithMockUser
    @DisplayName("Get /curvePoint/add")
    void addBidForm() throws Exception {
        //GIVEN

        //WHEN
        mockMvc.perform(get("/curvePoint/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"))
                .andExpect(model().attributeExists("curvePoint"))
                .andExpect(model().attribute("curvePoint", new CurverCreateRequestDTO()));
        //THEN
    }

    @Test
    @WithMockUser
    @DisplayName("POST /curvePoint/validate")
    void validate() throws Exception {
        //GIVEN
        CurverCreateRequestDTO curverCreateRequestDTO = new CurverCreateRequestDTO();
        curverCreateRequestDTO.setCurveId(2);
        curverCreateRequestDTO.setTerm(0.02);
        curverCreateRequestDTO.setValue(0.05);

        //WHEN
        mockMvc.perform(post("/curvePoint/validate")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("curveId", String.valueOf(curverCreateRequestDTO.getCurveId()))
                        .param("term", String.valueOf(curverCreateRequestDTO.getTerm()))
                        .param("value", String.valueOf(curverCreateRequestDTO.getValue())))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));

        //THEN
    }

    @Test
    @WithMockUser
    @DisplayName("POST /curvePoint/validate - Errors Present")
    void validateWithErrorsValidate() throws Exception {
        //GIVEN
        CurverCreateRequestDTO curverCreateRequestDTO = new CurverCreateRequestDTO();
        curverCreateRequestDTO.setCurveId(null);
        curverCreateRequestDTO.setTerm(null);
        curverCreateRequestDTO.setValue(null);

        //WHEN
        mockMvc.perform(post("/curvePoint/validate")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("curveId", String.valueOf(curverCreateRequestDTO.getCurveId()))
                        .param("term", String.valueOf(curverCreateRequestDTO.getTerm()))
                        .param("value", String.valueOf(curverCreateRequestDTO.getValue())))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"));

        //THEN
        verify(curvePointService, times(0)).save(new CurvePoint());
    }

    @Test
    @WithMockUser
    @DisplayName("GET /curvePoint/update/{id}")
    void showUpdateForm() throws Exception {
        //GIVEN
        int id = 1;

        //WHEN
        when(curvePointService.getById(id)).thenReturn(new CurvePoint());

        mockMvc.perform(get("/curvePoint/update/{id}", id))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/update"))
                .andExpect(model().attributeExists("curvePoint"))
                .andExpect(model().attribute("curvePoint", new CurverUpdateRequestDTO()));
        //THEN
        verify(curvePointService, times(1)).getById(id);
    }

    @Test
    @WithMockUser
    @DisplayName("POST /curvePoint/update/{id}")
    void updateBid() throws Exception {
        //GIVEN
        int id = 1;
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setCurveId(1);
        curvePoint.setTerm(10.20);
        curvePoint.setValue(10.20);
        curvePoint.setId(id);
        //WHEN
        doNothing().when(curvePointService).update(curvePoint);


        mockMvc.perform(post("/curvePoint/update/{id}", id)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("curveId", "1")
                        .param("term", "10.20")
                        .param("value", "10.20"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));

        //THEN
        verify(curvePointService, times(1)).update(curvePoint);
    }

    @Test
    @WithMockUser
    @DisplayName("POST /curvePoint/update/{id} - error")
    void updateBidError() throws Exception {
        //GIVEN
        int id = 1;
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setCurveId(null);
        curvePoint.setTerm(null);
        curvePoint.setValue(null);
        curvePoint.setId(id);

        //WHEN
        mockMvc.perform(post("/curvePoint/update/{id}", id)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("curveId", String.valueOf(curvePoint.getCurveId()))
                        .param("term", String.valueOf(curvePoint.getTerm()))
                        .param("value", String.valueOf(curvePoint.getValue())))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/update"));

        //THEN
        verify(curvePointService, times(0)).update(curvePoint);
    }

    @Test
    @WithMockUser
    @DisplayName("GET /curvePoint/delete/{id}")
    void deleteBid() throws Exception {
        //GIVEN
        Integer id = 1;
        //WHEN
        doNothing().when(curvePointService).delete(id);

        mockMvc.perform(get("/curvePoint/delete/{id}", id)
                        .param("id", String.valueOf(id)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));

        //THEN
        verify(curvePointService, times(1)).delete(id);
    }
}