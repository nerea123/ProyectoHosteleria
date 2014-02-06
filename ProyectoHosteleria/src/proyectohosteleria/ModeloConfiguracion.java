/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectohosteleria;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.PrintWriter;
import javax.swing.JOptionPane;



public class ModeloConfiguracion implements ActionListener {

    
    private VistaConfiguracionDatos datos;
    private FileWriter fichero = null;
    private PrintWriter pw = null;
    private boolean error=false;
   
    
    public ModeloConfiguracion(VistaConfiguracionDatos datos ){
    
        this.datos=datos;
    }
    
     public ModeloConfiguracion(){
     
     }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
       if(!validar()) {
        creaFicheroConfiguracion();
        creaFicheroConexionPHP();
        datos.dispose();
        new VistaPrincipal().setVisible(true);
       }
        
    }
  
    private boolean validar(){
        boolean error=false;
        if(datos.getNombreBd().isEmpty()){
             Alerta.creaAlerta("Debes introducir el nombre de la base de datos","Error",datos);
            error=true;
        }
        else if(datos.getpswBd().isEmpty()){
             Alerta.creaAlerta("Debes introducir la contraseña de la base de datos","Error",datos);
            error=true;
        }
        else if(datos.getPuerto().isEmpty()){
            Alerta.creaAlerta("Debes introducir el puerto","Error",datos);
            error=true;
        }
            
        else if(datos.getServidor().isEmpty()){
            Alerta.creaAlerta("Debes introducir el servidor","Error",datos);
            error=true;
        }
        else if(datos.getUsuario().isEmpty()){
           Alerta.creaAlerta("Debes introducir el usuario la base de datos","Error",datos);
            error=true;
        }
        return error;
    }
    
    private void creaFicheroConfiguracion(){
    
         try
        {
            fichero = new FileWriter("bar.conf");
            pw = new PrintWriter(fichero);
 
            pw.println("Base de datos: " + datos.getNombreBd());
            pw.println("Contraseña: " + datos.getpswBd());
            pw.println("Usuario: " + datos.getUsuario());
            pw.println("Servidor: " + datos.getServidor());
            pw.println("Puerto: " + datos.getPuerto());
 
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
           // aprovechamos el finally para
           // asegurarnos que se cierra el fichero.
           if (null != fichero)
              fichero.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
    }
    
    private void creaFicheroConexionPHP(){
    
         try
        {
            fichero = new FileWriter("C:\\Apache\\htdocs\\PHP\\db_config.php");
            pw = new PrintWriter(fichero);
            
            pw.println("<?php");
            pw.println("define('DB_USER', \""+ datos.getUsuario()+"\");");
            pw.println("define('DB_PASSWORD', \""+ datos.getpswBd()+"\");" );
            pw.println("define('DB_DATABASE', \""+ datos.getNombreBd()+"\");" );
            pw.println("define('DB_SERVER', \""+ datos.getServidor()+"\");" );
            pw.println("?>" );
 
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
           // aprovechamos el finally para
           // asegurarnos que se cierra el fichero.
           if (null != fichero)
              fichero.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
    }
    
}
