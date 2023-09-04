package com.drunar.coincorner.http.controller;

import com.drunar.coincorner.service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final MainService mainService;

    @GetMapping("/")
    public String exchangeRate(Model model) {
        model.addAttribute("rate",mainService.getExchangeRatesList());
        return "main/exchangeRate";
    }

}
