
package com.mycompany.practica2.controllers;

import com.mycompany.practica2.models.NivelDificultad;
import com.mycompany.practica2.models.Piloto;


public class RegistroPilotos {
    
    private  static final int CAPACIDAD_INICIAL = 10;
    
    private Piloto [] pilotos;
    private int cantidad;
    
    public RegistroPilotos(){
        this.pilotos = new Piloto[CAPACIDAD_INICIAL]; 
        this.cantidad = 0;
            
    }
    /* si el arreglo ya esta lleno, crea uno nuevo del DOBLE del tamano y copia
    manualmente los elementos existentes.
    */
    private void aumentarCapacidadSiEsNecesario() {
        if (cantidad == pilotos.length) {
            Piloto[] nuevoArreglo = new Piloto[pilotos.length * 2];
            for (int i=0 ; i<pilotos.length; i++){
                nuevoArreglo[i] = pilotos[i];
            }
            pilotos = nuevoArreglo;
        }
    }
    /*verifica si ya existe un piloto con ese nombre (sin importar mayusculas/minusculas
    Recorrre solo hasta "cantidad", nunca hasta pilotos.length, porque el resto del arreglo
    puede estar vacio.
    */
    public boolean existePiloto(String nombre){
        for(int i=0; i<cantidad; i++){
            if(pilotos[i].getNombre().equalsIgnoreCase(nombre)){
                return true;
                
            }
        }
        return false;
    }
    /* intenta registrar un nuevo piloto aplicando las validaciones pedidas (sin datos duplicados
    o inconsistentes)
    @return null si el registro fue exitoso, o un mensaje de error explicando por que fallo.
    */
    public String registrarPiloto(String nombre, NivelDificultad nivel){
        if (nombre == null || nombre.trim().isEmpty()){
            return "El nombre del piloto no puede estar vacio.";
        }
        if (nombre.trim().length()<3){
            return "El nombre debe tener al menos 3 caracteres.";
        }
        if(nivel == null){
            return "Debes seleccionar un modelo de nave.";   
        }
        if (existePiloto(nombre)){
            return "Ya existe un piloto registrado con ese nombre.";
        }
        
        aumentarCapacidadSiEsNecesario();
        pilotos[cantidad] = new Piloto(nombre.trim(), nivel);
        cantidad ++;
        return null; //sin errores = registro exitoso
    }
    /* devuelve una "instantanea" del arreglo, del tamano exacto de pilotos
    registrados (sin huecos vacios al final).
    */
    public Piloto[] getPilotos(){
        Piloto[] copia = new Piloto[cantidad];
        for (int i=0; i<cantidad; i++){
            copia[i]= pilotos[i];
        }
        return copia;
    }
    
    public int getCantidad(){
        return cantidad;
    }
}
