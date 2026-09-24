
package com.mycompany.practica2.views;

import com.mycompany.practica2.controllers.Arbitro;
import com.mycompany.practica2.models.Enemigo;
import com.mycompany.practica2.models.Escena;
import com.mycompany.practica2.models.Movil;
import com.mycompany.practica2.models.NaveJugador;

import javax.swing.*;
import java.awt.*;

public class PanelJuego extends JPanel {
    
    private final NaveJugador nave;
    private final Escena escena;
    private final Arbitro arbitro;
    
    public PanelJuego(NaveJugador nave, Escena escena, Arbitro arbitro){
        this.nave = nave;
        this.escena = escena;
        this.arbitro = arbitro;
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(800, 500));
        setFocusable(true); // necesario para que el panel pueda recibir eventos de teclado     
    }
    
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);//limpia el fondo antes de redibujar
        
        dibujarEnemigos(g);
        
        if(nave.estaVivo()){
            g.setColor(Color.CYAN);
            g.fillRect(nave.getX(),nave.getY(), nave.getAncho(), nave.getAlto());
            
        }
        
        g.setColor(Color.WHITE);
        g.drawString("Nave: " + nave.getNivel().getNombreNave()
                + " (usa las flechas para moverte)", 10, 20);
        g.drawString("Puntaje: "+ arbitro.getPuntaje(), 10, 40);
    }
    
    private void dibujarEnemigos(Graphics g){
        Movil[] moviles = escena.instantanea();
        g.setColor(Color.RED);
        for(Movil movil : moviles){
            if (movil instanceof Enemigo && movil.estaVivo()){
                g.fillRect(movil.getX(), movil.getY(), movil.getAncho(),
                        movil.getAlto());
            }
                    
        }
    }
    
}
