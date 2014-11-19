package com.interrupt.elasticsearch;

import io.searchbox.client.JestResult;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class SearchIT {
    @Test
    public void shouldSetupClient() {
        SearchClient client = new SearchClient();
        JestResult hits = client.Search("Test");

        assertNotNull(hits);
    }
}
