package de.tasktracker.repo;

import de.tasktracker.model.Task;
import java.util.List;

public interface TaskRepository {

    /**
     * Speichert eine neue Aufgabe.
     * @param task Aufgabe ohne ID
     * @return gespeicherte Aufgabe mit gesetzter ID
     */
    Task add(Task task);

    /**
     * Gibt alle gespeicherten Aufgaben zurück.
     */
    List<Task> getAllTasks();

    /**
     * Aktualisiert eine bestehende Aufgabe.
     * @param task Aufgabe mit gültiger ID
     * @return aktualisierte Aufgabe
     * @throws IllegalArgumentException wenn keine Aufgabe mit dieser ID existiert
     */
    Task update(Task task);

    /**
     * Löscht eine Aufgabe mit der gegebenen ID.
     * @param id ID der zu löschenden Aufgabe
     * @throws IllegalArgumentException wenn keine Aufgabe mit dieser ID existiert
     */
    void deleteById(int id);

    /**
     * Gibt alle Aufgaben mit Status "offen" zurück.
     */
    List<Task> getOpenTasks();

}
