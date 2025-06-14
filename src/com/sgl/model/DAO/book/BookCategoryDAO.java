package com.sgl.model.DAO.book;

import com.sgl.model.DAO.DAOClass;
import com.sgl.model.DAO.databaseconnection.DatabaseConnection;
import com.sgl.model.book.BookCategory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BookCategoryDAO extends DAOClass<BookCategory>{
    
    @Override
    public boolean register(BookCategory bookCategory) {
        super.setSqlStatement (
            "INSERT INTO book_categories (category) VALUES (?)"
        );

        try (Connection conn = DatabaseConnection.connect(); 
                PreparedStatement stmt = conn.prepareStatement(super.getSqlStatement())) {
            
            stmt.setString(1, bookCategory.getCategory());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        } 
    }
    
    @Override
    public boolean edit (BookCategory bookCategory){
        super.setSqlStatement(
            "UPDATE book_categories SET category = ? WHERE id = ?"
        );
            
        try (Connection conn = DatabaseConnection.connect(); 
                PreparedStatement stmt = conn.prepareStatement(super.getSqlStatement())) {
            
            stmt.setString(1, bookCategory.getCategory());
            stmt.setInt(2, bookCategory.getId());
          
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }
    
    @Override
    public boolean delete (BookCategory bookCategory){
        super.setSqlStatement(
            "DELETE FROM book_categories WHERE id = ?"
        );

        try (Connection conn = DatabaseConnection.connect(); 
                PreparedStatement stmt = conn.prepareStatement(super.getSqlStatement())) {
           
            stmt.setInt(1, bookCategory.getId());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }
    
    @Override
    public List<BookCategory> search(BookCategory bookCategory) {
        List<BookCategory> booksCategoryList = new ArrayList<>();

        super.setSqlStatement(
            "SELECT * FROM book_categories WHERE category = ?"
        );

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(super.getSqlStatement())) {

            stmt.setString(1, bookCategory.getCategory());

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    BookCategory bookCategoryDB = new BookCategory(
                        rs.getInt("id"),
                        rs.getString("category")
                    );

                    booksCategoryList.add(bookCategoryDB);
                    }
                }
            } catch (SQLException e) {
                return booksCategoryList;
            }

        return booksCategoryList;
    }
    
    @Override
    public List<BookCategory> searchAll(){
        List<BookCategory> bookCategoriesList = new ArrayList<>();
        
        super.setSqlStatement("SELECT * FROM book_categories ORDER BY category");

        try (Connection conn = DatabaseConnection.connect(); 
                Statement stmt = conn.createStatement(); 
                    ResultSet rs = stmt.executeQuery(super.getSqlStatement())) {
            
            while (rs.next()) {
                bookCategoriesList.add (
                        new BookCategory(rs.getInt("id"), rs.getString("category"))
                );
            }
            
        } catch (SQLException e) {
            return bookCategoriesList; 
        }
        
        return bookCategoriesList;  
    }
    
   public boolean checkExistence (BookCategory bookCategory){
        List<BookCategory> bookCategoryList;
        bookCategoryList = this.search(bookCategory); 
        return !bookCategoryList.isEmpty();
    }
}
