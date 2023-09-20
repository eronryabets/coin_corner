package com.drunar.coincorner.http.rest;

import com.drunar.coincorner.database.filter.WalletFilter;
import com.drunar.coincorner.dto.PageResponse;
import com.drunar.coincorner.dto.WalletCreateEditDTO;
import com.drunar.coincorner.dto.WalletReadDTO;
import com.drunar.coincorner.http.handler.OptionalResponseHandler;
import com.drunar.coincorner.service.WalletServiceImpl;
import com.drunar.coincorner.util.InterestRate;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users/{userId}/wallets")
@Tag(name = "Wallet Rest Controller", description = "Wallets CRUD operations.")
@RequiredArgsConstructor
public class WalletRestController {

    private final WalletServiceImpl walletService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    private PageResponse<WalletReadDTO> findAll(WalletFilter filter, Pageable pageable,
                                                @PathVariable Long userId) {
        Page<WalletReadDTO> page = walletService.findAll(filter, pageable);
        return PageResponse.of(page);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WalletReadDTO> findById(@PathVariable Long userId,
                                                  @PathVariable Long id) {
        Optional<WalletReadDTO> wallet = walletService.findById(id);
        return OptionalResponseHandler.handleOptional(wallet);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public WalletReadDTO create(@RequestBody @Validated WalletCreateEditDTO wallet,
                                @PathVariable Long userId) {
        return walletService.create(wallet);
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public WalletReadDTO update(@PathVariable Long userId, @PathVariable Long id,
                                @RequestBody @Validated WalletCreateEditDTO wallet) {
        return walletService.update(id, wallet)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> delete(@PathVariable Long userId, @PathVariable("id") Long id) {
        if (!walletService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok("The wallet has been successfully deleted");

    }

    @GetMapping("/interestRate/{id}")
    public BigDecimal incomeInterestRate(@PathVariable Long userId, @PathVariable Long id,
                                         @RequestParam(required = false) String dateStart,
                                         @RequestParam(required = false) String dateEnd){
        return walletService.findById(id)
                .map(wallet -> InterestRate.calculate(wallet, dateStart, dateEnd))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }



}
