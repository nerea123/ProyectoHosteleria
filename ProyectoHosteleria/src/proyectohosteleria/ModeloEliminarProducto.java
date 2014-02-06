/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package proyectohosteleria;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JList;



public class ModeloEliminarProducto {
    
    Statement statement;
    public void rellenaComboFamilia(JComboBox familia,JList productos){
        try {
            statement=(Statement)Conexion.getInstance().conectar().createStatement();
            ResultSet resultset=statement.executeQuery("SELECT FAMILIA from productos group by FAMILIA;");
            while(resultset.next()){
                familia.addItem(resultset.getObject(1));
            }
            resultset.close();
            rellenaListaProductos(familia,productos);
        } catch (SQLException ex) {
            Logger.getLogger(ModeloModificarPrecio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     public void rellenaListaProductos(JComboBox familia,JList productos) {
        try {
            ResultSet resultset=statement.executeQuery("SELECT COUNT(DESCRIPCION) from productos WHERE FAMILIA='"+familia.getSelectedItem()+"'");
            resultset.next();
            Object[] descProduct=new Object[resultset.getInt(1)];
            int i=0;
            resultset.close();
            ResultSet result=statement.executeQuery("SELECT DESCRIPCION FROM productos WHERE FAMILIA='"+familia.getSelectedItem()+"'");
            while(result.next()){
                descProduct[i]=result.getObject(1);
                i++;
            }
            productos.setListData(descProduct);
            result.close();
           
        } catch (SQLException ex) {
            Logger.getLogger(ModeloModificarPrecio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
    public void ponActionListnerCombo(final JComboBox familia,final JList productos){
        ActionListener actionListener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                rellenaComboFamilia(familia,productos);
                rellenaListaProductos(familia,productos);
                
            }
        };
            familia.addActionListener(actionListener);
    } 
    public void ponActionListener(final JList productos,JButton boton,final JDialog dialog){
    ActionListener action=new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            
            int alerta=Alerta.creaAlertaWarning("¿Eliminar "+productos.getSelectedValue().toString()+" ?","Eliminar",dialog);
            if(alerta ==0){
                try {
                    statement.executeUpdate("DELETE FROM productos WHERE DESCRIPCION='"+productos.getSelectedValue().toString()+"'" );
                    Alerta.creaAlertaInf("Se ha eliminado el producto, debe reiniciar el programa", "Producto eliminado", dialog);
                } catch (SQLException ex) {
                    Logger.getLogger(ModeloEliminarProducto.class.getName()).log(Level.SEVERE, null, ex);
                }
              }
            }
        };
        boton.addActionListener(action);
    }
}
