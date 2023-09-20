package com.drunar.coincorner.service;

import com.drunar.coincorner.dto.FinancialSummaryDTO;
import com.drunar.coincorner.dto.PageResponse;
import com.drunar.coincorner.dto.WalletTransactionDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.Map;

@Service
@AllArgsConstructor
public class FileServiceImpl implements FileService{

    private final Map<String, ReportGenerator> reportGenerators;

    @Override
    public ByteArrayOutputStream generateReport(FinancialSummaryDTO finance,
                                                   PageResponse<WalletTransactionDTO> transactions,
                                                   String format) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try (PrintWriter writer = new PrintWriter(outputStream)) {
            String generatorName = format.toLowerCase() + "ReportGenerator";
            ReportGenerator reportGenerator = reportGenerators.get(generatorName);
            if (reportGenerator != null) {
                reportGenerator.generate(writer, finance, transactions);
            }
        }

        return outputStream;
    }


}