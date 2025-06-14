package com.sgl.model.book;

public class BookCategory {
    private int id;
    private String category;

    public BookCategory(int id, String category) {
        this.id = id;
        this.category = category;
    }
    
    public BookCategory(String category) {
        this.category = category;
    }
    
    public BookCategory(){}
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "BookCategory {" + "\n id: " + id + ", \n category: " + category + "\n" + '}';
    }
}
