
package com.mycompany.practica2.views;

import com.mycompany.practica2.controllers.HistorialPartidas;
import com.mycompany.practica2.controllers.RegistroPilotos;
import com.mycompany.practica2.models.Piloto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class MenuPrincipal extends JFrame {
    
    /*se crea una sola vez y se comparte con las ventanas hijas (crear piloto,
    top de puntajes,etc) para que todas trabajen sobre el mismo vector.
    */
    private RegistroPilotos registroPilotos = new RegistroPilotos();
    private HistorialPartidas historialPartidas = new HistorialPartidas ();
    
    public MenuPrincipal(){
        
        cargarDatos();
        
        //Configuracion basica de la ventana
        setTitle("Quetzal Space Defender");
        setSize(400,400);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
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
        JButton btnEliminarPiloto = crearBoton("Eliminar Piloto");
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
            //ahora usamos el primer piloto registrado; 
            //aparece un selector si hay varios.
            Piloto pilotoSeleccionado = seleccionarPiloto(pilotos);
            if (pilotoSeleccionado == null){
                return; // el usuario cerro el selector sin elegir
                
            }
            VentanaJuego ventanaJuego = new VentanaJuego(pilotoSeleccionado, historialPartidas);
            ventanaJuego.setVisible(true);
        });
        
         btnCrearPiloto.addActionListener(e -> {
            VentanaCrearPiloto ventana = new VentanaCrearPiloto(this, registroPilotos);
            ventana.setVisible(true);
        });
         
         btnEliminarPiloto.addActionListener(e -> {
            VentanaEliminarPiloto ventana = new VentanaEliminarPiloto(this, registroPilotos);
            ventana.setVisible(true);
        });
 
        btnTopPuntajes.addActionListener(e -> {
            VentanaTopPuntajes ventana = new VentanaTopPuntajes(this, historialPartidas);
            ventana.setVisible(true);
        });
 
        btnSalir.addActionListener(e ->  confirmarYSalir());
            
 
        // Ensamblamos el panel
        panelPrincipal.add(titulo);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 25)));
        panelPrincipal.add(btnJugar);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 10)));
        panelPrincipal.add(btnCrearPiloto);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 10)));
        panelPrincipal.add(btnEliminarPiloto);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 10)));
        panelPrincipal.add(btnTopPuntajes);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 10)));
        panelPrincipal.add(btnSalir);
 
        add(panelPrincipal);
    // Tanto la X de la ventana como el botón "Salir" deben guardar
        // los datos antes de cerrar, así que ambos pasan por el mismo
        // método (confirmarYSalir).
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                confirmarYSalir();
            }
        });
    }
 
    /*
      Carga pilotos e historial guardados de una ejecución anterior.
      Si es la primera vez que se corre el programa (no hay archivos
      todavía) o el archivo está corrupto, simplemente se sigue con
      listas vacías: no es un error que deba detener el programa.
     */
    private void cargarDatos() {
        try {
            registroPilotos.cargar();
            historialPartidas.cargar();
        } catch (IOException | IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this,
                "No se pudieron cargar los datos guardados anteriormente.\n"
                    + "Se continuará con listas vacías.",
                "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }
 
    private void guardarDatos() {
        try {
            registroPilotos.guardar();
            historialPartidas.guardar();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,
                "No se pudieron guardar los datos:\n" + ex.getMessage(),
                "Error al guardar", JOptionPane.ERROR_MESSAGE);
        }
    }
 
    private void confirmarYSalir() {
        int confirmar = JOptionPane.showConfirmDialog(this,
            "¿Seguro que deseas salir?", "Salir",
            JOptionPane.YES_NO_OPTION);
        if (confirmar == JOptionPane.YES_OPTION) {
            guardarDatos();
            System.exit(0);
        }
    }
 
    
    /* si solo hay un piloto registrado, lo usa directamente. Si hay
    varios, muestra un selector para que el jugador elija con cual quiere
    jugar (por defecto, sugiere el mas reciente).
    */
    private Piloto seleccionarPiloto(Piloto[] pilotos){
        if(pilotos.length == 1){
            return pilotos[0];
        }
        return (Piloto) JOptionPane.showInputDialog(
        this,
                "Selecciona con que piloto quieres jugar: ",
                "Elegir piloto",
                JOptionPane.PLAIN_MESSAGE,
                null,
                pilotos,
                pilotos[pilotos.length - 1]
        );
    }
    
 
    /*
      Método auxiliar para no repetir código (principio DRY) al crear
      cada botón con el mismo estilo.
     */
    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        boton.setMaximumSize(new Dimension(200, 35));
        boton.setFocusPainted(false);
        return boton;
    }
}
                
        
     
                

    
    
    

