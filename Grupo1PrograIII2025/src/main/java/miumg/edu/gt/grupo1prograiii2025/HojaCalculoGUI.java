/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miumg.edu.gt.grupo1prograiii2025;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 *
 * @author kfsji
 */
public class HojaCalculoGUI extends JFrame {
  
    private hojaCalculo hoja;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private JTextField filaField, columnaField, valorField;

    public HojaCalculoGUI() {
        hoja = new hojaCalculo();
        configurarVentana();
        crearComponentes();
        actualizarTabla();
    }

    private void configurarVentana() {
        setTitle("Hoja de Cálculo - Grupo 1");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
    }

    private void crearComponentes() {
        // Panel de controles (superior)
        JPanel panelControles = new JPanel(new GridLayout(2, 6, 5, 5));
        panelControles.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        filaField = new JTextField();
        columnaField = new JTextField();
        valorField = new JTextField();

        JButton btnInsertar = new JButton("Insertar");
        btnInsertar.addActionListener(e -> insertarCelda());
        
        JButton btnModificar = new JButton("Modificar");
        btnModificar.addActionListener(e -> modificarCelda());
        
        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(e -> eliminarCelda());
        
        JButton btnVerValor = new JButton("Ver Valor");
        btnVerValor.addActionListener(e -> verValor());
        
        JButton btnActualizar = new JButton("----- ");
        btnActualizar.addActionListener(e -> actualizarTabla());

        panelControles.add(new JLabel("Fila:"));
        panelControles.add(filaField);
        panelControles.add(new JLabel("Columna:"));
        panelControles.add(columnaField);
        panelControles.add(new JLabel("Valor:"));
        panelControles.add(valorField);
        panelControles.add(btnInsertar);
        panelControles.add(btnModificar);
        panelControles.add(btnEliminar);
        panelControles.add(btnVerValor);
        panelControles.add(btnActualizar);

        add(panelControles, BorderLayout.NORTH);

        // Configurar tabla (20 filas x 10 columnas)
        modeloTabla = new DefaultTableModel(20, 10) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        // Configurar nombres de columnas
        String[] columnNames = new String[10];
        for (int i = 0; i < 10; i++) {
            columnNames[i] = "C" + (i+1);
        }
        modeloTabla.setColumnIdentifiers(columnNames);
        
        tabla = new JTable(modeloTabla);
        tabla.setRowHeight(25);
        
        add(new JScrollPane(tabla), BorderLayout.CENTER);
    }

    private void insertarCelda() {
        try {
            int fila = Integer.parseInt(filaField.getText());
            int columna = Integer.parseInt(columnaField.getText());
            Double valor = Double.parseDouble(valorField.getText());
            
            hoja.insertar(fila, columna, valor);
            actualizarTabla();
            JOptionPane.showMessageDialog(this, "Valor insertado correctamente");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese valores numéricos válidos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modificarCelda() {
        try {
            int fila = Integer.parseInt(filaField.getText());
            int columna = Integer.parseInt(columnaField.getText());
            Double valor = Double.parseDouble(valorField.getText());
            
            hoja.modificarValor(fila, columna, valor);
            actualizarTabla();
            JOptionPane.showMessageDialog(this, "Valor modificado correctamente");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese valores numéricos válidos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarCelda() {
        try {
            int fila = Integer.parseInt(filaField.getText());
            int columna = Integer.parseInt(columnaField.getText());
            
            hoja.eliminarCelda(fila, columna);
            actualizarTabla();
            JOptionPane.showMessageDialog(this, "Celda eliminada (valor establecido como vacío)");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese valores numéricos válidos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void verValor() {
        try {
            int fila = Integer.parseInt(filaField.getText());
            int columna = Integer.parseInt(columnaField.getText());
            
            Double valor = hoja.obtenerValor(fila, columna);
            if (valor != null) {
                JOptionPane.showMessageDialog(this, "El valor en [" + fila + "," + columna + "] es: " + valor);
            } else {
                JOptionPane.showMessageDialog(this, "La celda está vacía o no existe", "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese valores numéricos válidos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarTabla() {
        // Limpiar la tabla
        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            for (int j = 0; j < modeloTabla.getColumnCount(); j++) {
                modeloTabla.setValueAt("", i, j);
            }
        }
        
        // Recorrer la hoja de cálculo y actualizar la tabla
        celda filaActual = hoja.getInicio();
        int fila = 0;
        while (filaActual != null && fila < modeloTabla.getRowCount()) {
            celda columnaActual = filaActual;
            int columna = 0;
            while (columnaActual != null && columna < modeloTabla.getColumnCount()) {
                if (columnaActual.getValor() != null) {
                    modeloTabla.setValueAt(columnaActual.getValor(), fila, columna);
                }
                columnaActual = columnaActual.getDerecha();
                columna++;
            }
            filaActual = filaActual.getAbajo();
            fila++;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HojaCalculoGUI gui = new HojaCalculoGUI();
            gui.setVisible(true);
        });
    }

}