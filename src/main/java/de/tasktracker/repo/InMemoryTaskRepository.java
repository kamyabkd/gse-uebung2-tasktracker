package de.tasktracker.repo;

import de.tasktracker.model.Task;
import java.util.ArrayList;
import java.util.List;

public class InMemoryTaskRepository implements TaskRepository {

    private final List<Task> tasks = new ArrayList<>();
    private int nextId = 1;

    @Override
    public Task add(Task task) {
        task.setId(nextId++);
        tasks.add(task);
        return task;
    }
}
