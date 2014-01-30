

package proyectohosteleria;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextField;



public class ModeloDarCambio {
 
    public void ponActionListenerBotones(JButton[] button,final JTextField texto){
      
        ActionListener action= new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JButton boton=(JButton)e.getSource();
                texto.setText(texto.getText()+boton.getText());
            }
        };
        
        for (int i=0;i<button.length;i++){
            button[i].addActionListener(action);
        }
    }
    
    public void ponActionListenerBorrars(JButton button,final JTextField texto){
      
        ActionListener action= new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                texto.setText(texto.getText().substring(0, (texto.getText().length()-1)));
            }
        };
            button.addActionListener(action);
        
    }
    /*COMO PODEMOS DAR CAMBIO PARA LA MESA ACTUAL O BIEN DAR CAMBIO TAMBIEN AL SEPARAR UNA CUENTA
    UTILIZO UN STRING QUE SI ESTA VACIO DEVUELVE EL CAMBIO DE LA CUENTA PRINCIPAL Y SI NO DEVUEVE 
    EL CAMBIO DE LA CUENTA SEPARADA*/
    public void ponActionListenerCambio(JButton button,final JTextField texto,final ModeloPrincipal principal,final JDialog dialog,final String cobrar){
      
        ActionListener action= new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Float total=null;
                if(cobrar.equals("")){
                    total=ModeloPrincipal.redondear(principal.getTotal(),2);
                }
                else{
                    String aux="TOTAL: ";                   
                    total=ModeloPrincipal.redondear(Float.parseFloat(cobrar.substring(cobrar.indexOf(aux)+aux.length(), cobrar.length())),2);
                }
                Float cambio=ModeloPrincipal.redondear(Float.parseFloat(texto.getText()),2);
                cambio-=total;
                cambio=ModeloPrincipal.redondear(cambio,2);
                Alerta.getInstance().creaAlertaInf("El cambio es "+cambio, "Cambio", dialog);
                if(cobrar.equals(""))
                    principal.setTotal(0);
                dialog.dispose();
            }
        };
            button.addActionListener(action);
        
    }
    
 
}
