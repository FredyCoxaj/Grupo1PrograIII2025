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
import java.util.stream.IntStream;

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
        
        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.addActionListener(e -> actualizarTabla());

        // Panel de operaciones matemáticas
        JPanel panelOperaciones = new JPanel(new GridLayout(1, 4, 5, 5));
        panelOperaciones.setBorder(BorderFactory.createTitledBorder("Operaciones"));

        // Botones de operaciones
        JButton btnSuma = new JButton("Sumar (+)");
        btnSuma.addActionListener(e -> operarCelda("+"));
        
        JButton btnResta = new JButton("Restar (-)");
        btnResta.addActionListener(e -> operarCelda("-"));
        
        JButton btnMultiplicacion = new JButton("Multiplicar (*)");
        btnMultiplicacion.addActionListener(e -> operarCelda("*"));
        
        JButton btnDivision = new JButton("Dividir (/)");
        btnDivision.addActionListener(e -> operarCelda("/"));

        panelOperaciones.add(btnSuma);
        panelOperaciones.add(btnResta);
        panelOperaciones.add(btnMultiplicacion);
        panelOperaciones.add(btnDivision);

        // Layout superior
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.add(panelControles, BorderLayout.NORTH);
        panelSuperior.add(panelOperaciones, BorderLayout.SOUTH);

        // Componentes básicos
        panelControles.add(new JLabel("Fila (número):"));
        panelControles.add(filaField);
        panelControles.add(new JLabel("Columna (letra):"));
        panelControles.add(columnaField);
        panelControles.add(new JLabel("Valor:"));
        panelControles.add(valorField);
        panelControles.add(btnInsertar);
        panelControles.add(btnModificar);
        panelControles.add(btnEliminar);
        panelControles.add(btnVerValor);
        panelControles.add(btnActualizar);

        add(panelSuperior, BorderLayout.NORTH);

        // Configurar tabla (20 filas x 10 columnas)
        modeloTabla = new DefaultTableModel(20, 10) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            
            @Override
            public String getColumnName(int column) {
                return String.valueOf((char) ('A' + column)); // Columnas: A, B, C, ...
            }
        };
        
        tabla = new JTable(modeloTabla);
        tabla.setRowHeight(25);
        
        //add(new JScrollPane(tabla), BorderLayout.CENTER);
        JScrollPane scrollPane = new JScrollPane(tabla);
        add(scrollPane, BorderLayout.CENTER);
        agregarNumerosDeFila();  // Llama al método que creamos
    }

    // Convierte letra de columna a número (A->1, B->2, ...)
    private int columnaLetraANumero(String letra) {
        return letra.toUpperCase().charAt(0) - 'A' + 1;
    }

    // Convierte número de columna a letra (1->A, 2->B, ...)
    private String columnaNumeroALetra(int numero) {
        return String.valueOf((char) ('A' + numero - 1));
    }
    
    private void agregarNumerosDeFila() {
        // Crear un JList con los números de fila (1 al 20)
        JList<String> listaFilas = new JList<>(IntStream.rangeClosed(1, 20)
                .mapToObj(String::valueOf)
                .toArray(String[]::new));

        listaFilas.setFixedCellWidth(40);  // Ancho de la columna de números
        listaFilas.setFixedCellHeight(tabla.getRowHeight());
        listaFilas.setBackground(Color.LIGHT_GRAY);
        listaFilas.setSelectionModel(tabla.getSelectionModel());  // Sincronizar selección

        // Añadir el JList como "header" de filas
        JScrollPane scrollPane = (JScrollPane) getContentPane().getComponent(1);  // Obtener el JScrollPane de la tabla
        scrollPane.setRowHeaderView(listaFilas);  // Fijar el JList a la izquierda
    }

    private void insertarCelda() {
        try {
            int fila = Integer.parseInt(filaField.getText());
            String columnaLetra = columnaField.getText().trim().toUpperCase();
            int columna = columnaLetraANumero(columnaLetra);
            Double valor = Double.parseDouble(valorField.getText());
            
            if (fila < 1 || columna < 1 || columna > 10) {
                throw new IllegalArgumentException("Fila debe ser ≥1 y columna entre A-J");
            }
            
            hoja.insertar(fila, columna, valor);
            actualizarTabla();
            JOptionPane.showMessageDialog(this, "Valor insertado en " + columnaLetra + fila);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error: " + e.getMessage() + "\nFormato esperado:\nFila: 1\nColumna: A", 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modificarCelda() {
        try {
            int fila = Integer.parseInt(filaField.getText());
            String columnaLetra = columnaField.getText().trim().toUpperCase();
            int columna = columnaLetraANumero(columnaLetra);
            Double valor = Double.parseDouble(valorField.getText());
            
            if (fila < 1 || columna < 1 || columna > 10) {
                throw new IllegalArgumentException("Fila debe ser ≥1 y columna entre A-J");
            }
            
            hoja.modificarValor(fila, columna, valor);
            actualizarTabla();
            JOptionPane.showMessageDialog(this, "Valor modificado en " + columnaLetra + fila);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error: " + e.getMessage() + "\nFormato esperado:\nFila: 1\nColumna: A", 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarCelda() {
        try {
            int fila = Integer.parseInt(filaField.getText());
            String columnaLetra = columnaField.getText().trim().toUpperCase();
            int columna = columnaLetraANumero(columnaLetra);
            
            if (fila < 1 || columna < 1 || columna > 10) {
                throw new IllegalArgumentException("Fila debe ser ≥1 y columna entre A-J");
            }
            
            hoja.eliminarCelda(fila, columna);
            actualizarTabla();
            JOptionPane.showMessageDialog(this, "Celda " + columnaLetra + fila + " eliminada (valor establecido como vacío)");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error: " + e.getMessage() + "\nFormato esperado:\nFila: 1\nColumna: A", 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void verValor() {
        try {
            int fila = Integer.parseInt(filaField.getText());
            String columnaLetra = columnaField.getText().trim().toUpperCase();
            int columna = columnaLetraANumero(columnaLetra);
            
            if (fila < 1 || columna < 1 || columna > 10) {
                throw new IllegalArgumentException("Fila debe ser ≥1 y columna entre A-J");
            }
            
            Double valor = hoja.obtenerValor(fila, columna);
            if (valor != null) {
                JOptionPane.showMessageDialog(this, "El valor en " + columnaLetra + fila + " es: " + valor);
            } else {
                JOptionPane.showMessageDialog(this, "La celda " + columnaLetra + fila + " está vacía", "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error: " + e.getMessage() + "\nFormato esperado:\nFila: 1\nColumna: A", 
                "Error", JOptionPane.ERROR_MESSAGE);
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
    
    private void operarCelda(String operador) {
        try {
            // Celda destino
            int filaDestino = Integer.parseInt(filaField.getText());
            String columnaLetraDestino = columnaField.getText().trim().toUpperCase();
            int columnaDestino = columnaLetraANumero(columnaLetraDestino);
            
            if (filaDestino < 1 || columnaDestino < 1 || columnaDestino > 10) {
                throw new IllegalArgumentException("Fila debe ser ≥1 y columna entre A-J");
            }

            // Pedir las celdas origen
            String input = JOptionPane.showInputDialog(this, 
                "Ingrese coordenadas de celdas a operar (ej: A1;B2)", "Operación " + operador, 
                JOptionPane.QUESTION_MESSAGE);

            if (input != null && !input.isEmpty()) {
                String[] partes = input.split(";");
                Double resultado = null;

                for (String celdaCoord : partes) {
                    // Extraer letra de columna y número de fila (ej: "B3" -> columna=2, fila=3)
                    String columnaLetra = celdaCoord.replaceAll("[^A-Za-z]", "").toUpperCase();
                    int fila = Integer.parseInt(celdaCoord.replaceAll("[^0-9]", ""));
                    int columna = columnaLetraANumero(columnaLetra);
                    
                    Double valor = hoja.obtenerValor(fila, columna);
                    if (valor == null) {
                        valor = 0.0;
                    }

                    if (resultado == null) {
                        resultado = valor;
                    } else {
                        switch (operador) {
                            case "+": resultado += valor; break;
                            case "-": resultado -= valor; break;
                            case "*": resultado *= valor; break;
                            case "/": 
                                if (valor == 0) {
                                    JOptionPane.showMessageDialog(this, 
                                        "No se puede dividir por cero", 
                                        "Error", JOptionPane.ERROR_MESSAGE);
                                    return;
                                }
                                resultado /= valor; 
                                break;
                        }
                    }
                }

                hoja.insertar(filaDestino, columnaDestino, resultado);
                actualizarTabla();
                JOptionPane.showMessageDialog(this, 
                    "Resultado: " + resultado + "\nGuardado en: " + 
                    columnaLetraDestino + filaDestino);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error en formato o cálculo. Use: LetraNúmero;LetraNúmero\n" +
                "Ejemplo: A1;B2", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HojaCalculoGUI gui = new HojaCalculoGUI();
            gui.setVisible(true);
        });
    }
}