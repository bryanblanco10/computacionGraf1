
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

public class Sonido {
    private AudioClip clip;
    
    public static final Sonido[] sounds = {
        new Sonido("/audio/audioKameha.wav"),
        new Sonido("/audio/audio-destruccion-enemigos.wav"),
        new Sonido("/audio/audio-fin-del-juego-pierde-goku.wav"),
        new Sonido("/audio/audio-fin-juego-victoria.wav")
    };
    
    public Sonido(String path){
        clip = Applet.newAudioClip(getClass().getResource(path));
    }
    
    public void play(){
        new Thread(){
            public void run(){
                clip.play();
            }
        }.start();
    }
    
    public void loop(){
        new Thread(){
            public void run(){
                clip.loop();
            }
        }.start();
    }
    
    public void stop(){
        clip.stop();
    }
}
