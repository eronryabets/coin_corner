package com.drunar.coincorner.util;

import com.drunar.coincorner.dto.WalletTransactionDTO;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public class TransactionThreadLocal {
    private static final ThreadLocal<WalletTransactionDTO> threadLocal = new ThreadLocal<>();
    private static final ThreadLocal<RedirectAttributes> redirectAttributesThreadLocal = new ThreadLocal<>();

    public static WalletTransactionDTO getTransaction() {
        return threadLocal.get();
    }

    public static void setTransaction(WalletTransactionDTO transactionDTO) {
        threadLocal.set(transactionDTO);
    }

    public static void removeTransaction() {
        threadLocal.remove();
    }

    public static RedirectAttributes getRedirectAttributes() {
        return redirectAttributesThreadLocal.get();
    }

    public static void setRedirectAttributes(RedirectAttributes redirectAttributes) {
        redirectAttributesThreadLocal.set(redirectAttributes);
    }

    public static void removeRedirectAttributes() {
        redirectAttributesThreadLocal.remove();
    }
}