
package com.mycompany.practica2.controllers;

import com.mycompany.practica2.models.*;

import java.util.Random;

public class HiloGeneradorObjetos extends Thread {
    
    private static final int INTERVALO_GENERACION_MS = 1200;
    
    //PROBABILIDADES ACUMULADAS (de 0 a 99): enemigo 55%, asteroides 20%,
    //quaflle 20%, snitch 5%.4
    private static final int LIMITE_ENEMIGO = 55;
    private static final int LIMITE_ASTEROIDE = 75;
    private static final int LIMITE_QUAFFLE = 95;
    
    private final Escena escena;
    private final int anchoPantalla;
    private final int altoPantalla;
    private final Random random = new Random();
    private volatile boolean activo = true;
    
    public HiloGeneradorObjetos(Escena escena, int anchoPantalla, int altoPantalla){
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
            crearObjetoAleatorio();
            
            try{
                Thread.sleep(INTERVALO_GENERACION_MS);
            } catch (InterruptedException e){
                activo = false;
            }
        }
    }
    
    private void crearObjetoAleatorio(){
        int probabilidad = random.nextInt(100);
        MovilHorizontal nuevo;
        
        if(probabilidad < LIMITE_ENEMIGO){
            nuevo = new Enemigo(anchoPantalla, alturaAleatoria(30), 40, 30, velocidadAleatoria(3,6));
            
        }else if (probabilidad < LIMITE_ASTEROIDE) {
            nuevo = new Asteroide(anchoPantalla, alturaAleatoria(45), 45, 45, 
            velocidadAleatoria(2, 4));
        }else if (probabilidad < LIMITE_QUAFFLE) {
            nuevo = new Quaffle(anchoPantalla, alturaAleatoria(25), 25, 25, 
            velocidadAleatoria(3, 5));
        }else {
            nuevo = new Snitch(anchoPantalla, alturaAleatoria(20), 20, 20, 
            velocidadAleatoria(5, 8));
        }
        
        escena.agregar(nuevo);
        new HiloMovimientoHorizontal(nuevo).start();
    }
    
    private int alturaAleatoria(int altoObjeto){
        return random.nextInt(Math.max(altoPantalla - altoObjeto, 1));
    }
    
    private int velocidadAleatoria(int minimo, int maximo){
        return minimo + random.nextInt(maximo - minimo + 1);
    }
           
    
}
