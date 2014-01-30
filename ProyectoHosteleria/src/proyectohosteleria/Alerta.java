/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package proyectohosteleria;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 *
 * @author Nerea
 */
public class Alerta {
    
    private static Alerta INSTANCE = new Alerta();
    private Alerta() {
    }
    
    public static Alerta getInstance() {
        return INSTANCE;
    }
    
    public void creaAlerta(String alert, String titulo,JDialog dialog){
    
        JOptionPane.showMessageDialog(dialog,alert,titulo,JOptionPane.ERROR_MESSAGE);
              
    }
    
    public void creaAlertaInf(String alert, String titulo,JDialog dialog){
    
        JOptionPane.showMessageDialog(dialog,alert,titulo,JOptionPane.INFORMATION_MESSAGE);
              
    }
    
    public int creaAlertaWarning(String alert, String titulo,JDialog dialog){
    
        return JOptionPane.showOptionDialog(null, alert, titulo, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
              
    }
}
