package org.ocr.poseidon.controllers;

import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.ocr.poseidon.domain.User;
import org.ocr.poseidon.dto.UserCreationDTO;
import org.ocr.poseidon.dto.UserUpdateDTO;
import org.ocr.poseidon.services.UserServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class UserController {

    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }


    @GetMapping("/login")
    public String login() {
        log.info("GET/login");
        return "registration/login";
    }

    @RequestMapping("/user/list")
    public String home(Model model) {
        log.info("GET:  /user/list/");
        model.addAttribute("users", userService.getAll());
        return "user/list";
    }

    @GetMapping("/user/add")
    public String addUser(Model model) {
        log.info("GET:  /user/add/");
        model.addAttribute("userCreationDto", new UserCreationDTO());

        return "user/add";
    }

    @PostMapping("/user/validate")
    public String validate(@Valid @ModelAttribute("userCreationDto") UserCreationDTO userCreationDTO, BindingResult result, Model model) {
        log.info("POST:  /user/validate/");

        if (result.hasErrors()) {
            return "user/add";
        }

        try {
            userService.saveUser(userCreationDTO);
        } catch (ValidationException e) {
            model.addAttribute("usernameNotUnique", true);
            return "user/add";
        }

        return "redirect:/user/list";
    }

    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        log.info("GET:  /user/update/" + id);

        User user = userService.getById(id);

        UserUpdateDTO userUpdateDTO = new UserUpdateDTO(user);

        model.addAttribute("userUpdateDTO", userUpdateDTO);
        return "user/update";
    }

    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid @ModelAttribute("userUpdateDTO") UserUpdateDTO userUpdateDTO,
                             BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "user/update";
        }

        User userConverted = userUpdateDTO.convertToUser();

        try {
            User user = userService.controlUser(userConverted);
            userService.update(user);
        } catch (ValidationException e) {
            model.addAttribute("usernameNotUnique", true);
            return "user/update";
        }

        return "redirect:/user/list";
    }

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id) {
        log.info("DELETE:  /user/delete" + id);

        userService.delete(id);

        return "redirect:/user/list";
    }
}
