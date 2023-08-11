package com.drunar.coincorner.http.controller;

import com.drunar.coincorner.database.filter.WalletTransactionFilter;
import com.drunar.coincorner.service.WalletTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class WalletTransactionController {

    private final WalletTransactionService walletTrService;

    @GetMapping
    public String findAll(Model model, WalletTransactionFilter filter) {
        model.addAttribute("transactions", walletTrService.findAll(filter));
        return "transaction/transactions";
    }

}
