/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntregableTres;

/**
 *
 * @author Freddy Elias
 */
class BloqueMemoria {
    int inicio;
    int tamaño;
    boolean ocupado;
    String proceso;

    public BloqueMemoria(int inicio, int tamaño, boolean ocupado, String proceso) {
        this.inicio = inicio;
        this.tamaño = tamaño;
        this.ocupado = ocupado;
        this.proceso = proceso;
    }
}