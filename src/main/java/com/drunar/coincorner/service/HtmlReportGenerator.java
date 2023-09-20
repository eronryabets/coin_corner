package com.drunar.coincorner.service;

import com.drunar.coincorner.dto.FinancialSummaryDTO;
import com.drunar.coincorner.dto.PageResponse;
import com.drunar.coincorner.dto.WalletTransactionDTO;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;

@Service
public class HtmlReportGenerator implements ReportGenerator {
    @Override
    public void generate(PrintWriter writer,
                         FinancialSummaryDTO finance,
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
