/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntregableDos;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.*;
import miumg.edu.gt.grupo1prograiii2025.MainMenu;

/**
 *
 * @author Freddy Elias
 */
public class ArbolesGUI extends JFrame {
    private final Connection conexion;
    private JButton btnBinario, btnAVL, btnVolver;

    public ArbolesGUI(Connection conexion) {
        this.conexion = conexion;
        initComponents();
        configurarVentana();
    }

    private void initComponents() {
        // Panel principal con borde y espaciado
        JPanel panel = new JPanel(new BorderLayout(10, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título centrado
        JLabel lblTitulo = new JLabel("Operaciones con Árboles", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        panel.add(lblTitulo, BorderLayout.NORTH);

        // Panel de botones con grid layout
        JPanel panelBotones = new JPanel(new GridLayout(3, 1, 15, 15));
        
        // Botón para árbol binario
        btnBinario = crearBotonEstilado("Árbol Binario", e -> abrirBinario());
        
        // Botón para árbol AVL
        btnAVL = crearBotonEstilado("Árbol AVL", e -> abrirAVL());
        
        // Botón para volver
        btnVolver = crearBotonEstilado("Volver al Menú", e -> volverMenuPrincipal());

        panelBotones.add(btnBinario);
        panelBotones.add(btnAVL);
        panelBotones.add(btnVolver);

        panel.add(panelBotones, BorderLayout.CENTER);

        // Pie de página
        JLabel lblPie = new JLabel("Seleccione una operación", SwingConstants.CENTER);
        lblPie.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        panel.add(lblPie, BorderLayout.SOUTH);

        add(panel);
    }

    private JButton crearBotonEstilado(String texto, ActionListener listener) {
        JButton boton = new JButton(texto);
        boton.addActionListener(listener);
        boton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        boton.setBackground(new Color(70, 130, 180));
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setPreferredSize(new Dimension(200, 50));
        return boton;
    }

    private void configurarVentana() {
        setTitle("Gestión de Árboles - Universidad Mariano Gálvez");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
    }

    private void abrirBinario() {
        try {
            BinarioSubMenu binarioMenu = new BinarioSubMenu(conexion);
            binarioMenu.setVisible(true);
            this.dispose();
        } catch (Exception e) {
            mostrarError("Error al abrir Árbol Binario", e);
        }
    }

    private void abrirAVL() {
        try {
            if (conexion == null || conexion.isClosed()) {
                throw new SQLException("No hay conexión activa a la BD");
            }
            AVLSubMenu avlMenu = new AVLSubMenu(conexion);
            avlMenu.setVisible(true);
            this.dispose();
        } catch (SQLException e) {
            mostrarError("Error de conexión con la BD", e);
        } catch (Exception e) {
            mostrarError("Error al abrir Árbol AVL", e);
        }
    }

    private void volverMenuPrincipal() {
        try {
            MainMenu mainMenu = new MainMenu(conexion);
            mainMenu.setVisible(true);
            this.dispose();
        } catch (Exception e) {
            mostrarError("Error al volver al menú", e);
        }
    }

    private void mostrarError(String mensaje, Exception e) {
        JOptionPane.showMessageDialog(
            this,
            mensaje + ":\n" + e.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE
        );
        e.printStackTrace();
    }

    public static void main(String[] args) {
        // Solo para pruebas - No usar en producción
        SwingUtilities.invokeLater(() -> {
            try {
                Connection testConn = DatabaseConnection.getConnection();
                new ArbolesGUI(testConn).setVisible(true);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, 
                    "Error de conexión: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}