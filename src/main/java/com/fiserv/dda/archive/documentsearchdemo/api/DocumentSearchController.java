package com.fiserv.dda.archive.documentsearchdemo.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiserv.dda.archive.documentsearchdemo.api.request.SearchRequest;
import com.fiserv.dda.archive.documentsearchdemo.api.response.SearchHit;
import com.fiserv.dda.archive.documentsearchdemo.api.response.SearchResponse;
import com.fiserv.dda.archive.documentsearchdemo.domain.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class DocumentSearchController {

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final MongoTemplate template;

    public DocumentSearchController(MongoTemplate template) {
        this.template = template;
    }

    @PostMapping("/search")
    public ResponseEntity<?> indexesSearch(@RequestBody SearchRequest request) throws Exception {

        Query query = new Query();

        // Take care of DocumentDate
        if (request.getDocumentDate() != null) {
            query.addCriteria(Criteria.where("documentDate").is(request.getDocumentDate()));
        } else if (request.getDateRange() != null && request.getDateRange().isValid()) {
            Criteria criteria = new Criteria().andOperator(
                    Criteria.where("documentDate").gte(request.getDateRange().getStartDate()),
                    Criteria.where("documentDate").lte(request.getDateRange().getEndDate())
            );
            query.addCriteria(criteria);
        }

        // For other search indexes
        if (request.hasSearchIndexes()) {
            request.getSearchIndexes().forEach(index -> {
                Criteria criteria = Criteria.where(index.getName());
                boolean validOperator = true;
                switch (index.getOperator()) {
                    case eq:
                        criteria.is(index.getValues().get(0));
                        break;
                    case ne:
                        criteria.ne(index.getValues().get(0));
                        break;
                    case in:
                        criteria.in(index.getValues());
                        break;
                    default:
                        validOperator = false;
                }
                if (validOperator) {
                    query.addCriteria(criteria);
                }
            });
        }

        List<SearchHit> hits = template.find(query, Document.class, "documents")
                .stream().map(this::toSearchHit)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new SearchResponse(hits));
    }

    private SearchHit toSearchHit(Document document) {
        return SearchHit.builder()
                .documentId(document.getDocumentId())
                .externalId(document.getExternalId())
                .totalPages(document.getTotalPages())
                .indexes(document.getIndexes())
                .build();
    }
}
