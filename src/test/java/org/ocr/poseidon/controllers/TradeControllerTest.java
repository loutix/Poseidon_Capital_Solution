package org.ocr.poseidon.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.ocr.poseidon.domain.Trade;
import org.ocr.poseidon.dto.TradeCreationDTO;
import org.ocr.poseidon.dto.TradeUpdateDTO;
import org.ocr.poseidon.security.OAuth2UserService;
import org.ocr.poseidon.security.SecurityConfig;
import org.ocr.poseidon.services.TradeServiceImpl;
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

@WebMvcTest(controllers = Trade.class)
@ContextConfiguration(classes = {SecurityConfig.class, TradeController.class})
class TradeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OAuth2UserService oAuth2UserService;

    @MockBean
    private TradeServiceImpl tradeService;

    @MockBean
    private AuthUtils authUtils;

    @Test
    @WithMockUser
    @DisplayName("Get /trade/list isAdmin")
    void home() throws Exception {
        //GIVEN
        List<Trade> tradeList = new ArrayList<>(List.of(new Trade()));

        //WHEN
        when(authUtils.isAdmin()).thenReturn(true);
        when(tradeService.getAll()).thenReturn(tradeList);


        mockMvc.perform(get("/trade/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/list"))
                .andExpect(model().attributeExists("trades"))
                .andExpect(model().attribute("trades", tradeList))
                .andExpect(model().attributeExists("isAdmin"))
                .andExpect(model().attribute("isAdmin", true));

        //THEN
        verify(tradeService, times(1)).getAll();
        verify(authUtils, times(1)).isAdmin();

    }

    @Test
    @WithMockUser
    @DisplayName("Get /trade/add")
    void addUser() throws Exception {
        //GIVEN

        //WHEN
        mockMvc.perform(get("/trade/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"))
                .andExpect(model().attributeExists("tradeCreationDTO"))
                .andExpect(model().attribute("tradeCreationDTO", new TradeCreationDTO()));
        //THEN
    }

    @Test
    @WithMockUser
    @DisplayName("POST /trade/validate")
    void validate() throws Exception {
        //GIVEN
        TradeCreationDTO tradeCreationDTO = new TradeCreationDTO();
        tradeCreationDTO.setAccount("account1");
        tradeCreationDTO.setType("type1");
        tradeCreationDTO.setBuyQuantity(0.10);

        //WHEN
        mockMvc.perform(post("/trade/validate")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("account", tradeCreationDTO.getAccount())
                        .param("type", tradeCreationDTO.getType())
                        .param("buyQuantity", String.valueOf(tradeCreationDTO.getBuyQuantity())))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));

        //THEN
    }

    @Test
    @WithMockUser
    @DisplayName("GET /trade/update/{id}")
    void showUpdateForm() throws Exception {

        //GIVEN
        int id = 1;

        Trade tradeToUpdate = new Trade();
        tradeToUpdate.setTradeId(id);

        TradeUpdateDTO dto = new TradeUpdateDTO();
        dto.setId(tradeToUpdate.getTradeId());

        //WHEN
        when(tradeService.getById(id)).thenReturn(tradeToUpdate);

        mockMvc.perform(get("/trade/update/{id}", id))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/update"))
                .andExpect(model().attributeExists("tradeUpdateDTO"))
                .andExpect(model().attribute("tradeUpdateDTO", dto));
        //THEN
        verify(tradeService, times(1)).getById(id);

    }

    @Test
    @WithMockUser
    @DisplayName("POST /trade/update/{id}")
    void updateTrade() throws Exception {
        //GIVEN
        int id = 1;
        Trade trade = new Trade();
        trade.setAccount("account1");
        trade.setType("type1");
        trade.setBuyQuantity(0.10);
        trade.setTradeId(id);

        //WHEN
        doNothing().when(tradeService).update(trade);


        mockMvc.perform(post("/trade/update/{id}", id)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id", String.valueOf(trade.getTradeId()))
                        .param("account", trade.getAccount())
                        .param("type", trade.getType())
                        .param("buyQuantity", String.valueOf(trade.getBuyQuantity())))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));

        //THEN
        verify(tradeService, times(1)).update(trade);
    }

    @Test
    @WithMockUser
    @DisplayName("GET /trade/delete/{id}")
    void deleteTrade() throws Exception {
        //GIVEN
        Integer id = 1;
        //WHEN
        doNothing().when(tradeService).delete(id);

        mockMvc.perform(get("/trade/delete/{id}", id)
                        .param("id", String.valueOf(id)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));

        //THEN
        verify(tradeService, times(1)).delete(id);
    }
}