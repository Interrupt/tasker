package com.interrupt.elasticsearch.commands;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.interrupt.elasticsearch.SearchClient;
import com.interrupt.tasks.model.Task;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Search;

import java.util.*;
import java.util.logging.Logger;

/**
 * Command to run a search for tasks on a given query, and return a sorted list of the found items
 */
public class SearchCommand extends HystrixCommand<JsonObject> {

    final private Logger logger = Logger.getLogger(SearchCommand.class.getName());
    private JestClient client;
    private String query;

    public SearchCommand(String query) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("Search")).
                andCommandPropertiesDefaults(HystrixCommandProperties.Setter().
                        withExecutionIsolationThreadTimeoutInMilliseconds(5000)));

        this.query = query;
        client = SearchClient.getClient();
    }

    @Override
    protected JsonObject run() throws Exception {
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

            JestResult results = client.execute(search);
            return results.getJsonObject();
        }
        catch (Exception e) {
            logger.severe(e.getMessage());
            return null;
        }
    }

    @Override
    protected JsonObject getFallback() {
        return null;
    }
}
