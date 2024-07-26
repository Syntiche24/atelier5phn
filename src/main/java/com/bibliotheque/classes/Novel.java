package com.bibliotheque.classes;

public class Novel extends Book {
    public Novel(String id, String title, String author) {
        super(id, title, author);
    }

    @Override
    public String getCategory() {
        return "Novel";
    }
}
