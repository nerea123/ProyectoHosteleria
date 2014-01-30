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
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class ModeloModificarPrecio {
    
    private Statement statement;
    
    public void ponActionListenerCombo(final JComboBox familia,final JList productos, final JTextField label ){
    
          ActionListener actionListener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                rellenaComboFamilia(familia,productos,label);
                rellenaListaProductos(familia,productos,label);
                ponListSelectionListenerLista(productos,label);
            }
        };
            familia.addActionListener(actionListener);
    }
    
    public void rellenaComboFamilia(JComboBox familia,JList productos,JTextField label){
        try {
            statement=(Statement)Conexion.getInstance().conectar().createStatement();
            ResultSet resultset=statement.executeQuery("SELECT FAMILIA from productos group by FAMILIA;");
            while(resultset.next()){
                familia.addItem(resultset.getObject(1));
            }
            resultset.close();
            rellenaListaProductos(familia,productos,label);
        } catch (SQLException ex) {
            Logger.getLogger(ModeloModificarPrecio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void rellenaListaProductos(JComboBox familia,JList productos,JTextField label) {
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
            ponListSelectionListenerLista(productos,label);
        } catch (SQLException ex) {
            Logger.getLogger(ModeloModificarPrecio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void ponListSelectionListenerLista(final JList productos,final JTextField label){
        ListSelectionListener actionListener = new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                setPrecio(productos,label);
            }
        };
        
        productos.addListSelectionListener(actionListener);
    }
    
    private void setPrecio(JList productos,JTextField label){
        if(productos.getSelectedValue()==null)
            label.setText("0");
        else{
            try {
                ResultSet resultset=statement.executeQuery("SELECT PRECIO from productos WHERE DESCRIPCION='"+productos.getSelectedValue()+"'");
                resultset.next();
                label.setText(String.valueOf(resultset.getObject(1)));
            } catch (SQLException ex) {
                Logger.getLogger(ModeloModificarPrecio.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void ponActionListener(JButton boton,final JList productos,final JTextField label,final JDialog dialog){
    
          ActionListener actionListener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    statement.executeUpdate("UPDATE  productos SET PRECIO="+Float.parseFloat(label.getText())+" WHERE DESCRIPCION='"+productos.getSelectedValue()+"'");
                    JOptionPane.showMessageDialog(dialog, "Precio de "+productos.getSelectedValue()+" actualizado a "+label.getText());
                    statement.close();
                    dialog.dispose();
                } catch (SQLException ex) {
                    Logger.getLogger(ModeloModificarPrecio.class.getName()).log(Level.SEVERE, null, ex);
                }catch(NumberFormatException number){
                    JOptionPane.showMessageDialog(dialog, "El precio debe de ser un numero, asegurese de que lo ha escrito bien","Error al modificar",JOptionPane.ERROR_MESSAGE);
                }
               
            }
        };
            boton.addActionListener(actionListener);
    }
}
