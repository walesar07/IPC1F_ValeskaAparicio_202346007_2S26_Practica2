
package com.mycompany.practica2.controllers;

import com.mycompany.practica2.models.NivelDificultad;
import com.mycompany.practica2.models.Piloto;

import java.io.IOException;

public class RegistroPilotos {
    
    private  static final int CAPACIDAD_INICIAL = 10;
    private static final String ARCHIVO_PILOTOS = "pilotos.txt";
    private static final String SEPARADOR = ";";
    
    private Piloto [] pilotos;
    private int cantidad;
    private final GestorPersistencia gestorPersistencia;
    
    public RegistroPilotos(){
        this.pilotos = new Piloto[CAPACIDAD_INICIAL]; 
        this.cantidad = 0;
        this.gestorPersistencia = new GestorPersistencia();
            
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
    
        /*
      Elimina al piloto con ese nombre (sin importar mayúsculas), si
      existe. Recorre el arreglo y, al encontrarlo, recorre las
      posiciones siguientes "un paso hacia atrás" para no dejar un
      hueco vacío en medio del arreglo.
     
      @return true si se encontró y eliminó, false si no existía.
     */
    public boolean eliminarPiloto(String nombre) {
        for (int i = 0; i < cantidad; i++) {
            if (pilotos[i].getNombre().equalsIgnoreCase(nombre)) {
                for (int j = i; j < cantidad - 1; j++) {
                    pilotos[j] = pilotos[j + 1];
                }
                pilotos[cantidad - 1] = null;
                cantidad--;
                return true;
            }
        }
        return false;
    }
    
    /*convierte cada piloto a su linea de texto y le pide a 
    GestorPersistencia que la escriba en el archivo.
    */
    public void guardar() throws IOException{
     
    String[] lineas = new String[cantidad];
        for (int i = 0; i < cantidad; i++) {
            Piloto piloto = pilotos[i];
            lineas[i] = piloto.getNombre() + SEPARADOR
                    + piloto.getNivel().name() + SEPARADOR
                    + piloto.getMejorPuntaje();
        }
        gestorPersistencia.guardarLineas(ARCHIVO_PILOTOS, lineas);
    }
    
 /*
      Le pide a GestorPersistencia las líneas guardadas y reconstruye
      cada Piloto a partir de su línea de texto.
     */
    public void cargar() throws IOException {
        String[] lineas = gestorPersistencia.cargarLineas(ARCHIVO_PILOTOS);
 
        for (int i = 0; i < lineas.length; i++) {
            String[] campos = lineas[i].split(SEPARADOR);
            String nombre = campos[0];
            NivelDificultad nivel = NivelDificultad.valueOf(campos[1]);
            int mejorPuntaje = Integer.parseInt(campos[2]);
 
            aumentarCapacidadSiEsNecesario();
            pilotos[cantidad] = new Piloto(nombre, nivel, mejorPuntaje);
            cantidad++;
        }
    }
}
   
