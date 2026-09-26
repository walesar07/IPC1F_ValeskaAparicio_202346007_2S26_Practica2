
package com.mycompany.practica2.views;

import com.mycompany.practica2.controllers.HistorialPartidas;
import com.mycompany.practica2.models.Partida;

import javax.swing.*;
import java.awt.*;

public class VentanaTopPuntajes extends JDialog {
 
    private static final int TOP_N = 10;
 
    public VentanaTopPuntajes(JFrame padre, HistorialPartidas historial) {
        super(padre, "Top de Puntajes", true);
        setSize(380, 400);
        setLocationRelativeTo(padre);
 
        Partida[] top = historial.obtenerTop(TOP_N);
 
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
 
        add(titulo, BorderLayout.NORTH);
        add(new JScrollPane(lista), BorderLayout.CENTER);
    }
}
 
