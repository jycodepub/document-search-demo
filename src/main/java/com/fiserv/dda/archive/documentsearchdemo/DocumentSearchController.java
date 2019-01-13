package com.fiserv.dda.archive.documentsearchdemo;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class DocumentSearchController {

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private final MongoTemplate template;

    public DocumentSearchController(MongoTemplate template) {
        this.template = template;
    }

    @PostMapping("/search")
    public ResponseEntity<?> indexesSearch(@RequestBody SearchRequest request) throws Exception {

        Query query = new Query();

        // Take care of DocumentDate
        Map<String, String> searchIndexes = request.getSearchIndexes();
        String documentDate = searchIndexes.get("documentDate");
        if (documentDate != null) {
            query.addCriteria(Criteria.where("documentDate").is(simpleDateFormat.parse(documentDate)));
        }

        // Convert the Map to a list of string array and filter out 'documentDate'
        List<String[]> indexList = request.getSearchIndexes().entrySet().stream()
                .filter(e -> !e.getKey().equals("documentDate"))
                .map(e -> new String[] {e.getKey(), e.getValue()})
                .collect(Collectors.toList());

        // For other search indexes
        if (indexList.size() > 0) {
            Criteria criteria = Criteria.where("indexes");

            if (request.getOperator() == SearchOperator.AND)
                criteria.all(indexList);
            else if (request.getOperator() == SearchOperator.OR)
                criteria.in(indexList);
            else
                return ResponseEntity.badRequest().body("Unsupported operator");

            query.addCriteria(criteria);
        }

        List<SearchHit> hits = template.find(query, SearchHit.class, "idx-92554");

        return ResponseEntity.ok(new SearchResponse(request.getSearchIndexes(), hits));
    }

}
