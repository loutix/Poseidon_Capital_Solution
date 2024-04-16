package org.ocr.poseidon.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.ocr.poseidon.domain.Trade;
import org.ocr.poseidon.dto.TradeCreationDTO;
import org.ocr.poseidon.dto.TradeUpdateDTO;
import org.ocr.poseidon.services.TradeServiceImpl;
import org.ocr.poseidon.util.AuthUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class TradeController {
    private final TradeServiceImpl tradeService;
    private final AuthUtils authUtils;

    public TradeController(TradeServiceImpl tradeService, AuthUtils authUtils) {
        this.tradeService = tradeService;
        this.authUtils = authUtils;
    }

    @RequestMapping("/trade/list")
    public String home(Model model) {
        log.info("GET:  /trade/list");

        if (authUtils.isAdmin()) {
            model.addAttribute("isAdmin", true);
        }

        model.addAttribute("trades", tradeService.getAll());
        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addUser(Model model) {
        log.info("GET:  /trade/add");

        model.addAttribute("tradeCreationDTO", new TradeCreationDTO());
        return "trade/add";
    }

    @PostMapping("/trade/validate")
    public String validate(@Valid @ModelAttribute("tradeCreationDTO") TradeCreationDTO tradeCreationDTO, BindingResult result) {
        log.info("POST:  /trade/validate");

        if (result.hasErrors()) {
            return "trade/add";
        }

        Trade tradeConverted = tradeCreationDTO.convertToTrade();
        tradeService.save(tradeConverted);

        return "redirect:/trade/list";
    }

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

        log.info("GET:  /trade/update/{}", id);

        Trade trade = tradeService.getById(id);

        TradeUpdateDTO tradeUpdateDTO = new TradeUpdateDTO(trade);

        model.addAttribute("tradeUpdateDTO", tradeUpdateDTO);

        return "trade/update";
    }

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid @ModelAttribute("tradeUpdateDTO") TradeUpdateDTO tradeUpdateDTO,
                              BindingResult result) {

        log.info("POST:  /trade/update/" + id);

        if (result.hasErrors()) {
            return "trade/update";
        }

        Trade tradeConverted = tradeUpdateDTO.convertToTrade();

        tradeService.update(tradeConverted);

        return "redirect:/trade/list";
    }

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id) {
        log.info("DELETE:  /trade/delete" + id);
        tradeService.delete(id);
        return "redirect:/trade/list";
    }
}
