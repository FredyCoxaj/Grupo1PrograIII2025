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
    NodoAVL raiz;

    public NodoAVL getRaiz() {
        return raiz;
    }

    private int altura(NodoAVL nodo) {
        return nodo == null ? 0 : nodo.altura;
    }

    private int max(int a, int b) {
        return (a > b) ? a : b;
    }

    private NodoAVL rotacionDerecha(NodoAVL y) {
        NodoAVL x = y.izquierdo;
        NodoAVL T2 = x.derecho;

        // rotacion
        x.derecho = y;
        y.izquierdo = T2;

        // Actualizar alturas
        y.altura = Math.max(altura(y.izquierdo), altura(y.derecho)) + 1;
        x.altura = Math.max(altura(x.izquierdo), altura(x.derecho)) + 1;

        // Retornar nueva raiz
        return x;
    }

    private NodoAVL rotacionIzquierda(NodoAVL x) {
        NodoAVL y = x.derecho;
        NodoAVL T2 = y.izquierdo;

        y.izquierdo = x;
        x.derecho = T2;

        x.altura = max(altura(x.izquierdo), altura(x.derecho)) + 1;
        y.altura = max(altura(y.izquierdo), altura(y.derecho)) + 1;

        return y;
    }

    private int getBalance(NodoAVL nodo) {
        return nodo == null ? 0 : altura(nodo.izquierdo) - altura(nodo.derecho);
    }

    public void insertar(int clave) {
        raiz = insertar(raiz, clave);
    }

    private NodoAVL insertar(NodoAVL nodo, int clave) {
        if (nodo == null)
            return new NodoAVL(clave);

        if (clave < nodo.clave)
            nodo.izquierdo = insertar(nodo.izquierdo, clave);
        else if (clave > nodo.clave)
            nodo.derecho = insertar(nodo.derecho, clave);
        else
            return nodo; // No se permiten duplicados

        nodo.altura = 1 + max(altura(nodo.izquierdo), altura(nodo.derecho));

        int balance = getBalance(nodo);

        // Casos de desbalance
        if (balance > 1 && clave < nodo.izquierdo.clave)
            return rotacionDerecha(nodo);

        if (balance < -1 && clave > nodo.derecho.clave)
            return rotacionIzquierda(nodo);

        if (balance > 1 && clave > nodo.izquierdo.clave) {
            nodo.izquierdo = rotacionIzquierda(nodo.izquierdo);
            return rotacionDerecha(nodo);
        }

        if (balance < -1 && clave < nodo.derecho.clave) {
            nodo.derecho = rotacionDerecha(nodo.derecho);
            return rotacionIzquierda(nodo);
        }

        return nodo;
    }

    public void eliminar(int clave) {
        raiz = eliminar(raiz, clave);
    }

    private NodoAVL eliminar(NodoAVL raiz, int clave) {
        if (raiz == null)
            return raiz;

        if (clave < raiz.clave)
            raiz.izquierdo = eliminar(raiz.izquierdo, clave);
        else if (clave > raiz.clave)
            raiz.derecho = eliminar(raiz.derecho, clave);
        else {
            if ((raiz.izquierdo == null) || (raiz.derecho == null)) {
                NodoAVL temp = null;
                if (temp == raiz.izquierdo)
                    temp = raiz.derecho;
                else
                    temp = raiz.izquierdo;

                if (temp == null) {
                    temp = raiz;
                    raiz = null;
                } else
                    raiz = temp;
            } else {
                NodoAVL temp = minValueNode(raiz.derecho);
                raiz.clave = temp.clave;
                raiz.derecho = eliminar(raiz.derecho, temp.clave);
            }
        }

        if (raiz == null)
            return raiz;

        raiz.altura = max(altura(raiz.izquierdo), altura(raiz.derecho)) + 1;

        int balance = getBalance(raiz);

        // Balancear el árbol
        if (balance > 1 && getBalance(raiz.izquierdo) >= 0)
            return rotacionDerecha(raiz);

        if (balance > 1 && getBalance(raiz.izquierdo) < 0) {
            raiz.izquierdo = rotacionIzquierda(raiz.izquierdo);
            return rotacionDerecha(raiz);
        }

        if (balance < -1 && getBalance(raiz.derecho) <= 0)
            return rotacionIzquierda(raiz);

        if (balance < -1 && getBalance(raiz.derecho) > 0) {
            raiz.derecho = rotacionDerecha(raiz.derecho);
            return rotacionIzquierda(raiz);
        }

        return raiz;
    }

    private NodoAVL minValueNode(NodoAVL nodo) {
        NodoAVL actual = nodo;
        while (actual.izquierdo != null)
            actual = actual.izquierdo;
        return actual;
    }

    public boolean buscar(int clave) {
        return buscar(raiz, clave);
    }

    private boolean buscar(NodoAVL raiz, int clave) {
        if (raiz == null)
            return false;
        if (raiz.clave == clave)
            return true;
        if (raiz.clave > clave)
            return buscar(raiz.izquierdo, clave);
        return buscar(raiz.derecho, clave);
    }
}