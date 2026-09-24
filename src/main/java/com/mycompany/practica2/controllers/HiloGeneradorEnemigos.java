
package com.mycompany.practica2.controllers;

import com.mycompany.practica2.models.Enemigo;
import com.mycompany.practica2.models.Escena;

import java.util.Random;

public class HiloGeneradorEnemigos extends Thread {
    
    private static final int INTERVALO_GENERACION_MS = 1200;
    private static final int ANCHO_ENEMIGO = 40;
    private static final int ALTO_ENEMIGO = 30;
    private static final int VELOCIDAD_MINIMA = 3;
    private static final int VELOCIDAD_MAXIMA = 6;
    
    private final Escena escena;
    private final int anchoPantalla;
    private final int altoPantalla;
    private final Random random = new Random();
    private volatile boolean activo = true;
    
    public HiloGeneradorEnemigos(Escena escena, int anchoPantalla, int altoPantalla){
        this.escena = escena;
        this.anchoPantalla = anchoPantalla;
        this.altoPantalla = altoPantalla;
        setDaemon(true);
       
    }
    
    public void detener(){
        activo = false;
        
    }
    @Override
    public void run(){
        while(activo){
            crearEnemigo();
            
            try{
                Thread.sleep(INTERVALO_GENERACION_MS);
            }catch(InterruptedException e){
                activo = false;
            }
        }
    }   
    
    private void crearEnemigo(){
        int yAleatorio = random.nextInt(Math.max(altoPantalla - ALTO_ENEMIGO, 1));
        int velocidad = VELOCIDAD_MAXIMA + random.nextInt(VELOCIDAD_MAXIMA - VELOCIDAD_MINIMA + 1);
        
        Enemigo enemigo = new Enemigo(anchoPantalla, yAleatorio, ANCHO_ENEMIGO,
        ALTO_ENEMIGO, velocidad);
        escena.agregar(enemigo);
        
        HiloEnemigo hiloEnemigo = new HiloEnemigo(enemigo);
        hiloEnemigo.start();
    }
    
    
    
}
