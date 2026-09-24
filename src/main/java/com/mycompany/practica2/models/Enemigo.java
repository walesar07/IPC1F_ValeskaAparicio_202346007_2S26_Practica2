
package com.mycompany.practica2.models;


public class Enemigo extends Movil {
    
    private final int velocidad;
    
    public Enemigo(int x, int y, int ancho, int alto, int velocidad){
        super(x, y, ancho, alto);
        this.velocidad = velocidad;
    }
    
    public int getVelocidad(){
        return velocidad;
    }
    
}
