package com.interrupt.elasticsearch;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.JestResult;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Search;

import java.util.logging.Logger;

public class SearchClient {
    JestClient client;
    final Logger logger = Logger.getLogger(SearchClient.class.getName());

    public SearchClient() {
        String connectionUrl = "https://site:8d32324fc58e4f5a3e0163d5d0df40d5@bofur-us-east-1.searchly.com";
        JestClientFactory factory = new JestClientFactory();
        factory.setHttpClientConfig(new HttpClientConfig
                .Builder(connectionUrl)
                .multiThreaded(true)
                .build());
        JestClient client = factory.getObject();
    }

    public JestResult Search(String query) {
        String fullQuery =
                "{\n" +
                "  \"query\": {\n" +
                "  \"multi_match\" : {\n" +
                "    \"query\": \"" + query + "\", \n" +
                "    \"fields\": [ \"name^3\", \"owner\" ] \n" +
                "  }\n" +
                " }\n" +
                "}";

        Search search = new Search.Builder(fullQuery)
                .addIndex("tasks")
                .addType("task")
                .build();

        try {
            return client.execute(search);
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return null;
        }
    }
}
