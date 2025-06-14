package com.sgl.model.DAO.book;

import com.sgl.model.DAO.DAOClass;
import com.sgl.model.DAO.databaseconnection.DatabaseConnection;
import com.sgl.model.book.Book;
import com.sgl.model.book.BookCategory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BookDAO extends DAOClass<Book>{
    
    @Override
    public boolean register(Book book) {
        super.setSqlStatement (
            "INSERT INTO books (id_book_category, title, author, description, total_quantity) VALUES (?, ?, ?, ?, ?)"
        );
        
        try (Connection conn = DatabaseConnection.connect(); 
                PreparedStatement stmt = conn.prepareStatement(super.getSqlStatement())) {
            
            stmt.setInt(1, book.getCategoryId());
            stmt.setString(2, book.getTitle());
            stmt.setString(3, book.getAuthor());
            stmt.setString(4, book.getDescription());
            stmt.setInt(5, book.getTotalQuantity());
    
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }
    
    @Override
    public boolean edit (Book book){        
        super.setSqlStatement (
            "UPDATE books SET id_book_category = ?, title = ?, author = ?, description = ?, total_quantity = ? WHERE id = ?"
        );
        
        try (Connection conn = DatabaseConnection.connect(); 
                PreparedStatement stmt = conn.prepareStatement(super.getSqlStatement())) {
            
            stmt.setInt(1, book.getCategoryId());
            stmt.setString(2, book.getTitle());
            stmt.setString(3, book.getAuthor());
            stmt.setString(4, book.getDescription());
            stmt.setInt(5, book.getTotalQuantity());
            stmt.setInt(6, book.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }
    
    @Override
    public boolean delete (Book book){
        super.setSqlStatement ("DELETE FROM books WHERE id = ?");
        
        try (Connection conn = DatabaseConnection.connect(); 
                PreparedStatement stmt = conn.prepareStatement(super.getSqlStatement())) {
            
            stmt.setInt(1, book.getId());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }
    
    @Override
    public List<Book> search(Book book) {
        List<Book> booksList = new ArrayList<>();

        super.setSqlStatement(
            "SELECT b.id, bc.category, b.title, b.author, b.description, b.total_quantity FROM books b INNER JOIN book_categories bc ON b.id_book_category = bc.id WHERE title = ? AND author = ?"
        );

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(super.getSqlStatement())) {

            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    BookCategory bookCategory = new BookCategory(rs.getString("category"));

                    Book bookDB = new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        bookCategory,
                        rs.getString("description"),
                        rs.getInt("total_quantity")
                    );

                    booksList.add(bookDB);
                }
            }
        } catch (SQLException e) {
            return booksList;
        }

        return booksList;
    }
    
    @Override
    public List<Book> searchAll(){
        List<Book> booksList = new ArrayList<>();
        
        super.setSqlStatement (
             "SELECT b.id, bc.category, b.title, b.author, b.description, b.total_quantity FROM books b INNER JOIN book_categories bc ON b.id_book_category = bc.id;"
        );

        try (Connection conn = DatabaseConnection.connect(); 
                Statement stmt = conn.createStatement(); 
                    ResultSet rs = stmt.executeQuery(super.getSqlStatement())) {
            
            while (rs.next()) {
                BookCategory bookCategory = new BookCategory(rs.getString("category"));
                
                Book book = new Book(
                        rs.getString("title"), 
                        rs.getString("author"),
                        bookCategory,
                        rs.getString("description"),
                        rs.getInt("total_quantity")
                );
                
                book.setId(rs.getInt("id"));
                
                booksList.add(book);
            }
            
        } catch (SQLException e) {
            return booksList; 
        }
        
        return booksList;  
    }

    public boolean checkExistence (Book book){
        List<Book> bookList;
        bookList = this.search(book); 
        return !bookList.isEmpty();
    }
}