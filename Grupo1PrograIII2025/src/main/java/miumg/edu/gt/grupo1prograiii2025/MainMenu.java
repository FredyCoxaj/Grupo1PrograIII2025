package miumg.edu.gt.grupo1prograiii2025;

import EntregableUno.HojaCalculoGUI;
import EntregableDos.AVLSubMenu;
import EntregableDos.ArbolesGUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class MainMenu extends JFrame {

    public MainMenu() {
        initComponents();
        configurarVentana();
    }

    private void initComponents() {
        // Panel principal
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Título
        JLabel lblTitulo = new JLabel("Programacion III - Grupo 1");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(lblTitulo, BorderLayout.NORTH);

        // Descripción
        JLabel txtDescripcion = new JLabel("Seleccione el módulo que desea utilizar:");
        txtDescripcion.setFont(new Font("Arial", Font.PLAIN, 16));
        txtDescripcion.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(txtDescripcion, BorderLayout.CENTER);

        // Panel de botones
        JPanel panelBotones = new JPanel(new GridLayout(2, 1, 10, 15));

        JButton btnHojaCalculo = new JButton("Hoja de Cálculo");
        btnHojaCalculo.setFont(new Font("Arial", Font.BOLD, 18));
        btnHojaCalculo.addActionListener(this::abrirHojaCalculoGUI);

        JButton btnAVL = new JButton("Árboles AVL");
        btnAVL.setFont(new Font("Arial", Font.BOLD, 18));
        btnAVL.addActionListener(this::abrirArbolesGUI);

        panelBotones.add(btnHojaCalculo);
        panelBotones.add(btnAVL);

        JPanel panelCentro = new JPanel();
        panelCentro.add(panelBotones);
        panel.add(panelCentro, BorderLayout.SOUTH);

        this.add(panel);
    }

    private void configurarVentana() {
        setTitle("Menú Principal");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void abrirArbolesGUI(ActionEvent evt) {
        new ArbolesGUI().setVisible(true);
        this.dispose();
    }

    private void abrirHojaCalculoGUI(ActionEvent evt) {
        new HojaCalculoGUI().setVisible(true);
        this.dispose();
    }
}