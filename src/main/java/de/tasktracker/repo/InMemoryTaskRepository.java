package de.tasktracker.repo;

import de.tasktracker.model.Task;
import java.util.ArrayList;
import java.util.List;

public class InMemoryTaskRepository implements TaskRepository {

    private final List<Task> tasks = new ArrayList<>();
    private int nextId = 1;

    @Override
    public Task add(Task task) {

        if (task.getTitle() == null || task.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Title darf nicht leer sein");
        }

        task.setId(nextId++);
        tasks.add(task);
        return task;
    }

    @Override
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks);
    }
}
