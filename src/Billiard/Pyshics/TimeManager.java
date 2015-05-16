package Billiard.Pyshics;

import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;

public class TimeManager {
	
	public static int delta=0;
	public static boolean lag=false;
    private static long lastFrame;/** time at last frame */
    private static int fps;/** frames per second */
    private static long lastFPS;/** last fps time */;
    
    /*
     * Devuelve los milisegundos del tiempo del sistema
     */
    public static long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }
	
    /*
     * Muestra los FPS en la barra del título
     */
    public static void updateFPS() {
        if (getTime() - lastFPS > 1000) {
            Display.setTitle("Billiard - FPS: " + fps); 
            if (fps<10){
            	lag = true;
            } else {
            	lag = false;
            }
            fps = 0;
            lastFPS += 1000;
        }
        fps++;
    }
    
    /*
     * Actualiza el valor de delta
     *   - Delta = intervalo de tiempo entre FPS en milisegundos
     */
    public static int getDelta() {
        long time = getTime();
        delta = (int) (time - lastFrame);
        lastFrame = time;
        return delta;
    }
    
    public static void update(){
        delta = getDelta();
        lastFPS = getTime();
    }

}
