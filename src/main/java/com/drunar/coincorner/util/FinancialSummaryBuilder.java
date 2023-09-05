package com.drunar.coincorner.util;

import com.drunar.coincorner.database.entity.WalletTransaction;
import com.drunar.coincorner.database.filter.WalletTransactionFilter;
import com.drunar.coincorner.database.repository.WalletTransactionRepository;
import com.drunar.coincorner.dto.FinancialSummaryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Component
public class FinancialSummaryBuilder {

    private final WalletTransactionRepository walletTransactionRepository;

    public FinancialSummaryDTO buildDTO(WalletTransactionFilter filter) {
        List<WalletTransaction> transactions = walletTransactionRepository.findAllByFilter(filter);

        return FinancialSummaryDTO.builder()
                .walletId(filter.getWalletId())
                .operationType(filter.getOperationType())
                .currency(filter.getCurrency())
                .dateStart(calculateDateStart(filter, transactions))
                .dateEnd(calculateDateEnd(filter, transactions))
                .transactionAmount(calculateTransactionAmount(transactions))
                .incomeAmount(calculateIncomeAmount(transactions))
                .expenseAmount(calculateExpenseAmount(transactions))
                .build();
    }

    private BigDecimal calculateTransactionAmount(List<WalletTransaction> transactions) {
        BigDecimal transactionAmount = BigDecimal.ZERO;

        for (WalletTransaction transaction : transactions) {
            if (transaction.getAmount() != null) {
                transactionAmount = transactionAmount.add(transaction.getAmount());
            }
        }

        return transactionAmount;
    }

    private BigDecimal calculateIncomeAmount(List<WalletTransaction> transactions) {
        return transactions.stream()
                .filter(transaction -> transaction.getOperationType()
                        .equals(WalletTransaction.OperationType.INCOME))
                .map(WalletTransaction::getAmount)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateExpenseAmount(List<WalletTransaction> transactions) {
        return transactions.stream()
                .filter(transaction -> transaction.getOperationType()
                        .equals(WalletTransaction.OperationType.EXPENSE))
                .map(WalletTransaction::getAmount)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private LocalDateTime calculateDateStart(WalletTransactionFilter filter,
                                             List<WalletTransaction> transactions) {
        LocalDateTime filterDateStart = filter.getTransactionDateStart();

        if (filterDateStart != null) {
            return filterDateStart;
        }

        LocalDateTime earliestTransactionDate = transactions.stream()
                .map(WalletTransaction::getTransactionDate)
                .min(LocalDateTime::compareTo)
                .orElse(null);

        return earliestTransactionDate;
    }

    private LocalDateTime calculateDateEnd(WalletTransactionFilter filter,
                                           List<WalletTransaction> transactions) {
        LocalDateTime filterDateEnd = filter.getTransactionDateEnd();

        if (filterDateEnd != null) {
            return filterDateEnd;
        }

        LocalDateTime latestTransactionDate = transactions.stream()
                .map(WalletTransaction::getTransactionDate)
                .max(LocalDateTime::compareTo)
                .orElse(null);

        return latestTransactionDate;
    }
}