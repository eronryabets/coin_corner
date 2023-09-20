package com.drunar.coincorner.service;

import com.drunar.coincorner.dto.FinancialSummaryDTO;
import com.drunar.coincorner.dto.PageResponse;
import com.drunar.coincorner.dto.WalletTransactionDTO;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

@Service
public class FileService implements ReportGenerator{

    @Override
    public ByteArrayOutputStream generateReport(FinancialSummaryDTO finance,
                                                PageResponse<WalletTransactionDTO> transactions,
                                                String format) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try (PrintWriter writer = new PrintWriter(outputStream)) {
            if ("txt".equals(format)) {
                generateTxtFile(writer, finance, transactions);
            } else if ("xml".equals(format)) {
                generateXmlFile(writer, finance, transactions);
            } else if ("html".equals(format)) {
                generateHtmlFile(writer, finance, transactions);
            }
        }

        return outputStream;
    }

    public void generateTxtFile(PrintWriter writer, FinancialSummaryDTO finance,
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

    public void generateXmlFile(PrintWriter writer, FinancialSummaryDTO finance,
                                PageResponse<WalletTransactionDTO> transactions) {
        writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        writer.println("<report>");
        writer.println("  <financial-summary>");
        writer.println("    <currency>" + finance.getCurrency() + "</currency>");
        writer.println("    <transaction-amount>" + finance.getTransactionAmount() + "</transaction-amount>");
        writer.println("    <income-amount>" + finance.getIncomeAmount() + "</income-amount>");
        writer.println("    <expense-amount>" + finance.getExpenseAmount() + "</expense-amount>");
        writer.println("    <date-start>" + finance.getDateStart() + "</date-start>");
        writer.println("    <date-end>" + finance.getDateEnd() + "</date-end>");
        writer.println("  </financial-summary>");

        writer.println("  <transactions>");
        for (WalletTransactionDTO transaction : transactions.getContent()) {
            writer.println("    <transaction>");
            writer.println("      <id>" + transaction.getId() + "</id>");
            writer.println("      <wallet-id>" + transaction.getWalletId() + "</wallet-id>");
            writer.println("      <wallet-name>" + transaction.getWalletName() + "</wallet-name>");
            writer.println("      <currency>" + transaction.getCurrency() + "</currency>");
            writer.println("      <previous-balance>" + transaction.getPreviousBalance() + "</previous-balance>");
            writer.println("      <amount>" + transaction.getAmount() + "</amount>");
            writer.println("      <current-balance>" + transaction.getCurrentBalance() + "</current-balance>");
            writer.println("      <operation-type>" + transaction.getOperationType() + "</operation-type>");
            writer.println("      <date>" + transaction.getTransactionDate() + "</date>");
            writer.println("    </transaction>");
        }
        writer.println("  </transactions>");

        writer.println("</report>");
    }

    public void generateHtmlFile(PrintWriter writer, FinancialSummaryDTO finance,
                                 PageResponse<WalletTransactionDTO> transactions) {
        writer.println("<html>");
        writer.println("<head><title>Financial Report</title></head>");
        writer.println("<body>");

        writer.println("<h1>Financial Summary:</h1>");
        writer.println("<p>Currency: " + finance.getCurrency() + "</p>");
        writer.println("<p>Transaction Amount: " + finance.getTransactionAmount() + "</p>");
        writer.println("<p>Income Amount: " + finance.getIncomeAmount() + "</p>");
        writer.println("<p>Expense Amount: " + finance.getExpenseAmount() + "</p>");
        writer.println("<p>Date Start: " + finance.getDateStart() + "</p>");
        writer.println("<p>Date End: " + finance.getDateEnd() + "</p>");

        writer.println("<h1>Transactions:</h1>");
        writer.println("<table>");
        writer.println("<tr><th>ID</th><th>Wallet ID</th><th>Wallet Name</th><th>Currency</th><th>Previous Balance</th><th>Amount</th><th>Current Balance</th><th>Operation Type</th><th>Date</th></tr>");
        for (WalletTransactionDTO transaction : transactions.getContent()) {
            writer.println("<tr>");
            writer.println("<td>" + transaction.getId() + "</td>");
            writer.println("<td>" + transaction.getWalletId() + "</td>");
            writer.println("<td>" + transaction.getWalletName() + "</td>");
            writer.println("<td>" + transaction.getCurrency() + "</td>");
            writer.println("<td>" + transaction.getPreviousBalance() + "</td>");
            writer.println("<td>" + transaction.getAmount() + "</td>");
            writer.println("<td>" + transaction.getCurrentBalance() + "</td>");
            writer.println("<td>" + transaction.getOperationType() + "</td>");
            writer.println("<td>" + transaction.getTransactionDate() + "</td>");
            writer.println("</tr>");
        }
        writer.println("</table>");

        writer.println("</body>");
        writer.println("</html>");
    }
}