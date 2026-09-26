
package com.mycompany.practica2.controllers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
 
public class GestorPersistencia {
    
    private static final String CARPETA_DATOS = "datos";
 
    /*
      Escribe cada elemento del arreglo como una línea del archivo
      indicado, dentro de la carpeta "datos" (la crea si no existe).
     */
    public void guardarLineas(String nombreArchivo, String[] lineas) throws IOException {
        File carpeta = new File(CARPETA_DATOS);
        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }
 
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(CARPETA_DATOS + "/" + nombreArchivo))) {
            for (int i = 0; i < lineas.length; i++) {
                escritor.write(lineas[i]);
                escritor.newLine();
            }
        }
    }
 
    /*
      Lee el archivo indicado (dentro de la carpeta "datos") y
      devuelve sus líneas no vacías como un arreglo. Si el archivo no
      existe todavía (primera ejecución del programa), devuelve un
      arreglo vacío en vez de fallar.
     */
    public String[] cargarLineas(String nombreArchivo) throws IOException {
        File archivo = new File(CARPETA_DATOS + "/" + nombreArchivo);
        if (!archivo.exists()) {
            return new String[0];
        }
 
        String[] lineas = new String[10];
        int cantidad = 0;
 
        try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                if (linea.trim().isEmpty()) {
                    continue;
                }
                if (cantidad == lineas.length) {
                    String[] nuevoArreglo = new String[lineas.length * 2];
                    for (int i = 0; i < lineas.length; i++) {
                        nuevoArreglo[i] = lineas[i];
                    }
                    lineas = nuevoArreglo;
                }
                lineas[cantidad] = linea;
                cantidad++;
            }
        }
 
        String[] resultado = new String[cantidad];
        for (int i = 0; i < cantidad; i++) {
            resultado[i] = lineas[i];
        }
        return resultado;
    }
}
    

