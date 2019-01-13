package com.fiserv.dda.archive.documentsearchdemo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchHit {
    private String documentId;
    private int totalPages;
    @JsonProperty("documentIndexes")
    private List<String[]> indexes;
}
