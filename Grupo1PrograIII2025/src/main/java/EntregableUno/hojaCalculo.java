/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntregableUno;

import EntregableUno.celda;

/**
 *
 * @author Freddy Elias
 */
public class hojaCalculo {

    private celda inicio;

    public hojaCalculo() {
        this.inicio = null;
    }

    public void insertar(int fila, int columna, Double valor) {
        celda nuevaCelda = new celda(valor);

        if (inicio == null) {
            inicio = nuevaCelda;
        } else {
            celda actualFila = inicio;
            celda actualColumna = inicio;

            // Moverse a la fila correcta
            for (int i = 1; i < fila; i++) {
                if (actualFila.getAbajo() == null) {
                    // Crear una nueva fila si no existe
                    celda nuevaFila = new celda(null);
                    actualFila.setAbajo(nuevaFila);
                    nuevaFila.setArriba(actualFila);
                }
                actualFila = actualFila.getAbajo();
            }

            // Moverse a la columna correcta
            for (int j = 1; j < columna; j++) {
                if (actualFila.getDerecha() == null) {
                    // Crear una nueva columna si no existe
                    celda nuevaColumna = new celda(null);
                    actualFila.setDerecha(nuevaColumna);
                    nuevaColumna.setIzquierda(actualFila);
                }
                actualFila = actualFila.getDerecha();
            }

            // Insertar la nueva celda en la posición correcta
            if (actualFila.getValor() == null) {
                // Si la celda actual está vacía, simplemente reemplazamos su valor
                actualFila.setValor(valor);
            } else {
                // Si la celda actual no está vacía, insertamos la nueva celda
                nuevaCelda.setIzquierda(actualFila.getIzquierda());
                nuevaCelda.setDerecha(actualFila);
                nuevaCelda.setArriba(actualFila.getArriba());
                nuevaCelda.setAbajo(actualFila.getAbajo());

                if (actualFila.getIzquierda() != null) {
                    actualFila.getIzquierda().setDerecha(nuevaCelda);
                }
                if (actualFila.getArriba() != null) {
                    actualFila.getArriba().setAbajo(nuevaCelda);
                }
                if (actualFila.getDerecha() != null) {
                    actualFila.getDerecha().setIzquierda(nuevaCelda);
                }
                if (actualFila.getAbajo() != null) {
                    actualFila.getAbajo().setArriba(nuevaCelda);
                }

                // Si la celda actual es el inicio, actualizar el puntero de inicio
                if (actualFila == inicio) {
                    inicio = nuevaCelda;
                }
            }
        }
    }

    //metodo de obtencion de datos de una celda especifica
    public Double obtenerValor(Integer fila, Integer columna) {
        celda actual = inicio;

        //moverse a la fila correcta
        for (Integer i = 1; i < fila; i++) {
            if (actual.getAbajo() == null) {
                return null;
            }
            actual = actual.getAbajo();
        }

        //moverse a la columna correcta
        for (Integer j = 1; j < columna; j++) {
            if (actual.getDerecha() == null) {
                return null;
            }
            actual = actual.getDerecha();
        }
        return actual.getValor();
    }

    public void eliminarCelda(int fila, int columna) {
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

        // Establecer el valor de la celda como null (vacía)
        celdaActual.setValor(null);

        System.out.println("Celda [" + fila + "," + columna + "] eliminada (valor establecido como null).");
    }

    // Método para modificar el valor de una celda existente***
    public void modificarValor(int fila, int columna, Double nuevoValor) {
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
    public void operacciones(Integer fila, Integer columna) {

    }

    //metodo de impresion de la hoja 
    public void imprimir() {
        celda filaActual = inicio;
        while (filaActual != null) {
            celda columnaActual = filaActual;
            while (columnaActual != null) {
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

    public celda getInicio() {
        return inicio;
    }

    public boolean validarCelda(int fila, int columna) {
        if (fila < 1 || columna < 1) {
            return false;
        }

        celda actual = inicio;
        for (int i = 1; i < fila && actual != null; i++) {
            actual = actual.getAbajo();
        }

        if (actual == null) {
            return false;
        }

        for (int j = 1; j < columna && actual != null; j++) {
            actual = actual.getDerecha();
        }

        return actual != null;
    }
}
