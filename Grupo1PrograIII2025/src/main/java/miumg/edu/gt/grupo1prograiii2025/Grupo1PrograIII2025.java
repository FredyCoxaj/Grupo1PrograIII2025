/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package miumg.edu.gt.grupo1prograiii2025;

import EntregableDos.DatabaseConnection;
import javax.swing.SwingUtilities;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Freddy Elias
 */
public class Grupo1PrograIII2025 {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                Connection conexion = DatabaseConnection.getConnection();
                new MainMenu(conexion).setVisible(true);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, 
                    "Error al conectar a la base de datos: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
