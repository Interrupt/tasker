tasker
======

Test REST API using Java / Jersey, runs an embedded Grizzly web server.

Starts up on http://localhost:8080/tasker

### Get all tasks
<code>GET</code> /tasks

### Get one task
<code>GET</code> /tasks/{key}

### Create a new task
<code>POST</code> /tasks/{key}

<code>
{
"body": "Another Body",
"title": "Hello World"
}
</code>

### Update a task
<code>PUT</code> /tasks/{key}

<code>
{
"body": "Another Body",
"title": "Hello World",
"done": true
}
</code>

### Delete a task
<code>DELETE</code> /tasks/{key}

