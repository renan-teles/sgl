package com.sgl.view.bookwindows;

import com.sgl.model.DAO.book.BookDAO;
import com.sgl.model.book.Book;
import com.sgl.model.book.BookCategory;
import com.sgl.view.utilsswingwindow.UtilsSwingWindow;
import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class RegisterBookWindow extends JFrame {
    private  JTextField txtTitle, txtAuthor;
    private  JTextArea txtDescription;
    private  JComboBox<String> comboCategory;
    private  JSpinner spinnerTotalQuantity;
    private  JButton btnRegisterBook;   
    private Map<String, Integer> bookCategoriesMap;
    
    public RegisterBookWindow() {
        setTitle("Cadastro de Livro");
        setSize(500, 375);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        //MainPanel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Title
        mainPanel.add(UtilsSwingWindow.createPanelWithLabel("Título: "));
        txtTitle = new JTextField();
        mainPanel.add(txtTitle);
        
        mainPanel.add(Box.createVerticalStrut(10)); 

        // Author
        mainPanel.add(UtilsSwingWindow.createPanelWithLabel("Autor: "));
        txtAuthor = new JTextField();
        mainPanel.add(txtAuthor);
        
        mainPanel.add(Box.createVerticalStrut(10));
      
        // Categoty
        mainPanel.add(UtilsSwingWindow.createPanelWithLabel("Categoria: "));
        bookCategoriesMap = UtilsSwingWindow.getBookCategoriesMap();
        comboCategory = new JComboBox<>(bookCategoriesMap.keySet().toArray(new String[0]));
        mainPanel.add(comboCategory);
        
        mainPanel.add(Box.createVerticalStrut(10));

        // Description
        mainPanel.add(UtilsSwingWindow.createPanelWithLabel("Descrição: "));
        txtDescription = new JTextArea(4, 30);
        txtDescription.setLineWrap(true);
        txtDescription.setWrapStyleWord(true);
        JScrollPane scroll = new JScrollPane(txtDescription);
        mainPanel.add(scroll);
        
        mainPanel.add(Box.createVerticalStrut(10));

        // Total Quantity
        mainPanel.add(UtilsSwingWindow.createPanelWithLabel("Quantidade Total: "));
        spinnerTotalQuantity = new JSpinner(new SpinnerNumberModel(1, 1, 10000, 1));
        UtilsSwingWindow.leftAlignValueJSpinner(spinnerTotalQuantity);
        
        mainPanel.add(spinnerTotalQuantity);
        
        mainPanel.add(Box.createVerticalStrut(15));

        // BTN Register Book
        JPanel panelBtnRegisterBook = new JPanel(new BorderLayout());
        btnRegisterBook = new JButton("Cadastrar Livro");
        btnRegisterBook.addActionListener(e -> this.registerBook());
        panelBtnRegisterBook.add(btnRegisterBook, BorderLayout.EAST);
        
        mainPanel.add(panelBtnRegisterBook);

        add(mainPanel, BorderLayout.NORTH);
        setVisible(true);
        
        if(bookCategoriesMap.isEmpty()){
            btnRegisterBook.setVisible(false);
            JOptionPane.showMessageDialog(null, "Não é possível cadastrar livros sem que existam categorias previamente cadastradas!", "Mensagem de Alerta", JOptionPane.WARNING_MESSAGE);
         }
    }
        
    private void registerBook() {
        String title = txtTitle.getText().trim();
        String author = txtAuthor.getText().trim();
        String category =  comboCategory.getSelectedItem().toString();
        int categoryId = bookCategoriesMap.get(category);
        String description = txtDescription.getText().trim();
        int totalQuantity = (Integer) spinnerTotalQuantity.getValue();
        
        if(title.isEmpty() || author.isEmpty() || description.isEmpty()){
            JOptionPane.showMessageDialog(this, "Todos os campos devem estar preenchidos corretamente!", "Mensagem de Alerta", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        BookCategory bookCategory = new BookCategory(categoryId, category);
        Book book = new Book(title, author, bookCategory,description, totalQuantity);

        BookDAO bookDAO = new BookDAO();
        if(bookDAO.checkExistence(book)){
            JOptionPane.showMessageDialog(this, "Livro já cadastrado no sistema.", "Mensagem de Erro", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if(!bookDAO.register(book)){
            JOptionPane.showMessageDialog(this, "Não foi possível cadastrar o livro.", "Mensagem de Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(this, "Livro cadastrado com sucesso!", "Mensagem de Sucesso", JOptionPane.INFORMATION_MESSAGE);
      
        this.cleanForm();
    }
    
    private void cleanForm(){
        txtTitle.setText("");
        txtAuthor.setText("");
        txtAuthor.setText("");
        txtDescription.setText("");
        spinnerTotalQuantity.setValue(1);
    }
}