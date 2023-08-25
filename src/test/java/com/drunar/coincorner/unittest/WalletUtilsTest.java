package com.drunar.coincorner.unittest;

import com.drunar.coincorner.dto.WalletReadDTO;
import com.drunar.coincorner.util.incomeInterestRate;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.drunar.coincorner.database.entity.Wallet.Currency.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class WalletUtilsTest {

    @Test
    public void testCalculateIncomeInterestRate() {
        WalletReadDTO wallet1 = Mockito.mock(WalletReadDTO.class);
        Mockito.when(wallet1.getBalance()).thenReturn(new BigDecimal("1000.00"));
        Mockito.when(wallet1.getCurrency()).thenReturn(String.valueOf(USD));

        WalletReadDTO wallet2 = Mockito.mock(WalletReadDTO.class);
        Mockito.when(wallet2.getBalance()).thenReturn(new BigDecimal("1000.00"));
        Mockito.when(wallet2.getCurrency()).thenReturn(String.valueOf(PLN));

        WalletReadDTO wallet3 = Mockito.mock(WalletReadDTO.class);
        Mockito.when(wallet3.getBalance()).thenReturn(new BigDecimal("1000.00"));
        Mockito.when(wallet3.getCurrency()).thenReturn(String.valueOf(UAH));

        WalletReadDTO wallet4 = Mockito.mock(WalletReadDTO.class);
        Mockito.when(wallet4.getBalance()).thenReturn(new BigDecimal("1000.00"));
        Mockito.when(wallet4.getCurrency()).thenReturn(String.valueOf(EUR));

        LocalDate localDateStart = LocalDate.of(2023, 8, 1);
        LocalDate localDateEnd = LocalDate.of(2023, 9, 1);
        String dateStart = localDateStart.toString();
        String dateEnd = localDateEnd.toString();


        BigDecimal expectedIncreasedBalanceWallet1 = new BigDecimal("1006.200000");
        BigDecimal calculatedBalanceWallet1 = incomeInterestRate.calculate(wallet1, dateStart, dateEnd);

        BigDecimal expectedIncreasedBalanceWallet2 = new BigDecimal("1009.300000");
        BigDecimal calculatedBalanceWallet2 = incomeInterestRate.calculate(wallet2, dateStart, dateEnd);

        BigDecimal expectedIncreasedBalanceWallet3 = new BigDecimal("1012.400000");
        BigDecimal calculatedBalanceWallet3 = incomeInterestRate.calculate(wallet3, dateStart, dateEnd);

        BigDecimal expectedIncreasedBalanceWallet4 = new BigDecimal("1006.200000");
        BigDecimal calculatedBalanceWallet4 = incomeInterestRate.calculate(wallet4, dateStart, dateEnd);


        assertEquals(expectedIncreasedBalanceWallet1, calculatedBalanceWallet1);
        assertEquals(expectedIncreasedBalanceWallet2, calculatedBalanceWallet2);
        assertEquals(expectedIncreasedBalanceWallet3, calculatedBalanceWallet3);
        assertEquals(expectedIncreasedBalanceWallet4, calculatedBalanceWallet4);
    }

    @Test
    public void testCalculateAllDateNull() {
        WalletReadDTO wallet1 = Mockito.mock(WalletReadDTO.class);
        Mockito.when(wallet1.getBalance()).thenReturn(new BigDecimal("1000.00"));
        Mockito.when(wallet1.getCurrency()).thenReturn(String.valueOf(USD));

        String dateStart = null;
        String dateEnd = null;

        BigDecimal expectedIncreasedBalanceWallet1 = new BigDecimal("1000.00");
        BigDecimal calculatedBalanceWallet1 = incomeInterestRate.calculate(wallet1, dateStart, dateEnd);

        assertEquals(expectedIncreasedBalanceWallet1, calculatedBalanceWallet1);
    }



}