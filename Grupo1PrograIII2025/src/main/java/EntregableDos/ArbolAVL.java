/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntregableDos;

import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Freddy Elias
 */
public class ArbolAVL {
    private NodoAVL raiz;

    // Obtener altura de un nodo (maneja nodos nulos)
    private int altura(NodoAVL nodo) {
        if (nodo == null) {
            return 0;
        }
        return nodo.altura;
    }

    // Actualizar altura de un nodo
    private void actualizarAltura(NodoAVL nodo) {
        if (nodo != null) {
            nodo.altura = 1 + Math.max(altura(nodo.izquierdo), altura(nodo.derecho));
        }
    }

    // Obtener factor de balance
    int factorBalance(NodoAVL nodo) {
        if (nodo == null) {
            return 0;
        }
        return altura(nodo.izquierdo) - altura(nodo.derecho);
    }

    // Rotación simple a la derecha (LL)
    private NodoAVL rotacionDerecha(NodoAVL y) {
        NodoAVL x = y.izquierdo;
        NodoAVL T2 = x.derecho;

        // Realizar rotación
        x.derecho = y;
        y.izquierdo = T2;

        // Actualizar alturas
        actualizarAltura(y);
        actualizarAltura(x);

        return x; // Nueva raíz
    }

    // Rotación simple a la izquierda (RR)
    private NodoAVL rotacionIzquierda(NodoAVL x) {
        NodoAVL y = x.derecho;
        NodoAVL T2 = y.izquierdo;

        // Realizar rotación
        y.izquierdo = x;
        x.derecho = T2;

        // Actualizar alturas
        actualizarAltura(x);
        actualizarAltura(y);

        return y; // Nueva raíz
    }

    // Rotación doble izquierda-derecha (LR)
    private NodoAVL rotacionIzquierdaDerecha(NodoAVL nodo) {
        nodo.izquierdo = rotacionIzquierda(nodo.izquierdo);
        return rotacionDerecha(nodo);
    }

    // Rotación doble derecha-izquierda (RL)
    private NodoAVL rotacionDerechaIzquierda(NodoAVL nodo) {
        nodo.derecho = rotacionDerecha(nodo.derecho);
        return rotacionIzquierda(nodo);
    }

    // Balancear el árbol
    private NodoAVL balancear(NodoAVL nodo) {
        if (nodo == null) {
            return null;
        }

        actualizarAltura(nodo);
        int balance = factorBalance(nodo);

        // Casos de desbalance:
        // Left Left (LL)
        if (balance > 1 && factorBalance(nodo.izquierdo) >= 0) {
            return rotacionDerecha(nodo);
        }

        // Right Right (RR)
        if (balance < -1 && factorBalance(nodo.derecho) <= 0) {
            return rotacionIzquierda(nodo);
        }

        // Left Right (LR)
        if (balance > 1 && factorBalance(nodo.izquierdo) < 0) {
            return rotacionIzquierdaDerecha(nodo);
        }

        // Right Left (RL)
        if (balance < -1 && factorBalance(nodo.derecho) > 0) {
            return rotacionDerechaIzquierda(nodo);
        }

        return nodo; // Ya está balanceado
    }

    // Insertar un nodo
    public void insertar(int clave) {
        raiz = insertar(raiz, clave);
    }

    private NodoAVL insertar(NodoAVL nodo, int clave) {
        // Inserción estándar en BST
        if (nodo == null) {
            return new NodoAVL(clave);
        }

        if (clave < nodo.clave) {
            nodo.izquierdo = insertar(nodo.izquierdo, clave);
        } else if (clave > nodo.clave) {
            nodo.derecho = insertar(nodo.derecho, clave);
        } else // No se permiten duplicados
        {
            return nodo;
        }

        // Balancear el árbol después de la inserción
        return balancear(nodo);
    }
    
    // Eliminar un nodo
    public void eliminar(int clave) {
        raiz = eliminar(raiz, clave);
    }

    private NodoAVL eliminar(NodoAVL nodo, int clave) {
        if (nodo == null) return null;

        if (clave < nodo.clave) {
            nodo.izquierdo = eliminar(nodo.izquierdo, clave);
        } else if (clave > nodo.clave) {
            nodo.derecho = eliminar(nodo.derecho, clave);
        } else {
            if (nodo.izquierdo == null || nodo.derecho == null) {
                nodo = (nodo.izquierdo != null) ? nodo.izquierdo : nodo.derecho;
            } else {
                NodoAVL temp = minimo(nodo.derecho);
                nodo.clave = temp.clave;
                nodo.derecho = eliminar(nodo.derecho, temp.clave);
            }
        }

        return balancear(nodo);
    }
    
    private NodoAVL minimo(NodoAVL nodo) {
        while (nodo.izquierdo != null) {
            nodo = nodo.izquierdo;
        }
        return nodo;
    }
    
    // Guardar árbol en archivo
    public void guardarEnArchivo(String nombreArchivo) {
        
    }

    // Método para imprimir el árbol (In-Order)
    public void inOrder() {
        inOrder(raiz);
    }

    private void inOrder(NodoAVL nodo) {
        if (nodo != null) {
            inOrder(nodo.izquierdo);
            System.out.print(nodo.clave + " ");
            inOrder(nodo.derecho);
        }
    }

    // Método para obtener la altura del árbol (público)
    public int altura() {
        return altura(raiz);
    }

    // Método para obtener la raíz (necesario para el dibujo)
    public NodoAVL getRaiz() {
        return raiz;
    }

    // Método para recorrido por niveles (útil para dibujo)
    public void recorridoPorNiveles() {
        if (raiz == null) {
            return;
        }

        Queue<NodoAVL> cola = new LinkedList<>();
        cola.add(raiz);

        while (!cola.isEmpty()) {
            NodoAVL actual = cola.poll();
            System.out.print(actual.clave + "(" + factorBalance(actual) + ") ");

            if (actual.izquierdo != null) {
                cola.add(actual.izquierdo);
            }
            if (actual.derecho != null) {
                cola.add(actual.derecho);
            }
        }
    }
}
