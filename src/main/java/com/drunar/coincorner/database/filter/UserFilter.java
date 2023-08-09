package com.drunar.coincorner.database.filter;

import java.time.LocalDate;

public record UserFilter(String firstname,
                         String lastname,
                         LocalDate birthDate) {
}
