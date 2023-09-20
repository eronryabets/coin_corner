package com.drunar.coincorner.service;

import com.drunar.coincorner.dto.FinancialSummaryDTO;
import com.drunar.coincorner.dto.PageResponse;
import com.drunar.coincorner.dto.WalletTransactionDTO;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;

@Service
public class XmlReportGenerator implements ReportGenerator {
    @Override
    public void generate(PrintWriter writer,
                                FinancialSummaryDTO finance,
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
}
