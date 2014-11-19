package com.interrupt.elasticsearch.commands;

import com.interrupt.elasticsearch.SearchClient;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import io.searchbox.client.JestClient;
import io.searchbox.core.Delete;

import java.util.logging.Logger;

/**
 * Command to remove a task from search by key
 */
public class DeleteCommand extends HystrixCommand<Boolean> {

    final private Logger logger = Logger.getLogger(DeleteCommand.class.getName());
    private JestClient client;
    private String key;

    public DeleteCommand(String key) {
        super(HystrixCommandGroupKey.Factory.asKey("Search"));
        this.key = key;
        client = SearchClient.getClient();
    }

    @Override
    protected Boolean run() throws Exception {
        try {
            Delete delete = new Delete.Builder(key).index("tasks").build();
            client.execute(delete);
        }
        catch(Exception e) {
            logger.severe(e.getMessage());
        }

        return true;
    }

    @Override
    protected Boolean getFallback() {
        return false;
    }
}
