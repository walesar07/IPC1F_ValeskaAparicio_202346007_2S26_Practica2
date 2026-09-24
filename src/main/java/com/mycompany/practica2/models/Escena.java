package com.mycompany.practica2.models;


public class Escena {
    
    private static final int CAPACIDAD_INICIAL = 20;
    
    private Movil[] moviles;
    private int cantidad;
    
    public Escena(){
        this.moviles = new Movil[CAPACIDAD_INICIAL];
        this.cantidad = 0;
    }
    
    public synchronized void agregar (Movil movil){
        aumentarCapacidadSiEsNecesario();
        moviles[cantidad] = movil;
        cantidad++;
        
    }
    
    private void aumentarCapacidadSiEsNecesario(){
        if (cantidad == moviles.length){
            Movil[] nuevoArreglo = new Movil[moviles.length*2];
            for(int i = 0; i<moviles.length; i++){
                nuevoArreglo[i]= moviles[i];
            }
            moviles= nuevoArreglo;
        }
    }
    /* devuelve una copia del arreglo, del tamano exacto en uso. Los 
    hilos que solo necesitan REVISAR los moviles (arbitro, el panel
    que dibuja) deben usar esta copia, para no verse afectados si justo
    en ese instante otro hilo agrega un nuevo enemigo.
    */
    public synchronized Movil[] instantanea(){
        Movil[]copia = new Movil[cantidad];
        for(int i = 0; i<cantidad; i++){
            copia[i]= moviles[i];
        }
        return copia;
    }
    
    /* elimina del arreglo interno los moviles que ya no estan vivos
    (fueron destruidos en una colision o salieron de la pantalla),
    compactando el resto para no dejar huecos.
    */
    public synchronized void limpiarMuertos(){
        int nuevaCantidad = 0;
        for(int i=0; i<cantidad; i++){
            if(moviles[i].estaVivo()){
                moviles[nuevaCantidad] = moviles[i];
                nuevaCantidad++;
            }
        }
        for (int i= nuevaCantidad; i<cantidad; i++){
            moviles[i] = null;
        }
        cantidad = nuevaCantidad;
    }
    
}
