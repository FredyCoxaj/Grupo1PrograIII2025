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
    
    // Método para insertar un valor en una celda específica***
    public void insertarValor(int fila, int columna, Integer valor) {
        celda celdaActual = inicio;

        // Navegar hasta la fila deseada
        for (int i = 1; i < fila; i++) {
            if (celdaActual.getAbajo() == null) {
                // Si no existe la fila, la creamos
                celda nuevaFila = new celda(null);
                celdaActual.setAbajo(nuevaFila);
                nuevaFila.setArriba(celdaActual);
            }
            celdaActual = celdaActual.getAbajo();
        }

        // Navegar hasta la columna deseada
        for (int j = 1; j < columna; j++) {
            if (celdaActual.getDerecha() == null) {
                // Si no existe la columna, la creamos
                celda nuevaColumna = new celda(null);
                celdaActual.setDerecha(nuevaColumna);
                nuevaColumna.setIzquierda(celdaActual);
            }
            celdaActual = celdaActual.getDerecha();
        }

        // Asignar el valor a la celda
        celdaActual.setValor(valor);
    }
    // Método para modificar el valor de una celda existente***
    public void modificarValor(int fila, int columna, Integer nuevoValor) {
        celda celdaActual = inicio;

        // Moverse a la fila correcta
        for (int i = 1; i < fila; i++) {
            if (celdaActual.getAbajo() == null) {
                System.out.println("La fila " + fila + " no existe.");
                return; // La fila no existe
            }
            celdaActual = celdaActual.getAbajo();
        }

        // Moverse a la columna correcta
        for (int j = 1; j < columna; j++) {
            if (celdaActual.getDerecha() == null) {
                System.out.println("La columna " + columna + " no existe.");
                return; // La columna no existe
            }
            celdaActual = celdaActual.getDerecha();
        }

        // Modificar el valor de la celda
        celdaActual.setValor(nuevoValor);
        System.out.println("Valor modificado en la celda [" + fila + "," + columna + "].");
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
    private celda celdaActual; // Celda actualmente seleccionada

    // Método para mover a la derecha
    public void moverDerecha() {
        if (celdaActual.getDerecha() != null) {
            celdaActual = celdaActual.getDerecha();
        }
    }

    // Método para mover a la izquierda
    public void moverIzquierda() {
        if (celdaActual.getIzquierda() != null) {
            celdaActual = celdaActual.getIzquierda();
        }
    }

    // Método para mover arriba
    public void moverArriba() {
        if (celdaActual.getArriba() != null) {
            
            celdaActual = celdaActual.getArriba();
        }
    }

    // Método para mover abajo
    public void moverAbajo() {
        if (celdaActual.getAbajo() != null) {
            celdaActual = celdaActual.getAbajo();
        }
    }

}
