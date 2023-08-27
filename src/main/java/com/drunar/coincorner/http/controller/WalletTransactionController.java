package com.drunar.coincorner.http.controller;

import com.drunar.coincorner.database.filter.WalletTransactionFilter;
import com.drunar.coincorner.dto.*;
import com.drunar.coincorner.service.WalletService;
import com.drunar.coincorner.service.WalletTransactionService;
import com.drunar.coincorner.util.FinancialSummaryBuilder;
import com.drunar.coincorner.util.WalletTransactionEnricher;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class WalletTransactionController {

    private final WalletTransactionService walletTrService;
    private final WalletService walletService;

    @GetMapping
    public String findAll(Model model, WalletTransactionFilter filter, Pageable pageable) {
        Page<WalletTransactionDTO> page = walletTrService.findAll(filter, pageable);
        model.addAttribute("transactions", PageResponse.of(page));
        model.addAttribute("filter", filter);

        return "transaction/transactions";
    }

    @GetMapping("/finances")
    public String finances(Model model, WalletTransactionFilter filter, Pageable pageable){
        Page<WalletTransactionDTO> page = walletTrService.findAll(filter, pageable);
        FinancialSummaryDTO finance = FinancialSummaryBuilder.buildDTO(filter, page);

        model.addAttribute("transactions", PageResponse.of(page));
        model.addAttribute("filter", filter);
        model.addAttribute("finance", finance);

        return "transaction/finances";
    }

    @GetMapping("/walletTransaction")
    public String findAllByWallet(Model model, WalletTransactionFilter filter, Pageable pageable){
        Page<WalletTransactionDTO> page = walletTrService.findAll(filter,pageable);

        model.addAttribute("transactions", PageResponse.of(page));
        model.addAttribute("filter", filter);

        return "transaction/walletTransaction";

    }

    @GetMapping("/my")
    public String findAllByUser(Model model, WalletTransactionFilter filter, Pageable pageable) {
        Page<WalletTransactionDTO> page = walletTrService.findAll(filter, pageable);
        model.addAttribute("transactions", PageResponse.of(page));
        model.addAttribute("filter", filter);

        return "transaction/myTransactions";
    }


    @GetMapping("/incomeAndExpenses")
    public String incomeAndExpenses(Model model, WalletTransactionFilter filter, Pageable pageable){
        Page<WalletTransactionDTO> page = walletTrService.findAll(filter, pageable);
        FinancialSummaryDTO finance = FinancialSummaryBuilder.buildDTO(filter, page);

        model.addAttribute("transactions", PageResponse.of(page));
        model.addAttribute("filter", filter);
        model.addAttribute("finance", finance);

        return "transaction/incomeAndExpenses";

    }

    @GetMapping("/addingMoney/{id}")
    public String showAddMoneyForm(@PathVariable("id") Long walletId, Model model,
                                   @ModelAttribute WalletTransactionDTO transaction){
        WalletReadDTO wallet = walletService.findById(walletId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addAttribute("wallet", wallet);
        model.addAttribute("transaction", transaction);
        model.addAttribute("moneyForm", new MoneyForm());
        return "transaction/addingMoney";

    }

    @PostMapping("/addingMoney/{id}/balanceUpdate")
    public String processAddMoneyForm(@PathVariable("id") Long walletId, @ModelAttribute MoneyForm moneyForm,
                                      @ModelAttribute WalletTransactionDTO transaction){
        WalletReadDTO wallet = walletService.findById(walletId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        walletService.addingBalance(walletId, moneyForm.getAmount());
        walletTrService.create(WalletTransactionEnricher.enrich(transaction, wallet));

        return "redirect:/wallets/my";

    }

    @GetMapping("/moneyWithdrawal/{id}")
    public String showMoneyWithdrawalForm(@PathVariable("id") Long walletId, Model model,
                                          @ModelAttribute WalletTransactionDTO transaction){
        WalletReadDTO wallet = walletService.findById(walletId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addAttribute("wallet", wallet);
        model.addAttribute("transaction", transaction);
        model.addAttribute("moneyForm", new MoneyForm());
        return "transaction/moneyWithdrawal";

    }

    @PostMapping("/moneyWithdrawal/{id}/balanceUpdate")
    public String processMoneyWithdrawalForm(@PathVariable("id") Long walletId, @ModelAttribute MoneyForm moneyForm,
                                             @ModelAttribute WalletTransactionDTO transaction){
        WalletReadDTO wallet = walletService.findById(walletId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        walletService.withdrawalBalance(walletId, moneyForm.getAmount());
        walletTrService.create(WalletTransactionEnricher.enrich(transaction, wallet));
        return "redirect:/wallets/my";

    }

    @GetMapping("/cashTransfer")
    public String cashTransfer(){
        return "transaction/cashTransfer";

    }


}
