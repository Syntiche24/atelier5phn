package com.bibliotheque.classes;

public class Biography extends Book {
    public Biography(String id, String title, String author) {
        super(id, title, author);
    }

    @Override
    public String getCategory() {
        return "Biography";
    }
}
