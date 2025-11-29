package de.tasktracker.repo;

import de.tasktracker.model.Task;

public interface TaskRepository {

    /**
     * Speichert eine neue Aufgabe.
     * @param task Aufgabe ohne ID
     * @return gespeicherte Aufgabe mit gesetzter ID
     */
    Task add(Task task);
}
