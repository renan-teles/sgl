package com.sgl.view.bookwindows;

import com.sgl.model.DAO.book.BookCategoryDAO;
import com.sgl.model.book.BookCategory;
import com.sgl.view.utilsswingwindow.UtilsSwingWindow;
import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class RegisterBookCategoryWindow extends JFrame{
    private JTextField txtCategory;
    private JButton btnRegisterBookCategory;
    
    public RegisterBookCategoryWindow(){
        setTitle("Cadastro de Categoria de Livro");
        setSize(350, 135);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        //MainPanel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Title
        mainPanel.add(UtilsSwingWindow.createPanelWithLabel("Categoria: "));
        txtCategory = new JTextField();
        mainPanel.add(txtCategory);
        
        mainPanel.add(Box.createVerticalStrut(15));

        // BTN Register Book
        JPanel panelBtnRegisterBook = new JPanel(new BorderLayout());
        btnRegisterBookCategory = new JButton("Cadastrar Categoria de Livro");
        btnRegisterBookCategory.addActionListener(e -> this.registerBookCategory());
        panelBtnRegisterBook.add(btnRegisterBookCategory, BorderLayout.EAST);
        
        mainPanel.add(panelBtnRegisterBook);
                
        add(mainPanel, BorderLayout.NORTH);
        setVisible(true);
    }
    
    public void registerBookCategory(){
        String category = txtCategory.getText().trim();

        if(category.isEmpty()){
            JOptionPane.showMessageDialog(this, "O campos categoria deve estar preenchidos corretamente!", "Mensagem de Alerta", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        BookCategory bookCategory = new BookCategory(category);
    
        BookCategoryDAO bookCategoryDAO = new BookCategoryDAO();
        if(bookCategoryDAO.checkExistence(bookCategory)){
            JOptionPane.showMessageDialog(this, "Categoria de livro já cadastrada no sistema.", "Mensagem de Erro", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if(!bookCategoryDAO.register(bookCategory)){
            JOptionPane.showMessageDialog(this, "Não foi possível cadastrar a categoria de livro.", "Mensagem de Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(this, "Categoria de livro cadastrada com sucesso!", "Mensagem de Sucesso", JOptionPane.INFORMATION_MESSAGE);
      
        txtCategory.setText("");
    }
}
