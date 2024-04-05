package org.ocr.poseidon.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.ocr.poseidon.domain.CurvePoint;
import org.ocr.poseidon.dto.CurverCreateRequestDTO;
import org.ocr.poseidon.dto.CurverUpdateRequestDTO;
import org.ocr.poseidon.services.CurvePointServiceImpl;
import org.ocr.poseidon.util.AuthUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Slf4j
@Controller
public class CurveController {
    private final CurvePointServiceImpl curvePointService;
    private final AuthUtils authUtils;

    public CurveController(CurvePointServiceImpl curvePointService, AuthUtils authUtils) {
        this.curvePointService = curvePointService;
        this.authUtils = authUtils;
    }

    @RequestMapping("/curvePoint/list")
    public String home(Model model) {
        log.info("GET:  /curvePoint/list");

        if (authUtils.isAdmin()) {
            model.addAttribute("isAdmin", true);
        }


        model.addAttribute("curvePoints", curvePointService.getAll());
        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addBidForm(Model model) {
        log.info("GET:  /curvePoint/add");
        model.addAttribute("curvePoint", new CurverCreateRequestDTO());
        return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(@Valid @ModelAttribute("curvePoint") CurverCreateRequestDTO curverCreateRequestDTO, BindingResult result, Model model) {

        log.info("POST:  /curvePoint/validate");

        if (result.hasErrors()) {
            return "curvePoint/add";
        }
        CurvePoint curvePointConverted = curverCreateRequestDTO.convertToCurvePoint();

        curvePointService.save(curvePointConverted);

        return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

        log.info("GET:  /curvePoint/update/" + id);

        CurvePoint curvePoint = curvePointService.getById(id);

        CurverUpdateRequestDTO dto = new CurverUpdateRequestDTO(curvePoint);

        model.addAttribute("curvePoint", dto);

        return "curvePoint/update";
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid @ModelAttribute("curvePoint") CurverUpdateRequestDTO curverUpdateRequestDTO,
                            BindingResult result, Model model) {

        log.info("POST:  /curvePoint/update/" + id);

        if (result.hasErrors()) {
            model.addAttribute("id", id);
            return "curvePoint/update";
        }

        CurvePoint curvePointConverted = curverUpdateRequestDTO.convertToCurvePoint();

        curvePointService.update(curvePointConverted);

        return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id) {
        log.info("DELETE:  /curvePoint/delete" + id);

        curvePointService.delete(id);

        return "redirect:/curvePoint/list";
    }
}
