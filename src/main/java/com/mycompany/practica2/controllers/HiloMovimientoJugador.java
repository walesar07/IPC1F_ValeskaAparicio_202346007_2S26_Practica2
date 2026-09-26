
package com.mycompany.practica2.controllers;

import com.mycompany.practica2.models.NaveJugador;
import com.mycompany.practica2.views.PanelJuego;


public class HiloMovimientoJugador extends Thread {
    
    private static final int PASO_PIXELES = 5; //pixeles por actualizacion, igual para los 3 niveles.
    
    private final NaveJugador nave;
    private final ControlTeclado teclado;
    private final PanelJuego panel;
    private volatile boolean activo = true;
    
    public HiloMovimientoJugador(NaveJugador nave, ControlTeclado teclado,
            PanelJuego panel){
        
        this.nave = nave;
        this.teclado = teclado;
        this.panel = panel;
        setDaemon(true);
    }
    public void detener(){
        activo = false;
    }
    
    @Override
    public void run(){
        while(activo && nave.estaVivo()){
            actualizarPosicion();
            panel.repaint();//repaint() es seguro de llamar desde cualquier hilo
            
            try {
                Thread.sleep(nave.getNivel().getIntervaloMovimientoMs());
            }catch(InterruptedException e){
                activo = false;
            }
        }
    }
    
    private void actualizarPosicion(){
        if(nave.estaBloqueada()){
            return;//mientras esta bloqueada, no responde al teclado
        }
        
        int nuevoX = nave.getX();
        int nuevoY = nave.getY();
        
        if (teclado.isArriba()) nuevoY -= PASO_PIXELES;
        if (teclado.isAbajo()) nuevoY += PASO_PIXELES;
        if (teclado.isIzquierda()) nuevoX -= PASO_PIXELES;
        if (teclado.isDerecha()) nuevoX += PASO_PIXELES;
        
        //evitar que la nave se salga de los limites visibles del panel
        int limiteX = panel.getWidth() - nave.getAncho();
        int limiteY = panel.getHeight() - nave.getAlto();
        nuevoX = Math.max(0, Math.min(nuevoX, Math.max(limiteX, 0)));
        nuevoY = Math.max(0, Math.min(nuevoY, Math.max(limiteY, 0)));
        
        nave.fijarPosicion(nuevoX, nuevoY);
        
    }
    
    
}

