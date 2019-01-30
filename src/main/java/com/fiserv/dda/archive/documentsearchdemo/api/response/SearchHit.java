package com.fiserv.dda.archive.documentsearchdemo.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchHit {
    private String documentId;
    private String externalId;
    private String totalPages;
    @JsonProperty("documentIndexes")
    private Map<String, String> indexes;
}
