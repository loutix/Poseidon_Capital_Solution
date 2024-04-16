package org.ocr.poseidon.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.ocr.poseidon.domain.BidList;
import org.ocr.poseidon.dto.BidListCreateRequestDTO;
import org.ocr.poseidon.dto.BidListUpdateRequestDTO;
import org.ocr.poseidon.services.BidListServiceImpl;
import org.ocr.poseidon.util.AuthUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class BidListController {

    private final BidListServiceImpl bidListService;

    private final AuthUtils authUtils;

    public BidListController(BidListServiceImpl bidListService, AuthUtils authUtils) {
        this.bidListService = bidListService;
        this.authUtils = authUtils;
    }

    @RequestMapping("/bidList/list")
    public String home(Model model) {
        log.info("GET:  /bidList/list");

       if (authUtils.isAdmin()){
           model.addAttribute("isAdmin", true);
       }

        model.addAttribute("bidLists", bidListService.getAll());
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(Model model) {
        log.info("GET:  /bidList/add");
        model.addAttribute("bidListCreateRequestDto", new BidListCreateRequestDTO());

        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid @ModelAttribute("bidListRequestDto") BidListCreateRequestDTO bidListCreateRequestDto, BindingResult result, Model model) {

        log.info("POST:  /bidList/validate");

        if (result.hasErrors()) {
            model.addAttribute("bidListCreateRequestDto", new BidListCreateRequestDTO());
            return "bidList/add";
        }

        BidList bidListConverted = bidListCreateRequestDto.convertToBidList();
        bidListService.save(bidListConverted);

        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

        log.info("GET:  /bidList/update/" + id);

        BidList bidList = bidListService.getById(id);

        BidListUpdateRequestDTO dto = new BidListUpdateRequestDTO(bidList);

        model.addAttribute("bidList", dto);

        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid @ModelAttribute("bidList") BidListUpdateRequestDTO bidListUpdateRequestDTO,
                            BindingResult result, Model model) {

        log.info("POST:  /bidList/update/{}", id);

        if (result.hasErrors()) {
            model.addAttribute("id", id);
            return "bidList/update";
        }

        BidList bidListConverted = bidListUpdateRequestDTO.convertToBidList();

        bidListService.update(bidListConverted);

        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id) {

        log.info("DELETE:  /bidList/delete" + id);

        bidListService.delete(id);
        return "redirect:/bidList/list";
    }
}
