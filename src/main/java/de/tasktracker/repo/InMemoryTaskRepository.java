package de.tasktracker.repo;

import de.tasktracker.model.Task;
import java.util.ArrayList;
import java.util.List;

public class InMemoryTaskRepository implements TaskRepository {

    private final List<Task> tasks = new ArrayList<>();
    private int nextId = 1;


    private void validateTitle(Task task) {
        if (task.getTitle() == null || task.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Title darf nicht leer sein");
        }
    }


    @Override
    public Task add(Task task) {

        validateTitle(task);

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

                if (task.getTitle() != null) {
                    validateTitle(task);
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

    @Override
    public void deleteById(int id) {
        // passende Aufgabe suchen
        Task toDelete = null;
        for (Task existing : tasks) {
            if (existing.getId() == id) {
                toDelete = existing;
                break;
            }
        }

        if (toDelete == null) {
            throw new IllegalArgumentException("Aufgabe nicht gefunden");
        }

        tasks.remove(toDelete);
    }
}




