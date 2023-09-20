package com.drunar.coincorner.service;

import java.math.BigDecimal;

public interface CashTransferService {

    boolean cashTransfer(Long senderWalletId, Long recipientWalletId, BigDecimal amount);

}
