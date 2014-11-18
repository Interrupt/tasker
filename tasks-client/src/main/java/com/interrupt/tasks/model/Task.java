package com.interrupt.tasks.model;

public class Task {
    private String title = "";
    private String body = "";
    private Boolean done = false;

    // empty constructor needed for json serializing
    public Task() { }

    public Task(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    private void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    private void setBody(String body) {
        this.body = body;
    }

    public Boolean getDone() {
        return done;
    }

    private void setDone(Boolean done) {
        this.done = done;
    }
}
