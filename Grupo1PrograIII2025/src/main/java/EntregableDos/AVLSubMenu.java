package EntregableDos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;



/**
 * 
 * @author Freddy Elias
 */

public class AVLSubMenu extends JFrame {

    private ArbolAVL arbol;
    private JTextField campoNumero;
    private JPanel panelDibujo;
    private Connection conexion;
    private int idArbolActual;

    public AVLSubMenu(Connection conexion) {
        this.conexion = conexion;
        this.arbol = new ArbolAVL();
        configurarVentana();
        inicializarComponentes();
    }

    private void configurarVentana() {
        setTitle("Árbol AVL");
        setSize(800, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(240, 240, 240));
    }

    private void inicializarComponentes() {
        // Panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel de controles
        JPanel panelControles = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        campoNumero = new JTextField(10);
        JButton btnInsertar = new JButton("Insertar Nodo");
        JButton btnEliminar = new JButton("Eliminar Nodo");
        JButton btnGuardar = new JButton("Guardar AVL");
        JButton btnVolver = new JButton("Volver");

        // Personalización de botones
        estiloBoton(btnInsertar, new Color(76, 175, 80));
        estiloBoton(btnEliminar, new Color(244, 67, 54));
        estiloBoton(btnGuardar, new Color(33, 150, 243));
        estiloBoton(btnVolver, new Color(158, 158, 158));

        // Acciones de los botones
        btnInsertar.addActionListener(this::insertarNodo);
        btnEliminar.addActionListener(this::eliminarNodo);
        btnGuardar.addActionListener(this::guardarAVL);
        btnVolver.addActionListener(this::accionVolver);

        panelControles.add(new JLabel("Valor:"));
        panelControles.add(campoNumero);
        panelControles.add(btnInsertar);
        panelControles.add(btnEliminar);
        panelControles.add(btnGuardar);
        panelControles.add(btnVolver);

        // Panel de dibujo del árbol
        panelDibujo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                dibujarArbol(g2d, getWidth() / 2, 50, 200, arbol.getRaiz());
            }
        };
        panelDibujo.setPreferredSize(new Dimension(700, 500));
        panelDibujo.setBackground(Color.WHITE);
        panelDibujo.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        panelPrincipal.add(panelControles, BorderLayout.NORTH);
        panelPrincipal.add(new JScrollPane(panelDibujo), BorderLayout.CENTER);

        add(panelPrincipal);
    }

    private void estiloBoton(JButton boton, Color color) {
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setFont(new Font("Arial", Font.BOLD, 12));
    }

    private void dibujarArbol(Graphics g, int x, int y, int separacion, NodoAVL nodo) {
        if (nodo == null) {
            return;
        }

        int diametro = 40;
        g.setColor(new Color(173, 216, 230));
        g.fillOval(x - diametro / 2, y - diametro / 2, diametro, diametro);
        g.setColor(Color.BLACK);
        g.drawOval(x - diametro / 2, y - diametro / 2, diametro, diametro);

        // Dibujar valor y altura
        String texto = nodo.clave + " (h:" + nodo.altura + ")";
        FontMetrics fm = g.getFontMetrics();
        g.drawString(texto, x - fm.stringWidth(texto) / 2, y + fm.getAscent() / 2);

        // Dibujar conexiones
        if (nodo.izquierdo != null) {
            int x2 = x - separacion;
            int y2 = y + 80;
            g.drawLine(x, y + diametro / 2, x2, y2 - diametro / 2);
            dibujarArbol(g, x2, y2, separacion / 2, nodo.izquierdo);
        }

        if (nodo.derecho != null) {
            int x2 = x + separacion;
            int y2 = y + 80;
            g.drawLine(x, y + diametro / 2, x2, y2 - diametro / 2);
            dibujarArbol(g, x2, y2, separacion / 2, nodo.derecho);
        }
    }

    private void insertarNodo(ActionEvent e) {
        try {
            String input = campoNumero.getText().trim();
            if (input.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese un valor numérico",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int valor = Integer.parseInt(input);

            if (valor < 0 || valor > 9999) {
                JOptionPane.showMessageDialog(this, "Ingrese un valor entre 0 y 9999",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (arbol.buscar(valor)) {
                JOptionPane.showMessageDialog(this, "El valor ya existe en el árbol",
                        "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }

            arbol.insertar(valor);
            campoNumero.setText("");
            panelDibujo.repaint();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingrese un número válido",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarNodo(ActionEvent e) {
        try {
            if (arbol.getRaiz() == null) {
                JOptionPane.showMessageDialog(this, "El árbol está vacío",
                        "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String input = campoNumero.getText().trim();
            if (input.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese un valor a eliminar",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int valor = Integer.parseInt(input);

            if (!arbol.buscar(valor)) {
                JOptionPane.showMessageDialog(this, "El valor no existe en el árbol",
                        "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }

            arbol.eliminar(valor);
            campoNumero.setText("");
            panelDibujo.repaint();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingrese un número válido",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void guardarAVL(ActionEvent e) {
        if (conexion == null) {
            JOptionPane.showMessageDialog(this, "No hay conexión a la base de datos",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (arbol.getRaiz() == null) {
            JOptionPane.showMessageDialog(this, "El árbol está vacío",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            if (conexion.isClosed()) {
                conexion = DatabaseConnection.getConnection();
            }

            guardarArbolEnBD();
            JOptionPane.showMessageDialog(this, "Árbol AVL guardado exitosamente",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar en la BD: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void accionVolver(ActionEvent e) {
        new ArbolesGUI(conexion).setVisible(true);
        dispose();
    }

    private void guardarArbolEnBD() throws SQLException {
        // Guardar información del árbol

        String sqlArbol = "INSERT INTO arboles (nombre, fechaCreacion, idTipoArbol) VALUES (?, GETDATE(), ?)";

        try (PreparedStatement pstmt = conexion.prepareStatement(sqlArbol, Statement.RETURN_GENERATED_KEYS)) {
            String nombreArbol = "AVL_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            pstmt.setString(1, nombreArbol);
            pstmt.setInt(2, 2); // ID para Árbol AVL

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("No se pudo guardar el árbol, ninguna fila afectada");
            }

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    idArbolActual = rs.getInt(1);
                } else {
                    throw new SQLException("No se obtuvo ID del árbol insertado");
                }
            }
        }

        // Guardar nodos recursivamente
        if (idArbolActual > 0) {
            guardarNodosRecursivo(arbol.getRaiz(), null, "raiz");
        }
    }

    private void guardarNodosRecursivo(NodoAVL nodo, Integer idPadre, String tipoHijo) throws SQLException {
        if (nodo == null) {
            return;
        }

        String sqlNodo = "INSERT INTO nodos_arbol (idArbol, valor, idPadre, tipoHijo, altura) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conexion.prepareStatement(sqlNodo, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, idArbolActual);
            pstmt.setInt(2, nodo.clave);

            if (idPadre != null) {
                pstmt.setInt(3, idPadre);
            } else {
                pstmt.setNull(3, Types.INTEGER);
            }

            pstmt.setString(4, tipoHijo);
            pstmt.setInt(5, nodo.altura);

            pstmt.executeUpdate();

            int idNodoActual = -1;
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    idNodoActual = rs.getInt(1);
                }
            }

            // Guardar hijos recursivamente
            if (idNodoActual != -1) {
                guardarNodosRecursivo(nodo.izquierdo, idNodoActual, "izquierdo");
                guardarNodosRecursivo(nodo.derecho, idNodoActual, "derecho");
            }
        }
    }

    private void volver() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
        }
        dispose();
    }
}

// BLOQUEADO POR LA PRUEBA   
//  private void configurarVentana() {
//       setTitle("Árbol AVL");
//       setSize(600, 500);
//       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//      setLayout(new BorderLayout(10, 10));
//       getContentPane().setBackground(new Color(240, 240, 240));
//   }

