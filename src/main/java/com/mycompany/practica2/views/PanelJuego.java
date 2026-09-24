
package com.mycompany.practica2.views;

import com.mycompany.practica2.models.NaveJugador;

import javax.swing.*;
import java.awt.*;

public class PanelJuego extends JPanel {
    
    private final NaveJugador nave;
    
    public PanelJuego(NaveJugador nave){
        this.nave = nave;
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(800, 500));
        setFocusable(true); // necesario para que el panel pueda recibir eventos de teclado     
    }
    
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);//limpia el fondo antes de redibujar
        
        if(nave.estaVivo()){
            g.setColor(Color.CYAN);
            g.fillRect(nave.getX(),nave.getY(), nave.getAncho(), nave.getAlto());
            
        }
        
        g.setColor(Color.WHITE);
        g.drawString("Nave: " + nave.getNivel().getNombreNave()
                + " (usa las flechas para moverte)", 10, 20);
    }
    
}
