package com.fiserv.dda.archive.documentsearchdemo;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class DocumentSearchController {

    private final MongoTemplate template;

    public DocumentSearchController(MongoTemplate template) {
        this.template = template;
    }

    @PostMapping("/search")
    public SearchResponse indexesSearch(@RequestBody SearchRequest request) {

        List<String[]> indexList = request.getSearchIndexes().entrySet().stream()
                .map(e -> new String[] {e.getKey(), e.getValue()})
                .collect(Collectors.toList());

        Criteria criteria = Criteria.where("indexes");
        if (request.getOperator() == SearchOperator.AND)
            criteria.all(indexList);
        else if (request.getOperator() == SearchOperator.OR)
            criteria.in(indexList);

        Query query = new Query(criteria);

        List<SearchHit> hits = template.find(query, SearchHit.class, "idx-92554");
        return new SearchResponse(request.getSearchIndexes(), hits);
    }
}
