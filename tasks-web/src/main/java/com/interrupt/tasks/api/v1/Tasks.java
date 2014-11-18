package com.interrupt.tasks.api.v1;

import com.interrupt.tasks.model.Task;
import com.interrupt.tasks.model.TasksManager;
import com.interrupt.twilio.TwilioClient;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;

@Path("tasks")
public class Tasks {

    private static TasksManager manager = new TasksManager();
    private static TwilioClient twilioClient = new TwilioClient();

    static {
        manager.create("task1", new Task("Hello World", "This is the first task!"));
        manager.create("task2", new Task("Task Two", "And this is the second task."));
    }

    // Return a list of all of the tasks
    @GET @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public HashMap<String, Task> getAllTasks() {
        return manager.getAll();
    }

    // Get one task, by their key
    @GET @Path("/{key}")
    @Produces(MediaType.APPLICATION_JSON)
    public Task getTask(@PathParam("key") String key) {
       return manager.get(key);
    }

    // Create a new task
    @POST @Path("/{key}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Task createTask(@PathParam("key") String key, Task task) {
        if(manager.get(key) != null) throw new RuntimeException("Cannot create task, it already exists");
        return manager.create(key, task);
    }

    // Update details for a task
    @PUT @Path("/{key}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Task updateTask(@PathParam("key") String key, Task task) {
        Task existing = manager.get(key);
        if(existing == null) throw new RuntimeException("Cannot update task, it does not exist");

        // send the finished notification when the task changes to done
        if(!existing.getDone() && task.getDone()) {
            twilioClient.sendText("7017213796", String.format("\"%s\" task has been marked as done.", task.getTitle()));
        }

        return manager.create(key, task);
    }

    // Delete a task
    @DELETE @Path("/{key}")
    @Produces(MediaType.APPLICATION_JSON)
    public Boolean deleteTask(@PathParam("key") String key) {
        if(manager.get(key) == null) throw new RuntimeException("Cannot delete task, it does not exist");
        return manager.remove(key);
    }
}