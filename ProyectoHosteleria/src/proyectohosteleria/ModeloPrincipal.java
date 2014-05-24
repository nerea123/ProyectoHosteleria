/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package proyectohosteleria;

import com.mysql.jdbc.Statement;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;



/**
 *
 * @author Nerea
 */
public class ModeloPrincipal implements Serializable {

    private Statement statement;
    private JButton arrayBoton[];
    private int numFila=0,cantidad=1,auxNumFila=0;
    private float total=0;
    private String stringCantidad="",stringTotal="";
    private DefaultTableModel modelo;
    private boolean delTiempo=false;
    private ActionListener action;
    Color color;
   // public static VistaMesas mesas;
    
    
    public void creaColumnas(JTable table){
    
        modelo=new DefaultTableModel();
        modelo.addColumn("Producto");
        modelo.addColumn("Precio");
        modelo.addColumn("Cantidad");
        modelo.addColumn("Total");
       

        table.setModel(modelo);
    }
    
    public int getNumFilas(){
         int numFilas=0;
         try {
            statement = (Statement)Conexion.getInstance().conectar().createStatement();
            ResultSet resultset= statement.executeQuery("SELECT COUNT(IDP) from productos;");
            while (resultset.next()){
               numFilas=(int)resultset.getInt(1);   
            }
   
            statement.close();
            resultset.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(ModeloPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
         return numFilas;
    }
    
     public void ponChangeListener(JTabbedPane tabbed,final JButton boton){
         ChangeListener changeListener = new ChangeListener() {
         public void stateChanged(ChangeEvent changeEvent) {
         JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent.getSource();
         int index = sourceTabbedPane.getSelectedIndex();
         if(index == 2) boton.setVisible(true);
         else 
             boton.setVisible(false);
      }
    };
         tabbed.addChangeListener(changeListener);
     }
    
    public void añadirPestañas(JTabbedPane tabbed){
        
        
        añadirBotones();
        
        try {
            statement = (Statement)Conexion.getInstance().conectar().createStatement();
            ResultSet resultset= statement.executeQuery("SELECT FAMILIA from productos group by FAMILIA;");
    
            //AÑADIMOS TANTAS PESTAÑAS COMO FAMILIAS HAY EN LA BD
            while (resultset.next()){
                String resultado=(String)resultset.getObject(1);
                JPanel panel=new JPanel();
                panel.setBackground(Color.green);
                panel.setBorder(null);
                
                panel.setLayout(new GridLayout(0,2,10,5));
               
                JScrollPane scroll=new JScrollPane();
                scroll.setViewportView(panel); 
                  
                scroll.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                scroll.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
                scroll.setBorder(javax.swing.BorderFactory.createMatteBorder(5, 5, 5, 5, new java.awt.Color(255, 204, 0))); 
               
                //SEGUN SEA LA FAMILIA DEL BOTON LO AÑADIMOS A SU PESTAÑA CORRESPONDIENTE
                
                for(int i=0;i<arrayBoton.length;i++){
                    if(arrayBoton[i].getName().equals(resultado))
                        panel.add(arrayBoton[i]);
                    
                }
                 
                
                tabbed.addTab(resultado, scroll);
                
               
            }
        
            statement.close();
            resultset.close();
        } catch (SQLException ex) {
            Logger.getLogger(ModeloPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void añadirBotones(){
        try {
            statement = (Statement)Conexion.getInstance().conectar().createStatement();
            ResultSet resultset= statement.executeQuery("SELECT DESCRIPCION,FAMILIA,IMAGEN from productos;");
            
            int i=0;
            arrayBoton=new JButton[getNumFilas()];
            
            while (resultset.next()){
                //CREAMOS LOS BOTONES CON ICONOS CON LA RUTA RELATIVA DE LA BD Y CON EL TEXTO DESCRIPCION DE LA BD
                //AÑADIMOS AL NAME LA FAMILIA DE LA BD PARA DESPUES PODER AÑADIRLOS EN EL JTABPANE
              
                JButton boton=null;
                String icono;
                if(resultset.getObject(3).toString().indexOf("\\") == -1){
                    boton=new JButton((String)resultset.getObject(1),new javax.swing.ImageIcon(getClass().getResource((String)resultset.getObject(3))));
                }
                else{
                    boton=new JButton((String)resultset.getObject(1),new javax.swing.ImageIcon((String)resultset.getObject(3)));
                }
                boton.setToolTipText((String)resultset.getObject(1));
                boton.setName((String)resultset.getObject(2));
                
                //poner texto abajo y centrar imagen
                boton.setHorizontalTextPosition(SwingConstants.CENTER);
                boton.setVerticalTextPosition(SwingConstants.BOTTOM) ;
                boton.setVerticalAlignment(SwingConstants.CENTER);
                boton.addActionListener(action);
                color=boton.getBackground();
                
                arrayBoton[i]=boton;
                i++;
                
            }
            
            statement.close();
            resultset.close();
        } catch (SQLException ex) {
            Logger.getLogger(ModeloPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void ponTitulo(JLabel label){
        
        label.setText(Conexion.getInstance().getBd().toUpperCase());
        label.setFont(new java.awt.Font("Tahoma", 1, 36));
        label.setForeground(Color.yellow);
        label.setHorizontalAlignment( SwingConstants.CENTER );
    }

    
    public void ponActionListener(final JLabel label,final JFrame principal){
        action=new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JButton boton=(JButton)e.getSource();
                Object[] descripcion=new Object[4];
                if(modelo.getRowCount()==0)
                    numFila=0;
                try {

                    statement = (Statement)Conexion.getInstance().conectar().createStatement();

                    ResultSet rs = statement.executeQuery("select DESCRIPCION,PRECIO from PRODUCTOS where DESCRIPCION='"+boton.getText()+"'");
                    while(rs.next()){
                    for(int i=0;i<2;i++){
                        descripcion[i]=rs.getObject(i+1); 
                    }
                    modelo.addRow(descripcion);
                    }
                    if(getCantidad()==0)
                        cantidad=1;
                    modelo.setValueAt(cantidad, numFila, 2);
                    float precio=(float) modelo.getValueAt(numFila, 1);
                    if(delTiempo){
                       precio+=0.10;
                       modelo.setValueAt(redondear(precio,2), numFila, 1);
                   }
                    delTiempo=false;
                    
                    modelo.setValueAt(redondear(precio*cantidad,2), numFila, 3);
                    setTotal(getTotal() + redondear(precio*getCantidad(),2));
                    stringTotal=String.valueOf(redondear(getTotal(),2));
                    label.setText("TOTAL: "+stringTotal);
                    
                    VistaPrincipal p=(VistaPrincipal)principal;
                    int idt=p.getIdtiquet();

                    ResultSet result=statement.executeQuery("select IDP from PRODUCTOS where DESCRIPCION='"+descripcion[0]+"'");
                    result.next();
                    int idp=result.getInt(1);
                    result.close();
                    statement.executeUpdate("insert into LINEA(IDT,IDP,CANTIDAD) values("+idt+","+idp+","+cantidad+")");
                    statement.close();
                    cantidad=1;stringCantidad="";

                    rs.close();
                    numFila++;
                    cantidad=1;    
                     

                }catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        };
    }
    public void ponBotonesCantidad(JPanel panel){
    
        panel.setLayout(new GridLayout(0,3,5,5));
       
        for(int i=0;i<10;i++){
            String texto=String.valueOf(i);
            JButton boton = new JButton(texto);
            boton.setBackground(Color.BLACK);
            boton.setForeground(Color.WHITE);
            boton.setVisible(true);
            ponActionListenerBotonesCantidad(boton);
            panel.add(boton);
            
        }
    }
    
    public void ponActionListenerBotonesCantidad(JButton button){
    
        ActionListener actionListener = new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                JButton boton=(JButton)e.getSource();
                stringCantidad+=boton.getText();
                setCantidad(Integer.parseInt(stringCantidad));
               
             }               
        };
        
        button.addActionListener(actionListener);
    }
    
    public void ponActionListenerBotonCobrar(final JButton cobrar,final JLabel label,final JFrame frame,final JTable table,DefaultTableModel model,final ModeloPrincipal principal){
        
       
        ActionListener actionListener=new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                int alerta = Alerta.creaAlertaWarning("¿Cobrar cuenta?", "Cobrar", null);
              if(alerta==0){
                  VistaPrincipal p=null;
                    try {
                        Statement statement = (Statement)Conexion.getInstance().conectar().createStatement();
                        p=(VistaPrincipal)frame;
                        statement.executeUpdate("update tiquet set COBRADO=1 where IDT="+p.getIdtiquet() );
                    } catch (SQLException ex) {
                        Logger.getLogger(ModeloPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    VistaDarCambio cambio=new VistaDarCambio(frame, true);                    
                    cambio.setModeloPrincipal(principal,"");
                    cambio.setVisible(true);
                    int alert=Alerta.creaAlertaWarning("¿Imprimir tiquet?", "Imprimir", cambio);
                     Imprimir.getInstance().abrirCajon();
                    if(alert==0){
                        Imprimir.getInstance().guardar(modelo, principal);
                        Imprimir.getInstance().escribir("tiquet.txt");
                        Imprimir.getInstance().cortar();
                    }
                    
                    label.setText("TOTAL: 0");
                    auxNumFila=0;
                    cierraMesas(cobrar,frame,label,table);
                    for(int i=modelo.getRowCount();i>0;i--)
                                modelo.removeRow(i-1);
                    if(p.getTitle().equalsIgnoreCase("Terminal hostelería"))
                        creaIdTiquet(p);
              }
            }
        };
        cobrar.addActionListener(actionListener);
    }
    
    public void ponActionListenerBotonDelTiempo(final JButton tiempo){
        
        ActionListener actionListener=new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                delTiempo=true;
            }
        };
        
        tiempo.addActionListener(actionListener);
    
    }
    
    public void ponerActionListenerBotonBorrarFila(JButton fila, final JTable table,final JLabel label,final JFrame principal){
    
         
        ActionListener actionListener=new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int filaSeleccionada=table.getSelectedRow();
                    float precio=(float) modelo.getValueAt(filaSeleccionada, 1);
                    int cantidad=(int) modelo.getValueAt(filaSeleccionada, 2);
                    
                    setTotal(getTotal() - cantidad*precio);
                    setTotal(redondear(getTotal(), 2));
                    
                    //Obtenemos los idl para el tiquet actual y borramos la linea que
                    //petenece a la linea selecconada
                    VistaPrincipal p=(VistaPrincipal)principal;
                    int idt=p.getIdtiquet();
                    Statement statement = (Statement)Conexion.getInstance().conectar().createStatement();
                    ResultSet result=statement.executeQuery("select IDP from PRODUCTOS where DESCRIPCION='"+modelo.getValueAt(filaSeleccionada, 0)+"'");
                    result.next();
                    int idp=result.getInt(1);
                    result.close();
                    ResultSet r=statement.executeQuery("select IDL from linea where IDT="+idt);
                    int contador = 0;
                    while(contador<= filaSeleccionada){
                        r.next();
                        contador++;
                    }
                    
                    int idl=r.getInt(1);
                    statement.executeUpdate("delete from linea where idl=+"+idl);
                    statement.close();
                    
                    modelo.removeRow(table.getSelectedRow());
                    label.setText(String.valueOf(getTotal()));
                    numFila--;
                } catch (SQLException ex) {
                    Logger.getLogger(ModeloPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        
        fila.addActionListener(actionListener);
        
    }

    
    public void ponActionListenerBotonAbreMesas(final JButton mesa,final JFrame frame){
        
        ActionListener actionListener=new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
               /*SINGLETON DE VISTA MESAS */
                
                VistaMesas.getInstance().setVisible(true);
                
            }
        };
        
        mesa.addActionListener(actionListener);
    
    }
    
    public void cierraMesas(final JButton mesa,final JFrame frame,final JLabel label,final JTable table){
      
      for (int i=0;i<VistaMesas.getInstance().mesas.arrayMesas.length;i++) {

                    if(VistaMesas.getInstance().mesas.arrayVistaPrincipal[i] == frame){
                       
                        VistaMesas.getInstance().mesas.estaAbiertoVistaPrincipal[i]=false;
                        VistaMesas.getInstance().mesas.arrayVistaPrincipal[i].dispose();
                        VistaMesas.getInstance().mesas.arrayMesas[i].setBackground(color);
                     }
                  }
        
    }
 
  
    public static float redondear(float pNumero, int pCantidadDecimales) {
            
            BigDecimal value = new BigDecimal(pNumero);
            value = value.setScale(pCantidadDecimales, RoundingMode.HALF_EVEN); 
            return value.floatValue(); 
}
    
  /*  public void insertarDatos(JLabel label,JFrame frame,JTable table,DefaultTableModel modelo){
    
            try {
                Statement statement;
                               
                Vector o=modelo.getDataVector();
                Object [] ctabla=new Object[o.size()*5];
                System.out.print(o.size()*3);
                statement = (Statement) Conexion.getInstance().conectar().createStatement();
               
                //statement.executeUpdate("INSERT INTO TIQUET (IDM,FECHA) VALUES ("+idMesa+",SYSDATE())");
                
                int idt=0;
                 for (int i=0;i<VistaMesas.getInstance().mesas.arrayMesas.length;i++) {
                    if(VistaMesas.getInstance().mesas.arrayVistaPrincipal[i] == frame){
                        idt=VistaMesas.getInstance().mesas.arrayVistaPrincipal[i].getIdtiquet();                        
                     }
                  }
                 
                /*for(int e=0;e<modelo.getRowCount();e++){
                    for(int i=0;i<modelo.getColumnCount();i++){
                        ctabla[i]=table.getValueAt(e, i);
                  
                    }
                ResultSet result=statement.executeQuery("select IDP from PRODUCTOS where DESCRIPCION='"+ctabla[0]+"'");
                result.next();
                int idp=result.getInt(1);
                result.close();
                statement.executeUpdate("insert into LINEA(IDT,IDP,CANTIDAD) values("+idt+","+idp+","+ctabla[2]+")");
        //}   
        
        } catch (SQLException ex) {
            Logger.getLogger(ModeloPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }*/
    
    public int getIdMesa(String id){System.out.println("id"+id);
        int idMesa=0;
        for(int i=0;i<VistaMesas.getInstance().mesas.arrayMesas.length;i++){
                    if(VistaMesas.getInstance().mesas.estaAbiertoVistaPrincipal[i]==true){
                        try {
                            Statement statement = (Statement)Conexion.getInstance().conectar().createStatement();
                            ResultSet resultset=statement.executeQuery("SELECT IDM FROM mesas WHERE DESCRIPCION='"+id+"'");
                            while(resultset.next())
                                idMesa=resultset.getInt(1);
                            resultset.close();
                            statement.close();
                            break;
                        } catch (SQLException ex) {
                            Logger.getLogger(ModeloPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                        
                }
                if(idMesa==0)
                    idMesa=8;
                return idMesa;
    }
    
    public int getIdt(Statement statement){
        int idt=0;
        try {
            ResultSet resultset=statement.executeQuery("select max(IDT) from TIQUET");
            resultset.next();
            idt=resultset.getInt(1)+1;
            resultset.close();
        } catch (SQLException ex) {
            Logger.getLogger(ModeloPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
        return idt;
    }
    
    
    public void creaIdTiquet(VistaPrincipal principal){
         Statement statement=null;
         int idt=0,idMesa=0;
         String id=principal.getTitle();
       try {
           statement = (Statement) Conexion.getInstance().conectar().createStatement();
           //MIRAMOS SI HAY ALGUNA MESA ABIERTA. SI HAY ALGUNA COJEMOS SU IDMESA SINO LE ASIGNAMOS UNO
                idMesa=getIdMesa(id);

           idt=this.getIdt(statement);
           statement.executeUpdate("insert into tiquet (IDT,IDM,FECHA) values("+idt+","+idMesa+", SYSDATE() )");
       } catch (SQLException ex) {
           Logger.getLogger(VistaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
       }
        principal.setIdtiquet(idt);
         
    }
    
    public void insertaPedido(JFrame frame,DefaultTableModel modelo,JTable table){
    
        String id=frame.getTitle();
        int idMesa=getIdMesa(id);
        Statement statement=null;
        int idt=0;
               
        Vector o=modelo.getDataVector();
        VistaPrincipal p=(VistaPrincipal)frame;
       idt= p.getIdtiquet();
        try {
            statement = (Statement) Conexion.getInstance().conectar().createStatement();
            statement.executeUpdate("UPDATE tiquet set ENVIADO=1 where IDT ="+idt); 
            
            auxNumFila=numFila;
            statement.close();
        } catch (SQLException ex) {
            Logger.getLogger(ModeloPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    /*public void refresh(JTabbedPane tab,String titulo,String titBoton,String imagen){
    
        for(int i=0;i<=tab.getTabCount();i++){
           
            if(tab.getTitleAt(i).equals(titulo)){
                System.out.println(tab.getTitleAt(i));
                JButton boton=new JButton(titBoton,new javax.swing.ImageIcon(imagen));
                boton.setVisible(true);
                tab.add(boton, i);
                
                break;
            }
        }
    }*/
    
    public void vistaHoy(JMenuItem item,final JFrame frame){
    
            ActionListener actionListener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new VistaTotalHoy(frame,true).setVisible(true);
            }
        };
            item.addActionListener(actionListener);
       }
    
    public void vistaFechas(JMenuItem item,final JFrame frame){
    
            ActionListener actionListener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new VistaFechas(frame,true).setVisible(true);
            }
        };
            item.addActionListener(actionListener);
       }
    
    public void vistaModificar(JMenuItem item,final JFrame frame){
    
         ActionListener actionListener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new VistaModificarPrecio(frame,true).setVisible(true);
            }
        };
            item.addActionListener(actionListener);
    }
    
    public void vistaAñadir(JMenuItem item,final JFrame frame){
    
         ActionListener actionListener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new VistaAñadirProducto(frame,true).setVisible(true);
            }
        };
            item.addActionListener(actionListener);
    }
     public void vistaEliminar(JMenuItem item,final JFrame frame){
    
         ActionListener actionListener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new VistaEliminarProducto(frame,true).setVisible(true);
            }
        };
            item.addActionListener(actionListener);
    }
     
      public void vistaSeparar(JMenuItem item,final JFrame frame,final ModeloPrincipal principal){
    
         ActionListener actionListener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
               VistaSeparar separar= new VistaSeparar(frame,true);
               separar.setModelo(modelo,(VistaPrincipal)frame,principal);
               separar.setVisible(true);
            }
        };
            item.addActionListener(actionListener);
    }
      
      public void vistaSeparar1024(JMenuItem item,final JFrame frame,final ModeloPrincipal principal){
    
         ActionListener actionListener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
               VistaSeparar1024 separar= new VistaSeparar1024(frame,true);
               separar.setModelo(modelo,(VistaPrincipal1024)frame,principal);
               separar.setVisible(true);
            }
        };
            item.addActionListener(actionListener);
    }
      
       public void vistaEnvio(JMenuItem item,final JFrame frame,final JButton b){
    
         ActionListener actionListener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
              VistaEnvio envio= new VistaEnvio(frame,true);
              envio.setButton(b);
              envio.setVisible(true);
            }
        };
            item.addActionListener(actionListener);
    }
       
       public void ponActionListenerEnviar(JButton enviar,final JFrame frame,final JTable table){
       
            ActionListener actionListener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                insertaPedido(frame, modelo, table);
                new Thread(new HiloEnvio()).start();
            }
        };
            enviar.addActionListener(actionListener);
          
       }
     
      public void ponActionListenerSalir(JButton button){
      
       ActionListener actionListener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
               int alert= Alerta.creaAlertaWarning("¿Salir del programa?\n", "Cerrar", null);
               if(alert == 0) 
                     System.exit(0);
            }
        };
            button.addActionListener(actionListener);
      }

    /**
     * @param numFila the numFila to set
     */
    public void setNumFila(int numFila) {
        this.numFila = numFila;
    }

    /**
     * @return the cantidad
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * @param cantidad the cantidad to set
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * @return the total
     */
    public  float getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(float total) {
        this.total = total;
    }
    
   
}
