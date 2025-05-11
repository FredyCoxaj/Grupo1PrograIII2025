/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Freddy Elias
 */

package EntregableDos;

public class NodoAVL {
    int clave;
    int altura;
    NodoAVL izquierdo;
    NodoAVL derecho;

    public NodoAVL(int clave) {
        this.clave = clave;
        this.altura = 1;
    }
}