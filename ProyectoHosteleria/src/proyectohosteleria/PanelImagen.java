/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectohosteleria;

    import java.awt.Dimension;
    import java.awt.Graphics;
    import javax.swing.ImageIcon;
    public class PanelImagen extends javax.swing.JPanel {
        
        String imagen;
        
    public PanelImagen(String imagen){
        
        this.imagen=imagen;
        
        Dimension dim=super.getToolkit().getScreenSize();
        super.setSize(dim);
    }
    
    public PanelImagen(int x, int y, int width, int heigth, String imagen){
        
        this.imagen=imagen;
        this.setBounds(x, y, width, heigth);
    }
    
    @Override
    public void paintComponent (Graphics g){
    Dimension tamanio = getSize();
    ImageIcon imagenFondo = new ImageIcon(getClass().getResource(imagen));
    g.drawImage(imagenFondo.getImage(),0,0,tamanio.width, tamanio.height, null);
    setOpaque(false);
    super.paintComponent(g);
    }
    }