package com.fiserv.dda.archive.documentsearchdemo;

import com.fiserv.dda.archive.documentsearchdemo.domain.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class DocumentSearchDemoApplication {

    @Autowired
    private MongoTemplate mongoTemplate;

    public static void main(String[] args) {
        SpringApplication.run(DocumentSearchDemoApplication.class, args);
    }

    @Bean
    public CommandLineRunner documentLoader() {
        return args -> {
            // Drop collection if exists
            if (mongoTemplate.collectionExists("documents")) {
                mongoTemplate.dropCollection("documents");
            }

            // Create collection
            mongoTemplate.createCollection("documents");

            // Create indexes in ASC direction
            String[] indexes = new String[] {
                    "indexes.documentId",
                    "indexes.statementNumber",
                    "indexes.customerName",
                    "indexes.tinNumber"
            };
            Arrays.stream(indexes).forEach(i -> mongoTemplate.indexOps("documents")
                    .ensureIndex(new Index().on(i, Sort.Direction.ASC)));

            // Create documentDate index in DESC direction
            mongoTemplate.indexOps("documents")
                    .ensureIndex(new Index().on("documentDate", Sort.Direction.DESC));

            // Insert sample data
            createDocuments().forEach(d -> mongoTemplate.insert(d, "documents"));
        };
    }

    private List<Document> createDocuments() {
        return Arrays.asList(
                Document.builder()
                        .documentId(UUID.randomUUID().toString())
                        .externalId(UUID.randomUUID().toString())
                        .documentDate(LocalDate.parse("2019-01-29"))
                        .totalPages("2")
                        .build()
                        .addIndex("customerName", "customer1")
                        .addIndex("statementNumber", "101")
                        .addIndex("tinNumber", "1001"),
                Document.builder()
                        .documentId(UUID.randomUUID().toString())
                        .externalId(UUID.randomUUID().toString())
                        .documentDate(LocalDate.parse("2019-01-29"))
                        .totalPages("2")
                        .build()
                        .addIndex("customerName", "customer2")
                        .addIndex("statementNumber", "102")
                        .addIndex("tinNumber", "1002"),
                Document.builder()
                        .documentId(UUID.randomUUID().toString())
                        .externalId(UUID.randomUUID().toString())
                        .documentDate(LocalDate.parse("2019-01-29"))
                        .totalPages("2")
                        .build()
                        .addIndex("customerName", "customer3")
                        .addIndex("statementNumber", "103")
                        .addIndex("tinNumber", "1003"),
                Document.builder()
                        .documentId(UUID.randomUUID().toString())
                        .externalId(UUID.randomUUID().toString())
                        .documentDate(LocalDate.parse("2019-03-10"))
                        .totalPages("5")
                        .build()
                        .addIndex("customerName", "customer4")
                        .addIndex("statementNumber", "104")
                        .addIndex("tinNumber", "1004"),
                Document.builder()
                        .documentId(UUID.randomUUID().toString())
                        .externalId(UUID.randomUUID().toString())
                        .documentDate(LocalDate.parse("2019-03-10"))
                        .totalPages("2")
                        .build()
                        .addIndex("customerName", "customer5")
                        .addIndex("statementNumber", "105")
                        .addIndex("tinNumber", "1005")
                );
    }
}

