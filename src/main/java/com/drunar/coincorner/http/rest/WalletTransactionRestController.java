package com.drunar.coincorner.http.rest;

import com.drunar.coincorner.database.filter.WalletTransactionFilter;
import com.drunar.coincorner.dto.FinancialSummaryDTO;
import com.drunar.coincorner.dto.PageResponse;
import com.drunar.coincorner.dto.WalletTransactionDTO;
import com.drunar.coincorner.service.FileService;
import com.drunar.coincorner.service.WalletTransactionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;

@RestController
@RequestMapping("/api/v1/transactions")
@Tag(name = "Wallet Transaction Rest Controller", description = "Finance transaction CRUD operations.")
@RequiredArgsConstructor
public class WalletTransactionRestController {

    private final FileService fileService;
    private final WalletTransactionService walletTrService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public PageResponse<WalletTransactionDTO> findAll(WalletTransactionFilter filter, Pageable pageable) {
        Page<WalletTransactionDTO> page = walletTrService.findAll(filter, pageable);
        return PageResponse.of(page);
    }





    @PostMapping("/finances/saveToFile")
    public ResponseEntity<Resource> saveToFile(HttpServletRequest request,
                                               @RequestParam(name = "format") String format) {
        HttpSession session = request.getSession();
        FinancialSummaryDTO financeData = (FinancialSummaryDTO) session.getAttribute("financeData");
        @SuppressWarnings("unchecked")
        PageResponse<WalletTransactionDTO> transactionsData =
                (PageResponse<WalletTransactionDTO>) session.getAttribute("transactionsData");

        try {
            ByteArrayOutputStream outputStream = fileService.generateFileBytes(financeData, transactionsData, format);

            if (outputStream != null) {
                ByteArrayResource resource = new ByteArrayResource(outputStream.toByteArray());

                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=report." + format)
                        .contentLength(outputStream.size())
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .body(resource);
            }
            else {
                return ResponseEntity.badRequest()
                        .body(new ByteArrayResource("Invalid format.".getBytes()));

            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ByteArrayResource("An error occurred while generating or saving the file.".getBytes()));
        }
    }

}
