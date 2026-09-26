
package com.mycompany.practica2.controllers;

import com.mycompany.practica2.models.Escena;
import com.mycompany.practica2.models.NaveJugador;
import com.mycompany.practica2.models.Proyectil;

public class HiloDisparo extends Thread {
 
    private static final int ANCHO_PROYECTIL = 10;
    private static final int ALTO_PROYECTIL = 4;
    private static final int VELOCIDAD_PROYECTIL = 12;
    private static final int INTERVALO_ESPERA_MS = 20;
 
    private final NaveJugador nave;
    private final ControlTeclado teclado;
    private final Escena escena;
    private final int anchoPantalla;
    private volatile boolean activo = true;
 
    public HiloDisparo(NaveJugador nave, ControlTeclado teclado, Escena escena, int anchoPantalla) {
        this.nave = nave;
        this.teclado = teclado;
        this.escena = escena;
        this.anchoPantalla = anchoPantalla;
        setDaemon(true);
    }
 
    public void detener() {
        activo = false;
    }
 
    @Override
    public void run() {
        while (activo && nave.estaVivo()) {
            if (teclado.isDisparar() && !nave.estaBloqueada()) {
                dispararProyectil();
                dormir(nave.getNivel().getCadenciaDisparoMs());
            } else {
                dormir(INTERVALO_ESPERA_MS);
            }
        }
    }
 
    private void dispararProyectil() {
        int xInicial = nave.getX() + nave.getAncho();
        int yInicial = nave.getY() + (nave.getAlto() / 2) - (ALTO_PROYECTIL / 2);
 
        Proyectil proyectil = new Proyectil(xInicial, yInicial, ANCHO_PROYECTIL, ALTO_PROYECTIL, VELOCIDAD_PROYECTIL);
        escena.agregar(proyectil);
 
        new HiloProyectil(proyectil, anchoPantalla).start();
    }
 
    private void dormir(int milisegundos) {
        try {
            Thread.sleep(milisegundos);
        } catch (InterruptedException e) {
            activo = false;
        }
    }
}
