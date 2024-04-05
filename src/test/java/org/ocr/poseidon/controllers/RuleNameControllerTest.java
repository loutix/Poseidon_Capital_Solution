package org.ocr.poseidon.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.ocr.poseidon.domain.RuleName;
import org.ocr.poseidon.dto.RuleCreateDTO;
import org.ocr.poseidon.dto.RuleUpdateDTO;
import org.ocr.poseidon.security.OAuth2UserService;
import org.ocr.poseidon.security.SecurityConfig;
import org.ocr.poseidon.services.RuleService;
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


@WebMvcTest(controllers = RuleName.class)
@ContextConfiguration(classes = {SecurityConfig.class, RuleNameController.class})
class RuleNameControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OAuth2UserService oAuth2UserService;

    @MockBean
    private RuleService ruleService;

    @MockBean
    private AuthUtils authUtils;


    @Test
    @WithMockUser
    @DisplayName("Get /curvePoint/list isAdmin")
    void home() throws Exception {
        //GIVEN
        List<RuleName> ruleNameList = new ArrayList<>(List.of(new RuleName()));

        //WHEN
        when(authUtils.isAdmin()).thenReturn(true);
        when(ruleService.getAll()).thenReturn(ruleNameList);


        mockMvc.perform(get("/ruleName/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/list"))
                .andExpect(model().attributeExists("ruleNames"))
                .andExpect(model().attribute("ruleNames", ruleNameList))
                .andExpect(model().attributeExists("isAdmin"))
                .andExpect(model().attribute("isAdmin", true));

        //THEN
        verify(ruleService, times(1)).getAll();
        verify(authUtils, times(1)).isAdmin();
    }

    @Test
    @WithMockUser
    @DisplayName("Get /curvePoint/list isUSER")
    void homeUser() throws Exception {
        //GIVEN
        List<RuleName> ruleNameList = new ArrayList<>(List.of(new RuleName()));

        //WHEN
        when(authUtils.isAdmin()).thenReturn(false);
        when(ruleService.getAll()).thenReturn(ruleNameList);


        mockMvc.perform(get("/ruleName/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/list"))
                .andExpect(model().attributeExists("ruleNames"))
                .andExpect(model().attribute("ruleNames", ruleNameList));

        //THEN
        verify(ruleService, times(1)).getAll();
        verify(authUtils, times(1)).isAdmin();
    }

    @Test
    @WithMockUser
    @DisplayName("Get /ruleName/add")
    void addRuleForm() throws Exception {
        //GIVEN

        //WHEN
        mockMvc.perform(get("/ruleName/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"))
                .andExpect(model().attributeExists("ruleCreateDTO"))
                .andExpect(model().attribute("ruleCreateDTO", new RuleCreateDTO()));
        //THEN
    }

    @Test
    @WithMockUser
    @DisplayName("POST /ruleName/validate")
    void validate() throws Exception {
        //GIVEN
        RuleCreateDTO ruleCreateDTO = new RuleCreateDTO();
        ruleCreateDTO.setName("aa");
        ruleCreateDTO.setDescription("desc");
        ruleCreateDTO.setJson("json");
        ruleCreateDTO.setTemplate("template");
        ruleCreateDTO.setSqlStr("sql str");
        ruleCreateDTO.setSqlPart("sql part");

        //WHEN
        mockMvc.perform(post("/ruleName/validate")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", ruleCreateDTO.getName())
                        .param("description", ruleCreateDTO.getDescription())
                        .param("json", ruleCreateDTO.getJson())
                        .param("template", ruleCreateDTO.getTemplate())
                        .param("sqlStr", ruleCreateDTO.getSqlStr())
                        .param("sqlPart", ruleCreateDTO.getSqlPart()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));

        //THEN
    }

    @Test
    @WithMockUser
    @DisplayName("GET /ruleName/update/{id}")
    void showUpdateForm() throws Exception {
        //GIVEN
        int id = 1;

        RuleName ruleName = new RuleName();
        ruleName.setId(id);

        RuleUpdateDTO dto = new RuleUpdateDTO();
        dto.setId(ruleName.getId());

        //WHEN
        when(ruleService.getById(id)).thenReturn(ruleName);

        mockMvc.perform(get("/ruleName/update/{id}", id))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/update"))
                .andExpect(model().attributeExists("ruleUpdateDTO"))
                .andExpect(model().attribute("ruleUpdateDTO", dto));
        //THEN
        verify(ruleService, times(1)).getById(id);
    }

    @Test
    @WithMockUser
    @DisplayName("POST /ruleName/update/{id}")
    void updateRuleName() throws Exception {
        //GIVEN
        int id = 1;
        RuleName ruleName = new RuleName();
        ruleName.setName("aa");
        ruleName.setDescription("desc");
        ruleName.setJson("json");
        ruleName.setTemplate("template");
        ruleName.setSqlStr("sql str");
        ruleName.setSqlPart("sql part");
        ruleName.setId(id);

        //WHEN
        doNothing().when(ruleService).update(ruleName);


        mockMvc.perform(post("/ruleName/update/{id}", id)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", ruleName.getName())
                        .param("description", ruleName.getDescription())
                        .param("json", ruleName.getJson())
                        .param("template", ruleName.getTemplate())
                        .param("sqlStr", ruleName.getSqlStr())
                        .param("sqlPart", ruleName.getSqlPart()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));

        //THEN
        verify(ruleService, times(1)).update(ruleName);
    }

    @Test
    @WithMockUser
    @DisplayName("GET /ruleName/delete/{id}")
    void deleteRuleName() throws Exception {
        //GIVEN
        Integer id = 1;
        //WHEN
        doNothing().when(ruleService).delete(id);

        mockMvc.perform(get("/ruleName/delete/{id}", id)
                        .param("id", String.valueOf(id)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));

        //THEN
        verify(ruleService, times(1)).delete(id);
    }
}