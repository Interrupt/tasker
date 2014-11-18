package com.interrupt.tasks.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class TasksManagerTest {

    TasksManager taskManager;

    @Before
    public void init() {
        taskManager = new TasksManager();
    }

    @Test
    public void shouldCreateTasks() {
        for(int i = 0; i < 5; i++) {
            final String key = "testkey" + i;
            final String title = "Task " + i;
            final String body = "Item number " + i;

            Task created = taskManager.create(key, new Task(title, body));

            assertNotNull(created);
            assertEquals(created.getTitle(), title);
            assertEquals(created.getBody(), body);
        }
    }

    @Test
    public void shouldRetrieveTasks() {
        // create a bunch of tasks
        for(int i = 0; i < 5; i++) {
            final String key = "testkey" + i;
            final String title = "Task " + i;
            final String body = "Item number " + i;
            taskManager.create(key, new Task(title, body));
        }

        // now see if they exist
        for(int i = 0; i < 5; i++) {
            final String key = "testkey" + i;
            final String title = "Task " + i;
            final String body = "Item number " + i;
            Task found = taskManager.get(key);

            assertNotNull(found);
            assertEquals(found.getTitle(), title);
            assertEquals(found.getBody(), body);
        }
    }

    @Test
    public void shouldGetAllTasks() {
        // create a bunch of tasks
        for(int i = 0; i < 5; i++) {
            final String key = "testkey" + i;
            final String title = "Task " + i;
            final String body = "Item number " + i;
            taskManager.create(key, new Task(title, body));
        }

        HashMap<String, Task> tasks = taskManager.getAll();
        assertEquals(tasks.size(), 5);

        // now make sure they all exist
        for(int i = 0; i < 5; i++) {
            final String key = "testkey" + i;
            final String title = "Task " + i;
            final String body = "Item number " + i;

            Task found = tasks.get(key);
            assertNotNull(found);
            assertEquals(found.getTitle(), title);
            assertEquals(found.getBody(), body);
        }
    }

    @Test
    public void shouldDeleteTasks() {
        final String key = "test-task";

        taskManager.create(key, new Task("Hello", "World"));
        assertNotNull(taskManager.get(key));

        taskManager.remove(key);

        assertNull(taskManager.get(key));
    }
}
