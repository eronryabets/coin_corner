package com.drunar.coincorner.http.controller;

import com.drunar.coincorner.database.filter.WalletFilter;
import com.drunar.coincorner.dto.CustomUserDetails;
import com.drunar.coincorner.dto.PageResponse;
import com.drunar.coincorner.dto.WalletCreateEditDTO;
import com.drunar.coincorner.dto.WalletReadDTO;
import com.drunar.coincorner.service.WalletService;
import com.drunar.coincorner.util.InterestRate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
                         @ModelAttribute @Validated WalletCreateEditDTO wallet) {
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
    public String newWallet(Model model,@ModelAttribute("wallet") WalletCreateEditDTO wallet){
        model.addAttribute("wallet", wallet);
        return "wallet/createWallet";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute @Validated WalletCreateEditDTO wallet,
                         BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("wallet",wallet);
            redirectAttributes.addFlashAttribute("errors",bindingResult.getAllErrors());
            return "redirect:/wallets/create";
        }

        walletService.create(wallet);
        return "redirect:/wallets/my";
    }


    @GetMapping("/incomeInterestRate/{id}")
    public String incomeInterestRate(@PathVariable Long id, Model model,
                                     @RequestParam(required = false) String dateStart,
                                     @RequestParam(required = false) String dateEnd){
        return walletService.findById(id)
                .map(wallet -> {
                    model.addAttribute("wallet", wallet);
                    model.addAttribute("dateStart", dateStart);
                    model.addAttribute("dateEnd", dateEnd);
                    model.addAttribute("calculated",
                            InterestRate.calculate(wallet, dateStart, dateEnd));
                    return "wallet/incomeInterestRate";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    }

    @GetMapping("/creditDebt/{id}")
    public String creditDebt(@PathVariable Long id, Model model,
                             @RequestParam(required = false) String dateStart,
                             @RequestParam(required = false) String dateEnd){
        return walletService.findById(id)
                .map(wallet -> {
                    model.addAttribute("wallet", wallet);
                    model.addAttribute("dateStart", dateStart);
                    model.addAttribute("dateEnd", dateEnd);
                    model.addAttribute("calculated",
                            InterestRate.calculate(wallet, dateStart, dateEnd));
                    return "wallet/creditDebt";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));


    }



}
