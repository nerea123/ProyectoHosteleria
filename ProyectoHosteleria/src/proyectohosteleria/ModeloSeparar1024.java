/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package proyectohosteleria;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ModeloSeparar1024 {
    JLabel label;
     float totalF;
     float total2=0;
     private DefaultTableModel model = new DefaultTableModel();
     private DefaultTableModel modelo = new DefaultTableModel();
     
     public ModeloSeparar1024(JTable table,JTable tabla,DefaultTableModel modelo,ModeloPrincipal principal,JLabel total){
         
         this.modelo=modelo;
         table.setModel(modelo);
         
         totalF=principal.getTotal();
         total.setText(String.valueOf(ModeloPrincipal.redondear(principal.getTotal(),2)));
         
         for(int i=0;i<modelo.getColumnCount();i++)
            model.addColumn(modelo.getColumnName(i));
         tabla.setModel(model);
         
     }
     
    public void ponActionLisstener(JButton boton,final JTable table,final JTable tabla,final JLabel total,final JLabel total1,final VistaPrincipal1024 frame,final ModeloPrincipal principal){
    
            ActionListener action=new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    if(table.getRowCount()>0){
                        int fila[]=table.getSelectedRows();
                        Object object=null;
                        Vector vector=new Vector();

                        /*CREAMOS UN ARRAY fila con la posicion o posiciones de las filas seleccionadas y recogemos
                        con dos bucles los valores de esa fila o filas*/
                        for(int i=0;i<fila.length;i++){                         
                            for(int j=0;j<model.getColumnCount();j++){
                                object=table.getValueAt(fila[i], j);
                                vector.add(object);
                            }
                            modelo.removeRow(fila[i]);
                            /*Como el ultimo valor es el del total y esta almacenado en object al salir del
                            primer bucle podemos coger su valor*/
                            total2+=(Float)object;
                            total2=ModeloPrincipal.redondear(total2, 2);
                            total1.setText("TOTAL: "+String.valueOf(total2));
                            totalF-=(Float)object;
                            totalF=ModeloPrincipal.redondear(totalF, 2);
                            total.setText("TOTAL: "+String.valueOf(totalF));
                            frame.total.setText("TOTAL: "+String.valueOf(totalF));
                            principal.setTotal(totalF);
                            principal.setNumFila(table.getRowCount());


                        }

                        model.addRow(vector);
                }
               }   
            };
            boton.addActionListener(action);
      }
    
    public void ponActionListenerCobrar2(JButton boton,final JLabel label,final JFrame frame,final JTable table,final ModeloPrincipal principal){
    ActionListener actionListener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) { 
                int alerta = Alerta.creaAlertaWarning("¿Cobrar cuenta?", "Cobrar", null);
                if(alerta==0){
                darCambio(principal,label.getText());
                int alert=Alerta.creaAlertaWarning("¿Imprimir tiquet?", "Imprimir", new JDialog());
                Imprimir.getInstance().abrirCajon();
                if(alert==0){
                        Imprimir.getInstance().guardar(modelo, principal);
                        Imprimir.getInstance().escribir("tiquet.txt");
                        Imprimir.getInstance().cortar();
                    }
                principal.insertarDatos(label, frame, table, model);
                label.setText("TOTAL: 0");
                for(int i=model.getRowCount();i>0;i--)
                      model.removeRow(i-1);
              }  
            }
        };
            boton.addActionListener(actionListener);
       
    }
    
    public void ponActionListenerCobrar(JButton boton,final JLabel lab,final VistaPrincipal1024 frame,final JTable table,final ModeloPrincipal principal){
    ActionListener actionListener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
              
                int alerta = Alerta.creaAlertaWarning("¿Cobrar cuenta?", "Cobrar", null);
                if(alerta==0){
                darCambio(principal,"");
                int alert=Alerta.creaAlertaWarning("¿Imprimir tiquet?", "Imprimir", new JDialog());
                Imprimir.getInstance().abrirCajon();
                if(alert==0){
                        Imprimir.getInstance().guardar(modelo, principal);
                        Imprimir.getInstance().escribir("tiquet.txt");
                        Imprimir.getInstance().cortar();
                    }
                principal.insertarDatos(label, frame, table, modelo);
                lab.setText("TOTAL: 0");
                frame.total.setText("TOTAL: 0");
                principal.setNumFila(0);
                principal.setTotal(0);
                for(int i=modelo.getRowCount();i>0;i--)
                      modelo.removeRow(i-1);
                 principal.setNumFila(0);
                }

            }
        };
            boton.addActionListener(actionListener);
       
    }
    
    private void darCambio(ModeloPrincipal principal,String cobrar){
    
        VistaDarCambio cambio=new VistaDarCambio(new JFrame(), true);
        
        cambio.setModeloPrincipal(principal,cobrar);
        cambio.setVisible(true);
    }
    
  
}
