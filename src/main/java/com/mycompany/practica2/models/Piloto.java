
package com.mycompany.practica2.models;

public class Piloto {
    
    private String nombre;
    private NivelDificultad nivel;
    private int mejorPuntaje;

    public Piloto(String nombre, NivelDificultad nivel) {
        this(nombre, nivel,0);
    }
    
    /*constructor usado internamente por RegistroPilotos.cargar() para
    reconstruir un piloto ya existente (con su mejor puntaje previo)
    al leerlo desde el archivo de texto guardado en una ejecucion
    anterior.
    */
    public Piloto(String nombre, NivelDificultad nivel, int mejorPuntaje){
        this.nombre = nombre;
        this.nivel = nivel;
        this.mejorPuntaje = mejorPuntaje;
    }

    public String getNombre() {
        return nombre;
    }

    public NivelDificultad getNivel() {
        return nivel;
    }

    public int getMejorPuntaje() {
        return mejorPuntaje;
    }
    
    /* Actualiza el mejor puntaje solo si el nuevo puntaje es mayor.
    la vamos a usar cuanddo termine una partida (fases siguientes).
    */
    public void registrarPuntaje(int puntajeObtenido){
        if (puntajeObtenido > this.mejorPuntaje){
            this.mejorPuntaje = puntajeObtenido;
        }
    }
    
    @Override
    public String toString(){
        return nombre + " - " + nivel.getNombreNave() + " (mejor puntaje: " + mejorPuntaje + ")";
    }
            
    
}
