package com.drunar.coincorner.util;

import java.time.LocalDate;

public interface DateRangeCalculator {
    LocalDate[] calculateDateRange(String period);
}
