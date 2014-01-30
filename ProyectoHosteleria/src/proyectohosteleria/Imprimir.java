/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package proyectohosteleria;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.TableModel;

/**
 *
 * @author Nerea
 */

/*códigos ESC-POS*/
public class Imprimir {

    private static int cont=0;
    private static Imprimir INSTANCE=new Imprimir();
    
    private Imprimir() {
    }
    
    public static Imprimir getInstance() {
        return INSTANCE;
    }
    
    public void abrirCajon(){
        
        try {
            FileOutputStream fileOutputStream= new FileOutputStream("COM3");
             PrintStream printStream= new PrintStream(fileOutputStream);
             
             int[] Caracter = new int[]{27, 112, 0, 10, 100};
            for (int i = 0; i < Caracter.length; i++) {
                printStream.write(Caracter[i]);
            }
     
            printStream.close();
            fileOutputStream.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Imprimir.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Imprimir.class.getName()).log(Level.SEVERE, null, ex);
        
        }
    }
    
    public void escribir(String archivo){
        FileReader fichero = null;
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream("COM3");
            PrintStream ps = new PrintStream(fileOutputStream);
            fichero = new FileReader(archivo);
            BufferedReader b = new BufferedReader(fichero);
            String linea;
            while ((linea = b.readLine()) != null) {
              ps.println(linea.toString());
              
              System.out.println(linea.toString());
              cont++;

            }
            fichero.close();
            ps.close();
            fileOutputStream.close();
        } catch (IOException ex) {
            Logger.getLogger(Imprimir.class.getName()).log(Level.SEVERE, null, ex);
        }  
       }
    
    public void cortar(){
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream("COM3");
            PrintStream printStream = new PrintStream(fileOutputStream);
            int[] cortar=new int[]{29,86,65,cont};
            for(int i=0;i<cortar.length;i++)
                printStream.write(cortar[i]);
            cont=0;
            printStream.close();
            fileOutputStream.close();
        } catch (IOException ex) {
            Logger.getLogger(Imprimir.class.getName()).log(Level.SEVERE, null, ex);
        }  
       }
    
     public void guardar(TableModel modelo,ModeloPrincipal principal){
        FileWriter fichero = null;
        PrintWriter pw = null;
        String producto[]=new String[modelo.getRowCount()];
        String precio[]=new String[modelo.getRowCount()];
        String cantidad[]=new String[modelo.getRowCount()];
           Object t;
                try {
                    fichero = new FileWriter("tiquet.txt");
                    pw = new PrintWriter(fichero);
                    pw.println("----------------------");
                    pw.println("Bar 883 ");
                    pw.println("Calle les palmeres  ");
                    pw.println("----------------------");
                    pw.println();
                    
                    for(int i=0;i<modelo.getRowCount();i++){
                        for(int j=0;j<modelo.getColumnCount();j++){
                            switch(j){
                                case 0:
                                     t=modelo.getValueAt(i, j);
                                     String sproducto=String.valueOf(t);
                                     producto[i]=sproducto;
                                    break;
                                case 1:
                                    t=modelo.getValueAt(i, j);
                                     String sprecio=String.valueOf(t);
                                     precio[i]=sprecio;
                                    break;
                                case 2:
                                    t=modelo.getValueAt(i, j);
                                     String scantidad=String.valueOf(t);
                                     cantidad[i]=scantidad;
                                    break;
                            }
                            
                        }
                    }
                     for(int i=0;i<modelo.getRowCount();i++){
                         pw.println(cantidad[i]+"X "+precio[i]+"   "+producto[i] );
                     }
                      
                    pw.println();  
                    pw.println(); 
                    pw.println("\t  Total: "+principal.getTotal());
                    pw.println(); 
                    pw.print("Gracias por su visita");

                    
                    
                    pw.close();
                } catch (IOException ex) {
                    Logger.getLogger(Imprimir.class.getName()).log(Level.SEVERE, null, ex);
                }
    }
        
    
   
}
