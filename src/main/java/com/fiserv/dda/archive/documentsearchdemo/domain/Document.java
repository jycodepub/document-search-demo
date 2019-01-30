package com.fiserv.dda.archive.documentsearchdemo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@org.springframework.data.mongodb.core.mapping.Document
public class Document {
    @Id
    private String id;
    private String documentId;
    private String externalId;
    private LocalDate documentDate;
    private String totalPages;
    private Map<String, String> indexes;

    public Document addIndex(String name, String value) {
        if (indexes == null)
            indexes = new HashMap<>();
        indexes.put(name, value);
        return this;
    }
}
