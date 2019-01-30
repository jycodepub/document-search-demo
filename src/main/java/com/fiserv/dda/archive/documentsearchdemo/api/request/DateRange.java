package com.fiserv.dda.archive.documentsearchdemo.api.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DateRange {
    private LocalDate startDate;
    private LocalDate endDate;

    public boolean isValid() {
        return startDate != null && endDate != null && startDate.isBefore(endDate);
    }
}
