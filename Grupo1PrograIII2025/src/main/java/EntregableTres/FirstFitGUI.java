/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntregableTres;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
/**
 *
 * @author kfsji
 */
public class FirstFitGUI extends JFrame {
    private ArrayList<BloqueMemoria> memoria;
    private int tamañoTotal;
    private JPanel memoriaPanel;
    private JTextArea logArea;
    private JTextField sizeField;
    private JTextField processField;
    private JTextField addressField;
    private int nextProcessId = 1;
    private JFrame parentWindow;

    public FirstFitGUI(int tamañoTotal, JFrame parentWindow) {
        this.tamañoTotal = tamañoTotal;
        this.parentWindow = parentWindow;
        this.memoria = new ArrayList<>();
        this.memoria.add(new BloqueMemoria(0, tamañoTotal, false, ""));
        
        setTitle("Gestión de Memoria - First Fit");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Panel de controles superior
        JPanel topPanel = new JPanel(new BorderLayout());
        
        // Boton de volver
        JButton backButton = new JButton("Volver al menu");
        backButton.addActionListener(e -> returnToMainWindow());
        topPanel.add(backButton, BorderLayout.WEST);
        
        // Panel de controles de memoria
        JPanel controlPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        
        // Panel para asignar memoria
        JPanel allocatePanel = new JPanel(new FlowLayout());
        sizeField = new JTextField(10);
        processField = new JTextField(10);
        JButton allocateButton = new JButton("Asignar Memoria");
        allocateButton.addActionListener(e -> asignarMemoria());
        
        allocatePanel.add(new JLabel("Tamaño:"));
        allocatePanel.add(sizeField);
        allocatePanel.add(new JLabel("Proceso:"));
        allocatePanel.add(processField);
        allocatePanel.add(allocateButton);
        
        // Panel para liberar memoria
        JPanel freePanel = new JPanel(new FlowLayout());
        addressField = new JTextField(10);
        JButton freeButton = new JButton("Liberar Memoria");
        freeButton.addActionListener(e -> liberarMemoria());
        
        freePanel.add(new JLabel("Dirección:"));
        freePanel.add(addressField);
        freePanel.add(freeButton);
        
        controlPanel.add(allocatePanel);
        controlPanel.add(freePanel);
        
        topPanel.add(controlPanel, BorderLayout.CENTER);
        
        // Panel de visualizacion de memoria
        memoriaPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                dibujarMemoria(g);
            }
        };
        memoriaPanel.setPreferredSize(new Dimension(750, 150));
        memoriaPanel.setBorder(BorderFactory.createTitledBorder("Mapa de Memoria"));
        
        // Area de registro
        logArea = new JTextArea();
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);
        
        // Agregar componentes al frame
        add(topPanel, BorderLayout.NORTH);
        add(memoriaPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);
        
        log("Sistema iniciado. Tamaño total de memoria: " + tamañoTotal);
        
        // Manejar el cierre de la ventana
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                returnToMainWindow();
            }
        });
    }
    
    private void returnToMainWindow() {
        this.dispose();
        if (parentWindow != null) {
            parentWindow.setVisible(true);
        }
    }
    
    private void dibujarMemoria(Graphics g) {
        int panelWidth = memoriaPanel.getWidth() - 20;
        int y = 30;
        
        // Dibujar la memoria total
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(10, y, panelWidth, 30);
        g.setColor(Color.BLACK);
        g.drawRect(10, y, panelWidth, 30);
        g.drawString("Memoria Total: " + tamañoTotal, 15, y + 20);
        
        y += 50;
        
        // Dibujar cada bloque
        for (BloqueMemoria bloque : memoria) {
            int blockWidth = (int)((double)bloque.tamaño / tamañoTotal * panelWidth);
            
            if (bloque.ocupado) {
                g.setColor(new Color(255, 100, 100)); // Rojo bloques ocupados
            } else {
                g.setColor(new Color(100, 255, 100)); // Verde bloques libres
            }
            
            g.fillRect(10, y, blockWidth, 30);
            g.setColor(Color.BLACK);
            g.drawRect(10, y, blockWidth, 30);
            
            String info = bloque.ocupado ? 
                String.format("Ocupado (%s) %d-%d (%d)", bloque.proceso, bloque.inicio, bloque.inicio + bloque.tamaño - 1, bloque.tamaño) :
                String.format("Libre %d-%d (%d)", bloque.inicio, bloque.inicio + bloque.tamaño - 1, bloque.tamaño);
            
            g.drawString(info, 15, y + 20);
            
            y += 40;
        }
    }
    
    private void asignarMemoria() {
        try {
            int tamaño = Integer.parseInt(sizeField.getText());
            String proceso = processField.getText().trim();
            
            if (proceso.isEmpty()) {
                proceso = "P" + nextProcessId++;
            }
            
            // Buscar el primer bloque libre que pueda contener la solicitud
            int indiceFirst = -1;
            
            for (int i = 0; i < memoria.size(); i++) {
                BloqueMemoria bloque = memoria.get(i);
                if (!bloque.ocupado && bloque.tamaño >= tamaño) {
                    indiceFirst = i;
                    break; // Tomamos el primero que encontramos
                }
            }

            if (indiceFirst == -1) {
                log("No hay suficiente memoria contigua para asignar " + tamaño + " unidades al proceso " + proceso);
                return;
            }

            BloqueMemoria bloqueFirst = memoria.get(indiceFirst);

            // Si el bloque tiene exactamente el tamaño necesario
            if (bloqueFirst.tamaño == tamaño) {
                bloqueFirst.ocupado = true;
                bloqueFirst.proceso = proceso;
                log("Memoria asignada al proceso " + proceso + ": Inicio=" + bloqueFirst.inicio + ", Tamaño=" + tamaño);
            } 
            // Si el bloque es mayor, dividirlo
            else {
                BloqueMemoria nuevoBloque = new BloqueMemoria(
                    bloqueFirst.inicio + tamaño,
                    bloqueFirst.tamaño - tamaño,
                    false,
                    ""
                );
                
                bloqueFirst.tamaño = tamaño;
                bloqueFirst.ocupado = true;
                bloqueFirst.proceso = proceso;
                
                memoria.add(indiceFirst + 1, nuevoBloque);
                log("Memoria asignada al proceso " + proceso + ": Inicio=" + bloqueFirst.inicio + ", Tamaño=" + tamaño);
            }
            
            memoriaPanel.repaint();
            sizeField.setText("");
            processField.setText("");
        } catch (NumberFormatException ex) {
            log("Error: Ingrese un tamaño válido");
        }
    }
    
    private void liberarMemoria() {
        try {
            int inicio = Integer.parseInt(addressField.getText());
            boolean encontrado = false;
            int indice = -1;

            // Buscar el bloque a liberar
            for (int i = 0; i < memoria.size(); i++) {
                BloqueMemoria bloque = memoria.get(i);
                if (bloque.inicio == inicio && bloque.ocupado) {
                    String proceso = bloque.proceso;
                    bloque.ocupado = false;
                    bloque.proceso = "";
                    encontrado = true;
                    indice = i;
                    log("Memoria liberada del proceso " + proceso + ": Inicio=" + inicio + ", Tamaño=" + bloque.tamaño);
                    break;
                }
            }

            if (!encontrado) {
                log("No se encontró un bloque ocupado que comience en " + inicio);
                return;
            }

            // Fusionar con bloques libres adyacentes
            // Fusionar con el siguiente si esta libre
            if (indice < memoria.size() - 1) {
                BloqueMemoria actual = memoria.get(indice);
                BloqueMemoria siguiente = memoria.get(indice + 1);
                
                if (!siguiente.ocupado) {
                    actual.tamaño += siguiente.tamaño;
                    memoria.remove(indice + 1);
                }
            }

            // Fusionar con el anterior si esta libre
            if (indice > 0) {
                BloqueMemoria actual = memoria.get(indice);
                BloqueMemoria anterior = memoria.get(indice - 1);
                
                if (!anterior.ocupado) {
                    anterior.tamaño += actual.tamaño;
                    memoria.remove(indice);
                }
            }
            
            memoriaPanel.repaint();
            addressField.setText("");
        } catch (NumberFormatException ex) {
            log("Error: Ingrese una dirección válida");
        }
    }
    
    private void log(String message) {
        logArea.append(message + "\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }
}

