/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package proyectohosteleria;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Nerea
 */
public class ModeloAñadirProducto {
    private Component parent;
    private Statement statement;
    private String nombreImagen;
    
    public void ponActionListenerImagen(JButton boton, JDialog dialog,final JTextField imagen){
    
        ActionListener actionListener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG, PNG & GIF Images", "jpg", "gif","png","jpeg");
                chooser.setFileFilter(filter);
                int returnVal = chooser.showOpenDialog(parent);
                if(returnVal == JFileChooser.APPROVE_OPTION) {
                      nombreImagen=chooser.getSelectedFile().getName();
                      System.out.print(nombreImagen);
                      imagen.setText( chooser.getSelectedFile().getAbsolutePath());
                      
                    } 
                 }
        };
            boton.addActionListener(actionListener);
        
    }
    
    public void rellenaComboFamilia(JComboBox familia){
    
         try {
            statement=(Statement)Conexion.getInstance().conectar().createStatement();
            ResultSet resultset=statement.executeQuery("SELECT FAMILIA from productos group by FAMILIA;");
            while(resultset.next()){
                familia.addItem(resultset.getObject(1));
            }
            resultset.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(ModeloModificarPrecio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void ponActionListener(JButton boton,final JComboBox familia, final JDialog dialog,final JTextField textoimagen,final JTextField nombre,final JTextField precio){
    
        ActionListener actionListener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                boolean error=false;
                if(nombre.getText().isEmpty()){
                     Alerta.getInstance().creaAlerta("Debes introducir el nombre del producto","Error",dialog);
                    error=true;
                }
                else if(precio.getText().isEmpty()){
                     Alerta.getInstance().creaAlerta("Debes introducir el nombre del producto","Error",dialog);
                    error=true;
                }
                
                if(!error){
                    String imagen;
                    try {
                        if(textoimagen.getText().isEmpty())
                            imagen="imagenes/n.jpg";
                        else
                            imagen=textoimagen.getText();
                       
                        /*CAMBIAMOS LA \ POR \\ PERO AL INSERTAR EN LA BD SE INSERTA COMO \ 
                        POR LO QUE VOLVEMOS A CAMBIARLO PARA QUE QUEDE \\\\ Y SE INSERTE COMO \\ */
                        
                        imagen=imagen.replace("\\", "\\\\");
                        
                        //new ModeloPrincipal().refresh(new VistaPrincipal().getJTabbedPane(),familia.getSelectedItem().toString(), nombre.getText(), imagen);
                        imagen=imagen.replace("\\", "\\\\");
                         System.out.println(imagen);
                        statement.executeUpdate("INSERT INTO productos (DESCRIPCION,PRECIO,FAMILIA,IMAGEN) VALUES "
                                + "( '"+nombre.getText()+"',"+Float.parseFloat(precio.getText())+", '"+familia.getSelectedItem().toString()+"', '"+imagen+"')");
                        
                        JOptionPane.showMessageDialog(dialog,"Debe reiniciar el programa para que los cambios se apliquen","Producto agregado",JOptionPane.INFORMATION_MESSAGE);
                        dialog.dispose();
                        statement.close();
                    } catch (SQLException ex) {
                       Alerta.getInstance().creaAlerta("Error al añadir el producto, por favor compruebe que todos los datos son correctos","Error",dialog);
                        Logger.getLogger(ModeloAñadirProducto.class.getName()).log(Level.SEVERE, null, ex);
                    }catch(NumberFormatException num){
                        Alerta.getInstance().creaAlerta("Error al añadir el precio, por favor compruebe que todos los datos son correctos","Error",dialog);
                    }
                }
            }
        };
            boton.addActionListener(actionListener);
    }
    
    public void copiarImagen(String nombreFichero,String nombreFicherocopiado){
        try {
            FileInputStream fregis = new FileInputStream(nombreFichero);
            FileOutputStream fsalida = new FileOutputStream(nombreFicherocopiado, true);
            
            int b = fregis.read();
            while (b != -1) {
                fsalida.write(b);
                b = fregis.read();
                
            }
            
            fsalida.flush();
            fsalida.close();
            fregis.close();
        } catch (IOException ex) {
            Logger.getLogger(ModeloAñadirProducto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
}
