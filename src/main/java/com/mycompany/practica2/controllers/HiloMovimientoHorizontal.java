
package com.mycompany.practica2.controllers;

import com.mycompany.practica2.models.Enemigo;
import com.mycompany.practica2.models.MovilHorizontal;

public class HiloMovimientoHorizontal extends Thread {
    
    private static final int INTERVALO_MS = 20;
    
    private final MovilHorizontal movil;
    
    public HiloMovimientoHorizontal(MovilHorizontal movil){
        this.movil = movil;
        setDaemon(true);
    }
    
    @Override
    public void run(){
        while (movil.estaVivo()){
            movil.trasladar(-movil.getVelocidad(), 0);
            
            if(movil.getX() + movil.getAncho()<0){
                //si es un enemigo, se marca como "escapado" antes de 
                //matarlo, para que el Arbitro sepa que salio de la 
                //pantalla sin ser destruido (y aplique la penalizacion -1pt)
                if (movil instanceof Enemigo){
                    ((Enemigo) movil).marcarEscape();
                }
                movil.matar();
                
            }
            
            try {
                Thread.sleep(INTERVALO_MS);
            } catch (InterruptedException e){
                movil.matar();
            }
        }
    }
    
}
