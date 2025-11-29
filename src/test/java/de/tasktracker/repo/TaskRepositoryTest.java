package de.tasktracker.repo;

import de.tasktracker.model.Task;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class
TaskRepositoryTest {

    @Test
    public void testAddTaskSuccessfully() {
        TaskRepository repo = new InMemoryTaskRepository();

        Task newTask = new Task("Server reparieren", "Festplatte austauschen");
        Task saved = repo.add(newTask);

        assertTrue(saved.getId() > 0);
        assertEquals("Server reparieren", saved.getTitle());
        assertEquals("offen", saved.getStatus());
    }

    @Test
    public void testAddTaskWithEmptyTitleThrowsException() {
        TaskRepository repo = new InMemoryTaskRepository();

        Task newTask = new Task("", "Beschreibung egal");

        assertThrows(IllegalArgumentException.class, () -> {
            repo.add(newTask);
        });
    }

    @Test
    public void testGetAllTasksReturnsAllSavedTasks() {
        TaskRepository repo = new InMemoryTaskRepository();

        Task t1 = new Task("Aufgabe 1", "eins");
        Task t2 = new Task("Aufgabe 2", "zwei");

        repo.add(t1);
        repo.add(t2);

        List<Task> tasks = repo.getAllTasks();

        assertEquals(2, tasks.size());
        assertTrue(tasks.stream().anyMatch(t -> t.getTitle().equals("Aufgabe 1")));
        assertTrue(tasks.stream().anyMatch(t -> t.getTitle().equals("Aufgabe 2")));
    }


}
