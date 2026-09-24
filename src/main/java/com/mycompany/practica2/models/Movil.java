
package com.mycompany.practica2.models;

public abstract class Movil {
    
    protected volatile int x;
    protected volatile int y;
    protected final int ancho;
    protected final int alto;
    private volatile boolean vivo = true;
    
    public Movil(int x, int y, int ancho, int alto){
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getAncho() {
        return ancho;
    }

    public int getAlto() {
        return alto;
    }

    public boolean estaVivo() {
        return vivo;
    }
    
    public void matar(){
        vivo= false;
    }
    /*mueve el objeto de forma relativa (suma dx, dy a su posicion
    actual.*/
    public void trasladar(int dx,int dy){
        this.x += dx;
        this.y += dy;
    }
    /* establece la posicion de forma absoluta. La usamos,
    por ejemplo, cuando hay que recortar la posicion para que 
    no se salga de la pantalla
    */
    public void fijarPosicion(int nuevoX, int nuevoY){
        this.x= nuevoX;
        this.y = nuevoY;
                
    }
    
}
