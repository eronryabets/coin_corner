package com.drunar.coincorner.http.controller;

import com.drunar.coincorner.database.filter.WalletFilter;
import com.drunar.coincorner.dto.CustomUserDetails;
import com.drunar.coincorner.dto.PageResponse;
import com.drunar.coincorner.dto.WalletCreateEditDTO;
import com.drunar.coincorner.dto.WalletReadDTO;
import com.drunar.coincorner.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/wallets")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @GetMapping
    private String findAll(Model model, WalletFilter filter, Pageable pageable) {
        Page<WalletReadDTO> page = walletService.findAll(filter, pageable);
        model.addAttribute("wallets", PageResponse.of(page));
        model.addAttribute("filter", filter);
        return "wallet/wallets";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model) {
        return walletService.findById(id)
                .map(wallet -> {
                    model.addAttribute("wallet", wallet);
                    return "wallet/wallet";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Long id,
                         @ModelAttribute WalletCreateEditDTO wallet) {
        return walletService.update(id, wallet)
                .map(it -> "redirect:/wallets/{id}")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/my")
    public String findAllByUser(Model model, @AuthenticationPrincipal CustomUserDetails user) {
        return walletService.findAllByUserDetails(user)
                .map(wallets ->  {
                    model.addAttribute("wallets", wallets);
                    return "wallet/myWallets";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/create")
    public String newWallet(Model model, WalletCreateEditDTO wallet){
        model.addAttribute("wallet", wallet);
        return "wallet/createWallet";
    }

    @PostMapping("/create")
    public String create(Model model, WalletCreateEditDTO wallet){
        model.addAttribute("wallet", wallet);
        System.out.println();
        walletService.create(wallet);
        return "wallet/createWallet";
    }

}
