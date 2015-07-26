package Rubik.Backup;

import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glViewport;

import java.util.ArrayList;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import Camera.CamListener;
import Camera.Perspective;
import Rubik.Cube;
import Utilities.Dibujo;
import Utilities.Vector;
import Rubik.Piece;

public class Main {

    //Frames
    long lastFrame;/** time at last frame */
    int fps;/** frames per second */
    long lastFPS;/** last fps time */

    //Cam
    private CamListener camera;
    
    //Cubo
    private Cube rubik;
    private Piece pieza;
    private ArrayList<Piece> piezas = new ArrayList<Piece>();

    //Enable vsync
    public static final boolean VSYNC = true;
    // Width and height of our window
    public static final int WIDTH = 800;
    public static final int HEIGHT = 800;
    // Whether to use fullscreen mode
    public static final boolean FULLSCREEN = false;
    // Whether our MainDenis loop is running
    protected boolean running = false;

    //Create objects
    protected void create() {
        //Camara
        camera= new CamListener(new Perspective());
		          camera.addCam(new Perspective());
		          camera.addCam(new Perspective());
        
        rubik = new Cube(3, 10, 0);
        
        	Matrix4f m = new Matrix4f();
        	m.setIdentity();
        	Vector back;
        	
        	for (int i=0; i<8; i++) {
        		/*
        		 * Los roto una única vez el ángulo correcto
        		 */
        		m.setIdentity();
        		pieza = new Piece(new Matrix4f(m.translate(new Vector3f(12,0,0))),5);
        		back = pieza.getPos();
        		pieza.setPos(new Vector(0,0,0));
	        	pieza.rotate(i*45, new Vector(0,1,0));
	        	pieza.translate(back);
        		piezas.add(pieza);
        		
        	}
        	
        	for (int i=0; i<8; i++){
            	m.setIdentity();
            	pieza = new Piece(new Matrix4f(m.translate(new Vector3f(24,0,0))),5);
            	back = pieza.getPos();
            	pieza.setPos(new Vector(0,0,0));
            	for (int j=0; j<i; j++){
                	pieza.rotate(45, new Vector(0,1,0));
                	//pieza.translate(new Vector(12,0,0));
            	}
            	pieza.translate(back);
            	
            	piezas.add(pieza);
        	}
        	
        	m.setIdentity();
        	pieza = new Piece(new Matrix4f(m.translate(new Vector3f(0, 24, 0))), 5);
        	piezas.add(pieza);
        	
    }
    
    public void input(int delta){
        while(Keyboard.next()){
            if (Keyboard.isKeyDown(Keyboard.KEY_U)) {
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_J)) {
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_H)) {
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_K)) {
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_I)) {
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_Y)) {
            }
        }
            
        int dWheel = Mouse.getDWheel();
        if (dWheel < 0) {
            camera.morezoom();
        } else if (dWheel > 0){
            camera.lesszoom();
        }
    }
    

    

    protected void resize() {

       camera.setWindow(Display.getWidth(), Display.getHeight());
        glViewport(0, 0, Display.getWidth(), Display.getHeight());
    }

    //Ejecucion en bucle
    public void start() {
        try {
            Display.setResizable(true); //whether our window is resizable
            Display.setDisplayMode(new DisplayMode(WIDTH,HEIGHT));
            Display.setVSyncEnabled(VSYNC); //whether hardware VSync is enabled
            Display.setFullscreen(FULLSCREEN); //whether fullscreen is enable
            Display.create();            //Create and show our display
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(0);
        }

        create(); // Create our OpenGL context and initialize any resources
        initGL(); // init OpenGL
        resize();     // Call this before running to set up our initial size
        getDelta(); // call once before loop to initialise lastFrame
        lastFPS = getTime(); // call before loop to initialise fps timer
        running=true;

        while (running &&!Display.isCloseRequested()) {
            if (Display.wasResized())
                resize();
            update(getDelta());
            renderGL();
            Display.update();
            Display.sync(60); // cap fps to 60fps
        }
        // Dispose any resources and destroy our window
        dispose();
        Display.destroy();
    }



    public void update(int delta) {
    //Metodos de entrada por teclado
        input(delta);
        camera.listen();

    }

    public void renderGL() {
        updateFPS(); // update FPS Counter
        //Renderizo la camara
        camera.render();

        glPushMatrix();
        Dibujo.drawMalla(100);
        //rubik.render();
        for (int i=0; i<piezas.size(); i++){
        	piezas.get(i).render();
        }
        
        glPopMatrix();

    }





    //Init opengl
    public void initGL() {

    }
    // Called to destroy our MainDenis upon exiting
    protected void dispose() {}

    //------------------Time and fps
    /*** Calculate how many milliseconds have passed
     * since last frame.*/
    public int getDelta() {
        long time = getTime();
        int delta = (int) (time - lastFrame);
        lastFrame = time;
        return delta;
    }

    /*** @return The system time in milliseconds*/
    public long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }

    /**Calculate the FPS and set it in the title bar*/
    public void updateFPS() {
        if (getTime() - lastFPS > 1000) {
            Display.setTitle("Rubik "+rubik.getN()+"- FPS: " + fps);
            fps = 0;
            lastFPS += 1000;

        }
        fps++;
    }

    // Exit our MainDenis loop and close the window
    public void exit() {
        running = false;
    }

    public static void main(String[] argv) {
        Main MainDenis = new Main();
        MainDenis.start();
    }
}

