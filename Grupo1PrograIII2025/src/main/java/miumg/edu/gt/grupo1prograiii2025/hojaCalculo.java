/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miumg.edu.gt.grupo1prograiii2025;

/**
 *
 * @author Freddy Elias
 */
public class hojaCalculo {
    private celda inicio;

    public hojaCalculo() {
        this.inicio = null;
    }
    
    public void insertar(int fila, int columna, Integer valor){
        celda nuevaCelda = new celda(valor);
        
        if(inicio == null){
            inicio = nuevaCelda;
        } else {
            
            //busca la pocision correcta para incestar la celda
            celda actualFila = inicio;
            celda actualColumna = inicio;
            
            //se mueve a la fila correcta
            for(int i = 0; i < fila; i++){
                if(actualFila.getAbajo() == null){
                    //crea una nueva fila en caso de que no exista
                    celda nuevaFlia = new celda(null);
                    actualFila.setAbajo(nuevaFlia);
                    nuevaFlia.setArriba(actualFila);
                }
                actualFila = actualFila.getAbajo();
            }
            
            //moverses a la columna correcta
            for(int j = 1; j < columna; j++){
                if(actualColumna.getDerecha() == null){
                    //crea una nueva columna
                    celda nuevaColumna = new celda(null);
                    actualColumna.setDerecha(nuevaColumna);
                    nuevaColumna.setIzquierda(actualColumna);
                }
                actualColumna = actualColumna.getDerecha();
            }
            
            //inserta la celda en la posicion correcta
            nuevaCelda.setIzquierda(actualColumna.getIzquierda());
            nuevaCelda.setDerecha(actualColumna);
            nuevaCelda.setArriba(actualFila.getArriba());
            nuevaCelda.setAbajo(actualFila);
            
            if(actualColumna.getIzquierda() != null){
                actualColumna.getIzquierda().setDerecha(nuevaCelda);
            }
            if(actualFila.getArriba() != null){
                actualFila.getArriba().setAbajo(nuevaCelda);
            }
            actualColumna.setIzquierda(nuevaCelda);
            actualFila.setArriba(nuevaCelda);
        }
    }
    
    //metodo de obtencion de datos de una celda especifica
    public int obtenerValor(Integer fila, Integer columna){
        celda actual = inicio;
        
        //moverse a la fila correcta
        for(Integer i = 1; i < fila; i++){
            if(actual.getAbajo() == null){
                return -1;
            }
            actual = actual.getAbajo();
        }
        
        //moverse a la columna correcta
        for(Integer j = 1; j < columna; j++){
            if(actual.getDerecha() == null){
                return -1;
            }
            actual = actual.getDerecha();
        }
        return actual.getValor();
    }
    
    //metodo para operaciones entre celdas
    public void operacciones(Integer fila, Integer columna){
        
    }
    
    //metodo de impresion de la hoja 
    public void imprimir(){
        celda filaActual = inicio;
        while(filaActual != null){
            celda columnaActual = filaActual;
            while(columnaActual != null){
                System.out.print(columnaActual.getValor() + "\t");
                columnaActual = columnaActual.getDerecha();
            }
            System.out.println();
            filaActual = filaActual.getAbajo();
        }
    }
}
