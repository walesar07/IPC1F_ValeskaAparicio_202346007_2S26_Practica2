
package com.mycompany.practica2.views;

import com.mycompany.practica2.controllers.GeneradorReporte;
import com.mycompany.practica2.controllers.HistorialPartidas;
import com.mycompany.practica2.models.Partida;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class VentanaTopPuntajes extends JDialog {
 
    private static final int TOP_N = 10;
    
    private final Partida[] top;
 
    public VentanaTopPuntajes(JFrame padre, HistorialPartidas historial) {
        super(padre, "Top de Puntajes", true);
        setSize(480, 450);
        setLocationRelativeTo(padre);
        setLayout(new BorderLayout());
        
        this.top = historial.obtenerTop(TOP_N);
 
        if (top.length == 0) {
            JLabel etiquetaVacio = new JLabel(
                    "Aún no hay partidas registradas.", SwingConstants.CENTER);
            add(etiquetaVacio, BorderLayout.CENTER);
            return;
        }
 
        DefaultListModel<Partida> modelo = new DefaultListModel<>();
        for (int i = 0; i < top.length; i++) {
            modelo.addElement(top[i]);
        }
 
        JList<Partida> lista = new JList<>(modelo);
        lista.setFont(new Font("Monospaced", Font.PLAIN, 13));
        lista.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
 
        JLabel titulo = new JLabel("Piloto / Nave / Puntaje", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 14));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        JButton botonGenerarReporte = new JButton("Generar Reporte (HTML + gráfica)");
        botonGenerarReporte.addActionListener(e -> generarReporte());
 
        JPanel panelInferior = new JPanel();
        panelInferior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelInferior.add(botonGenerarReporte);
 
        add(titulo, BorderLayout.NORTH);
        add(new JScrollPane(lista), BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);
    }
    
    private void generarReporte() {
        try {
            GeneradorReporte generador = new GeneradorReporte();
            File carpetaDestino = new File("reportes");
            String rutaHtml = generador.generarReporte(top, carpetaDestino);
            
            abrirEnNavegador(new File(rutaHtml));
 
            JOptionPane.showMessageDialog(this,
                    "Reporte generado en:\n" + rutaHtml
                            + "\n\nÁbrelo con tu navegador. Desde ahí puedes usar "
                            + "\"Imprimir -> Guardar como PDF\" para obtener el PDF.",
                    "Reporte generado", JOptionPane.INFORMATION_MESSAGE);
 
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,
                    "Ocurrió un error al generar el reporte:\n" + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        
    }
    
        /*
      Intenta abrir el HTML generado con el navegador predeterminado
      del sistema. Si por alguna razón no se puede (por ejemplo, en un
      entorno sin soporte de escritorio), no es un error grave: el
      archivo ya quedó generado y el usuario puede abrirlo a mano.
     */
    private void abrirEnNavegador(File archivoHtml) {
        try {
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(archivoHtml.toURI());
            }
        } catch (IOException ex) {
            // No es crítico: el archivo ya existe, solo no se pudo abrir solo.
        }
    }
}
    


 
