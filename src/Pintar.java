
import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
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
public class Pintar extends Applet {

    final int ANCHO_PANTALLA = 800;
    final int ALTO_PANTALLA = 800;
    ArrayList<Personaje> enemigos = new ArrayList<>();
    Personaje goku = null, freezer = null, boo = null, vegeta = null, broly = null, kameHa = null;

    public void init() {
        resize(ANCHO_PANTALLA, ALTO_PANTALLA);

    }

    public void paint(Graphics g) {
        pintarMundo(g);
        crearHeroe(g);
        crearEnemigos(g);

    }

    private Image obtenerImagen(String imagen) {
        String ruta = "/img/" + imagen;
        ImageIcon img = new ImageIcon(getClass().getResource(ruta));
        return img.getImage();
    }

    private void crearHeroe(Graphics g) {
        goku = new Personaje("Goku", "heroe.png", 0, 200);
        goku.pintar(g, this);

        kameHa = new Personaje("Hame-Ha", "kame-ha.png", 100, 230);
        kameHa.pintar(g, this);

    }

    private void pintarMundo(Graphics g) {
        Image mundo = obtenerImagen("mundo.jpg");
        g.drawImage(mundo, 0, 0, this);

    }

    private void crearEnemigos(Graphics g) {

        freezer = new Personaje("Freezer", "01-enemigo.png", 680, 90);
        enemigos.add(freezer);
        freezer.pintar(g, this);
        //freezer.mensaje("Hola cacaroto soy " + freezer.getNombre());
        esperar(500);
        boo = new Personaje("Majin Boo", "02-enemigo.png ", 680, 260);
        enemigos.add(boo);
        boo.pintar(g, this);
        //boo.mensaje("Hola cacaroto soy " + boo.getNombre());
        esperar(500);
        vegeta = new Personaje("Vegeta", "04-enemigo.png", 680, 430);
        enemigos.add(vegeta);
        vegeta.pintar(g, this);
        //vegeta.mensaje("Hola cacaroto soy " + vegeta.getNombre() + "\n Estupido Sayayin");
        esperar(500);
        broly = new Personaje("Broly", "03-enemigo.png", 680, 590);
        enemigos.add(broly);
        broly.pintar(g, this);
        //broly.mensaje("Hola cacaroto soy " + broly.getNombre());

        int i = 680, desplazamiento = 50;

        while (i > 150) {
            limpiarPantalla(g);
            pintarMundo(g);
            crearHeroe(g);
            
            freezer.pintar(g, this);
            freezer.setX(freezer.getX() - desplazamiento);
            esperar(100);

            boo.pintar(g, this);
            boo.setX(boo.getX() - desplazamiento);
            esperar(100);

            vegeta.pintar(g, this);
            vegeta.setX(vegeta.getX() - desplazamiento);
            esperar(100);

            broly.pintar(g, this);
            broly.setX(broly.getX() - desplazamiento);
            esperar(100);
            i -= desplazamiento;
        }

    }

    private void limpiarPantalla(Graphics g) {
        g.clearRect(0, 0, ANCHO_PANTALLA, ALTO_PANTALLA);
    }

    private void esperar(int s) {
        try {
            Thread.sleep(s);
        } catch (InterruptedException ex) {
            System.err.println("[ERROR]: " + ex.getMessage());
        }
    }
}
