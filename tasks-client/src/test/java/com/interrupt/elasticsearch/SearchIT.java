package com.interrupt.elasticsearch;

import com.interrupt.tasks.model.Task;
import io.searchbox.client.JestResult;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class SearchIT {

    @Test
    public void shouldIndexTask() {
        SearchClient client = new SearchClient();
        client.Index("testkey", new Task("Test Search Title", "Test Search Body"));
    }

    @Test
    public void shouldExecuteSearch() {
        SearchClient client = new SearchClient();
        JestResult hits = client.Search("Search");

        assertNotNull(hits);
    }
}
