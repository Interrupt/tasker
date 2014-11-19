package com.interrupt.elasticsearch.commands;

import com.interrupt.elasticsearch.SearchClient;
import com.interrupt.tasks.model.Task;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;

import java.util.logging.Logger;

/**
 * Command to index a given task for searching
 */
public class IndexCommand extends HystrixCommand<Boolean> {

    final private Logger logger = Logger.getLogger(IndexCommand.class.getName());
    private JestClient client;
    private Task task;
    private String key;

    public IndexCommand(String key, Task task) {
        super(HystrixCommandGroupKey.Factory.asKey("Search"));
        this.key = key;
        this.task = task;
        client = SearchClient.getClient();
    }

    @Override
    protected Boolean run() throws Exception {
        try {
            Index index = new Index.Builder(task).index("tasks").type("task").id(key).build();
            client.execute(index);
        }
        catch (Exception e) {
            logger.severe(e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    protected Boolean getFallback() {
        return false;
    }
}
