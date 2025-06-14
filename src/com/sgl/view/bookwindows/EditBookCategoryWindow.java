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

public class EditBookCategoryWindow extends JFrame{
    private BookCategory bookCategory;
    private JTextField txtCategory;
    private JButton btnEditBookCategory;
    
    public EditBookCategoryWindow(BookCategory bookCategory){
         this.bookCategory = bookCategory;
        
        setTitle("Edição de Categoria de Livro");
        setSize(350, 135);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
         
        //Main Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Category
        mainPanel.add(UtilsSwingWindow.createPanelWithLabel("Categoria: "));
        txtCategory = new JTextField(this.bookCategory.getCategory());
        mainPanel.add(txtCategory);

        mainPanel.add(Box.createVerticalStrut(15));

        // BTN Edit Book
        JPanel panelBtnEditBookCategory = new JPanel(new BorderLayout());
        btnEditBookCategory = new JButton("Editar Livro");
        btnEditBookCategory.addActionListener(e -> this.editBookCategory());
        panelBtnEditBookCategory.add(btnEditBookCategory, BorderLayout.EAST);
        
        mainPanel.add(panelBtnEditBookCategory);

        add(mainPanel, BorderLayout.NORTH);
        setVisible(true);
    }
    
    public void editBookCategory(){
        String category = txtCategory.getText().trim();
        
        if(category.isEmpty()){
            JOptionPane.showMessageDialog(this, "Todos os campos devem estar preenchidos corretamente!", "Mensagem de Alerta", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        this.bookCategory.setCategory(category);

        BookCategoryDAO bookCategoryDAO = new BookCategoryDAO();
        if(!bookCategoryDAO.edit(this.bookCategory)){
            JOptionPane.showMessageDialog(this, "Não foi possível editar a categoria de livro.", "Mensagem de Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(this, "Categoria de livro editada com sucesso!", "Mensagem de Sucesso", JOptionPane.INFORMATION_MESSAGE);
    
        this.setVisible(false);
    }
}
