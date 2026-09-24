
package com.mycompany.practica2.controllers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ControlTeclado implements KeyListener {
    
    private volatile boolean arriba;
    private volatile boolean abajo;
    private volatile boolean izquierda;
    private volatile boolean derecha;
    
    @Override
    public void keyPressed(KeyEvent e){
        actualizarBandera(e.getKeyCode(), true);
    }
    @Override
    public void keyReleased(KeyEvent e){
        actualizarBandera(e.getKeyCode(), false);
    }
   @Override
   public void keyTyped(KeyEvent e){
       //no lo necesitamos, pero la interfaz KeyListener obliga
       //a implementar los 3 metodos.
   }
   private void actualizarBandera(int codigoTecla, boolean presionada){
       switch (codigoTecla){
           case KeyEvent.VK_UP:
               arriba = presionada;
               break;
           case KeyEvent.VK_DOWN:
               abajo = presionada;
               break;
           case KeyEvent.VK_LEFT:
               izquierda = presionada;
               break;
           case KeyEvent.VK_RIGHT:
               derecha = presionada;
               break;
           default:
               break;
       }
   }
   
   public boolean isArriba(){
       return arriba;
   }
   
   public boolean isAbajo(){
       return abajo;
       
   }
   
   public boolean isIzquierda(){
       return izquierda;
   }
   
   public boolean isDerecha(){
       return derecha;
   }
    
}
