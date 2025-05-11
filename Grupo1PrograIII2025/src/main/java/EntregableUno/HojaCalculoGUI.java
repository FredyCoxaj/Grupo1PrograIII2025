/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntregableUno;

import EntregableDos.DatabaseConnection;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.stream.IntStream;
import miumg.edu.gt.grupo1prograiii2025.MainMenu;

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
    
    private void volverMenuPrincipal() {
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

        JButton btnVolver = new JButton("Volver al Menu");
        btnVolver.addActionListener(e -> volverMenuPrincipal());

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
        panelControles.add(btnVolver);

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

    private Double evaluarFormula(String formula) {
        try {
            // Si es un rango (contiene :)
            if (formula.contains(":")) {
                String[] rango = formula.split(":");
                String inicio = rango[0].trim();
                String fin = rango[1].trim();

                int filaInicio = Integer.parseInt(inicio.replaceAll("[^0-9]", ""));
                String colLetraInicio = inicio.replaceAll("[^A-Za-z]", "").toUpperCase();
                int colInicio = columnaLetraANumero(colLetraInicio);

                int filaFin = Integer.parseInt(fin.replaceAll("[^0-9]", ""));
                String colLetraFin = fin.replaceAll("[^A-Za-z]", "").toUpperCase();
                int colFin = columnaLetraANumero(colLetraFin);

                double suma = 0.0;
                int celdasValidas = 0;

                // Rango vertical (misma columna)
                if (colInicio == colFin) {
                    for (int f = Math.min(filaInicio, filaFin); f <= Math.max(filaInicio, filaFin); f++) {
                        Double valor = hoja.obtenerValor(f, colInicio);
                        if (valor != null) {
                            suma += valor;
                            celdasValidas++;
                        }
                    }
                } // Rango horizontal (misma fila)
                else if (filaInicio == filaFin) {
                    for (int c = Math.min(colInicio, colFin); c <= Math.max(colInicio, colFin); c++) {
                        Double valor = hoja.obtenerValor(filaInicio, c);
                        if (valor != null) {
                            suma += valor;
                            celdasValidas++;
                        }
                    }
                } else {
                    throw new IllegalArgumentException("El rango debe ser en línea recta");
                }

                return celdasValidas > 0 ? suma : 0.0;
            } // Si es referencia a celda individual
            else {
                int fila = Integer.parseInt(formula.replaceAll("[^0-9]", ""));
                String colLetra = formula.replaceAll("[^A-Za-z]", "").toUpperCase();
                int columna = columnaLetraANumero(colLetra);
                Double valor = hoja.obtenerValor(fila, columna);
                return valor != null ? valor : 0.0;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error en fórmula: " + e.getMessage() + "\nEjemplo: =A1:A3",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return null;
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

            // Pedir las celdas/rangos origen
            String input = JOptionPane.showInputDialog(this,
                    "Ingrese coordenadas o rangos (ej: A1;B2 o A1:A3)", "Operación " + operador,
                    JOptionPane.QUESTION_MESSAGE);

            if (input != null && !input.isEmpty()) {
                String[] partes = input.split(";");
                Double resultado = null;

                for (String celdaORango : partes) {
                    // Verificar si es un rango (contiene :)
                    if (celdaORango.contains(":")) {
                        String[] rango = celdaORango.split(":");
                        String inicio = rango[0];
                        String fin = rango[1];

                        // Extraer coordenadas de inicio y fin del rango
                        int filaInicio = Integer.parseInt(inicio.replaceAll("[^0-9]", ""));
                        int columnaInicio = columnaLetraANumero(inicio.replaceAll("[^A-Za-z]", "").toUpperCase());

                        int filaFin = Integer.parseInt(fin.replaceAll("[^0-9]", ""));
                        int columnaFin = columnaLetraANumero(fin.replaceAll("[^A-Za-z]", "").toUpperCase());

                        // Validar que el rango sea válido (misma columna o misma fila)
                        if (columnaInicio != columnaFin && filaInicio != filaFin) {
                            throw new IllegalArgumentException("Rango debe ser en línea recta (misma columna o misma fila)");
                        }

                        // Procesar el rango
                        if (columnaInicio == columnaFin) { // Rango vertical
                            for (int fila = filaInicio; fila <= filaFin; fila++) {
                                Double valor = hoja.obtenerValor(fila, columnaInicio);
                                if (valor == null) {
                                    valor = 0.0;
                                }
                                resultado = (resultado == null) ? valor : aplicarOperacion(resultado, valor, operador);
                            }
                        } else { // Rango horizontal
                            for (int col = columnaInicio; col <= columnaFin; col++) {
                                Double valor = hoja.obtenerValor(filaInicio, col);
                                if (valor == null) {
                                    valor = 0.0;
                                }
                                resultado = (resultado == null) ? valor : aplicarOperacion(resultado, valor, operador);
                            }
                        }
                    } else { // Celda individual
                        String columnaLetra = celdaORango.replaceAll("[^A-Za-z]", "").toUpperCase();
                        int fila = Integer.parseInt(celdaORango.replaceAll("[^0-9]", ""));
                        int columna = columnaLetraANumero(columnaLetra);

                        Double valor = hoja.obtenerValor(fila, columna);
                        if (valor == null) {
                            valor = 0.0;
                        }
                        resultado = (resultado == null) ? valor : aplicarOperacion(resultado, valor, operador);
                    }
                }

                hoja.insertar(filaDestino, columnaDestino, resultado);
                actualizarTabla();
                JOptionPane.showMessageDialog(this,
                        "Resultado: " + resultado + "\nGuardado en: "
                        + columnaLetraDestino + filaDestino);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error: " + e.getMessage() + "\nFormatos válidos:\n"
                    + "Celda simple: A1\nRango vertical: A1:A3\nRango horizontal: A1:C1\n"
                    + "Múltiples entradas: A1:A3;B2", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

// Metodo auxiliar para aplicar operaciones
    private Double aplicarOperacion(Double acumulado, Double valor, String operador) {
        switch (operador) {
            case "+":
                return acumulado + valor;
            case "-":
                return acumulado - valor;
            case "*":
                return acumulado * valor;
            case "/":
                if (valor == 0) {
                    throw new ArithmeticException("División por cero");
                }
                return acumulado / valor;
            default:
                throw new IllegalArgumentException("Operador no válido");
        }
    }

}
