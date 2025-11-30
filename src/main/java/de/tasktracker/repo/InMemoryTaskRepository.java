package de.tasktracker.repo;

import de.tasktracker.model.Task;
import java.util.ArrayList;
import java.util.List;

public class InMemoryTaskRepository implements TaskRepository {

    private final List<Task> tasks = new ArrayList<>();
    private int nextId = 1;

    @Override
    public Task add(Task task) {

        validateTitle(task.getTitle());

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
        Task existing = findTaskByIdOrThrow(task.getId());

        if (task.getTitle() != null) {
            validateTitle(task.getTitle());
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

    @Override
    public void deleteById(int id) {
        Task toDelete = findTaskByIdOrThrow(id);
        tasks.remove(toDelete);
    }


    private Task findTaskByIdOrThrow(int id) {
        for (Task t : tasks) {
            if (t.getId() == id) {
                return t;
            }
        }
        throw new IllegalArgumentException("Aufgabe nicht gefunden");
    }

    private void validateTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title darf nicht leer sein");
        }
    }
}

