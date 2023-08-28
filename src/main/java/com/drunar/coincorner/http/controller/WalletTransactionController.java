package com.drunar.coincorner.http.controller;

import com.drunar.coincorner.aop.LogTransaction;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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


    @GetMapping("/addingMoney/{walletId}")
    public String showAddMoneyForm(@PathVariable("walletId") Long walletId, Model model,
                                   @ModelAttribute WalletTransactionDTO transaction){
        WalletReadDTO wallet = walletService.findById(walletId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addAttribute("wallet", wallet);
        model.addAttribute("transaction", transaction);
        model.addAttribute("moneyForm", new MoneyFormDTO());

        return "transaction/addingMoney";

    }

    @LogTransaction
    @PostMapping("/addingMoney/{walletId}/balanceUpdate")
    public String processAddMoneyForm(@PathVariable("walletId") Long walletId,
                                      @ModelAttribute MoneyFormDTO moneyForm,
                                      @ModelAttribute WalletTransactionDTO transaction,
                                      RedirectAttributes redirectAttributes){
        if(walletService.updateBalance(walletId, moneyForm.getAmount())){
            return "redirect:/transactions/addingMoney/{walletId}";
        }
        return "redirect:/error";

    }


    @GetMapping("/moneyWithdrawal/{walletId}")
    public String showMoneyWithdrawalForm(@PathVariable("walletId") Long walletId, Model model,
                                          @ModelAttribute WalletTransactionDTO transaction){
        WalletReadDTO wallet = walletService.findById(walletId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addAttribute("wallet", wallet);
        model.addAttribute("transaction", transaction);
        model.addAttribute("moneyForm", new MoneyFormDTO());
        return "transaction/moneyWithdrawal";

    }

    @LogTransaction
    @PostMapping("/moneyWithdrawal/{walletId}/balanceUpdate")
    public String processMoneyWithdrawalForm(@PathVariable("walletId") Long walletId,
                                             @ModelAttribute MoneyFormDTO moneyForm,
                                             @ModelAttribute WalletTransactionDTO transaction,
                                             RedirectAttributes redirectAttributes){
        moneyForm.setAmount(moneyForm.getAmount().negate());
        transaction.setAmount(transaction.getAmount().negate());
        if(walletService.updateBalance(walletId, moneyForm.getAmount())){
            return "redirect:/transactions/moneyWithdrawal/{walletId}";
        }

        return "redirect:/error";

    }



    @GetMapping("/cashTransfer/{walletId}")
    public String showCashTransferForm(@PathVariable("walletId") Long walletId, Model model,
                                       @ModelAttribute WalletTransactionDTO transaction){
        WalletReadDTO wallet = walletService.findById(walletId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addAttribute("wallet", wallet);
        model.addAttribute("transaction", transaction);
        model.addAttribute("moneyForm", new MoneyFormDTO());
        return "transaction/cashTransfer";

    }

    @PostMapping("/cashTransfer/{walletId}/balanceUpdate")
    public String processCashTransferForm(@PathVariable("walletId") Long walletId, @ModelAttribute MoneyFormDTO moneyForm,
                                          @ModelAttribute WalletTransactionDTO transaction){
        WalletReadDTO wallet = walletService.findById(walletId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        walletService.updateBalance(walletId, moneyForm.getAmount());
        walletTrService.create(WalletTransactionEnricher.enrich(transaction, wallet));
        return "redirect:/wallets/my";

    }


}
