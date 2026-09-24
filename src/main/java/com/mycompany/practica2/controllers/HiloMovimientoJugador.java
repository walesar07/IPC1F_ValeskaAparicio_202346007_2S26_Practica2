
package com.mycompany.practica2.controllers;

import com.mycompany.practica2.models.NaveJugador;
import com.mycompany.practica2.views.PanelJuego;


public class HiloMovimientoJugador extends Thread {
    
    private static final int INTERVALO_MS = 15; //-60 actualizaciones por segundo
    
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
                Thread.sleep(INTERVALO_MS);
            }catch(InterruptedException e){
                activo = false;
            }
        }
    }
    
    private void actualizarPosicion(){
        int velocidad = nave.getVelocidad();
        int nuevoX = nave.getX();
        int nuevoY = nave.getY();
        
        if (teclado.isArriba()) nuevoY -= velocidad;
        if (teclado.isAbajo()) nuevoY += velocidad;
        if (teclado.isIzquierda()) nuevoX -= velocidad;
        if (teclado.isDerecha()) nuevoX += velocidad;
        
        //evitar que la nave se salga de los limites visibles del panel
        int limiteX = panel.getWidth() - nave.getAncho();
        int limiteY = panel.getHeight() - nave.getAlto();
        nuevoX = Math.max(0, Math.min(nuevoX, Math.max(limiteX, 0)));
        nuevoY = Math.max(0, Math.min(nuevoY, Math.max(limiteY, 0)));
        
        nave.fijarPosicion(nuevoX, nuevoY);
        
    }
    
    
}
