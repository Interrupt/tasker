package com.interrupt.tasks.model;

import com.interrupt.elasticsearch.commands.DeleteCommand;
import com.interrupt.elasticsearch.commands.IndexCommand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TasksManager {
    // in memory cache of tasks
    private HashMap<String, Task> tasks = new HashMap<String, Task>();

    public TasksManager() { }

    public HashMap<String, Task> getAll() {
        return tasks;
    }

    public Task get(String key) {
        return tasks.get(key);
    }

    public boolean remove(String key) {
        // remove the search index
        new DeleteCommand(key).queue();

        return tasks.remove(key) != null;
    }

    public Task create(String key, Task task) {
        tasks.put(key, task);

        // index the task for searching
        new IndexCommand(key, task).queue();

        return task;
    }
}