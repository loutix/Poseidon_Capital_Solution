package org.ocr.poseidon.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.ocr.poseidon.domain.BidList;
import org.ocr.poseidon.domain.Rating;
import org.ocr.poseidon.dto.BidListUpdateRequestDTO;
import org.ocr.poseidon.dto.RatingUpdateRequestDTO;
import org.ocr.poseidon.dto.RatingCreateRequestDTO;
import org.ocr.poseidon.services.RatingServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
@Slf4j
@Controller
public class RatingController {


    private final RatingServiceImpl ratingService;

    public RatingController(RatingServiceImpl ratingService) {
        this.ratingService = ratingService;
    }


    @RequestMapping("/rating/list")
    public String home(Model model) {

        model.addAttribute("ratings", ratingService.getAll());
        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(Model model) {
        log.info("GET:  /rating/add");
        model.addAttribute("ratingCreateRequestDTO", new RatingCreateRequestDTO());

        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@Valid @ModelAttribute("ratingCreateRequestDTO") RatingCreateRequestDTO ratingCreateRequestDTO, BindingResult result, Model model) {
        log.info("POST:  /rating/validate");

        if (result.hasErrors()) {
            return "rating/add";
        }

        Rating ratingConverted = ratingCreateRequestDTO.convertToRating();
        ratingService.save(ratingConverted);

        return "redirect:/rating/list";
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

        log.info("GET:  /rating/update/" + id);

        Rating rating = ratingService.getById(id);

        RatingUpdateRequestDTO dto = new RatingUpdateRequestDTO(rating);

        model.addAttribute("ratingList", dto);


        return "rating/update";
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid @ModelAttribute("ratingList") RatingUpdateRequestDTO ratingUpdateRequestDTO,
                             BindingResult result, Model model) {

        log.info("POST:  /rating/update/" + id);

        if (result.hasErrors()) {
            model.addAttribute("id", id);
            return "rating/update";
        }

        Rating ratingConverted = ratingUpdateRequestDTO.convertToRating();

        ratingService.update(ratingConverted);


        return "redirect:/rating/list";
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        log.info("DELETE:  /rating/delete" + id);

        ratingService.delete(id);
        return "redirect:/rating/list";
    }
}
