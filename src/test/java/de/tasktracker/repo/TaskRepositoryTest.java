package de.tasktracker.repo;

import de.tasktracker.model.Task;
import org.junit.jupiter.api.Test;
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
}
