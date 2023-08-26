package com.drunar.coincorner.util;

  /*
    Income from an interest rate (or credit) of :
    USD, EUR - 7.2% per year - 0.02% per day
    PLN -  per year 10.8%,  0.03% per day
    UAH - er year 14.4% , 0.04% per day
     */

import com.drunar.coincorner.dto.WalletReadDTO;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class InterestRate {

    public static BigDecimal calculate(WalletReadDTO wallet, String dateStart, String dateEnd) {
        BigDecimal balance = wallet.getBalance();
        BigDecimal dailyInterestRate;

        LocalDate localDateStart = StringUtils.hasText(dateStart) ? LocalDate.parse(dateStart) : LocalDate.now();
        LocalDate localDateEnd = StringUtils.hasText(dateEnd) ? LocalDate.parse(dateEnd) : null;

        if (localDateEnd != null) {
            long daysBetween = ChronoUnit.DAYS.between(localDateStart, localDateEnd);

            dailyInterestRate = switch (wallet.getCurrency()) {
                case "USD", "EUR" -> new BigDecimal("0.0002");
                case "PLN" -> new BigDecimal("0.0003");
                case "UAH" -> new BigDecimal("0.0004");
                default -> throw new IllegalArgumentException("Unsupported currency: " + wallet.getCurrency());
            };

            BigDecimal percentageIncrease = dailyInterestRate.multiply(new BigDecimal(daysBetween));

            return balance.add(balance.multiply(percentageIncrease));
        } else {
            return balance;
        }
    }
}
