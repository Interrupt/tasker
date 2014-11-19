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
        client.Index("testkey", new Task("Test Search Title", "Test Search Body"));
    }

    @Test
    public void shouldExecuteSearch() {
        SearchClient client = new SearchClient();
        List<Task> found = client.Search("Search");

        assertNotNull(found);
        assertEquals(found.size(), 1);
    }
}
