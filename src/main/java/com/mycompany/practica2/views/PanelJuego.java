
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
        
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        dibujarObjetos(g2d);
                dibujarNave(g2d);
        dibujarTextoEstado(g2d);
    }
 
    private void dibujarObjetos(Graphics2D g2d) {
        Movil[] moviles = escena.instantanea();
        for (Movil movil : moviles) {
            if (!movil.estaVivo()) {
                continue;
            }
            if (movil instanceof Enemigo) {
                dibujarEnemigo(g2d, movil);
            } else if (movil instanceof Asteroide) {
                dibujarAsteroide(g2d, movil);
            } else if (movil instanceof Quaffle) {
                dibujarQuaffle(g2d, movil);
            } else if (movil instanceof Snitch) {
                dibujarSnitch(g2d, movil);
            } else if (movil instanceof Proyectil) {
                dibujarProyectil(g2d, movil);
            }
        }
    }
    
        /*
     Enemigo: flecha apuntando hacia la IZQUIERDA (viene hacia el
     jugador), espejo de la forma de la nave del jugador.
     */
    private void dibujarEnemigo(Graphics2D g2d, Movil movil) {
        int x = movil.getX();
        int y = movil.getY();
        int ancho = movil.getAncho();
        int alto = movil.getAlto();
 
        int[] xs = { x + ancho, x, x + ancho, x + (ancho * 3 / 5) };
        int[] ys = { y, y + (alto / 2), y + alto, y + (alto / 2) };
 
        g2d.setColor(Color.RED);
        g2d.fillPolygon(xs, ys, xs.length);
        g2d.setColor(Color.WHITE);
        g2d.drawPolygon(xs, ys, xs.length);
    }
 
    /*
     Asteroide: un polígono de 8 puntos con vértices desiguales, para
     que se vea como una roca irregular en vez de un círculo perfecto.
     */
    private void dibujarAsteroide(Graphics2D g2d, Movil movil) {
        int x = movil.getX();
        int y = movil.getY();
        int ancho = movil.getAncho();
        int alto = movil.getAlto();
 
        int[] xs = {
            x + ancho * 3 / 10, x + ancho * 7 / 10, x + ancho, x + ancho * 9 / 10,
            x + ancho * 6 / 10, x + ancho * 2 / 10, x, x + ancho / 10
        };
        int[] ys = {
            y, y + alto / 12, y + alto * 4 / 10, y + alto * 8 / 10,
            y + alto, y + alto * 9 / 10, y + alto * 5 / 10, y + alto * 2 / 10
        };
 
        g2d.setColor(Color.GRAY);
        g2d.fillPolygon(xs, ys, xs.length);
        g2d.setColor(Color.DARK_GRAY);
        g2d.drawPolygon(xs, ys, xs.length);
    }
 
    /*
     Contenedor Quaffle: una esfera dorada simple.
     */
    private void dibujarQuaffle(Graphics2D g2d, Movil movil) {
        g2d.setColor(Color.YELLOW);
        g2d.fillOval(movil.getX(), movil.getY(), movil.getAncho(), movil.getAlto());
        g2d.setColor(Color.ORANGE.darker());
        g2d.drawOval(movil.getX(), movil.getY(), movil.getAncho(), movil.getAlto());
    }
 
    /*
     Snitch Espacial: una esferita dorada con dos "alitas" ovaladas a
     los lados, como referencia al Snitch original.
     */
    private void dibujarSnitch(Graphics2D g2d, Movil movil) {
        int x = movil.getX();
        int y = movil.getY();
        int ancho = movil.getAncho();
        int alto = movil.getAlto();
 
        g2d.setColor(Color.WHITE);
        g2d.fillOval(x - ancho / 3, y + alto / 4, ancho / 2, alto / 3);
        g2d.fillOval(x + ancho * 5 / 6, y + alto / 4, ancho / 2, alto / 3);
 
        g2d.setColor(Color.ORANGE);
        g2d.fillOval(x, y, ancho, alto);
        g2d.setColor(Color.ORANGE.darker());
        g2d.drawOval(x, y, ancho, alto);
    }
 
    private void dibujarProyectil(Graphics2D g2d, Movil movil) {
        g2d.setColor(Color.GREEN);
        g2d.fillRect(movil.getX(), movil.getY(), movil.getAncho(), movil.getAlto());
    }
 
    
 
       /*
      Dibuja la nave como una silueta con forma de flecha (apuntando
      hacia la derecha, hacia donde dispara), en vez de un simple
      rectángulo. Es un Polygon de 4 puntos: esquina superior trasera,
     
     la punta (al frente, a media altura), esquina inferior trasera,
      y una muesca cóncava en medio de la parte trasera que le da la
      silueta característica de "nave".
     */
    private void dibujarNave(Graphics g) {
        if (!nave.estaVivo()) {
            return;
        }
 
        int x = nave.getX();
        int y = nave.getY();
        int ancho = nave.getAncho();
        int alto = nave.getAlto();
 
        int[] puntosX = { x, x + ancho, x, x + (ancho * 2 / 5) };
        int[] puntosY = { y, y + (alto / 2), y + alto, y + (alto / 2) };
 
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
 
        g2d.setColor(nave.estaBloqueada() ? Color.DARK_GRAY : Color.CYAN);
        g2d.fillPolygon(puntosX, puntosY, puntosX.length);
 
        g2d.setColor(Color.WHITE);
        g2d.drawPolygon(puntosX, puntosY, puntosX.length); // contorno, para que resalte
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
        
        

        

