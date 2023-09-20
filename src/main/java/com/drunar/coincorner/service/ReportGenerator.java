package com.drunar.coincorner.service;

import com.drunar.coincorner.dto.FinancialSummaryDTO;
import com.drunar.coincorner.dto.PageResponse;
import com.drunar.coincorner.dto.WalletTransactionDTO;

import java.io.PrintWriter;

public interface ReportGenerator {

    void generate(PrintWriter writer,
                  FinancialSummaryDTO finance,
                  PageResponse<WalletTransactionDTO> transactions);

}
