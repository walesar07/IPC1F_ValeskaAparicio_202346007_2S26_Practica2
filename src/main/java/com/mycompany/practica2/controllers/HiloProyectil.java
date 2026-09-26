
package com.mycompany.practica2.controllers;

import com.mycompany.practica2.models.Proyectil;


public class HiloProyectil extends Thread {
 
    private static final int INTERVALO_MS = 15;
 
    private final Proyectil proyectil;
    private final int anchoPantalla;
 
    public HiloProyectil(Proyectil proyectil, int anchoPantalla) {
        this.proyectil = proyectil;
        this.anchoPantalla = anchoPantalla;
        setDaemon(true);
    }
 
    @Override
    public void run() {
        while (proyectil.estaVivo()) {
            proyectil.trasladar(proyectil.getVelocidad(), 0);
 
            if (proyectil.getX() > anchoPantalla) {
                proyectil.matar(); // salió de la pantalla por la derecha
            }
 
            try {
                Thread.sleep(INTERVALO_MS);
            } catch (InterruptedException e) {
                proyectil.matar();
            }
        }
    }
}
