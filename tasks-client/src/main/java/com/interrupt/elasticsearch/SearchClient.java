package com.interrupt.elasticsearch;

import com.interrupt.tasks.model.Task;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.JestResult;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Delete;
import io.searchbox.core.Index;
import io.searchbox.core.Search;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class SearchClient {
    private static JestClient client;
    final Logger logger = Logger.getLogger(SearchClient.class.getName());

    static {
        // TODO: Move API keys to a properties file
        // TODO: Move this client to be dependency injected instead of being static
        String connectionUrl = "https://site:8d32324fc58e4f5a3e0163d5d0df40d5@bofur-us-east-1.searchly.com";
        JestClientFactory factory = new JestClientFactory();

        factory.setHttpClientConfig(new HttpClientConfig
                .Builder(connectionUrl)
                .multiThreaded(true)
                .build());

        client = factory.getObject();
    }

    public static JestClient getClient() {
        return client;
    }
}
