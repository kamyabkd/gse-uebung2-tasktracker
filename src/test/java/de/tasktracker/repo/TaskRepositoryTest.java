package de.tasktracker.repo;

import de.tasktracker.model.Task;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TaskRepositoryTest {

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

    @Test
    public void testGetAllTasksReturnsEmptyListWhenNoTasksExist() {
        TaskRepository repo = new InMemoryTaskRepository();

        List<Task> tasks = repo.getAllTasks();

        assertNotNull(tasks);
        assertTrue(tasks.isEmpty());
    }

    @Test
    public void testUpdateTaskSuccessfully() {
        TaskRepository repo = new InMemoryTaskRepository();

        Task original = new Task("Server reparieren", "Festplatte austauschen");
        Task saved = repo.add(original);
        int id = saved.getId();

        Task updated = new Task("Server reparieren", "Netzteil ersetzen");
        updated.setId(id);
        updated.setStatus("erledigt");

        Task result = repo.update(updated);

        assertEquals(id, result.getId());
        assertEquals("Server reparieren", result.getTitle());
        assertEquals("Netzteil ersetzen", result.getDescription());
        assertEquals("erledigt", result.getStatus());
    }

    @Test
    public void testUpdateTaskWithInvalidIdThrowsException() {
        TaskRepository repo = new InMemoryTaskRepository();

        Task updated = new Task("Dummy", "egal");
        updated.setId(999);

        assertThrows(IllegalArgumentException.class, () -> {
            repo.update(updated);
        });
    }

    @Test
    public void testDeleteTaskSuccessfully() {
        TaskRepository repo = new InMemoryTaskRepository();

        // Erst zwei Aufgaben anlegen
        Task t1 = new Task("Aufgabe 1", "eins");
        Task t2 = new Task("Aufgabe 2", "zwei");

        Task saved1 = repo.add(t1);
        Task saved2 = repo.add(t2);

        int idToDelete = saved1.getId();

        // Vor dem Löschen: 2 Aufgaben
        List<Task> before = repo.getAllTasks();
        assertEquals(2, before.size());

        // Löschen
        repo.deleteById(idToDelete);

        // Danach: nur noch 1 Aufgabe, und die gelöschte ist weg
        List<Task> after = repo.getAllTasks();
        assertEquals(1, after.size());
        assertTrue(after.stream().noneMatch(t -> t.getId() == idToDelete));
    }

    @Test
    public void testDeleteTaskWithInvalidIdThrowsException() {
        TaskRepository repo = new InMemoryTaskRepository();

        // keine Aufgabe mit ID 999 vorhanden
        assertThrows(IllegalArgumentException.class, () -> {
            repo.deleteById(999);
        });
    }

    @Test
    public void testGetOpenTasksReturnsOnlyOpenOnes() {
        TaskRepository repo = new InMemoryTaskRepository();

        Task t1 = new Task("Aufgabe 1", "eins");
        Task t2 = new Task("Aufgabe 2", "zwei");
        Task t3 = new Task("Aufgabe 3", "drei");

        // t1, t2: offen (Standard)
        repo.add(t1);
        repo.add(t2);

        // t3: erledigt
        t3.setStatus("erledigt");
        repo.add(t3);

        List<Task> openTasks = repo.getOpenTasks();

        assertEquals(2, openTasks.size());
        assertTrue(openTasks.stream().allMatch(t -> t.getStatus().equals("offen")));
    }

    @Test
    public void testGetOpenTasksReturnsEmptyListWhenNoOpenTasksExist() {
        TaskRepository repo = new InMemoryTaskRepository();

        Task t1 = new Task("Aufgabe 1", "eins");
        Task t2 = new Task("Aufgabe 2", "zwei");

        t1.setStatus("erledigt");
        t2.setStatus("erledigt");

        repo.add(t1);
        repo.add(t2);

        List<Task> openTasks = repo.getOpenTasks();

        assertNotNull(openTasks);
        assertTrue(openTasks.isEmpty());
    }





}
