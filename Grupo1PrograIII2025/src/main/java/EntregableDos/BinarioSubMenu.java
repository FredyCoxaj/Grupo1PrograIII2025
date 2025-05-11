/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntregableDos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

public class BinarioSubMenu extends JFrame {
    private final ArbolBinario arbol;
    private final Connection conexion;
    private JTextField campoNumero;
    private JPanel panelDibujo;
    private JButton btnInsertar, btnEliminar, btnGuardar, btnVolver;

    public BinarioSubMenu(Connection conexion) {
        this.conexion = conexion;
        this.arbol = new ArbolBinario();
        initComponents();
        configurarVentana();
    }

    private void initComponents() {
        // Panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Panel de controles
        JPanel panelControles = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Componentes
        campoNumero = new JTextField(15);
        campoNumero.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // Botones con estilos personalizados
        btnInsertar = crearBotonPersonalizado("Insertar", new Color(76, 175, 80));  // Verde
        btnEliminar = crearBotonPersonalizado("Eliminar", new Color(244, 67, 54));  // Rojo
        btnGuardar = crearBotonPersonalizado("Guardar", new Color(33, 150, 243));   // Azul
        btnVolver = crearBotonPersonalizado("Volver", new Color(158, 158, 158));    // Gris

        // Configurar acciones
        btnInsertar.addActionListener(this::accionInsertar);
        btnEliminar.addActionListener(this::accionEliminar);
        btnGuardar.addActionListener(this::accionGuardar);
        btnVolver.addActionListener(this::accionVolver);

        // Configuración GridBagLayout
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        panelControles.add(new JLabel("Valor:"), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        panelControles.add(campoNumero, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        panelBotones.add(btnInsertar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnGuardar);
        panelBotones.add(btnVolver);
        
        panelControles.add(panelBotones, gbc);
        panelPrincipal.add(panelControles, BorderLayout.NORTH);

        // Panel de dibujo
        panelDibujo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(Color.WHITE);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                
                if (arbol.getRaiz() != null) {
                    dibujarArbol(g2d, getWidth() / 2, 30, 150, arbol.getRaiz());
                }
            }
        };
        
        panelDibujo.setPreferredSize(new Dimension(600, 400));
        panelDibujo.setBorder(BorderFactory.createTitledBorder("Árbol Binario"));
        panelPrincipal.add(new JScrollPane(panelDibujo), BorderLayout.CENTER);

        add(panelPrincipal);
    }

    private JButton crearBotonPersonalizado(String texto, Color color) {
        JButton boton = new JButton(texto);
        
        // Estilo base
        boton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Colores personalizados
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        
        // Efectos hover
        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                boton.setBackground(brighter(color, 0.8f));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                boton.setBackground(color);
            }
        });
        
        return boton;
    }

    private Color brighter(Color color, float factor) {
        return new Color(
            Math.min((int)(color.getRed() * factor), 255),
            Math.min((int)(color.getGreen() * factor), 255),
            Math.min((int)(color.getBlue() * factor), 255)
        );
    }

    private void dibujarArbol(Graphics g, int x, int y, int separacionX, NodoAVL nodo) {
        if (nodo == null) return;

        int diametro = 30;
        g.setColor(new Color(173, 216, 230));
        g.fillOval(x - diametro / 2, y - diametro / 2, diametro, diametro);
        g.setColor(Color.BLACK);
        g.drawOval(x - diametro / 2, y - diametro / 2, diametro, diametro);

        String texto = String.valueOf(nodo.clave);
        FontMetrics fm = g.getFontMetrics();
        g.drawString(texto, x - fm.stringWidth(texto) / 2, y + fm.getAscent() / 2 - 2);

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

    private void configurarVentana() {
        setTitle("Árbol Binario - Universidad Mariano Gálvez");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        try {
            setIconImage(new ImageIcon(getClass().getResource("/resources/tree_icon.png")).getImage());
        } catch (Exception e) {
            System.err.println("Error al cargar icono: " + e.getMessage());
        }
    }

    private void accionInsertar(ActionEvent e) {
        try {
            validarCampoVacio();
            int valor = Integer.parseInt(campoNumero.getText());
            validarRangoValor(valor);
            
            arbol.insertar(valor);
            campoNumero.setText("");
            panelDibujo.repaint();
            
        } catch (NumberFormatException ex) {
            mostrarError("Debe ingresar un número válido", ex);
        } catch (IllegalArgumentException ex) {
            mostrarError(ex.getMessage(), ex);
        }
    }

    private void accionEliminar(ActionEvent e) {
        try {
            validarCampoVacio();
            int valor = Integer.parseInt(campoNumero.getText());
            
            if (!arbol.buscar(valor)) {
                throw new IllegalArgumentException("El valor " + valor + " no existe en el árbol");
            }
            
            arbol.eliminar(valor);
            campoNumero.setText("");
            panelDibujo.repaint();
            
        } catch (NumberFormatException ex) {
            mostrarError("Debe ingresar un número válido", ex);
        } catch (IllegalArgumentException ex) {
            mostrarError(ex.getMessage(), ex);
        }
    }

    private void accionGuardar(ActionEvent e) {
        try {
            if (conexion == null || conexion.isClosed()) {
                throw new SQLException("No hay conexión a la base de datos");
            }
            
            guardarArbolEnDB();
            
            JOptionPane.showMessageDialog(this,
                "Árbol guardado exitosamente",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE);
                
        } catch (SQLException ex) {
            mostrarError("Error al guardar en la base de datos", ex);
        }
    }

    private void accionVolver(ActionEvent e) {
        new ArbolesGUI(conexion).setVisible(true);
        dispose();
    }

    private void validarCampoVacio() {
        if (campoNumero.getText().trim().isEmpty()) {
            throw new IllegalArgumentException("Debe ingresar un valor");
        }
    }

    private void validarRangoValor(int valor) {
        if (valor < -9999 || valor > 9999) {
            throw new IllegalArgumentException("El valor debe estar entre -9999 y 9999");
        }
    }

    private void guardarArbolEnDB() throws SQLException {
        String sqlArbol = "INSERT INTO arboles (nombre, fechaCreacion, idTipoArbol) VALUES (?, GETDATE(), ?)";
        
        try (PreparedStatement pstmt = conexion.prepareStatement(sqlArbol, Statement.RETURN_GENERATED_KEYS)) {
            String nombreArbol = "Binario_" + System.currentTimeMillis();
            pstmt.setString(1, nombreArbol);
            pstmt.setInt(2, 1); // 1 = Árbol Binario
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("No se pudo guardar el árbol");
            }
            
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int idArbol = rs.getInt(1);
                    guardarNodosRecursivo(arbol.getRaiz(), null, idArbol, "raiz");
                }
            }
        }
    }

    private void guardarNodosRecursivo(NodoAVL nodo, Integer idPadre, int idArbol, String tipoHijo) throws SQLException {
        if (nodo == null) return;
        
        String sqlNodo = """
            INSERT INTO nodos_arbol 
            (idArbol, valor, idPadre, tipoHijo, altura) 
            VALUES (?, ?, ?, ?, ?)
            """;
        
        try (PreparedStatement pstmt = conexion.prepareStatement(sqlNodo, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, idArbol);
            pstmt.setInt(2, nodo.clave);
            pstmt.setObject(3, idPadre, Types.INTEGER);
            pstmt.setString(4, tipoHijo);
            pstmt.setInt(5, nodo.altura);
            
            pstmt.executeUpdate();
            
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int idNodo = rs.getInt(1);
                    guardarNodosRecursivo(nodo.izquierdo, idNodo, idArbol, "izquierdo");
                    guardarNodosRecursivo(nodo.derecho, idNodo, idArbol, "derecho");
                }
            }
        }
    }

    private void mostrarError(String mensaje, Exception ex) {
        JOptionPane.showMessageDialog(this,
            mensaje + "\nDetalles: " + ex.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE);
        ex.printStackTrace();
    }

}