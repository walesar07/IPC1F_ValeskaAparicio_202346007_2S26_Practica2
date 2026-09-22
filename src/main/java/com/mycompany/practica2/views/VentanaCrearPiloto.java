
package com.mycompany.practica2.views;

import com.mycompany.practica2.controllers.RegistroPilotos;
import com.mycompany.practica2.models.NivelDificultad;
import javax.swing.*;
import java.awt.*;

public class VentanaCrearPiloto extends JDialog{
    
    private JTextField campoNombre;
    private JRadioButton radioFacil, radioNormal, radioDificil;
    private RegistroPilotos registroPilotos;
    
    public VentanaCrearPiloto(JFrame padre, RegistroPilotos registroPilotos){
        super(padre,"Crear Piloto", true); //true = modal (blooquea la ventana padre)
        this.registroPilotos = registroPilotos;
        
        setSize(520, 320);
        setLocationRelativeTo(padre);
        setResizable(false);
        
        JPanel panel = new JPanel ();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20,25,20, 25));
        
        //campo de nombres
        JLabel etiquetaNombre = new JLabel("Nombre del piloto:");
        etiquetaNombre.setAlignmentX(Component.LEFT_ALIGNMENT);
        campoNombre = new JTextField();
        campoNombre.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        campoNombre.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        //Seleccion de nave (dificultad)
        JLabel etiquetaNave = new JLabel ("Modelo de nave:");
        etiquetaNave.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        radioFacil = new JRadioButton(NivelDificultad.FACIL.toString());
        radioNormal = new JRadioButton(NivelDificultad.NORMAL.toString());
        radioDificil = new JRadioButton(NivelDificultad.DIFICIL.toString());
        radioNormal.setSelected(true);//dificultad por defecto 
        
        ButtonGroup grupoNaves = new ButtonGroup();
        grupoNaves.add(radioFacil);
        grupoNaves.add(radioNormal);
        grupoNaves.add(radioDificil);
        
        for(JRadioButton r: new JRadioButton[] {radioFacil, radioNormal, radioDificil})
        {r.setAlignmentX(Component.LEFT_ALIGNMENT);
            }
        
        //Boton registrar
        JButton botonRegistrar = new JButton ("Registrar Piloto");
        botonRegistrar.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonRegistrar.addActionListener(e-> intentarRegistrar());
        
        panel.add(etiquetaNombre);
        panel.add(campoNombre);
        panel.add(Box.createRigidArea(new Dimension(0,15)));
        panel.add(etiquetaNave);
        panel.add(radioFacil);
        panel.add(radioNormal);
        panel.add(radioDificil);
        panel.add(Box.createRigidArea(new Dimension(0,20)));
        panel.add(botonRegistrar);
        
        add(panel);
              
}
    /* lee los datos del formularios, obtiene la dificultad seleccionada y le pide
    al controlador que registre al piloto. Muestra el resultado (exito o el mensaje
    de error de la validacion).
    */
    private void intentarRegistrar(){
        String nombre =campoNombre.getText();
        NivelDificultad nivelSeleccionado = obtenerNivelSeleccionado();
        
        String error = registroPilotos.registrarPiloto(nombre,nivelSeleccionado);
        
        if(error == null){
            JOptionPane.showConfirmDialog(this, "Piloto registrado con exito!", "Exito",
                    JOptionPane.OK_CANCEL_OPTION);
            dispose();//cierra la ventana
            
        }else{
            JOptionPane.showMessageDialog(this, error, "Error de validacion",
                    JOptionPane.ERROR_MESSAGE);
        }
        
    }
    private NivelDificultad obtenerNivelSeleccionado(){
        if (radioFacil.isSelected()) return NivelDificultad.FACIL;
        if (radioDificil.isSelected()) return NivelDificultad.DIFICIL;
        return NivelDificultad.NORMAL;
    }
}
