package de.tasktracker.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL =
            "jdbc:sqlserver://localhost\\SQLEXPRESS;databaseName=tasktracker;encrypt=true;trustServerCertificate=true";

    private static final String USER = "";
    private static final String PASS = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
