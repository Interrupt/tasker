package com.interrupt.elasticsearch;

import com.interrupt.tasks.model.Task;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SearchIT {

    @Test
    public void shouldIndexTask() {
        SearchClient client = new SearchClient();
        client.index("testkey", new Task("Test Search Title", "Test Search Body 2"));
    }

    @Test
    public void shouldExecuteSearch() {
        SearchClient client = new SearchClient();
        List<Task> found = client.search("Search");

        assertNotNull(found);
        assertEquals(1, found.size());
    }

    @Test
    public void shouldDeleteIndex() throws Exception {
        SearchClient client = new SearchClient();
        client.deleteIndex("testkey");

        List<Task> found = client.search("Search");
        assertNotNull(found);
        assertEquals(0, found.size());
    }
}
