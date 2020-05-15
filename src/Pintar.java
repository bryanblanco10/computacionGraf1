
import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.ImageIcon;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author BRYAN10
 */
public class Pintar extends Applet{
    ImageIcon imagenIcono; 
    final int ANCHO_PANTALLA = 800;
    final int ALTO_PANTALLA = 800;  
    
    public void init(){
        resize(ANCHO_PANTALLA, ALTO_PANTALLA);
       
    }
    public void paint(Graphics g){
        fondo(g);}
    
    public void fondo(Graphics g){
        imagenIcono = new ImageIcon(getClass().getResource("/img/mundo.jpg"));
        g.drawImage(imagenIcono.getImage(), ANCHO_PANTALLA, ALTO_PANTALLA, this);
       
    }
    
}
