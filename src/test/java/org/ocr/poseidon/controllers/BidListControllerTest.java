package org.ocr.poseidon.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.ocr.poseidon.domain.BidList;
import org.ocr.poseidon.dto.BidListCreateRequestDTO;
import org.ocr.poseidon.dto.BidListUpdateRequestDTO;
import org.ocr.poseidon.security.SecurityConfig;
import org.ocr.poseidon.services.BidListServiceImpl;
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

@WebMvcTest(controllers = BidList.class)
@ContextConfiguration(classes = {SecurityConfig.class, BidListController.class})
class BidListControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BidListServiceImpl bidListService;

    @MockBean
    private AuthUtils authUtils;


    @Test
    @WithMockUser
    @DisplayName("Get /bidList/list")
    void home() throws Exception {
        //GIVEN
        List<BidList> bidListList = new ArrayList<>(List.of(new BidList()));

        //WHEN
        when(authUtils.isAdmin()).thenReturn(true);
        when(bidListService.getAll()).thenReturn(bidListList);

        mockMvc.perform(get("/bidList/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/list"))
                .andExpect(model().attributeExists("bidLists"))
                .andExpect(model().attribute("bidLists", bidListList))
                .andExpect(model().attributeExists("isAdmin"))
                .andExpect(model().attribute("isAdmin", true));

        //THEN
        verify(bidListService, times(1)).getAll();
        verify(authUtils, times(1)).isAdmin();
    }

    @Test
    @WithMockUser
    @DisplayName("Get /bidList/add")
    void addBidForm() throws Exception {
        //GIVEN

        //WHEN
        mockMvc.perform(get("/bidList/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"))
                .andExpect(model().attributeExists("bidListCreateRequestDto"))
                .andExpect(model().attribute("bidListCreateRequestDto", new BidListCreateRequestDTO()));
        //THEN
    }

    @Test
    @WithMockUser
    @DisplayName("POST /bidList/validate")
    void validate() throws Exception {
        //GIVEN
        BidListCreateRequestDTO bidListCreateRequestDTO = new BidListCreateRequestDTO();
        bidListCreateRequestDTO.setAccount("acoount");
        bidListCreateRequestDTO.setType("type");
        bidListCreateRequestDTO.setBidQuantity(10.20);

        BidList bidListConverted = bidListCreateRequestDTO.convertToBidList();

        //WHEN
        when(bidListService.save(bidListConverted)).thenReturn(bidListConverted);

        mockMvc.perform(post("/bidList/validate")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("account", bidListCreateRequestDTO.getAccount())
                        .param("type", bidListCreateRequestDTO.getType())
                        .param("bidQuantity", String.valueOf(bidListCreateRequestDTO.getBidQuantity())))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));

        //THEN
        verify(bidListService, times(1)).save(bidListConverted);
    }

//    @Test
//    @WithMockUser
//    @DisplayName("POST /bidList/validate - Errors Present")
//    void validateWithErrorsValidate() throws Exception {
//        //GIVEN
//        BidListCreateRequestDTO bidListCreateRequestDTO = new BidListCreateRequestDTO();
//
//        BidList bidListConverted = bidListCreateRequestDTO.convertToBidList();
//
//        //WHEN
//        BindingResult bindingResult = mock(BindingResult.class);
//        when(bindingResult.hasErrors()).thenReturn(true);
//
//
//        mockMvc.perform(post("/bidList/validate")
//                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
//                        .param("account","testAccount")
//                        .param("type", "testType")
//                        .param("bidQuantity", "10.20"))
//                .andExpect(status().is3xxRedirection());
//
//        //THEN
//        verify(bindingResult, times(1)).hasErrors();
//    }


    @Test
    @WithMockUser
    @DisplayName("GET /bidList/update/{id}")
    void showUpdateForm() throws Exception {

        //GIVEN
        int id = 1;
        BidList bidListTest = new BidList();
        bidListTest.setBidListId(id);

        when(bidListService.getById(id)).thenReturn(bidListTest);

        BidListUpdateRequestDTO dto = new BidListUpdateRequestDTO(bidListTest);


        //WHEN
        mockMvc.perform(get("/bidList/update/{id}", id))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/update"))
                .andExpect(model().attributeExists("bidList"))
                .andExpect(model().attribute("bidList", dto));
        //THEN
        verify(bidListService, times(1)).getById(id);
    }

    @Test
    @WithMockUser
    @DisplayName("POST /bidList/update/{id}")
    void updateBid() throws Exception {
        //GIVEN
        int id = 1;
        BidListUpdateRequestDTO bidListUpdateRequestDTO = new BidListUpdateRequestDTO();
        bidListUpdateRequestDTO.setAccount("account");
        bidListUpdateRequestDTO.setType("type");
        bidListUpdateRequestDTO.setBidQuantity(10.20);
        bidListUpdateRequestDTO.setId(id);

        BidList bidListConverted = bidListUpdateRequestDTO.convertToBidList();

        //WHEN
        doNothing().when(bidListService).update(bidListConverted);

        mockMvc.perform(post("/bidList/update/{id}", id)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id", String.valueOf(bidListConverted.getId()))
                        .param("account", bidListConverted.getAccount())
                        .param("type", bidListConverted.getType())
                        .param("bidQuantity", String.valueOf(bidListConverted.getBidQuantity())))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));

        //THEN
        verify(bidListService, times(1)).update(bidListConverted);
    }

    @Test
    @WithMockUser
    @DisplayName("GET /bidList/delete/{id}")
    void deleteBid() throws Exception {
        //GIVEN
        Integer id = 1;

        //WHEN
        doNothing().when(bidListService).delete(id);

        mockMvc.perform(get("/bidList/delete/{id}", id)
                        .param("id", String.valueOf(id)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));

        //THEN
        verify(bidListService, times(1)).delete(id);

    }
}