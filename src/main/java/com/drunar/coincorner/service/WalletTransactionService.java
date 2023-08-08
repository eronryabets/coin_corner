package com.drunar.coincorner.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WalletTransactionService {

    //TODO: cashTransfer(); income(), expense() etc

}
