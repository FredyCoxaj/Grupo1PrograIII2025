/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntregableTres;

import EntregableDos.DatabaseConnection;
import EntregableUno.HojaCalculoGUI;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import miumg.edu.gt.grupo1prograiii2025.MainMenu;

/**
 *
 * @author Freddy Elias
 */
public class MenuAjustes extends JFrame {

    private JButton btnWorstFit, btnBestFit, btnFirstFit, btnVolver;
    private JFrame parentWindow;  // Referencia a la ventana padre

    // Constructor modificado para recibir la ventana padre
    public MenuAjustes(JFrame parentWindow) {
        this.parentWindow = parentWindow;
        initComponents();
        configurarVentana();
    }

    private void initComponents() {
        // Panel principal con diseño BorderLayout
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 20));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        // Título centrado
        JLabel lblTitulo = new JLabel("Sistema de Ajustes de Memoria", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);

        // Panel central con GridBagLayout para mejor control
        JPanel panelCentral = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Botones para los diferentes algoritmos
        btnWorstFit = crearBoton("Worst Fit", e -> abrirWorstFit());
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelCentral.add(btnWorstFit, gbc);

        btnBestFit = crearBoton("Best Fit", e -> abrirBestFit());
        gbc.gridy = 1;
        panelCentral.add(btnBestFit, gbc);

        btnFirstFit = crearBoton("First Fit", e -> abrirFirstFit());
        gbc.gridy = 2;
        panelCentral.add(btnFirstFit, gbc);

        btnVolver = crearBoton("Volver", e -> accionVolver());
        gbc.gridy = 3;
        panelCentral.add(btnVolver, gbc);

        panelPrincipal.add(panelCentral, BorderLayout.CENTER);

        // Pie de página
        JLabel lblVersion = new JLabel("Versión 1.0 - © 2025 Universidad Mariano Gálvez", SwingConstants.CENTER);
        lblVersion.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        panelPrincipal.add(lblVersion, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    private JButton crearBoton(String texto, ActionListener listener) {
        JButton boton = new JButton(texto);
        boton.addActionListener(listener);
        boton.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        boton.setBackground(new Color(70, 130, 180));
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setPreferredSize(new Dimension(250, 50));
        return boton;
    }

    private void configurarVentana() {
        setTitle("Menú de Ajustes de Memoria");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                accionVolver();
            }
        });
    }

    private void abrirWorstFit() {
        try {
            // Pedir tamaño de memoria al usuario
            String input = JOptionPane.showInputDialog(
                    this,
                    "Ingrese el tamaño total de memoria:",
                    "Configuración Worst Fit",
                    JOptionPane.QUESTION_MESSAGE
            );

            if (input != null) { // Si el usuario no cancela
                int tamaño = Integer.parseInt(input);
                if (tamaño > 0) {
                    new WorstFitGUI(tamaño, this).setVisible(true);
                    this.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            "Debe ingresar un número mayor que 0",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        } catch (NumberFormatException ex) {
            mostrarError("Debe ingresar un número válido", ex);
        } catch (Exception ex) {
            mostrarError("Error al abrir Worst Fit", ex);
        }
    }

        private void abrirBestFit() {
            try {
                String input = JOptionPane.showInputDialog(
                        this,
                        "Ingrese el tamaño total de memoria:",
                        "Configuración Best Fit",
                        JOptionPane.QUESTION_MESSAGE
                );

                if (input != null) {
                    int tamaño = Integer.parseInt(input);
                    if (tamaño > 0) {
                        new BestFitGUI(tamaño, this).setVisible(true);
                        this.setVisible(false);
                    } else {
                        JOptionPane.showMessageDialog(
                                this,
                                "Debe ingresar un número mayor que 0",
                                "Error",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                }
            } catch (NumberFormatException ex) {
                mostrarError("Debe ingresar un número válido", ex);
            } catch (Exception ex) {
                mostrarError("Error al abrir Best Fit", ex);
            }
        }

    private void abrirFirstFit() {
    try {
        // Pedir tamaño de memoria al usuario
        String input = JOptionPane.showInputDialog(
                this,
                "Ingrese el tamaño total de memoria:",
                "Configuración First Fit",
                JOptionPane.QUESTION_MESSAGE
        );

        if (input != null) { // Si el usuario no cancela
            int tamaño = Integer.parseInt(input);
            if (tamaño > 0) {
                new FirstFitGUI(tamaño, this).setVisible(true);
                this.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Debe ingresar un número mayor que 0",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    } catch (NumberFormatException ex) {
        mostrarError("Debe ingresar un número válido", ex);
    } catch (Exception ex) {
        mostrarError("Error al abrir First Fit", ex);
    }
}

    private void accionVolver() {
        try {
            Connection conexion = DatabaseConnection.getConnection();
            new MainMenu(conexion).setVisible(true);
            this.dispose();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                "Error al conectar a la base de datos: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarError(String mensaje, Exception ex) {
        JOptionPane.showMessageDialog(
                this,
                mensaje + ":\n" + ex.getMessage(),
                "Error del Sistema",
                JOptionPane.ERROR_MESSAGE
        );
        ex.printStackTrace();
    }
}
