/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectohosteleria;

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
                 System.out.print("No existe");
                 new VistaConfiguracion(new JFrame(),true).setVisible(true);
             }
             else
                 new VistaPrincipal().setVisible(true);
                  //System.out.print("existe");
    }
}
