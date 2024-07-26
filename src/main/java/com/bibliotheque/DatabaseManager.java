package com.bibliotheque;

import java.sql.*;

public class DatabaseManager {
    private static final String URL = "jdbc:mysql://localhost:3306/library";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    private Connection connection;

    public DatabaseManager() throws SQLException {
        connection = DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
