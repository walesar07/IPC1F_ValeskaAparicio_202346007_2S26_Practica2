
package com.mycompany.practica2.models;

public class NaveJugador extends Movil {
    
    private final NivelDificultad nivel;
    
    public NaveJugador(int x, int y, int ancho, int alto,
            NivelDificultad nivel){
        super(x, y, ancho, alto);
        this.nivel = nivel;
        
    }
    
    public NivelDificultad getNivel(){
        return nivel;
    }
    
    /* cuantos pixeles se mueve por cada actualizacion del hilo 
    de movimiento. Viene directamente del nivel de dificultad 
    elegido al crear al piloto.
    */
    public int getVelocidad(){
        return nivel.getVelocidad();
        
    }

    
}
