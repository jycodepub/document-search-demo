package com.fiserv.dda.archive.documentsearchdemo.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchResponse {
    private List<SearchHit> hits;

    public int getNumberOfHits() {
        return hits == null ? 0 : hits.size();
    }

    public void addHit(SearchHit hit) {
        if (hits == null)
            hits = new ArrayList<>();
        hits.add(hit);
    }
}
