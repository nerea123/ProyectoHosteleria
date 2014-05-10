/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package proyectohosteleria;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

/**
 *
 * @author Nerea
 */
public class ModeloMesas implements ActionListener {
    
    JDialog jMesa;
    boolean estaAbiertoVistaPrincipal[];
    VistaPrincipal arrayVistaPrincipal[];
    JButton arrayMesas[];
       
    public ModeloMesas(){
    
        estaAbiertoVistaPrincipal=new boolean[getNumMesas()];
        arrayVistaPrincipal=new VistaPrincipal[getNumMesas()];
        arrayMesas=new JButton[getNumMesas()];
    }
    
    public void ponBotones(JPanel mesas){
        try {
            int i=0;
            Statement statement=Conexion.getInstance().conectar().createStatement();
            ResultSet resultset=statement.executeQuery("select DESCRIPCION from MESAS ");
            while(resultset.next()){
                JButton b=new JButton();
                b.setText((String)resultset.getObject(1));
                b.addActionListener(this);
                b.setVisible(true);
                mesas.add(b);
                arrayMesas[i]=b;
                estaAbiertoVistaPrincipal[i]=false;
                i++;
                //arrayVistaPrincipal[i]=new VistaPrincipal();
            }
            statement.close();
            resultset.close();
        } catch (SQLException ex) {
            Logger.getLogger(ModeloMesas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private int getNumMesas(){
    
        int numMesas=0;
        try {
            Statement statement=Conexion.getInstance().conectar().createStatement();
            ResultSet resultset=statement.executeQuery("select COUNT(IDM) from mesas ");
            while(resultset.next()){
              numMesas=(int)resultset.getInt(1);
            }
            statement.close();
            resultset.close();
        } catch (SQLException ex) {
            Logger.getLogger(ModeloMesas.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return numMesas;
    }

    /*public void getJMesa(JDialog jMesa){
        this.jMesa=jMesa;
    }*/
   
    @Override
    public void actionPerformed(ActionEvent e) {
       /*SE GUARDAN EN 3 ARRAYS LOS BOTONES DE MESAS QUE SE CREAN, VARIABLES BOOLEANAS QUE INDICAN SI YA SE HA INSTANCIADO
        UN OBJETO DE TIPO VISTAPRINCIPAL RELACIONADO CON SU BOTON DE MESA Y OBJETOS DE TIPO VISTAPRINCIPAL.
        CUANDO UN BOTON MESA ES PULSADO SE COMPRUEBA LA VARIABLE BOOLEANA QUE ESTA EN SU MISMO INDICE Y SI ESTA 
        A FALSE SE INSTANCIA OBJETO VISTAPRINCIPAL DEL MISMO INDICCE Y SE PONE A TRUE, SI ESTA A TRUE SIMPLEMENTE SE MUESTRA EL 
        OBJETO VISTAPRINCIPAL YA INSTANCIADO ANTERIORMENTE */
        JButton boton=(JButton)e.getSource();
        VistaPrincipal principal;
        boton.setBackground(Color.yellow);
        VistaMesas.getInstance().dispose();
        //jMesa.dispose();
        //ModeloPrincipal.mesas=(VistaMesas)jMesa;
        for(int i=0;i<arrayMesas.length;i++){
            if(arrayMesas[i] == boton && estaAbiertoVistaPrincipal[i]== false){
                arrayVistaPrincipal[i]=new VistaPrincipal();
                //arrayVistaPrincipal[i].mesasAbiertas=true;
                arrayVistaPrincipal[i].setTitle(boton.getText());
                arrayVistaPrincipal[i].setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                arrayVistaPrincipal[i].setVisible(true);
                estaAbiertoVistaPrincipal[i]=true;
            }
            else if (arrayMesas[i] == boton && estaAbiertoVistaPrincipal[i]== true)
                arrayVistaPrincipal[i].setVisible(true);
        }
        
                
    }
}
