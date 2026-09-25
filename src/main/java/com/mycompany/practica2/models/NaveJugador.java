
package com.mycompany.practica2.models;

public class NaveJugador extends Movil {
    
    private static final int VIDAS_INICIALES = 3;
    
    private final NivelDificultad nivel;
    private volatile int vidas;
    private volatile long bloqueadaHastaMs;
    
    public NaveJugador(int x, int y, int ancho, int alto,
            NivelDificultad nivel){
        super(x, y, ancho, alto);
        this.nivel = nivel;
        this.vidas = VIDAS_INICIALES;
        this.bloqueadaHastaMs = 0;
        
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
    public int getVidas(){
         return vidas;
    }
    /*se llama cada vez que el jugador choca contra un enemigo. 
    al llegar a 0 vidas, se marcha la nave como no viva (matar()), lo cual
    detiene automaticamente el hilo de movimiento y hace que el panel deje
    de dibujarla - es la senal de "game over".
    */

    public void restarVida(){
        vidas--;
        if (vidas <= 0){
            vidas = 0;
            matar();
        }
    }
    
    /* bloquea el movimiento de la nave durante los milisegundos
    indicados (usado por el Asteroide/Bludger). HiloMovimientoJugador
    revisa estaBloqueada() antes de mover la nave.
    */
    public void bloquear(int milisegundos){
        bloqueadaHastaMs = System.currentTimeMillis() + milisegundos;
    }
    
    public boolean estaBloqueada(){
        return System.currentTimeMillis()< bloqueadaHastaMs;
    }

    
}
