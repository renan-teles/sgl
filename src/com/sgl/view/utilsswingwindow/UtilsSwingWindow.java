package com.sgl.view.utilsswingwindow;

import com.sgl.model.DAO.book.BookCategoryDAO;
import com.sgl.model.book.BookCategory;
import java.awt.BorderLayout;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SwingConstants;

public class UtilsSwingWindow {
     public static JPanel createPanelWithLabel (String text) {
        JPanel painelLabel = new JPanel(new BorderLayout());
        painelLabel.add(new JLabel(text), BorderLayout.WEST);
        return painelLabel;
    }
     
    public static void leftAlignValueJSpinner (JSpinner spinner){
        JComponent editor = spinner.getEditor();
        if (editor instanceof JSpinner.DefaultEditor) {
            JFormattedTextField txt = ((JSpinner.DefaultEditor) editor).getTextField();
            txt.setHorizontalAlignment(SwingConstants.LEFT);
        }
    }
     
    public static Map<String, Integer> getBookCategoriesMap(){
        BookCategoryDAO bookCategoryDAO = new BookCategoryDAO();
        List<BookCategory> bookCategoryList = bookCategoryDAO.searchAll();

        Map<String, Integer> categories = new HashMap<>();        
        for (BookCategory category : bookCategoryList) {
            categories.put(category.getCategory(), category.getId());
        }

        return categories;
    }
    
    
}
