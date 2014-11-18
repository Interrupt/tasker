package com.interrupt.tasks.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TasksManager {
    private HashMap<String, Task> tasks = new HashMap<String, Task>();

    public TasksManager() {
        create("task1", new Task("Hello World", "This is the first task!"));
        create("task2", new Task("Task Two", "And this is the second task."));
    }

    public HashMap<String, Task> getAll() {
        return tasks;
    }

    public Task get(String key) {
        return tasks.get(key);
    }

    public boolean remove(String key) {
        return tasks.remove(key) != null;
    }

    public Task create(String key, Task task) {
        tasks.put(key, task);
        return task;
    }
}