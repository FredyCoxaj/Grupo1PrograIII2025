package EntregableDos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import miumg.edu.gt.grupo1prograiii2025.MainMenu;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class AVLSubMenu extends JFrame {

    public AVLSubMenu() {
        initComponents();
        configurarVentana();
    }

    private void initComponents() {
        // Panel principal
        JPanel panel = new JPanel(new BorderLayout(10, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título
        JLabel lblTitulo = new JLabel("Operaciones AVL");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(lblTitulo, BorderLayout.NORTH);

        // Botones
        JPanel panelBotones = new JPanel(new GridLayout(3, 1, 10, 15));

        JButton btnInsertar = crearBoton("Insertar Nodo", Color.ORANGE);
        JButton btnEliminar = crearBoton("Eliminar Nodo", Color.GREEN);
        JButton btnVolver = crearBoton("Volver al Menú Principal", Color.RED);
        btnVolver.addActionListener(e -> volverMenuPrincipal());

        panelBotones.add(btnInsertar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnVolver);

        panel.add(panelBotones, BorderLayout.CENTER);

        // Pie de página
        JLabel lblPie = new JLabel("Seleccione una operación");
        lblPie.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(lblPie, BorderLayout.SOUTH);

        this.add(panel);
    }

    private JButton crearBoton(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.BOLD, 16));
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setPreferredSize(new Dimension(200, 50));
        return boton;
    }

    private void configurarVentana() {
        setTitle("Submenú AVL");
        setSize(500, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }
    
    private void volverMenuPrincipal() {
        new MainMenu().setVisible(true);
        this.dispose();
    }
}