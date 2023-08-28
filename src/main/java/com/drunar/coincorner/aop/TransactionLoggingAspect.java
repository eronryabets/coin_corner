package com.drunar.coincorner.aop;

import com.drunar.coincorner.dto.MoneyFormDTO;
import com.drunar.coincorner.dto.WalletReadDTO;
import com.drunar.coincorner.dto.WalletTransactionDTO;
import com.drunar.coincorner.service.WalletService;
import com.drunar.coincorner.service.WalletTransactionService;
import com.drunar.coincorner.util.WalletTransactionEnricher;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Aspect
@Component
@RequiredArgsConstructor
public class TransactionLoggingAspect {

    private final WalletTransactionService walletTrService;
    private final WalletService walletService;


    @Around("@annotation(LogTransaction) && args(walletId, moneyForm, transaction,redirectAttributes, ..)")
    public Object logTransaction(ProceedingJoinPoint joinPoint, Long walletId,
                                 MoneyFormDTO moneyForm, WalletTransactionDTO transaction,
                                 RedirectAttributes redirectAttributes) throws Throwable {
        Object result = joinPoint.proceed();

        WalletReadDTO wallet = walletService.findById(walletId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        WalletTransactionDTO walletTransactionDTO =
                walletTrService.create(WalletTransactionEnricher.enrich(transaction, wallet));

        redirectAttributes.addFlashAttribute("transactionSuccess", walletTransactionDTO);

        return result;
    }
}

