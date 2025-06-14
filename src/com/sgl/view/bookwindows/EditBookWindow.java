package com.sgl.view.bookwindows;

import javax.swing.JFrame;
import com.sgl.model.DAO.book.BookDAO;
import com.sgl.model.book.Book;
import com.sgl.view.utilsswingwindow.UtilsSwingWindow;
import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class EditBookWindow extends JFrame{
    private JTextField txtTitle, txtAuthor;
    private JTextArea txtDescription;
    private JComboBox<String> comboCategory;
    private JSpinner spinnerTotalQuantity;
    private JButton btnEditBook;
    private Book book;
    private Map<String, Integer> bookCategoriesMap;
    
    public EditBookWindow(Book book) {
        this.book = book;
        
        setTitle("Edição de Livro");
        setSize(500, 375);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
         
        //Main Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Title
        mainPanel.add(UtilsSwingWindow.createPanelWithLabel("Título: "));
        txtTitle = new JTextField(this.book.getTitle());
        mainPanel.add(txtTitle);
        
        mainPanel.add(Box.createVerticalStrut(10)); 

        // Campo Autor
        mainPanel.add(UtilsSwingWindow.createPanelWithLabel("Autor: "));
        txtAuthor = new JTextField(this.book.getAuthor());
        mainPanel.add(txtAuthor);
        
        mainPanel.add(Box.createVerticalStrut(10));

        // Campo Categoria
        mainPanel.add(UtilsSwingWindow.createPanelWithLabel("Categoria: "));
        bookCategoriesMap = UtilsSwingWindow.getBookCategoriesMap();
        comboCategory = new JComboBox<>(bookCategoriesMap.keySet().toArray(new String[0]));
        comboCategory.setSelectedItem(this.book.getCategory());
        mainPanel.add(comboCategory);
        
        mainPanel.add(Box.createVerticalStrut(10));

        // Campo Descrição
        mainPanel.add(UtilsSwingWindow.createPanelWithLabel("Descrição: "));
        txtDescription = new JTextArea(this.book.getDescription(), 4, 30);
        txtDescription.setLineWrap(true);
        txtDescription.setWrapStyleWord(true);
        JScrollPane scroll = new JScrollPane(txtDescription);
        mainPanel.add(scroll);
        
        mainPanel.add(Box.createVerticalStrut(10));

        // Total Quantity
        mainPanel.add(UtilsSwingWindow.createPanelWithLabel("Quantidade Total: "));
        spinnerTotalQuantity = new JSpinner(new SpinnerNumberModel(1, 1, 10000, 1));
        spinnerTotalQuantity.setValue(this.book.getTotalQuantity());
        UtilsSwingWindow.leftAlignValueJSpinner(spinnerTotalQuantity);
        mainPanel.add(spinnerTotalQuantity);
        
        mainPanel.add(Box.createVerticalStrut(15));

        // BTN Edit Book
        JPanel panelBtnEditBook = new JPanel(new BorderLayout());
        btnEditBook = new JButton("Editar Livro");
        btnEditBook.addActionListener(e -> this.editBook());
        panelBtnEditBook.add(btnEditBook, BorderLayout.EAST);
        
        mainPanel.add(panelBtnEditBook);

        add(mainPanel, BorderLayout.NORTH);
        setVisible(true);
    }
       
    private void editBook() {
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
        
        this.book.setTitle(title);
        this.book.setAuthor(author);  
        this.book.setCategoryId(categoryId);
        this.book.setDescription(description);
        this.book.setTotalQuantity(totalQuantity);
        
        BookDAO bookDAO = new BookDAO();
        if(!bookDAO.edit(this.book)){
            JOptionPane.showMessageDialog(this, "Não foi possível cadastrar o livro.", "Mensagem de Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        JOptionPane.showMessageDialog(this, "Livro editado com sucesso!", "Mensagem de Sucesso", JOptionPane.INFORMATION_MESSAGE);
    
        this.setVisible(false);
    }
}
