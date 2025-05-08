/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntregableDos;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 *
 * @author Freddy Elias
 */
public class BinarioSubMenu extends JFrame {

    private ArbolBinario arbol;
    private JTextField campoNumero;
    private JPanel panelDibujo;

    public BinarioSubMenu() {
        arbol = new ArbolBinario();
        configurarVentana();
        inicializarComponentes();
    }

    private void configurarVentana() {
        setTitle("Árbol AVL");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(240, 240, 240));
    }

    private void inicializarComponentes() {
        // Panel superior con controles
        JPanel panelControles = new JPanel(new GridBagLayout());
        panelControles.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelControles.setBackground(new Color(240, 240, 240));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Campo para ingresar número
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        panelControles.add(new JLabel("Ingrese el numero que desea agregar:"), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        campoNumero = new JTextField(10);
        panelControles.add(campoNumero, gbc);

        // Botones
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        panelBotones.setBackground(new Color(240, 240, 240));

        JButton botonGuardar = crearBoton("Guardar", this::accionGuardar);
        JButton botonInsertar = crearBoton("Insertar", this::accionInsertar);
        JButton botonEliminar = crearBoton("Eliminar", this::accionEliminar);
        JButton botonVolver = crearBoton("Volver", this::accionVolver);

        panelBotones.add(botonGuardar);
        panelBotones.add(botonInsertar);
        panelBotones.add(botonEliminar);
        panelBotones.add(botonVolver);

        panelControles.add(panelBotones, gbc);

        add(panelControles, BorderLayout.NORTH);

        // Panel de dibujo del árbol
        panelDibujo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Fondo del panel
                g2d.setColor(Color.WHITE);
                g2d.fillRect(0, 0, getWidth(), getHeight());

                // Dibujar el árbol
                dibujarArbol(g2d, getWidth() / 2, 30, 150, arbol.getRaiz());
            }
        };

        panelDibujo.setBorder(BorderFactory.createTitledBorder("Representación gráfica"));
        panelDibujo.setPreferredSize(new Dimension(500, 300));

        add(new JScrollPane(panelDibujo), BorderLayout.CENTER);
    }

    private JButton crearBoton(String texto, java.awt.event.ActionListener accion) {
        JButton boton = new JButton(texto);
        boton.addActionListener(accion);
        boton.setPreferredSize(new Dimension(90, 30));
        boton.setBackground(new Color(70, 130, 180));
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        return boton;
    }

    private void dibujarArbol(Graphics g, int x, int y, int separacionX, NodoAVL nodo) {
        if (nodo == null) {
            return;
        }

        int diametro = 30;
        // Dibujar el círculo del nodo
        g.setColor(new Color(173, 216, 230)); // Azul claro
        g.fillOval(x - diametro / 2, y - diametro / 2, diametro, diametro);
        g.setColor(Color.BLACK);
        g.drawOval(x - diametro / 2, y - diametro / 2, diametro, diametro);

        // Dibujar el valor del nodo
        FontMetrics fm = g.getFontMetrics();
        String texto = String.valueOf(nodo.clave);
        int textoX = x - fm.stringWidth(texto) / 2;
        int textoY = y + fm.getAscent() / 2;
        g.drawString(texto, textoX, textoY);

        // Dibujar conexiones con los hijos
        if (nodo.izquierdo != null) {
            int hijoX = x - separacionX;
            int hijoY = y + 60;
            g.drawLine(x, y + diametro / 2, hijoX, hijoY - diametro / 2);
            dibujarArbol(g, hijoX, hijoY, separacionX / 2, nodo.izquierdo);
        }

        if (nodo.derecho != null) {
            int hijoX = x + separacionX;
            int hijoY = y + 60;
            g.drawLine(x, y + diametro / 2, hijoX, hijoY - diametro / 2);
            dibujarArbol(g, hijoX, hijoY, separacionX / 2, nodo.derecho);
        }
    }

    private void accionInsertar(ActionEvent e) {
        try {
            int valor = Integer.parseInt(campoNumero.getText());
            arbol.insertar(valor);
            campoNumero.setText("");
            panelDibujo.repaint();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese un número válido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void accionEliminar(ActionEvent e) {
        try {
            int valor = Integer.parseInt(campoNumero.getText());
            arbol.eliminar(valor);
            campoNumero.setText("");
            panelDibujo.repaint();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese un número válido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void accionGuardar(ActionEvent e) {
        JFileChooser selectorArchivo = new JFileChooser();
        if (selectorArchivo.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            arbol.guardarEnArchivo(selectorArchivo.getSelectedFile().getAbsolutePath());
        }
    }

    private void accionVolver(ActionEvent e) {
        new ArbolesGUI().setVisible(true);
        this.dispose();
    }
}
