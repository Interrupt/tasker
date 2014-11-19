package com.interrupt.elasticsearch;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.JestResult;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Index;
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

        client = factory.getObject();
    }

    public JestResult Search(String query) {
        try {
            String fullQuery =
                    "{\n" +
                    "  \"query\": {\n" +
                    "  \"multi_match\" : {\n" +
                    "    \"query\": \"" + query + "\", \n" +
                    "    \"fields\": [ \"title^3\", \"body\" ] \n" +
                    "  }\n" +
                    " }\n" +
                    "}";

            Search search = new Search.Builder(fullQuery)
                    .addIndex("tasks")
                    .addType("task")
                    .build();

            return client.execute(search);
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return null;
        }
    }

    public void Index(String key, Object object) {
        try {
            Index index = new Index.Builder(object).index("tasks").type("task").id(key).build();
            client.execute(index);
        } catch (Exception e) {
            logger.severe(e.getMessage());
        }
    }
}
