
package com.mycompany.practica2.views;

import com.mycompany.practica2.controllers.RegistroPilotos;
import com.mycompany.practica2.models.Piloto;

import javax.swing.*;
import java.awt.*;

public class MenuPrincipal extends JFrame {
    
    /*se crea una sola vez y se comparte con las ventanas hijas (crear piloto,
    top de puntajes,etc) para que todas trabajen sobre el mismo vector.
    */
    private RegistroPilotos registroPilotos = new RegistroPilotos();
    
    public MenuPrincipal(){
        //Configuracion basica de la ventana
        setTitle("Quetzal Space Defender");
        setSize(400,350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);//centra la ventana en la pantalla
        setResizable(false);
        
        //Panel principal con distribucion vertical (BoxLayout)
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        
        //Titulo
        JLabel titulo = new JLabel("QUETZAL SPACE DEFENDER");
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        //Botones del menu
        JButton btnJugar = crearBoton("Jugar");
        JButton btnCrearPiloto = crearBoton("Crear Piloto");
        JButton btnTopPuntajes = crearBoton("Top de Puntajes");
        JButton btnSalir = crearBoton("Salir");
        
        //eventos de los botones
        btnJugar.addActionListener(e-> {
            Piloto[] pilotos = registroPilotos.getPilotos();
            if (pilotos.length == 0){
                JOptionPane.showMessageDialog(this,"Primero debes "
                        + "crear un piloto", "Sin pilotos", JOptionPane.WARNING_MESSAGE);
                return;
            }
            //por ahora usamos el primer piloto registrado; en una fase 
            //posterior agregaremos un selector si hay varios.
            Piloto pilotoSeleccionado = pilotos[0];
            VentanaJuego ventanaJuego = new VentanaJuego(pilotoSeleccionado);
            ventanaJuego.setVisible(true);
        });
        
         btnCrearPiloto.addActionListener(e -> {
            VentanaCrearPiloto ventana = new VentanaCrearPiloto(this, registroPilotos);
            ventana.setVisible(true);
        });
 
        btnTopPuntajes.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                "Aquí mostraremos el top de puntajes guardado en el vector\n" +
                "(se implementa junto con la persistencia).");
        });
 
        btnSalir.addActionListener(e -> {
            int confirmar = JOptionPane.showConfirmDialog(this,
                "¿Seguro que deseas salir?", "Salir",
                JOptionPane.YES_NO_OPTION);
            if (confirmar == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
 
        // Ensamblamos el panel
        panelPrincipal.add(titulo);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 25)));
        panelPrincipal.add(btnJugar);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 10)));
        panelPrincipal.add(btnCrearPiloto);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 10)));
        panelPrincipal.add(btnTopPuntajes);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 10)));
        panelPrincipal.add(btnSalir);
 
        add(panelPrincipal);
    }
 
    /**
     * Método auxiliar para no repetir código (principio DRY) al crear
     * cada botón con el mismo estilo.
     */
    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        boton.setMaximumSize(new Dimension(200, 35));
        boton.setFocusPainted(false);
        return boton;
    }
}
                
        
     
                

    
    
    

