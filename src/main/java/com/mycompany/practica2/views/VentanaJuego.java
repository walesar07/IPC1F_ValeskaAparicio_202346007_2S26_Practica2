
package com.mycompany.practica2.views;

import com.mycompany.practica2.controllers.Arbitro;
import com.mycompany.practica2.controllers.ControlTeclado;
import com.mycompany.practica2.controllers.HiloDisparo;
import com.mycompany.practica2.controllers.HiloGeneradorObjetos;
import com.mycompany.practica2.controllers.HiloMovimientoJugador;
import com.mycompany.practica2.models.Escena;
import com.mycompany.practica2.models.NaveJugador;
import com.mycompany.practica2.models.Piloto;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class VentanaJuego extends JFrame{
    
    private static final int ANCHO_PANTALLA = 800;
    private static final int ALTO_PANTALLA = 500;
    
    private final HiloMovimientoJugador hiloMovimiento;
    private final HiloGeneradorObjetos hiloGenerador;
    private final HiloDisparo hiloDisparo;
    private final Arbitro arbitro;
    
    public VentanaJuego(Piloto piloto){
        setTitle("Partida - " + piloto.getNombre());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        
        //la nave arranca en el borde izquiero, a media altura
        NaveJugador nave = new NaveJugador(20, 220, 40, 30, piloto.getNivel());
        Escena escena = new Escena();
        arbitro = new Arbitro(escena, nave);
        
        PanelJuego panel = new PanelJuego(nave, escena, arbitro);
        ControlTeclado teclado = new ControlTeclado();
        panel.addKeyListener(teclado);
        
        add(panel);
        pack();//ajusta el tamano de la ventana al referredSize del panel
        setLocationRelativeTo(null);
        
        hiloMovimiento = new HiloMovimientoJugador(nave, teclado, panel);
        hiloGenerador = new HiloGeneradorObjetos(escena, ANCHO_PANTALLA,
        ALTO_PANTALLA);
        hiloDisparo = new HiloDisparo(nave, teclado, escena, ANCHO_PANTALLA);
        
        hiloMovimiento.start();
        hiloGenerador.start();
        hiloDisparo.start();
        arbitro.start();
        
        /*timer de swing (corre en el EDT, seguro para tocar la UI):
        revisa cada 200 ms si la nave ya se quedo sin vidas, y si es asi
        detiene todos los hilos de la partida y avisa al jugador.
        */
        
        Timer verificadorGameOver = new Timer(200, null);
        verificadorGameOver.addActionListener(e-> {
            if (!nave.estaVivo()){
                verificadorGameOver.stop();
                hiloMovimiento.detener();
                hiloGenerador.detener();
                hiloDisparo.detener();
                arbitro.detener();
                
                JOptionPane.showMessageDialog(this,
                        "¡Nave destruida! Game over.\nPuntaje final: " + arbitro.getPuntaje(),
                        "Fin de la partida", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }
        });
        verificadorGameOver.start();
        
  
        
        addWindowListener(new WindowAdapter(){
            @Override
            public void windowOpened(WindowEvent e){
                /*el panel solo puede recibir el foco(y por lo tanto,
                eventos de teclado) una vez que la ventana ya esta visible
                enpantalla. Pedirlo antes (ej. en el constructor) no tiene efecto.
                */
                panel.requestFocusInWindow();
                
            }
            
            @Override
            public void windowClosing(WindowEvent e){
                //si se cierra la ventana, detenemos el hilo 
                //para no dejarlo corriendo en segundo plano.     
                hiloMovimiento.detener();
                hiloGenerador.detener();
                hiloDisparo.detener();
                arbitro.detener();
            }
        });              
        
    }
    
    
}
