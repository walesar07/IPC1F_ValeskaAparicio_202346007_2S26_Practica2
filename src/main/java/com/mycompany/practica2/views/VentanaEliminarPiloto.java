
package com.mycompany.practica2.views;

import com.mycompany.practica2.controllers.RegistroPilotos;
import com.mycompany.practica2.models.Piloto;
 
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class VentanaEliminarPiloto extends JDialog {
 
    public VentanaEliminarPiloto(JFrame padre, RegistroPilotos registroPilotos) {
        super(padre, "Eliminar Piloto", true);
        setSize(360, 190);
        setLocationRelativeTo(padre);
 
        Piloto[] pilotos = registroPilotos.getPilotos();
 
        if (pilotos.length == 0) {
            add(new JLabel("No hay pilotos registrados.", SwingConstants.CENTER));
            return;
        }
 
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
 
        JLabel etiqueta = new JLabel("Selecciona el piloto a eliminar:");
        etiqueta.setAlignmentX(Component.LEFT_ALIGNMENT);
 
        JComboBox<Piloto> combo = new JComboBox<>(pilotos);
        combo.setAlignmentX(Component.LEFT_ALIGNMENT);
        combo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
 
        JButton botonEliminar = new JButton("Eliminar");
        botonEliminar.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonEliminar.addActionListener(e -> eliminarSeleccionado(registroPilotos, combo));
 
        panel.add(etiqueta);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(combo);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(botonEliminar);
 
        add(panel);
    }
 
    private void eliminarSeleccionado(RegistroPilotos registroPilotos, JComboBox<Piloto> combo) {
        Piloto seleccionado = (Piloto) combo.getSelectedItem();
        if (seleccionado == null) {
            return;
        }
 
        int confirmar = JOptionPane.showConfirmDialog(this,
                "¿Seguro que deseas eliminar a \"" + seleccionado.getNombre() + "\"?\n"
                        + "Esta acción no se puede deshacer.",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
 
        if (confirmar != JOptionPane.YES_OPTION) {
            return;
        }
 
        registroPilotos.eliminarPiloto(seleccionado.getNombre());
 
        try {
            registroPilotos.guardar();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,
                    "El piloto se eliminó de la lista, pero no se pudo actualizar el archivo:\n"
                            + ex.getMessage(),
                    "Error al guardar", JOptionPane.ERROR_MESSAGE);
        }
 
        JOptionPane.showMessageDialog(this, "Piloto eliminado.");
        dispose();
    }
}
