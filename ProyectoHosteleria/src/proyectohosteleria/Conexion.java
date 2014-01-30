/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package proyectohosteleria;
import com.mysql.jdbc.Connection;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author Nerea
 */
public class Conexion {
    
    private static Conexion INSTANCE = new Conexion();
    private String bd;
    private String servidor;
    private String puerto;
    private String usuario;
    private String contraseña;
    
    
    private Conexion() {
        //Obtenemos los datos de configuracion
        leerFichero();
    }
    
    public static Conexion getInstance() {
        return INSTANCE;
    }
    
    
     public Connection conectar()
    {
          try{
            Class.forName("com.mysql.jdbc.Driver");
            String BaseDeDatos = "jdbc:mysql://"+servidor+":"+puerto+"/"+getBd()+"";
            String login = usuario;
            String passwd = contraseña;
           
            Connection Conexion = (Connection) DriverManager.getConnection(BaseDeDatos,login,passwd);
            return Conexion;
           }
           catch (ClassNotFoundException e) {
             System.out.println("Clase no encontrada");
           }
           catch (SQLException e) {
             System.out.println(e);
           }  

           
        return null;
    }

    public String getBd() {
        return bd;
    }
     
     public void leerFichero(){
          File archivo = null;
          FileReader fr = null;
          BufferedReader br = null;
          String base="Base de datos: ";
          String server="Servidor: ";
          String user="Usuario: ";
          String port="Puerto: ";
          String psw="Contraseña: ";
 
      try {
         // Apertura del fichero y creacion de BufferedReader para poder
         // hacer una lectura comoda (disponer del metodo readLine()).
         archivo = new File ("bar.conf");
         fr = new FileReader (archivo);
         br = new BufferedReader(fr);
 
         // Lectura del fichero
         String linea;
         while((linea=br.readLine())!=null){
            if(linea.indexOf(base)!=-1){
                bd=linea.substring(linea.indexOf(base)+base.length(), linea.length());
            }
             if(linea.indexOf(server)!=-1){
                servidor=linea.substring(linea.indexOf(server)+server.length(), linea.length());
            }
            if(linea.indexOf(user)!=-1){
                usuario=linea.substring(linea.indexOf(user)+user.length(), linea.length());
            }
             if(linea.indexOf(port)!=-1){
                puerto=linea.substring(linea.indexOf(port)+port.length(), linea.length());
            }
             if(linea.indexOf(psw)!=-1){
                contraseña=linea.substring(linea.indexOf(psw)+psw.length(), linea.length());
            }
         }
        
      }
      catch(Exception e){
         e.printStackTrace();
      }finally{
         // En el finally cerramos el fichero, para asegurarnos
         // que se cierra tanto si todo va bien como si salta
         // una excepcion.
         try{                   
            if( null != fr ){  
               fr.close();    
            }                 
         }catch (Exception e2){
            e2.printStackTrace();
         }
      }
   }
     }

