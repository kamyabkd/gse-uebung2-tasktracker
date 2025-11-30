package de.tasktracker.repo;

import de.tasktracker.db.DatabaseConnection;
import de.tasktracker.model.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlTaskRepository implements TaskRepository {

    @Override
    public Task add(Task task) {
        validateTitle(task.getTitle());

        if (task.getStatus() == null) {
            task.setStatus("offen");
        }

        String sql = "INSERT INTO tasks (title, description, status) VALUES (?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, task.getTitle());
            stmt.setString(2, task.getDescription());
            stmt.setString(3, task.getStatus());

            stmt.executeUpdate();

            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    task.setId(keys.getInt(1));
                }
            }

            return task;

        } catch (SQLException e) {
            throw new RuntimeException("Database INSERT failed", e);
        }
    }

    @Override
    public List<Task> getAllTasks() {
        String sql = "SELECT id, title, description, status FROM tasks";
        List<Task> result = new ArrayList<>();

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                result.add(mapRowToTask(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Database SELECT all failed", e);
        }

        return result;
    }

    @Override
    public Task update(Task task) {

        if (task.getTitle() != null) {
            validateTitle(task.getTitle());
        }

        Task existing = findTaskByIdOrThrow(task.getId());

        if (task.getTitle() != null) {
            existing.setTitle(task.getTitle());
        }
        if (task.getDescription() != null) {
            existing.setDescription(task.getDescription());
        }
        if (task.getStatus() != null) {
            existing.setStatus(task.getStatus());
        }

        String sql = "UPDATE tasks SET title = ?, description = ?, status = ? WHERE id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, existing.getTitle());
            stmt.setString(2, existing.getDescription());
            stmt.setString(3, existing.getStatus());
            stmt.setInt(4, existing.getId());

            int rows = stmt.executeUpdate();
            if (rows == 0) {
                throw new IllegalArgumentException("Aufgabe nicht gefunden");
            }

            return existing;

        } catch (SQLException e) {
            throw new RuntimeException("Database UPDATE failed", e);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM tasks WHERE id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            if (rows == 0) {
                throw new IllegalArgumentException("Aufgabe nicht gefunden");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Database DELETE failed", e);
        }
    }

    @Override
    public List<Task> getOpenTasks() {
        String sql = "SELECT id, title, description, status FROM tasks WHERE status = 'offen'";
        List<Task> result = new ArrayList<>();

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                result.add(mapRowToTask(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Database SELECT open tasks failed", e);
        }

        return result;
    }


    private Task mapRowToTask(ResultSet rs) throws SQLException {
        Task t = new Task(
                rs.getString("title"),
                rs.getString("description")
        );
        t.setId(rs.getInt("id"));
        t.setStatus(rs.getString("status"));
        return t;
    }

    private Task findTaskByIdOrThrow(int id) {
        String sql = "SELECT id, title, description, status FROM tasks WHERE id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToTask(rs);
                }
            }

            throw new IllegalArgumentException("Aufgabe nicht gefunden");

        } catch (SQLException e) {
            throw new RuntimeException("Database SELECT by id failed", e);
        }
    }

    private void validateTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title darf nicht leer sein");
        }
    }
}
