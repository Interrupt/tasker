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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Command to run a search for tasks on a given query, and return a sorted list of the found items
 */
public class SearchCommand extends HystrixCommand<Map<String, Task>> {

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
    protected Map<String, Task> run() throws Exception {
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

            // find all of the object keys
            List<String> keys = new ArrayList<>();

            JsonArray hits = results.getJsonObject().getAsJsonObject("hits").getAsJsonArray("hits");
            for(int i = 0; i < hits.size(); i++) {
                JsonObject obj = hits.get(i).getAsJsonObject();
                keys.add(obj.get("_id").getAsString());
            }

            // get all of the objects
            List<Task> foundTasks = results.getSourceAsObjectList(Task.class);

            Map<String, Task> finalList = new HashMap<>();
            for(int i = 0; i < keys.size(); i++) {
                finalList.put(keys.get(i), foundTasks.get(i));
            }

            return finalList;
        }
        catch (Exception e) {
            logger.severe(e.getMessage());
            return new HashMap<>();
        }
    }

    @Override
    protected Map<String, Task> getFallback() {
        return new HashMap<>();
    }
}
