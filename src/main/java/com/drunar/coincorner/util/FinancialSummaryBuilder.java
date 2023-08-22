package com.drunar.coincorner.util;

import com.drunar.coincorner.database.filter.WalletTransactionFilter;
import com.drunar.coincorner.dto.FinancialSummaryDTO;
import com.drunar.coincorner.dto.WalletTransactionDTO;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class FinancialSummaryBuilder {

    public static FinancialSummaryDTO buildDTO(WalletTransactionFilter filter, Page<WalletTransactionDTO> page){
        return FinancialSummaryDTO.builder()
                .walletId(filter.getWalletId())
                .operationType(filter.getOperationType())
                .currency(filter.getCurrency())
                .dateStart(calculateDateStart(filter,page))
                .dateEnd(calculateDateEnd(filter,page))
                .transactionAmount(calculateTransactionAmount(page))
                .build();
    }

    private static BigDecimal calculateTransactionAmount(Page<WalletTransactionDTO> page) {
        BigDecimal transactionAmount = BigDecimal.ZERO;

        for (WalletTransactionDTO dto : page.getContent()) {
            if (dto.getAmount() != null) {
                transactionAmount = transactionAmount.add(dto.getAmount());
            }
        }

        return transactionAmount;
    }

    private static LocalDateTime calculateDateStart(WalletTransactionFilter filter,
                                                    Page<WalletTransactionDTO> page) {
        LocalDateTime filterDateStart = filter.getTransactionDateStart();
        List<WalletTransactionDTO> content = page.getContent();

        if (filterDateStart != null) {
            return filterDateStart;
        }

        LocalDateTime earliestTransactionDate = content.stream()
                .map(WalletTransactionDTO::getTransactionDate)
                .min(LocalDateTime::compareTo)
                .orElse(null);

        return earliestTransactionDate;
    }

    private static LocalDateTime calculateDateEnd(WalletTransactionFilter filter,
                                                  Page<WalletTransactionDTO> page) {
        LocalDateTime filterDateEnd = filter.getTransactionDateEnd();
        List<WalletTransactionDTO> content = page.getContent();

        if (filterDateEnd != null) {
            return filterDateEnd;
        }

        LocalDateTime latestTransactionDate = content.stream()
                .map(WalletTransactionDTO::getTransactionDate)
                .max(LocalDateTime::compareTo)
                .orElse(null);

        return latestTransactionDate;
    }

}
