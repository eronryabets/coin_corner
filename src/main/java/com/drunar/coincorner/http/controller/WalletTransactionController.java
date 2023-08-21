package com.drunar.coincorner.http.controller;

import com.drunar.coincorner.database.filter.WalletTransactionFilter;
import com.drunar.coincorner.dto.CustomUserDetails;
import com.drunar.coincorner.dto.PageResponse;
import com.drunar.coincorner.dto.WalletTransactionDTO;
import com.drunar.coincorner.service.WalletTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public String findAll(Model model, WalletTransactionFilter filter, Pageable pageable) {
        Page<WalletTransactionDTO> page = walletTrService.findAll(filter, pageable);
        model.addAttribute("transactions", PageResponse.of(page));
        model.addAttribute("filter", filter);

        return "transaction/transactions";
    }

    @GetMapping("/my")
    public String findAllByUser(Model model, WalletTransactionFilter filter, Pageable pageable,
                                @AuthenticationPrincipal CustomUserDetails user) {
        Page<WalletTransactionDTO> page = walletTrService.findAllByUser(filter, pageable, user.getId());
        model.addAttribute("transactions", PageResponse.of(page));
        model.addAttribute("filter", filter);

        return "transaction/myTransactions";
    }

}
