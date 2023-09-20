package com.drunar.coincorner.service;

import com.drunar.coincorner.dto.FinancialSummaryDTO;
import com.drunar.coincorner.dto.PageResponse;
import com.drunar.coincorner.dto.WalletTransactionDTO;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;

@Service
public class TxtReportGenerator implements ReportGenerator {
    @Override
    public void generate(PrintWriter writer,
                         FinancialSummaryDTO finance,
                         PageResponse<WalletTransactionDTO> transactions) {
        writer.println("Financial Summary:");
        writer.println("Currency: " + finance.getCurrency());
        writer.println("Transaction Amount: " + finance.getTransactionAmount());
        writer.println("Income Amount: " + finance.getIncomeAmount());
        writer.println("Expense Amount: " + finance.getExpenseAmount());
        writer.println("Date Start: " + finance.getDateStart());
        writer.println("Date End: " + finance.getDateEnd());

        writer.println("\nTransactions:");
        for (WalletTransactionDTO transaction : transactions.getContent()) {
            writer.println("Transaction ID: " + transaction.getId());
            writer.println("Wallet ID: " + transaction.getWalletId());
            writer.println("Wallet name: " + transaction.getWalletName());
            writer.println("Currency: " + transaction.getCurrency());
            writer.println("Previous balance: " + transaction.getPreviousBalance());
            writer.println("Amount: " + transaction.getAmount());
            writer.println("Current balance: " + transaction.getCurrentBalance());
            writer.println("Operation type: " + transaction.getOperationType());
            writer.println("Date: " + transaction.getTransactionDate());
            writer.println("-----------------------------------------");
        }
    }
}
