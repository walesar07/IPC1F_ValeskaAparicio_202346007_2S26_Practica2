
package com.mycompany.practica2.models;


public class Partida {
    
    private final String nombrePiloto;
    private final NivelDificultad nivel;
    private final int puntajeObtenido;
 
    public Partida(String nombrePiloto, NivelDificultad nivel, int puntajeObtenido) {
        this.nombrePiloto = nombrePiloto;
        this.nivel = nivel;
        this.puntajeObtenido = puntajeObtenido;
    }
 
    public String getNombrePiloto() {
        return nombrePiloto;
    }
 
    public NivelDificultad getNivel() {
        return nivel;
    }
 
    public int getPuntajeObtenido() {
        return puntajeObtenido;
    }
 
    @Override
    public String toString() {
        return String.format("%-15s %-15s %5d pts",
                nombrePiloto, nivel.getNombreNave(), puntajeObtenido);
    }
}
    

