package com.fiserv.dda.archive.documentsearchdemo.api.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequest {
    private LocalDate documentDate;
    private DateRange dateRange;
    private List<SearchIndex> searchIndexes;

    public boolean hasSearchIndexes() {
        return searchIndexes != null && searchIndexes.size() > 0;
    }
}
