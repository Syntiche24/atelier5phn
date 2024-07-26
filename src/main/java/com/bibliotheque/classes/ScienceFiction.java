package com.bibliotheque.classes;

public class ScienceFiction extends Book {
    public ScienceFiction(String id, String title, String author) {
        super(id, title, author);
    }

    @Override
    public String getCategory() {
        return "Science Fiction";
    }
}
