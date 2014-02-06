/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectohosteleria;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author Nerea
 */
public class VistaPrincipal1024 extends javax.swing.JFrame  {

   private Dimension dim;
   private ModeloPrincipal modeloPrincipal=new ModeloPrincipal();
   //public static boolean mesasAbiertas=false;
   
   
    public VistaPrincipal1024() {
        initComponents();
        System.out.print("1024");
        JButton bCobrar=new JButton("Cobrar");
        PanelImagen panel=new PanelImagen(566,383,250,200,"imagenes/l1.jpg");
        bCobrar.setBounds(650, 537, 166, 45);
        bCobrar.setBackground(Color.green);
        
        this.add(bCobrar);
        this.add(panel);
        this.add(new PanelImagen("imagenes/l1.jpg"));
        
        dim=super.getToolkit().getScreenSize();
        super.setSize(dim);
        
        delTiempo.setVisible(false);
        
        File fichero = new File("envio.conf");
             if (!fichero.exists()) 
             
                 enviar.setEnabled(false);
             else
                 enviar.setEnabled(true);
        
        modeloPrincipal.ponActionListener(total);
        modeloPrincipal.ponBotonesCantidad(panel);
        modeloPrincipal.creaColumnas(jTable1);
        modeloPrincipal.añadirPestañas(jTabbedPane1);
        modeloPrincipal.ponChangeListener(jTabbedPane1, delTiempo);
        modeloPrincipal.ponActionListenerBotonDelTiempo(delTiempo);
        modeloPrincipal.ponActionListenerBotonCobrar(bCobrar,total,this,jTable1,new DefaultTableModel(),modeloPrincipal);
        modeloPrincipal.ponActionListenerBotonAbreMesas(mesa, this);
        modeloPrincipal.ponerActionListenerBotonBorrarFila(borrar,jTable1,total);
        modeloPrincipal.vistaHoy(totalHoy, this);
        modeloPrincipal.vistaFechas(visualizarFechas, this);
        modeloPrincipal.vistaModificar(modificarPrecio, this);
        modeloPrincipal.vistaAñadir(añadir, this);
        modeloPrincipal.vistaEliminar(eliminar, this);
        //TODO arreglar separar: se le pasa parametro de Vistaprincipal
        modeloPrincipal.vistaSeparar1024(separar, this,modeloPrincipal);
        modeloPrincipal.vistaEnvio(envio, this,enviar);
        modeloPrincipal.ponActionListenerEnviar(enviar, this, jTable1);
        modeloPrincipal.ponActionListenerSalir(Salir);
        modeloPrincipal.ponTitulo(jLabel1);
        
       
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        delTiempo = new javax.swing.JButton();
        total = new javax.swing.JLabel();
        borrar = new javax.swing.JButton();
        mesa = new javax.swing.JButton();
        enviar = new javax.swing.JButton();
        Salir = new javax.swing.JButton();
        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();
        totalHoy = new javax.swing.JMenuItem();
        visualizarFechas = new javax.swing.JMenuItem();
        editar = new javax.swing.JMenu();
        modificarPrecio = new javax.swing.JMenuItem();
        añadir = new javax.swing.JMenuItem();
        eliminar = new javax.swing.JMenuItem();
        separarC = new javax.swing.JMenu();
        separar = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        envio = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1024, 768));

        jLabel1.setText("jLabel1");

        jTabbedPane1.setBackground(new java.awt.Color(0, 0, 0));
        jTabbedPane1.setForeground(new java.awt.Color(255, 255, 255));
        jTabbedPane1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jScrollPane1.setBackground(new java.awt.Color(204, 255, 255));
        jScrollPane1.setBorder(javax.swing.BorderFactory.createMatteBorder(5, 5, 5, 5, new java.awt.Color(255, 204, 0)));

        jTable1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTable1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(jTable1);

        delTiempo.setText("Del tiempo");

        total.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        total.setForeground(new java.awt.Color(255, 255, 255));
        total.setText("TOTAL");

        borrar.setText("Borrar seleccion");

        mesa.setText("Abrir Mesas");

        enviar.setText("Enviar");
        enviar.setEnabled(false);

        Salir.setText("Salir");

        jMenu3.setText("Ver");

        totalHoy.setText("Ver total de hoy");
        jMenu3.add(totalHoy);

        visualizarFechas.setText("Elegir fechas a visualizar");
        jMenu3.add(visualizarFechas);

        jMenuBar2.add(jMenu3);

        editar.setText("Editar");

        modificarPrecio.setText("Modificar precio ");
        editar.add(modificarPrecio);

        añadir.setText("Añadir producto");
        editar.add(añadir);

        eliminar.setText("Eliminar producto");
        editar.add(eliminar);

        jMenuBar2.add(editar);

        separarC.setText("Cuenta");

        separar.setText("Separar cuenta");
        separarC.add(separar);

        jMenuBar2.add(separarC);

        jMenu1.setText("Habilitar envío");

        envio.setText("Habilitar");
        jMenu1.add(envio);

        jMenuBar2.add(jMenu1);

        setJMenuBar(jMenuBar2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 452, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(delTiempo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Salir, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(enviar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(mesa, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(borrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(total, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(total, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(borrar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(mesa, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(140, 140, 140)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(delTiempo, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(Salir, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(enviar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 561, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(62, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VistaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VistaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VistaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VistaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VistaPrincipal().setVisible(true);
            }
        });
    }
    
   
   /* public JTabbedPane getJTabbedPane(){
    
        return jTabbedPane1;
    }*/
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Salir;
    private javax.swing.JMenuItem añadir;
    private javax.swing.JButton borrar;
    private javax.swing.JButton delTiempo;
    private javax.swing.JMenu editar;
    private javax.swing.JMenuItem eliminar;
    public static javax.swing.JButton enviar;
    private javax.swing.JMenuItem envio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton mesa;
    private javax.swing.JMenuItem modificarPrecio;
    private javax.swing.JMenuItem separar;
    private javax.swing.JMenu separarC;
    public javax.swing.JLabel total;
    private javax.swing.JMenuItem totalHoy;
    private javax.swing.JMenuItem visualizarFechas;
    // End of variables declaration//GEN-END:variables

    
}
