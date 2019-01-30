package com.fiserv.dda.archive.documentsearchdemo.api.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchIndex {
    private SearchOperator operator;
    private String name;
    private List<String> values;
}
