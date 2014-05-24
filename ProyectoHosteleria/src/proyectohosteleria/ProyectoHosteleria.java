/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectohosteleria;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import javax.swing.JFrame;

/**
 *
 * @author Nerea
 */
public class ProyectoHosteleria {

    public static void main(String[] args) {
     
            File fichero = new File("bar.conf");
             if (!fichero.exists()) {
                 new VistaConfiguracion(new JFrame(),true).setVisible(true);
             }
             else{
                  VistaPrincipal principal= new VistaPrincipal("Terminal hostelería");
            
                principal.setVisible(true);
        }
    }
    
    public static void getResolucion(){
    
        Toolkit t = Toolkit.getDefaultToolkit();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        if(screenSize.getWidth() < 1366 ){
            new VistaPrincipal1024().setVisible(true);
        }
        else {
            VistaPrincipal principal= new VistaPrincipal("Terminal hostelería");
            
            principal.setVisible(true);
        }
    }
}
