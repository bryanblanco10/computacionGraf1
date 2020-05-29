
import java.applet.Applet;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
    ArrayList<Animacion> explociones;
    private int puntaje = 0;
    
    private ImageIcon imgPuntaje[];
    private ImageIcon imgIconGoku;
    private int vidas = 100;
    
    //Sonidos sonido;
    
    Personaje goku;

    
    public Pintar(){
        enemigos = new ArrayList<>();
        kameHa = new ArrayList<>();
        explociones = new ArrayList<>();
        goku = new Personaje("Goku", "heroe.png", 0, 200);
        crearEnemigos();
        imgPuntaje = new ImageIcon[11];
        imgIconGoku = new ImageIcon(getClass().getResource("/img/icono_goku.png"));
        //sonido = new Sonidos("audioKameha.wav");
    }
    
    public void init() {
        resize(ANCHO_PANTALLA, ALTO_PANTALLA);
        running = true;
        this.addKeyListener(this);
        getImagenNumero();
    }

    public void paint(Graphics g) {
        pintarMundo(g);
        goku.pintar(g, this);
        pintarKameHa(g);
        pintarEnemigos(g);
        actualizarExplosion(g, this);
        pintarPuntaje(g, this);
        pintarVidas(g, this);
        
        if(!running){
            limpiarPantalla(g);
        }
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
    
    //método que permite agregar valor a la puntuación
    public void addPuntaje(int valor){
        puntaje += valor;
    }
    
    
    private void pintarKameHa(Graphics g){
        for(int i = 0; i < kameHa.size(); i++){
            kameHa.get(i).pintar(g, this);
        }
    }
    
    private void trasladarKameHa(int dx, int dy){
        for(int i = 0; i < kameHa.size(); i++){
            kameHa.get(i).trasladar(dx, dy);
            if(kameHa.get(i).getX() > 800){
                kameHa.remove(i);
            }
            
        }
    }
    
    private void trasladarEnemigos(int dx, int dy){
        int numero_iteraciones = 2;
        for(int i = 0; i < numero_iteraciones; i++){
            if(!enemigos.isEmpty()){
                enemigos.get(i).trasladar(dx, dy);
                
                if(enemigos.get(i).getX()== 0){
                    enemigos.remove(i);
                }else{
                    //detectar colisiones
                    colisionaEnemigoPowerUp(i);
                    colisionaEnemigoGoku(i);
                }
            }
            
            if(enemigos.size() < numero_iteraciones){
                numero_iteraciones = enemigos.size();
            }
        }
    }
    
    public void colisionaEnemigoPowerUp(int indice){
        try {
            for(int i = 0; i < kameHa.size(); i++){
                if(enemigos.get(indice).getRectangle().intersects(kameHa.get(i).getRectangle())){
                    iniciarAnimaion(enemigos.get(indice).getX(), enemigos.get(indice).getY());
                    Sonido.sounds[1].play();
                    addPuntaje(10);
                    enemigos.remove(indice);//destruyo el enemigo
                    kameHa.remove(i);//destruyo kameha
                    
                }
            }
        } catch (Exception e) {
            System.err.println("Han Ocurrido en la colision Enemigo <-> kameha indice(" +indice +") " + e.getLocalizedMessage());
        }
    }
    
    public void colisionaEnemigoGoku(int indice){
        try {
            if(enemigos.get(indice).getRectangle().intersects(goku.getRectangle())){
                    iniciarAnimaion(goku.getX(), goku.getY());
                    Sonido.sounds[1].play();
                    setVidas(20);
                    enemigos.remove(indice);//destruyo el enemigo      
            }
        } catch (Exception e) {
            System.err.println("Han Ocurrido en la colision Enemigo <-> Goku indice(" +indice +") " + e.getLocalizedMessage());
        }
    }
    
    public void pintarPuntaje(Graphics g, Container c){
        //agrego las posiciones donde voy se colocara el puntaje
        int x = 700;
        int y = 25;
        
        //como voy a cargar imagen en vez de texto es muy conveniente convertir los puntajes en string para saber que número voy a pintar
        String stringPuntaje = Integer.toString(getPuntaje());
        
        //recorro el string creado
        for(int i = 0; i < stringPuntaje.length(); i++){
            g.drawImage(imgPuntaje[Integer.parseInt(stringPuntaje.substring(i, i + 1))].getImage(), x, y, c);
            //modifico a x para que la imagen no se monte encima de la otra
            x = x + 20;
        }
        
    }


    private void limpiarPantalla(Graphics g) {
        g.setColor(Color.BLACK);
        g.clearRect(0, 0, ANCHO_PANTALLA, ALTO_PANTALLA);
        //string
        Sonido.sounds[2].play();
    }
    
    //Método que permite iniciar todo las animaciones se inicien
    public void iniciarAnimaion(int x , int y){
        explociones.add(new Animacion(x, y, 200));
    }
    
    //método que permite actualizar la animacion
    public void actualizarExplosion(Graphics g, Container c){
        for(int i = 0; i < explociones.size(); i++){
            explociones.get(i).pintarExplosiones(g, c);
            
            if(!explociones.get(i).isRunning()){
                explociones.remove(i);
            }
        }
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
            if(getVidas() == 0){
                stop();
                repaint();
            }
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
                kameHa.add(new Personaje("Hame-Ha", "kame-ha.png", 40, goku.getCenter().getY()));
                Sonido.sounds[0].play();
                //sonido.play();
                break;

            default:
                e.consume();
                break;
        }
    }
    
    private void getImagenNumero(){
        for (int i = 0; i < imgPuntaje.length; i++) {
            imgPuntaje[i] = new ImageIcon(getClass().getResource("/numeros/" + i + ".png"));
        }
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }
    
    //método que permite pintar las vidas
    public void pintarVidas(Graphics g, Container c){
        int x = 25;
        int y = 25;
        
        //TODO:dibujar el simbolo
        g.drawImage(imgIconGoku.getImage(), x + 10, y, c);
        
        //dibujar el valor  x
        g.drawImage(imgPuntaje[10].getImage(), x + 40, y +5, c);
        
        String stringVidas = Integer.toString(getVidas() < 0 ? 0 : getVidas());
        //recorro el string creado
        for(int i = 0; i < stringVidas.length(); i++){
            int index = Integer.parseInt(stringVidas.substring(i,i+1));
            
            //valido la posibilidad de que no exista idas menores que 0
            if(index < 0){
               break;
            }
            
            g.drawImage(imgPuntaje[index].getImage(), x + 60, y + 5, c);
            //modifico a x para que la imagen no se monte encima de la otra
            x = x + 20;
        }
        
    }

    public int getVidas() {
        return vidas;
    }

    public void setVidas(int vidas) {
        this.vidas -= vidas;
    }
    
    
}
