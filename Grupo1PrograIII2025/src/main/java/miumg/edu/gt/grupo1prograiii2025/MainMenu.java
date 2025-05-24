package miumg.edu.gt.grupo1prograiii2025;

import EntregableDos.ArbolesGUI;
import EntregableTres.WorstFitGUI;
import EntregableUno.HojaCalculoGUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Menú principal del sistema con conexión a base de datos
 *
 * @author Freddy Elias
 */
public class MainMenu extends JFrame {

    private final Connection conexion;
    private JButton btnArboles, btnHojaCalculo, btnMemoria, btnSalir;

    public MainMenu(Connection conexion) {
        this.conexion = conexion;
        initComponents();
        configurarVentana();
        configurarEventosCierre();
    }

    private void initComponents() {
        // Panel principal con diseño BorderLayout
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 20));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        // Título centrado
        JLabel lblTitulo = new JLabel("Sistema de Estructuras de Datos", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);

        // Panel central con GridBagLayout para mejor control
        JPanel panelCentral = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Botón para módulo de árboles
        btnArboles = crearBoton("Árboles Binarios/AVL", e -> abrirModuloArboles());
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelCentral.add(btnArboles, gbc);

        // Botón para Ajustes de memoria
        btnMemoria = crearBoton("Ajustes de Memoria", e -> abrirAjustesMemoria());
        gbc.gridy = 2;
        panelCentral.add(btnMemoria, gbc);

        // Botón para hoja de cálculo
        btnHojaCalculo = crearBoton("Hoja de Cálculo", e -> abrirHojaCalculo());
        gbc.gridy = 1;
        panelCentral.add(btnHojaCalculo, gbc);

        // Botón para salir
        btnSalir = crearBoton("Salir del Sistema", e -> cerrarAplicacion());
        gbc.gridy = 3;
        panelCentral.add(btnSalir, gbc);

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
        setTitle("Menú Principal - Grupo 1 Programación III");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); // Se maneja en windowListener

        // Establecer icono
    }

    private void configurarEventosCierre() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                cerrarAplicacion();
            }
        });
    }

    private void abrirModuloArboles() {
        try {
            validarConexionActiva();
            new ArbolesGUI(conexion).setVisible(true);
            this.dispose();
        } catch (SQLException ex) {
            mostrarError("Error al acceder al módulo de Árboles", ex);
        }
    }

    private void abrirAjustesMemoria() {
        try {
            validarConexionActiva();

            // Pedir al usuario el tamaño de memoria
            String input = JOptionPane.showInputDialog(
                    this,
                    "Ingrese el tamaño total de memoria para el simulador:",
                    "Configuración del Simulador",
                    JOptionPane.QUESTION_MESSAGE
            );

            try {
                int tamaño = Integer.parseInt(input);
                if (tamaño <= 0) {
                    throw new NumberFormatException();
                }

                // Crear y mostrar el simulador
                new WorstFitGUI(tamaño, this).setVisible(true);
                this.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "Debe ingresar un número válido mayor que 0",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        } catch (SQLException ex) {
            mostrarError("Error al acceder al módulo de Ajustes de Memoria", ex);
        }

    }

    private void abrirHojaCalculo() {
        try {
            new HojaCalculoGUI().setVisible(true);
            this.dispose();
        } catch (Exception ex) {
            mostrarError("Error al abrir Hoja de Cálculo", ex);
        }
    }

    private void cerrarAplicacion() {
        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro que desea salir del sistema?",
                "Confirmar Salida",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            cerrarConexion();
            System.exit(0);
        }
    }

    private void validarConexionActiva() throws SQLException {
        if (conexion == null || conexion.isClosed()) {
            throw new SQLException("No hay conexión activa con la base de datos");
        }
    }

    private void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
            }
        } catch (SQLException ex) {
            System.err.println("Error al cerrar conexión: " + ex.getMessage());
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
