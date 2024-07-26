package com.bibliotheque.classes;

import java.sql.*;
import java.util.*;

import com.bibliotheque.DatabaseManager;

public class LibraryManager {
    private DatabaseManager dbManager;

    public LibraryManager() throws SQLException {
        dbManager = new DatabaseManager();
    }

    public void addBook(Book book) throws SQLException {
        String query = "INSERT INTO books (id, title, author, category) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = dbManager.getConnection().prepareStatement(query)) {
            stmt.setString(1, book.getId());
            stmt.setString(2, book.getTitle());
            stmt.setString(3, book.getAuthor());
            stmt.setString(4, book.getCategory());
            stmt.executeUpdate();
        }
    }

    public void removeBook(String id) throws SQLException {
        String query = "DELETE FROM books WHERE id = ?";
        try (PreparedStatement stmt = dbManager.getConnection().prepareStatement(query)) {
            stmt.setString(1, id);
            stmt.executeUpdate();
        }
    }

    public void updateBook(String id, String title, String author) throws SQLException {
        String query = "UPDATE books SET title = ?, author = ? WHERE id = ?";
        try (PreparedStatement stmt = dbManager.getConnection().prepareStatement(query)) {
            stmt.setString(1, title);
            stmt.setString(2, author);
            stmt.setString(3, id);
            stmt.executeUpdate();
        }
    }

    public Book searchBookByName(String name) throws SQLException {
        String query = "SELECT * FROM books WHERE title = ?";
        try (PreparedStatement stmt = dbManager.getConnection().prepareStatement(query)) {
            stmt.setString(1, name);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return createBookFromResultSet(rs);
                }
            }
        }
        return null;
    }

    public List<Book> listBooksByLetter(char letter) throws SQLException {
        String query = "SELECT * FROM books WHERE title LIKE ?";
        List<Book> result = new ArrayList<>();
        try (PreparedStatement stmt = dbManager.getConnection().prepareStatement(query)) {
            stmt.setString(1, letter + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    result.add(createBookFromResultSet(rs));
                }
            }
        }
        return result;
    }

    public int getBookCount() throws SQLException {
        String query = "SELECT COUNT(*) FROM books";
        try (Statement stmt = dbManager.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    public List<Book> getBooksByCategory(String category) throws SQLException {
        String query = "SELECT * FROM books WHERE category = ?";
        List<Book> result = new ArrayList<>();
        try (PreparedStatement stmt = dbManager.getConnection().prepareStatement(query)) {
            stmt.setString(1, category);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    result.add(createBookFromResultSet(rs));
                }
            }
        }
        return result;
    }

    public Book getBookById(String id) throws SQLException {
        String query = "SELECT * FROM books WHERE id = ?";
        try (PreparedStatement stmt = dbManager.getConnection().prepareStatement(query)) {
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return createBookFromResultSet(rs);
                }
            }
        }
        return null;
    }

    private Book createBookFromResultSet(ResultSet rs) throws SQLException {
        String id = rs.getString("id");
        String title = rs.getString("title");
        String author = rs.getString("author");
        String category = rs.getString("category");
        switch (category) {
            case "Novel":
                return new Novel(id, title, author);
            case "Science Fiction":
                return new ScienceFiction(id, title, author);
            case "Biography":
                return new Biography(id, title, author);
            default:
                throw new SQLException("Unknown category: " + category);
        }
    }

    // Custom Exception
    public static class BookNotFoundException extends Exception {
        public BookNotFoundException(String message) {
            super(message);
        }
    }

    public Book getBookByIdWithException(String id) throws BookNotFoundException, SQLException {
        Book book = getBookById(id);
        if (book == null) {
            throw new BookNotFoundException("Book with ID " + id + " not found.");
        }
        return book;
    }

    public void close() throws SQLException {
        dbManager.closeConnection();
    }
}
