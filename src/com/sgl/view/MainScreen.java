package com.sgl.view;

import com.sgl.model.DAO.book.BookCategoryDAO;
import com.sgl.view.bookwindows.EditBookWindow;
import com.sgl.view.bookwindows.RegisterBookWindow;
import com.sgl.model.DAO.book.BookDAO;
import com.sgl.model.book.Book;
import com.sgl.model.book.BookCategory;
import com.sgl.view.bookwindows.EditBookCategoryWindow;
import com.sgl.view.bookwindows.RegisterBookCategoryWindow;
import java.awt.BorderLayout;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class MainScreen extends JFrame {
    private JButton btnOpenWindowRegisterBook, btnUpdateTableBooks, btnDeleteBook,  btnOpenWindowEditBook, 
            btnOpenWindowRegisterBookCategory, btnUpdateTableBookCategory, btnOpenWindowEditBookCategory,
            btnDeleteBookCategory;
    private DefaultTableModel tableBooksModel, tableBookCategoriesModel;
    private JTable tableBooks, tableBookCategories;
    private Book book = new Book(new BookCategory());
    private BookCategory bookCategory = new BookCategory();
    private BookDAO bookDAO = new BookDAO();
    private BookCategoryDAO bookCategoryDAO = new BookCategoryDAO();
    
     public MainScreen(){
        setTitle("Sistema de Gestão de Livros");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Tab Component
        JTabbedPane tabbedPane = new JTabbedPane();
    
        //Books
        // Main Panel Books
        JPanel mainPanelBooks = new JPanel(new BorderLayout(10,10));
        mainPanelBooks.setBorder(BorderFactory.createTitledBorder("Gestão de Livros"));
       
        //Btns
        btnOpenWindowRegisterBook = new JButton("Cadastrar Livro");
        btnOpenWindowRegisterBook.addActionListener(e -> {
            RegisterBookWindow janelaCadastro = new RegisterBookWindow();
            janelaCadastro.setVisible(true);
        });
        
        btnUpdateTableBooks = new JButton("Atualizar Tabela");
        btnUpdateTableBooks.addActionListener(e -> { 
            btnOpenWindowEditBook.setVisible(false); 
            btnDeleteBook.setVisible(false);
            showBooks(true);
        });
        
        btnOpenWindowEditBook = new JButton("Editar Livro");
        btnOpenWindowEditBook.addActionListener(e -> {
            if(this.book.getId() <= 0) return;
            EditBookWindow janelaEditar = new EditBookWindow(this.book);
            janelaEditar.setVisible(true);
        });
        btnOpenWindowEditBook.setVisible(false);
        
        btnDeleteBook = new JButton("Excluir Livro");
        btnDeleteBook.addActionListener(e -> {
            if(this.book.getId() <= 0) return;
            int option = JOptionPane.showConfirmDialog(null, "Deseja realmente EXCLUIR este livro?");
            if(option == 0) this.deleteBook();
        });
        btnDeleteBook.setVisible(false);

         //Panel Header Panel Books
        JPanel panelHeaderBooks = new JPanel(new BorderLayout(10,10));
         JPanel panelBtnUpdateBook = new JPanel();
         panelBtnUpdateBook.add(btnUpdateTableBooks);
         
         panelHeaderBooks.add(panelBtnUpdateBook, BorderLayout.WEST);
         
        JPanel panelBtnsHeaderBooks = new JPanel();
        panelBtnsHeaderBooks.add(btnDeleteBook);
        panelBtnsHeaderBooks.add(btnOpenWindowEditBook);
        panelBtnsHeaderBooks.add(btnOpenWindowRegisterBook);
        panelHeaderBooks.add(panelBtnsHeaderBooks, BorderLayout.EAST);
     
        mainPanelBooks.add(panelHeaderBooks, BorderLayout.NORTH);
     
        //Table Books Model
         tableBooksModel = new DefaultTableModel (
                 new String[]{"ID", "Título", "Autor", "Categoria", "Descrição", "Quantidade Total"}, 0
         ) {
            @Override
            public boolean isCellEditable(int row, int column){ return false; }
        };
          
        //Table Books
        tableBooks = new JTable(tableBooksModel);
        tableBooks.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollTableBooks = new JScrollPane(tableBooks);
        scrollTableBooks.setBorder(BorderFactory.createTitledBorder("Livros Cadastrados"));
        
        mainPanelBooks.add(scrollTableBooks, BorderLayout.CENTER);
        
        //Table Selection Event
        tableBooks.getSelectionModel().addListSelectionListener(e -> {
            if(!e.getValueIsAdjusting() && tableBooks.getSelectedRow() != -1){
                 int linha = tableBooks.getSelectedRow();
                 book.setId((int) tableBooksModel.getValueAt(linha,0));
                 book.setTitle((String) tableBooksModel.getValueAt(linha, 1));
                 book.setAuthor((String) tableBooksModel.getValueAt(linha, 2));
                 book.setCategory((String) tableBooksModel.getValueAt(linha, 3));
                 book.setDescription((String) tableBooksModel.getValueAt(linha, 4));
                 book.setTotalQuantity((int) tableBooksModel.getValueAt(linha, 5));
                 btnOpenWindowEditBook.setVisible(true);
                 btnDeleteBook.setVisible(true);
             }
        });     
       
        showBooks(false);
       
        //Book Categories
        // Main Panel BookCategory
        JPanel mainPanelBookCategory = new JPanel(new BorderLayout(10,10));
        mainPanelBookCategory.setBorder(BorderFactory.createTitledBorder("Gestão de Categorias de Livros"));
       
        //Btns
        btnOpenWindowRegisterBookCategory = new JButton("Cadastrar Categoria");
        btnOpenWindowRegisterBookCategory.addActionListener(e -> {
            RegisterBookCategoryWindow windowRegisterCategory = new RegisterBookCategoryWindow();
            windowRegisterCategory.setVisible(true);
        });
        
        btnUpdateTableBookCategory = new JButton("Atualizar Tabela");
        btnUpdateTableBookCategory.addActionListener(e -> { 
            btnOpenWindowEditBookCategory.setVisible(false); 
            btnDeleteBookCategory.setVisible(false);
            showBookCategories(true);
        });
        
        btnOpenWindowEditBookCategory = new JButton("Editar Categoria");
        btnOpenWindowEditBookCategory.addActionListener(e -> {
            if(this.bookCategory.getId() <= 0) return;
            EditBookCategoryWindow windowEditCategory = new EditBookCategoryWindow(this.bookCategory);
            windowEditCategory.setVisible(true);
        });
        btnOpenWindowEditBookCategory.setVisible(false);
        
        btnDeleteBookCategory = new JButton("Excluir Categoria");
        btnDeleteBookCategory.addActionListener(e -> {
            if(this.bookCategory.getId() <= 0) return;
            int option = JOptionPane.showConfirmDialog(null, "Deseja realmente EXCLUIR esta categoria? Todos os livros associados a ela também serão deletados.");
            if(option == 0) this.deleteBookCategory();
        });
        btnDeleteBookCategory.setVisible(false);

         //Panel Header Panel Book Category
        JPanel panelHeaderBookCategory = new JPanel(new BorderLayout(10,10));
        JPanel panelBtnUpdateBookCategory = new JPanel();
        panelBtnUpdateBookCategory.add(btnUpdateTableBookCategory);
         
        panelHeaderBookCategory.add(panelBtnUpdateBookCategory, BorderLayout.WEST);
         
        JPanel panelBtnsHeaderBookCategory= new JPanel();
        panelBtnsHeaderBookCategory.add(btnDeleteBookCategory);
        panelBtnsHeaderBookCategory.add(btnOpenWindowEditBookCategory);
        panelBtnsHeaderBookCategory.add(btnOpenWindowRegisterBookCategory);
        panelHeaderBookCategory.add(panelBtnsHeaderBookCategory, BorderLayout.EAST);
     
        mainPanelBookCategory.add(panelHeaderBookCategory, BorderLayout.NORTH);
     
        //Table Book Category Model
         tableBookCategoriesModel = new DefaultTableModel (new String[]{"ID", "Categoria"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column){ return false; }
        };
          
        //Table Book Categories
        tableBookCategories = new JTable(tableBookCategoriesModel);
        tableBookCategories.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollTableBookCategories = new JScrollPane(tableBookCategories);
        scrollTableBookCategories.setBorder(BorderFactory.createTitledBorder("Categorias de Livros Cadastradas"));
        
        mainPanelBookCategory.add(scrollTableBookCategories, BorderLayout.CENTER);
        
        //Table Selection Event
        tableBookCategories.getSelectionModel().addListSelectionListener(e -> {
            if(!e.getValueIsAdjusting() && tableBookCategories.getSelectedRow() != -1){
                 int linha = tableBookCategories.getSelectedRow();
                 bookCategory.setId((int) tableBookCategoriesModel.getValueAt(linha,0));
                 bookCategory.setCategory((String) tableBookCategoriesModel.getValueAt(linha, 1));
              
                 btnOpenWindowEditBookCategory.setVisible(true);
                 btnDeleteBookCategory.setVisible(true);
             }
        });
        
       showBookCategories(false);
                
        tabbedPane.addTab("Livros", mainPanelBooks);
        tabbedPane.addTab("Categorias de Livros", mainPanelBookCategory);
        
        add(tabbedPane);
        setVisible(true);
     }
     
    private void showBooks(boolean showMessage) {
        tableBooksModel.setRowCount(0);

        List<Book> booksList = bookDAO.searchAll();
        if (booksList.isEmpty() && showMessage) {
            JOptionPane.showMessageDialog(this, "Nenhum livro encontrado.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        for (Book book : booksList) {
            tableBooksModel.addRow(new Object[]{
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getCategory(),
                book.getDescription(),
                book.getTotalQuantity()
            });
        }
    }
     
    private void deleteBook() {
         if(this.book.getId() <= 0) return;    
         
         if(!bookDAO.delete(this.book)){
            JOptionPane.showMessageDialog(this, "Não foi possível deletar o livro.", "Mensagem de Erro", JOptionPane.ERROR_MESSAGE);
            return;
         }
         
         JOptionPane.showMessageDialog(this, "Livro excluido com sucesso!", "Mensagem de Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void showBookCategories(boolean showMessage){
        tableBookCategoriesModel.setRowCount(0);

        List<BookCategory> booksCategoryList = bookCategoryDAO.searchAll();
        if (booksCategoryList.isEmpty() && showMessage) {
            JOptionPane.showMessageDialog(this, "Nenhuma categoria de livro encontrada.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        for (BookCategory category : booksCategoryList) {
            tableBookCategoriesModel.addRow(new Object[]{
                category.getId(),
                category.getCategory()
            });
        }
    }
    
    private void deleteBookCategory(){
        if(this.bookCategory.getId() <= 0) return;    
         
         if(!bookCategoryDAO.delete(this.bookCategory)){
            JOptionPane.showMessageDialog(this, "Não foi possível deletar a categoria.", "Mensagem de Erro", JOptionPane.ERROR_MESSAGE);
            return;
         }
         
         JOptionPane.showMessageDialog(this, "Categoria excluida com sucesso!", "Mensagem de Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }
    
     public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainScreen());
    }
}
