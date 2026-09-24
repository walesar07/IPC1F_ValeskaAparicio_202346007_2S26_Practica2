
package com.mycompany.practica2.controllers;

import com.mycompany.practica2.models.Enemigo;


public class HiloEnemigo extends Thread{
    
    private static final int INTERVALO_MS = 20;
    
    private final Enemigo enemigo;
    
    public HiloEnemigo(Enemigo enemigo){
        this.enemigo = enemigo;
        setDaemon(true);
    }
    
    @Override
    public void run(){
        while(enemigo.estaVivo()){
            enemigo.trasladar(-enemigo.getVelocidad(), 0);
            
            if(enemigo.getX() + enemigo.getAncho()<0){
                enemigo.matar(); //salio de la pantalla por la izquierda
            }
            
            try{
                Thread.sleep(INTERVALO_MS);
            }catch(InterruptedException e){
                enemigo.matar();
            }
                    
        }
    }
    
    
    
}
