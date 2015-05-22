package Billiard;

import Billiard.World.Billiard;
import Camera.*;
import Collision.*;
import Lights.*;
import Lights.ILight;
import Utilities.Dibujo;
import Utilities.Vector;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;

public class MainBillar {

    //Frames
    long lastFrame;/** time at last frame */
    int fps;/** frames per second */
    long lastFPS;/** last fps time */

    //Cam
    private CamListener camera;


    //Lights
    private ILight light1;

    //Lista de objetos
    private float sizeBilliard=1000;
    private Billiard billiard;

    //Esferas
    private float sizeSphere=sizeBilliard/30;
    private BBSphere black;



    //Lista de colisiones
    private CollisionsManager col;

    //Minecraft


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
        camera= new CamListener();
        camera.addCam(new Ortho());
        camera.addCam(new Perspective());

        //Luces
        light1 = new DirectionalLight();
        light1.on();
        light1.setLight_position(new float[]{ 20,20,-1,0});

        //Arrays auxiliares



//        //Objetos
            billiard = new Billiard(sizeBilliard);

        //Esferas
        black= new BBSphere(new Vector (0,0,0), 20,sizeSphere);

        //Colisiones



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

        //Movimiento
        black.move(delta);
        //Colisiones




    }

    public void renderGL() {
        updateFPS(); // update FPS Counter
        //Renderizo la camara
        camera.render();
        //Pongo las luces donde se debe solo para spot
//        Vector v = camera.getDireccion();
//        light1.setLight_position(new float[]{camera.getX(), camera.getY(), camera.getZ(), 1.0f});
//        light1.setSpotDir(new float[]{5*v.x, 5*v.y, 5*v.z, 1.0f});


        //Dibujo

        //Utilidades
        //Dibujo.drawMalla(5,5);
        Dibujo.drawAxes(sizeBilliard);

        //Billar
        glPushMatrix();
        glColor3d(0.6,0.6,0.6);
        glTranslated(sizeBilliard/11.5f,-sizeSphere,0);
        billiard.render();
        glPopMatrix();

        //Esferas
        glColor3d(0,0,0);
        black.draw();


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
            Display.setTitle("FPS: " + fps);
            fps = 0;
            lastFPS += 1000;

        }
        fps++;
    }

    // Exit our MainDenis loop and close the window
    public void exit() {
        running = false;
    }

    public void input(int delta){

            if (Keyboard.isKeyDown(Keyboard.KEY_I)) {
                black.setVelocity(0, 0, 0.03f);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_K)) {
                black.setVelocity(0,0,-0.03f);
            }

            //----------------------------------------------------------------------

            if (Keyboard.isKeyDown(Keyboard.KEY_J)) {
                black.setVelocity(-0.03f,0,0.0f);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_L)) {
                black.setVelocity(0.03f,0,0f);
            }

        int dWheel = Mouse.getDWheel();
        if (dWheel < 0) {
            camera.morezoom();
        } else if (dWheel > 0){
            camera.lesszoom();
        }
    }
    public static void main(String[] argv) {
        MainBillar MainDenis = new MainBillar();
        MainDenis.start();
    }
}

