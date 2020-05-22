
import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
public class Pintar extends Applet implements KeyListener, Runnable{

    final int ANCHO_PANTALLA = 800;
    final int ALTO_PANTALLA = 800;

    ArrayList<Personaje> enemigos;
    ArrayList<Personaje> kameHa;
    private Thread hilo;//hilo nuevo
    private boolean running;
    private static final int CANTIDAD_ENEMIGOS = 20;
    private static final int VELOCIDAD_ENEMIGOS = 25;
    private static final int VELOCIDAD_POWER_UP = 50;
    Personaje goku;

    
    public Pintar(){
        enemigos = new ArrayList<>();
        kameHa = new ArrayList<>();
        goku = new Personaje("Goku", "heroe.png", 0, 200);
        crearEnemigos();
    }
    
    public void init() {
        resize(ANCHO_PANTALLA, ALTO_PANTALLA);
        running = true;
        this.addKeyListener(this);
    }

    public void paint(Graphics g) {
        pintarMundo(g);
        goku.pintar(g, this);
        pintarKameHa(g);
        pintarEnemigos(g);
    }

    private Image obtenerImagen(String imagen) {
        String ruta = "/img/" + imagen;
        ImageIcon img = new ImageIcon(getClass().getResource(ruta));
        return img.getImage();
    }

    private void pintarMundo(Graphics g) {
        Image mundo = obtenerImagen("mundo.jpg");
        g.drawImage(mundo, 0, 0, this);
    }
    
    private void crearEnemigos(){
        String array_enemigos[][] = {
            {"Freezer", "01-enemigo.png"},
            {"Majin Boo", "02-enemigo.png "},
            {"Vegeta", "04-enemigo.png"},
            {"Broly", "03-enemigo.png"}
        };
        
        for(int i = 0; i < CANTIDAD_ENEMIGOS; i++){
            int cantidad = array_enemigos.length;
            int enemigo_aleatorio = (int)(Math.random()*cantidad);
            enemigos.add(new Personaje(array_enemigos[enemigo_aleatorio][0], array_enemigos[enemigo_aleatorio][1], 680, (int)(Math.random()*600)));
        }
    }
    
    private void pintarEnemigos(Graphics g){
        int numero_iteraciones = 2;
        for (int i = 0; i < numero_iteraciones; i++) {
            if(!enemigos.isEmpty()){
             enemigos.get(i).pintar(g, this);
            }
            if(numero_iteraciones > enemigos.size()){
                 numero_iteraciones = enemigos.size();
             } 
        }
    }
    
    private void pintarKameHa(Graphics g){
        for(int i = 0; i < kameHa.size(); i++){
            kameHa.get(i).pintar(g, this);
        }
    }
    
    private void trasladarKameHa(int dx, int dy){
        for(int i = 0; i < kameHa.size(); i++){
            kameHa.get(i).trasladar(dx, dy);
        }
    }
    
    private void trasladarEnemigos(int dx, int dy){
        int numero_iteraciones = 2;
        for(int i = 0; i < numero_iteraciones; i++){
            if(!enemigos.isEmpty()){
                enemigos.get(i).trasladar(dx, dy);
            }
            
            if(enemigos.size() < numero_iteraciones){
                numero_iteraciones = enemigos.size();
            }
        }
    }

    /*private void crearEnemigos(Graphics g) {

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

        int i = 680, desplazamiento = 20;

        while (i > 150) {
            limpiarPantalla(g);
            pintarMundo(g);
            crearHeroe(g);
            for (int j = 0; j < enemigos.size(); j++) {
                enemigos.get(j).pintar(g, this);
                enemigos.get(j).setX(desplazamiento);
                //esperar(500);
            }
            
            i -= desplazamiento;

//            freezer.pintar(g, this);
//            freezer.setX(freezer.getX() - desplazamiento);
//
//            boo.pintar(g, this);
//            boo.setX(boo.getX() - desplazamiento);
//
//            vegeta.pintar(g, this);
//            vegeta.setX(vegeta.getX() - desplazamiento);
//
//            broly.pintar(g, this);
//            broly.setX(broly.getX() - desplazamiento);
        }

    }*/

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

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        mover(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }

    @Override
    public void run() {
        while(running){
            //trasladar
            trasladarEnemigos(-VELOCIDAD_ENEMIGOS, 0);
            trasladarKameHa(VELOCIDAD_POWER_UP, 0);
            esperar(500);
            repaint();
        }
        
    }
    
    /*inicia el hilo*/
    public void start() {
        hilo = new Thread(this);
        hilo.start();
    }

    //detener el hilo
    public void stop() {
        running = false;
    }
    
    
    //método que permite mover objetos @jugador, enemigos
    private void mover(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_UP:
                goku.trasladar(0, -15);
                break;

            case KeyEvent.VK_DOWN:
                goku.trasladar(0, 15);
                break;

            case KeyEvent.VK_SPACE:
                kameHa.add(new Personaje("Hame-Ha", "kame-ha.png", 50, goku.getCenter().getY()));
                break;

            default:
                e.consume();
                break;
        }
    }
}
