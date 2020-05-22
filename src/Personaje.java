
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Jesus Salcedo
 */
public class Personaje {
    private boolean estado_juego = true;
    public int posInicio,posFin;
    private String nombre;
    private String imagen;
    private int x, y;
    private ImageIcon imagenIcono;
    //private ImageIcon kameHa;

    public Personaje(String nombre, String imagen, int x, int y) {
        this.nombre = nombre;
        this.imagen = imagen;
        this.x = x;
        this.y = y;
        this.imagenIcono = new ImageIcon(getClass().getResource("/img/" + imagen));
       // this.kameHa = new ImageIcon(getClass().getResource("/img/" + imagen));
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getPosInicio() {
        return posInicio;
    }

    public void setPosInicio(int posInicio) {
        this.posInicio = posInicio;
    }

    public int getPosFin() {
        return posFin;
    }

    public void setPosFin(int posFin) {
        this.posFin = posFin;
    }
    

    public void trasladar(int dx, int dy) {
        this.x = this.x + dx;
        this.y = this.y + dy;
        
        if(x == 800){
            x = 800-128;//ancho de pantalla - mayor ancho imagen enemigo 
        }else{
            if(x<0){
              x = 0;          
            }
        }
        
        if(y  >= 800){
            y= 600;
        }else{
            if(y<0){
                y = 0;
            }
        }
    }

    public void pintar(Graphics g, Container con) {

        g.drawImage(imagenIcono.getImage(), x, y, con);

    }

    
    public void mensaje (String msj){
        JOptionPane.showMessageDialog(null , ""+msj);
    }
    
    //Obtiene el centro del jugador
    public Personaje getCenter(){
        return new Personaje("", "",(int)(this.getX() + (114/2)), (int)(this.getY() + (128/2)));
    }

}
