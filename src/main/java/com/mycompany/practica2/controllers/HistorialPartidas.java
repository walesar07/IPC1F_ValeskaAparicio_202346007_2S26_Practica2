
package com.mycompany.practica2.controllers;

import com.mycompany.practica2.models.NivelDificultad;
import com.mycompany.practica2.models.Partida;

import java.io.IOException;


public class HistorialPartidas {
    
    
    private static final int CAPACIDAD_INICIAL = 10;
    private static final String ARCHIVO_HISTORIAL = "historial.txt";
    private static final String SEPARADOR = ";";
 
    private Partida[] partidas;
    private int cantidad;
    private final GestorPersistencia gestorPersistencia;
 
    public HistorialPartidas() {
        this.partidas = new Partida[CAPACIDAD_INICIAL];
        this.cantidad = 0;
        this.gestorPersistencia = new GestorPersistencia();
    }
 
    public void registrarPartida(Partida partida) {
        aumentarCapacidadSiEsNecesario();
        partidas[cantidad] = partida;
        cantidad++;
    }
 
    private void aumentarCapacidadSiEsNecesario() {
        if (cantidad == partidas.length) {
            Partida[] nuevoArreglo = new Partida[partidas.length * 2];
            for (int i = 0; i < partidas.length; i++) {
                nuevoArreglo[i] = partidas[i];
            }
            partidas = nuevoArreglo;
        }
    }
 
    /*
     Devuelve una copia de todo el historial, del tamaño exacto en uso.
     */
    public Partida[] getPartidas() {
        Partida[] copia = new Partida[cantidad];
        for (int i = 0; i < cantidad; i++) {
            copia[i] = partidas[i];
        }
        return copia;
    }
 
    /*
      Devuelve las mejores partidas, ordenadas de mayor a menor
      puntaje, limitando el resultado a "limite" elementos (para el
      Top N). Se ordena con un ordenamiento burbuja manual sobre una
      copia del arreglo, para no alterar el orden original en que se
      jugaron las partidas.
     */
    public Partida[] obtenerTop(int limite) {
        Partida[] copia = getPartidas();
 
        // Ordenamiento burbuja descendente por puntaje
        for (int i = 0; i < copia.length - 1; i++) {
            for (int j = 0; j < copia.length - 1 - i; j++) {
                if (copia[j].getPuntajeObtenido() < copia[j + 1].getPuntajeObtenido()) {
                    Partida temporal = copia[j];
                    copia[j] = copia[j + 1];
                    copia[j + 1] = temporal;
                }
            }
        }
 
        int tamanoResultado = Math.min(limite, copia.length);
        Partida[] top = new Partida[tamanoResultado];
        for (int i = 0; i < tamanoResultado; i++) {
            top[i] = copia[i];
        }
        return top;
    }


    /*
     Convierte cada partida a su línea de texto y le pide a
     GestorPersistencia que las escriba en el archivo.
     */
    public void guardar() throws IOException {
        String[] lineas = new String[cantidad];
        for (int i = 0; i < cantidad; i++) {
            Partida partida = partidas[i];
            lineas[i] = partida.getNombrePiloto() + SEPARADOR
                    + partida.getNivel().name() + SEPARADOR
                    + partida.getPuntajeObtenido();
        }
        gestorPersistencia.guardarLineas(ARCHIVO_HISTORIAL, lineas);
    }
 
    /*
      Le pide a GestorPersistencia las líneas guardadas y reconstruye
      cada Partida a partir de su línea de texto.
     */
    public void cargar() throws IOException {
        String[] lineas = gestorPersistencia.cargarLineas(ARCHIVO_HISTORIAL);
 
        for (int i = 0; i < lineas.length; i++) {
            String[] campos = lineas[i].split(SEPARADOR);
            String nombrePiloto = campos[0];
            NivelDificultad nivel = NivelDificultad.valueOf(campos[1]);
            int puntajeObtenido = Integer.parseInt(campos[2]);
 
            aumentarCapacidadSiEsNecesario();
            partidas[cantidad] = new Partida(nombrePiloto, nivel, puntajeObtenido);
            cantidad++;
        }
    }
}

    
