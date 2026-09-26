
package com.mycompany.practica2.controllers;

import com.mycompany.practica2.models.*;

public class Arbitro extends Thread {
    
    private static final int INTERVALO_MS = 20;
    private static final int MS_BLOQUEO_ASTEROIDE = 2000;
    private static final int PUNTOS_QUAFFLE = 10;
    private static final int PUNTOS_SNITCH = 150;
    private static final int PUNTOS_ENEMIGO_DESTRUIDO = 5;
    
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
            
            revisarColisionesJugador(copia);
            revisarColisionesProyectiles(copia);
            
            escena.limpiarMuertos();
            
            try{
                Thread.sleep(INTERVALO_MS);
            }catch(InterruptedException e){
                activo = false;
            }
        }
    }
    /*COLISIONES ENTRE LA NAVE DEL JUGADOR Y CUALQUIER OBJETO QUE NO
    SEA UN PROYECTIL (LOS PROYECTILES SON DEL PROPIO JUGADOR, NOLE 
    HACEN DA;O A SU NAVE).
    */
    private void revisarColisionesJugador(Movil[] copia){
        for(Movil movil : copia){
                if (movil instanceof Proyectil){
                    continue;
                }
                if(movil.estaVivo() && chocan(jugador, movil)){
                    resolver(movil);
                }
            }
    }
    
    /* colisiones entre cada proyectil vivo y cada enemigo vivo.
    un mismo proyectil solo puede destuir un enemigo(break al encontrar
    el primero).
    */
     private void revisarColisionesProyectiles(Movil[] copia) {
        for (Movil posibleProyectil : copia) {
            if (!(posibleProyectil instanceof Proyectil) || !posibleProyectil.estaVivo()) {
                continue;
            }
            for (Movil posibleEnemigo : copia) {
                if (!(posibleEnemigo instanceof Enemigo) || !posibleEnemigo.estaVivo()) {
                    continue;
                }
                if (chocan(posibleProyectil, posibleEnemigo)) {
                    posibleProyectil.matar();
                    posibleEnemigo.matar();
                    puntaje += PUNTOS_ENEMIGO_DESTRUIDO;
                    break;
                }
            }
        }
    }
    
    /*define que pasa cuando el jugador choca contra un movil, enemigo,
    asteroide(bludger), Quaffle y Snitch con sus propias reglas.
    */
    private void resolver(Movil movil){
        if (movil instanceof Enemigo){
                movil.matar();
                jugador.restarVida();
        }else if(movil instanceof Asteroide){
            movil.matar();
            jugador.bloquear(MS_BLOQUEO_ASTEROIDE);
        } else if (movil instanceof Quaffle){
            movil.matar();
            puntaje += PUNTOS_QUAFFLE;
        } else if (movil instanceof Snitch){
            movil.matar();
            puntaje += PUNTOS_SNITCH;
            destruirEnemigosVisibles(); 
                    
        }
    }
    
    private void destruirEnemigosVisibles(){
        Movil[]copia = escena.instantanea();
        for (Movil movil : copia){
            if(movil instanceof Enemigo && movil.estaVivo()) {
                movil.matar();
            }
        }
    }
    /* AABB: caja contra caja. son cuatro comparaciones y las cuatro
hacen falta.
*/
     public static boolean chocan(Movil a, Movil b){
         return a.getX() < b.getX() +b.getAncho()
             && a.getX() + a.getAncho() >b.getX()
             && a.getY() < b.getY() +b.getAlto()
             && a.getY() + a.getAlto() >b.getY();
     }

}

    

