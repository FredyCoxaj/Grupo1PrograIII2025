/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package miumg.edu.gt.grupo1prograiii2025;

/**
 *
 * @author Freddy Elias
 */
public class Grupo1PrograIII2025 {

    public static void main(String[] args) {
        hojaCalculo hoja = new hojaCalculo();
        
        hoja.insertar(1, 1, "A1");
        hoja.insertar(1, 2, "B1");
        hoja.insertar(2, 1, "A2");
        hoja.insertar(2, 2, "B2");
        
        hoja.imprimir();
        System.out.println("valor en (1, 2): " + hoja.obtenerValor(1, 2));
    }
}
