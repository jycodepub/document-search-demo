package com.fiserv.dda.archive.documentsearchdemo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequest {
    private SearchOperator operator = SearchOperator.AND;
    private Map<String, String> searchIndexes;
}
