
package com.mycompany.practica2.models;


public class Enemigo extends MovilHorizontal {
    
    private volatile boolean escapo = false;
    
    public Enemigo(int x, int y, int ancho, int alto, int velocidad){
        super(x, y, ancho, alto, velocidad);
    }
    
    public void marcarEscape(){
        escapo = true;
    }
    
    public boolean escapo(){
        return escapo;
    }
    
}
