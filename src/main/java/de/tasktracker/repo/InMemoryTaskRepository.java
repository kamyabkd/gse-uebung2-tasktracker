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

    @Override
    public Task update(Task task) {
        for (Task existing : tasks) {
            if (existing.getId() == task.getId()) {
                if (task.getTitle() != null && !task.getTitle().trim().isEmpty()) {
                    existing.setTitle(task.getTitle());
                }
                if (task.getDescription() != null) {
                    existing.setDescription(task.getDescription());
                }
                if (task.getStatus() != null) {
                    existing.setStatus(task.getStatus());
                }
                return existing;
            }
        }

        throw new IllegalArgumentException("Aufgabe nicht gefunden");
    }
}


