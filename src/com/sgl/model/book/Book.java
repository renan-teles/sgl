package com.sgl.model.book;

public class Book {
    private int id, total_quantity;
    private String title, author, description;
    private BookCategory category;

     public Book(int id, String title, String author, BookCategory category, String description, int total_quantity) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.description = description;
        this.total_quantity = total_quantity;
    }
    
    public Book(String title, String author, BookCategory category, String description, int total_quantity) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.description = description;
        this.total_quantity = total_quantity;
    }
    
    public Book(BookCategory category) {
        this.category = category;
    }
    
    public Book(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category.getCategory();
    }

    public void setCategory(String category) {
        this.category.setCategory(category);
    }

    public int getCategoryId() {
        return category.getId();
    }

    public void setCategoryId(int id) {
        category.setId(id);
    }
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTotalQuantity() {
        return total_quantity;
    }

    public void setTotalQuantity(int total_quantity) {
        this.total_quantity = total_quantity;
    }

    @Override
    public String toString() {
        return "Book {" + "\n id: " + id + ",\n title:" + title + ", \n author: " + author + ", \n category: " + category.getCategory() + ", \n description: " + description + ", \n total_quantity: " + total_quantity + "\n"+ '}';
    }
}
