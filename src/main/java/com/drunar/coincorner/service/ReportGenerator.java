package com.drunar.coincorner.service;

import com.drunar.coincorner.dto.FinancialSummaryDTO;
import com.drunar.coincorner.dto.PageResponse;
import com.drunar.coincorner.dto.WalletTransactionDTO;

import java.io.ByteArrayOutputStream;

public interface ReportGenerator {
    ByteArrayOutputStream generateReport(FinancialSummaryDTO finance,
                                         PageResponse<WalletTransactionDTO> transactions,
                                         String format);
}
