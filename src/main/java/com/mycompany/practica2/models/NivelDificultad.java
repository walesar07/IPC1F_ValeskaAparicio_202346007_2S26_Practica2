
package com.mycompany.practica2.models;


public enum NivelDificultad {
    
    FACIL("Explorador", 6, 2000, "Alta velocidad/ Disparo lento(recarga cada 2 seg)"),
    NORMAL("Caza Estelar", 4, 1000, "Velocidad media / Disparo medio (cada 1 seg)"),
    DIFICIL("Acorazado", 2, 300, "Velocidad baja/ Disparo rapido (rafagas cada 0.3 seg)");
    
    private final String nombreNave;
    private final int velocidad; //pixeles por paso de movimiento
    private final int cadenciaDisparoMs;//milisegundos entre disparos (para Thread.sleep)
    private final String descripcion;

    private NivelDificultad(String nombreNave, int velocidad, int cadenciaDisparoMs, String descripcion) {
        this.nombreNave = nombreNave;
        this.velocidad = velocidad;
        this.cadenciaDisparoMs = cadenciaDisparoMs;
        this.descripcion = descripcion;
    }

    public String getNombreNave() {
        return nombreNave;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public int getCadenciaDisparoMs() {
        return cadenciaDisparoMs;
    }

    public String getDescripcion() {
        return descripcion;
    }
    
    @Override
    public String toString(){
        //Esto define coo se ve el valor cuando lo mostramos en un JComboBox o similar
        
        return nombreNave + "("+ descripcion + ")";
    }
    
    
}
