package com.drunar.coincorner.http.rest;

import com.drunar.coincorner.database.filter.WalletTransactionFilter;
import com.drunar.coincorner.dto.CashTransferDTO;
import com.drunar.coincorner.dto.MoneyFormDTO;
import com.drunar.coincorner.dto.PageResponse;
import com.drunar.coincorner.dto.WalletTransactionDTO;
import com.drunar.coincorner.service.CashTransferService;
import com.drunar.coincorner.service.WalletServiceImpl;
import com.drunar.coincorner.service.WalletTransactionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users/{userId}/wallets/{walletId}/transactions")
@Tag(name = "Wallet Transaction Rest Controller", description = "Finance transaction CRUD operations.")
@RequiredArgsConstructor
public class WalletTransactionRestController {

    private final WalletTransactionService walletTrService;
    private final WalletServiceImpl walletService;
    private final CashTransferService cashTransferService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public PageResponse<WalletTransactionDTO> findAll(@PathVariable Long userId,
                                                      @PathVariable Long walletId,
                                                      WalletTransactionFilter filter, Pageable pageable) {
        Page<WalletTransactionDTO> page = walletTrService.findAll(filter, pageable);
        return PageResponse.of(page);
    }


    @PostMapping(path = "/balanceUpdate")
    public ResponseEntity<String> balanceUpdate(@PathVariable Long userId,
                                                @PathVariable Long walletId,
                                                @RequestBody MoneyFormDTO moneyForm) {
        if (walletService.updateBalance(walletId, moneyForm.getAmount())) {
            return ResponseEntity.ok("The balance has been successfully updated.");
        } else {
            return ResponseEntity.badRequest().body("Failed to update the balance. An error has occurred.");
        }

    }

    @PostMapping("/cashTransfer")
    public ResponseEntity<String> cashTransfer(@PathVariable Long userId,
                                               @PathVariable Long walletId,
                                               @RequestBody @Validated CashTransferDTO cashTransferDTO) {

        if (cashTransferService.cashTransfer(cashTransferDTO.getSenderWalletId(),
                cashTransferDTO.getRecipientWalletId(), cashTransferDTO.getAmount())) {
            return ResponseEntity.ok("The operation was successful.");
        } else {
            return ResponseEntity.badRequest().body("The operation was not successful.");
        }

    }


}
