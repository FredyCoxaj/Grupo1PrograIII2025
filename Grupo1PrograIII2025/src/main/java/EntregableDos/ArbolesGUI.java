/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntregableDos;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import miumg.edu.gt.grupo1prograiii2025.MainMenu;

/**
 *
 * @author Freddy Elias
 */
public class ArbolesGUI extends JFrame{
    public ArbolesGUI() {
        initComponents();
        configurarVentana();
    }
    
    private void initComponents() {
        JPanel panel = new JPanel(new BorderLayout(10, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel lblTitulo = new JLabel("Operaciones con Arboles");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(lblTitulo, BorderLayout.NORTH);
        
        JPanel panelBotones = new JPanel(new GridLayout(3, 1, 10, 15));
        
        JButton btnBinario = new JButton("Arbol Binario");
        btnBinario.addActionListener(e -> AbrirBinario());
        
        JButton btnAVL = new JButton("Arbol AVL");
        btnAVL.addActionListener(e -> AbrirAVL());
        
        JButton btnVolver = new JButton("Volver al Menu");
        btnVolver.addActionListener(e -> volverMenuPrincipal());
        
        panelBotones.add(btnBinario);
        panelBotones.add(btnAVL);
        panelBotones.add(btnVolver);
        
        panel.add(panelBotones, BorderLayout.CENTER);
        
        JLabel lblPie = new JLabel("Seleccione una operacion");
        lblPie.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(lblPie, BorderLayout.SOUTH);

        this.add(panel);
    }
    
    private void configurarVentana() {
        setTitle("Arboles");
        setSize(500, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }
    
    private void volverMenuPrincipal() {
        new MainMenu().setVisible(true);
        this.dispose();
    }
    
    private void AbrirAVL() {
        new AVLSubMenu().setVisible(true);
        this.dispose();
    }
    
    private void AbrirBinario() {
        new BinarioSubMenu().setVisible(true);
        this.dispose();
    }
}
