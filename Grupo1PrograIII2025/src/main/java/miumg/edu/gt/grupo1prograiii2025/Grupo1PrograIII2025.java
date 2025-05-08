/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package miumg.edu.gt.grupo1prograiii2025;

import javax.swing.SwingUtilities;
import javax.swing.JFrame;

/**
 *
 * @author Freddy Elias
 */
public class Grupo1PrograIII2025 extends JFrame{
public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainMenu().setVisible(true);
        });
    }
}
