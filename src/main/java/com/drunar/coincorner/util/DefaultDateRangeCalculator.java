package com.drunar.coincorner.util;

import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;

@Service
public class DefaultDateRangeCalculator implements DateRangeCalculator {
    @Override
    public LocalDate[] calculateDateRange(String period) {
        LocalDate startDate = null;
        LocalDate endDate = LocalDate.now();

        switch (period) {
            case "lastWeek":
                startDate = endDate.minusWeeks(1).with(DayOfWeek.MONDAY);
                endDate = endDate.minusWeeks(1).with(DayOfWeek.SUNDAY);
                break;
            case "currentWeek":
                startDate = endDate.with(DayOfWeek.MONDAY);
                break;
            case "lastMonth":
                startDate = endDate.minusMonths(1).withDayOfMonth(1);
                endDate = endDate.minusMonths(1).withDayOfMonth(1).plusMonths(1).minusDays(1);
                break;
            case "currentMonth":
                startDate = endDate.withDayOfMonth(1);
                break;
            case "lastQuarter":
                int lastMonth = endDate.getMonthValue();
                int lastQuarterStartMonth = (lastMonth - 1) / 3 * 3 + 1;
                startDate = LocalDate.of(endDate.getYear(), lastQuarterStartMonth, 1).minusMonths(3);
                endDate = startDate.plusMonths(3).minusDays(1);
                break;
            case "currentQuarter":
                int currentMonth = endDate.getMonthValue();
                int currentQuarterStartMonth = (currentMonth - 1) / 3 * 3 + 1;
                startDate = LocalDate.of(endDate.getYear(), currentQuarterStartMonth, 1);
                break;
            default:
                break;
        }

        return new LocalDate[]{startDate, endDate};
    }
}
