
package com.mycompany.practica2.models;


public  abstract class MovilHorizontal extends Movil {
    
    private final int velocidad;
    
    public MovilHorizontal(int x, int y, int ancho, int alto,
            int velocidad){
        super(x, y, ancho, alto);
        this.velocidad = velocidad;
    }
    
    public int getVelocidad(){
        return velocidad;
    }
    
}
