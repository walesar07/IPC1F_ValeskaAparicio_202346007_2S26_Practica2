
package com.mycompany.practica2.controllers;

import com.mycompany.practica2.models.Enemigo;
import com.mycompany.practica2.models.Escena;
import com.mycompany.practica2.models.Movil;
import com.mycompany.practica2.models.NaveJugador;


public class Arbitro extends Thread {
    
    private static final int INTERVALO_MS = 20;
    private static final int PUNTOS_POR_ENEMIGO = 5;
    
    private final Escena escena;
    private final NaveJugador jugador;
    private volatile boolean activo = true;
    private volatile int puntaje = 0;
    
    public Arbitro (Escena escena, NaveJugador jugador){
        this.escena = escena;
        this.jugador = jugador;
        setDaemon(true);
    }
    
    public int getPuntaje(){
        return puntaje;
        
    }
    public void detener(){
        activo = false;
    }
    
    @Override
    public void run(){
        while(activo){
            Movil[] copia = escena.instantanea();
            
            for(Movil movil : copia){
                if(movil.estaVivo() && chocan(jugador, movil)){
                    resolver(movil);
                }
            }
            escena.limpiarMuertos();
            
            try{
                Thread.sleep(INTERVALO_MS);
            }catch(InterruptedException e){
                activo = false;
            }
        }
    }
    
    /*define que pasa cuando el jugador choca contra un movil.
    por ahora solo manejamos enemigo; en la siguiente fase agregaremos 
    asteroide(bludger), Quaffle y Snitch con sus propias reglas.
    */
    private void resolver(Movil movil){
        if (movil instanceof Enemigo){
                movil.matar();
                puntaje += PUNTOS_POR_ENEMIGO;
        }
    }
    /* AABB: caja contra caja. son cuatro comparaciones y las cuatro
hacen falta (el error tipico es comparar solo x, y entonces detecta
colisiones aunque esten en alturas distintas)
*/
     public static boolean chocan(Movil a, Movil b){
         return a.getX() < b.getX() +b.getAncho()
             && a.getX() + a.getAncho() >b.getX()
             && a.getY() < b.getY() +b.getAlto()
             && a.getY() + a.getAlto() >b.getY();
     }

}

    

