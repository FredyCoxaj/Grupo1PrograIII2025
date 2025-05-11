/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntregableDos;

/**
 * Implementación completa de Árbol Binario de Búsqueda (BST)
 * con capacidad para guardar en base de datos
 */
public class ArbolBinario {
    private NodoAVL raiz;

    public void insertar(int clave) {
        raiz = insertar(raiz, clave);
    }
    
    public void eliminar(int clave) {
        raiz = eliminar(raiz, clave);
    }


    public boolean buscar(int clave) {
        return buscar(raiz, clave);
    }


    public NodoAVL getRaiz() {
        return raiz;
    }


    public String inOrder() {
        StringBuilder sb = new StringBuilder();
        inOrder(raiz, sb);
        return sb.toString();
    }

    private NodoAVL insertar(NodoAVL nodo, int clave) {
        if (nodo == null) {
            return new NodoAVL(clave);
        }

        if (clave < nodo.clave) {
            nodo.izquierdo = insertar(nodo.izquierdo, clave);
        } else if (clave > nodo.clave) {
            nodo.derecho = insertar(nodo.derecho, clave);
        }

        // Actualizar altura después de la inserción
        actualizarAltura(nodo);
        return nodo;
    }

    private NodoAVL eliminar(NodoAVL nodo, int clave) {
        if (nodo == null) return null;

        if (clave < nodo.clave) {
            nodo.izquierdo = eliminar(nodo.izquierdo, clave);
        } else if (clave > nodo.clave) {
            nodo.derecho = eliminar(nodo.derecho, clave);
        } else {
            // Caso 1: Nodo sin hijos o con un hijo
            if (nodo.izquierdo == null) {
                return nodo.derecho;
            } else if (nodo.derecho == null) {
                return nodo.izquierdo;
            }
            
            // Caso 2: Nodo con dos hijos
            nodo.clave = encontrarMinimo(nodo.derecho);
            nodo.derecho = eliminar(nodo.derecho, nodo.clave);
        }

        // Actualizar altura después de la eliminación
        actualizarAltura(nodo);
        return nodo;
    }

    private boolean buscar(NodoAVL nodo, int clave) {
        if (nodo == null) return false;
        if (clave == nodo.clave) return true;
        return clave < nodo.clave 
            ? buscar(nodo.izquierdo, clave) 
            : buscar(nodo.derecho, clave);
    }

    // ----------------------
    // Métodos Auxiliares
    // ----------------------

    private int encontrarMinimo(NodoAVL nodo) {
        return nodo.izquierdo == null 
            ? nodo.clave 
            : encontrarMinimo(nodo.izquierdo);
    }

    private void actualizarAltura(NodoAVL nodo) {
        if (nodo != null) {
            nodo.altura = 1 + Math.max(
                altura(nodo.izquierdo),
                altura(nodo.derecho)
            );
        }
    }

    private int altura(NodoAVL nodo) {
        return nodo == null ? 0 : nodo.altura;
    }

    private void inOrder(NodoAVL nodo, StringBuilder sb) {
        if (nodo != null) {
            inOrder(nodo.izquierdo, sb);
            sb.append(nodo.clave).append(" ");
            inOrder(nodo.derecho, sb);
        }
    }
}