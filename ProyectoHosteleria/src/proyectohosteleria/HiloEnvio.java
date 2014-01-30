/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package proyectohosteleria;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nerea
 */
public class HiloEnvio implements Runnable {

    @Override
    public void run() {
             
        ObjectOutputStream oos=null;
        Socket socket=null;
        try{
            String[] aux=getDatosEnvio();
            System.out.println(aux[1]);
            socket=new Socket(aux[1],Integer.parseInt(aux[2]));

            oos=new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(aux[0]);
            oos.flush();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        finally{
            try {
                oos.close();
                socket.close();
            } catch (IOException ex) {
                Logger.getLogger(ModeloPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
    
     public String[] getDatosEnvio(){
        FileReader read=null;
        String server="IP servidor: ";
        String movil="IP movil: ";
        String puerto="Puerto: ";
        String[] datos=new String[3];
        try {
            File archivo= new File("envio.conf");
            read = new FileReader(archivo);
            BufferedReader bufer=new BufferedReader(read);
            
            String linea;
            while((linea=bufer.readLine()) != null){
                if(linea.indexOf(server)!= -1)
                    datos[0]= linea.substring(linea.indexOf(server)+server.length());
                else if(linea.indexOf(movil)!= -1)
                    datos[1]= linea.substring(linea.indexOf(movil)+movil.length());
                else
                    datos[2]= linea.substring(linea.indexOf(puerto)+puerto.length());
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ModeloPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ModeloPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                read.close();
            } catch (IOException ex) {
                Logger.getLogger(ModeloPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return datos;
    }
    
}
