package com.fiserv.dda.archive.documentsearchdemo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchResponse {
    private Map<String, String> searchIndexes;
    private List<SearchHit> hits;

    public int getNumberOfHits() {
        return hits == null ? 0 : hits.size();
    }
}
