
package com.mycompany.practica2;

import com.mycompany.practica2.views.MenuPrincipal;
import javax.swing.SwingUtilities;


public class Practica2 {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(()-> {
            MenuPrincipal menu = new MenuPrincipal();
            menu.setVisible(true);
        });
    }
}
