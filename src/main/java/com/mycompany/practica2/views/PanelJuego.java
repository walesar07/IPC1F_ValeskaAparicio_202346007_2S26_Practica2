
package com.mycompany.practica2.views;

import com.mycompany.practica2.controllers.Arbitro;
import com.mycompany.practica2.models.*;

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
        
        dibujarObjetos(g);
                dibujarNave(g);
        dibujarTextoEstado(g);
    }
 
    private void dibujarObjetos(Graphics g) {
        Movil[] moviles = escena.instantanea();
        for (Movil movil : moviles) {
            if (!movil.estaVivo()) {
                continue;
            }
            g.setColor(colorPara(movil));
            g.fillRect(movil.getX(), movil.getY(), movil.getAncho(), movil.getAlto());
        }
    }
 
    private Color colorPara(Movil movil) {
        if (movil instanceof Enemigo) return Color.RED;
        if (movil instanceof Asteroide) return Color.GRAY;
        if (movil instanceof Quaffle) return Color.YELLOW;
        if (movil instanceof Snitch) return Color.ORANGE;
        return Color.MAGENTA; // no debería pasar, pero por si acaso
    }
 
    private void dibujarNave(Graphics g) {
        if (!nave.estaVivo()) {
            return;
        }
        g.setColor(nave.estaBloqueada() ? Color.DARK_GRAY : Color.CYAN);
        g.fillRect(nave.getX(), nave.getY(), nave.getAncho(), nave.getAlto());
    }
 
    private void dibujarTextoEstado(Graphics g) {
        g.setColor(Color.WHITE);
        g.drawString("Nave: " + nave.getNivel().getNombreNave()
                + "  (usa las flechas para moverte)", 10, 20);
        g.drawString("Vidas: " + nave.getVidas(), 10, 40);
        g.drawString("Puntaje: " + arbitro.getPuntaje(), 10, 60);
 
        if (nave.estaBloqueada()) {
            g.setColor(Color.LIGHT_GRAY);
            g.drawString("¡BLOQUEADA!", 10, 80);
        }
    }
}
        
        

        

