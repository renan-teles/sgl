package com.sgl.model.DAO.databaseconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class DatabaseConnection {
    public static Connection connect() {
        Connection conn = null;
        
        try {
            String url = "jdbc:mysql://localhost:3306/sgl";
            String user = "root";
            String password = "";
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Falha ao realizar conexão com o banco de dados: " + e.getMessage(), "Mensagem de Erro", JOptionPane.ERROR_MESSAGE);
        }
        
        return conn;
    }
}
