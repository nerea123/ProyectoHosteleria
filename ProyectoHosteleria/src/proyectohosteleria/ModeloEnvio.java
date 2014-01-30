/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package proyectohosteleria;

import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.PrintWriter;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextField;

/**
 *
 * @author Nerea
 */
public class ModeloEnvio {
    
    public void ponActionListener(JButton boton,final JButton b,final JTextField server, final JTextField movil,final JTextField puerto,final JDialog dialog){
    
        ActionListener action =new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                PrintWriter pw;
                FileWriter fichero;
                try
        {
            fichero = new FileWriter("envio.conf");
            pw = new PrintWriter(fichero);
 
            pw.println("IP servidor: " + server.getText());
            pw.println("IP movil: " + movil.getText());
            pw.println("Puerto: "+puerto.getText());
            
            
            fichero.close();
            pw.close();
            
            setEnabledButton(b);
            dialog.dispose();
        } catch (Exception ex) {
            ex.printStackTrace();
            } 
        }
            
        };
        boton.addActionListener(action);
    }
    
    public void setEnabledButton(JButton b){
    
        b.setEnabled(true);
    }
}
