package org.ocr.poseidon.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.ocr.poseidon.domain.RuleName;
import org.ocr.poseidon.dto.RuleCreateDTO;
import org.ocr.poseidon.dto.RuleUpdateDTO;
import org.ocr.poseidon.services.RuleService;
import org.ocr.poseidon.util.AuthUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class RuleNameController {
    private final RuleService ruleService;
    private final AuthUtils authUtils;

    public RuleNameController(RuleService ruleService, AuthUtils authUtils) {
        this.ruleService = ruleService;
        this.authUtils = authUtils;
    }


    @RequestMapping("/ruleName/list")
    public String home(Model model) {
        log.info("GET:  /ruleName/list");

        if (authUtils.isAdmin()) {
            model.addAttribute("isAdmin", true);
        }

        model.addAttribute("ruleNames", ruleService.getAll());
        return "ruleName/list";
    }

    @GetMapping("/ruleName/add")
    public String addRuleForm(Model model) {
        log.info("GET:  /ruleName/add");
        model.addAttribute("ruleCreateDTO", new RuleCreateDTO());

        return "ruleName/add";
    }

    @PostMapping("/ruleName/validate")
    public String validate(@Valid @ModelAttribute("ruleCreateDTO") RuleCreateDTO ruleCreateDTO, BindingResult result) {
        log.info("GET:  /ruleName/validate");
        if (result.hasErrors()) {
            return "ruleName/add";
        }

        RuleName ruleNameConverted = ruleCreateDTO.convertToRuleName();
        ruleService.save(ruleNameConverted);

        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

        RuleName ruleName = ruleService.getById(id);

        model.addAttribute("ruleUpdateDTO", new RuleUpdateDTO(ruleName));

        return "ruleName/update";
    }

    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid @ModelAttribute("ruleUpdateDTO") RuleUpdateDTO ruleUpdateDTO,
                                 BindingResult result) {

        log.info("POST:  /ruleName/update/{id}" + id);

        if (result.hasErrors()) {
            return "ruleName/update";
        }

        RuleName ruleNameConverted = ruleUpdateDTO.convertToRuleName();

        ruleService.update(ruleNameConverted);

        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id) {
        log.info("GET:  /ruleName/delete/{}", id);

        ruleService.delete(id);

        return "redirect:/ruleName/list";
    }
}
