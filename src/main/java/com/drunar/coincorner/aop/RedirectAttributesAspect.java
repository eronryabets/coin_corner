package com.drunar.coincorner.aop;

import com.drunar.coincorner.dto.WalletTransactionDTO;
import com.drunar.coincorner.util.TransactionThreadLocal;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Aspect
@Component
public class RedirectAttributesAspect {

    @Around(value = "@annotation(AddRedirectAttributes) && args(redirectAttributes, ..)")
    public Object processWithRedirectAttributes(ProceedingJoinPoint joinPoint,
                                                        RedirectAttributes redirectAttributes) throws Throwable {
        Object result = joinPoint.proceed();

        WalletTransactionDTO walletTransactionDTO = TransactionThreadLocal.getTransaction();
        redirectAttributes.addFlashAttribute("transactionSuccess", walletTransactionDTO);

        return result;
    }

}
