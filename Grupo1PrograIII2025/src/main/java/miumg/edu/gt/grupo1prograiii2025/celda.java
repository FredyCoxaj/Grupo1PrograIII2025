/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miumg.edu.gt.grupo1prograiii2025;

/**
 *
 * @author Freddy Elias
 */
public class celda {
    private Integer valor;
    private celda izquierda;
    private celda derecha;
    private celda arriba;
    private celda abajo;
    
    celda(Integer valor){
        this.valor = valor;
        this.izquierda = null;
        this.derecha = null;
        this.arriba = null;
        this.abajo = null;
    }

    public Integer getValor() {
        return valor;
    }

    public celda getIzquierda() {
        return izquierda;
    }

    public celda getDerecha() {
        return derecha;
    }

    public celda getArriba() {
        return arriba;
    }

    public celda getAbajo() {
        return abajo;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

    public void setIzquierda(celda izquierda) {
        this.izquierda = izquierda;
    }

    public void setDerecha(celda derecha) {
        this.derecha = derecha;
    }

    public void setArriba(celda arriba) {
        this.arriba = arriba;
    }

    public void setAbajo(celda abajo) {
        this.abajo = abajo;
    }
}
