package com.drunar.coincorner.database.filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Builder
@Value
@AllArgsConstructor
public class UserFilter {

     String firstname;
     String lastname;
     LocalDate birthDateIn;
     LocalDate birthDateBefore;
     LocalDate birthDateAfter;
     LocalDate birthDateRangeStart;
     LocalDate birthDateRangeEnd;

}