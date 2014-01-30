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
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class ModeloVistaHoy  {
    private DefaultTableModel modelo;

    public void creaColumnas(JTable table){
    
        modelo=new DefaultTableModel();
        modelo.addColumn("IDT");
        modelo.addColumn("Cantidad");
        modelo.addColumn("Precio");
        modelo.addColumn("Descripcion");
        modelo.addColumn("Fecha");

        table.setModel(modelo);
    }

    public void rellenaTabla(final JLabel label){
       
        
        Object[] fila=new Object[5];
        
        try {
            Statement statement=(Statement)Conexion.getInstance().conectar().createStatement();
            ResultSet resultset=statement.executeQuery("SELECT linea.IDT,CANTIDAD,PRECIO,DESCRIPCION,FECHA FROM "
                    + "linea INNER JOIN tiquet ON linea.IDT=tiquet.IDT where FECHA=curdate()");
            while(resultset.next()){
                for(int i=0;i<5;i++)
                    fila[i]=resultset.getObject(i+1);
                 modelo.addRow(fila);
            }
           resultset.close();
           
           ResultSet result=statement.executeQuery("SELECT ROUND(SUM(PRECIO),2) FROM linea INNER JOIN tiquet ON linea.IDT=tiquet.IDT where FECHA=curdate() ");
           result.next();
           label.setText("TOTAL: "+String.valueOf(result.getFloat(1)));
           
        } catch (SQLException ex) {
            Logger.getLogger(ModeloVistaHoy.class.getName()).log(Level.SEVERE, null, ex);
        }
            
      
    }
    
    
}
