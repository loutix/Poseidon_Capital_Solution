package org.ocr.poseidon.controllers;

import lombok.extern.slf4j.Slf4j;
import org.ocr.poseidon.util.AuthUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class HomeController {

    private final AuthUtils authUtils;

    public HomeController(AuthUtils authUtils) {
        this.authUtils = authUtils;
    }

    @RequestMapping("/home")
    public String home(Model model) {
        log.info("Get  route: /");

        model.addAttribute("isUserAnonymous", AuthUtils.isUserAnonymous());

        if (authUtils.isAdmin()) {
            model.addAttribute("isAdmin", true);
        }
        return "home";
    }

    @RequestMapping("/admin/home")
    public String adminHome() {
        return "redirect:/bidList/list";
    }


}
