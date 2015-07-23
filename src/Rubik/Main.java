package Rubik;

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

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;

public class Main {

    //Frames
    long lastFrame;/** time at last frame */
    int fps;/** frames per second */
    long lastFPS;/** last fps time */

    //Cam
    private CamListener camera;


    //Lights
    private ILight light1;

    //Lista de objetos
    private float sizeBilliard=1500;
    private float sizeSphere=sizeBilliard/35;
    private float largo= (float)sizeBilliard*1.5f-sizeBilliard/13;
    private float ancho=(float)12*sizeBilliard/20;
    private float ancho10=sizeBilliard/10;
    private float mini=sizeBilliard/10;


    //BIllar
    private Billiard billiard;

    //Huecos
    private ArrayList<BBGap> listaHuecos;

    //Esferas
    private ArrayList<BBSphere> listaEsferas;
    private BBSphere black;

    //Planos
    private ArrayList<BBQuad> listaPlanos;


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
        camera= new CamListener(new Perspective());
        camera.addCam(new Perspective());
        camera.addCam(new Perspective());

        //Luces
        light1 = new DirectionalLight();
        light1.on();
        light1.setLight_position(new float[]{ 20,20,-1,0});

        //Arrays auxiliares
        listaHuecos= new ArrayList<BBGap>();
        listaPlanos= new ArrayList<BBQuad>();
        listaEsferas = new ArrayList<BBSphere>();

//        //Objetos
            billiard = new Billiard(sizeBilliard);

        //Huecos
        listaHuecos.add(new BBGap(new Vector(0,-sizeSphere,-1150),1,sizeSphere*1.5f));

        //Esferas
        black= new BBSphere(new Vector (0,0,0), 20,sizeSphere);
        listaEsferas.add(black);
        for(int i=0;i<0;i++){
            listaEsferas.add(new BBSphere(new Vector(sizeSphere*i*3-1400,0,0),2,sizeSphere));
        }
        //Lista planos
        listaPlanos= new ArrayList<BBQuad>();
        //Lado derecho
        listaPlanos.add(new BBQuad(new Vector(largo,0,-ancho),new Vector(largo,0,ancho), new Vector(largo,50,ancho), new Vector(largo,50,-ancho)));
       listaPlanos.add(new BBQuad(new Vector(largo,0,-ancho),new Vector(largo+mini*3/4,0,-ancho-mini), new Vector(largo+mini*3/4,50,-ancho-mini),new Vector(largo,50,-ancho) ));
        listaPlanos.add(new BBQuad(new Vector(largo,0,ancho),new Vector(largo+mini*3/4,0,ancho+mini), new Vector(largo+mini*3/4,50,ancho+mini),new Vector(largo,50,ancho) ));

        //Lado izquierdo
        listaPlanos.add(new BBQuad(new Vector(-largo,0,-ancho),new Vector(-largo,0,ancho), new Vector(-largo,50,ancho), new Vector(-largo,50,-ancho)));
        listaPlanos.add(new BBQuad(new Vector(-largo,0,-ancho),new Vector(-largo-mini*3/4,0,-ancho-mini), new Vector(-largo-mini*3/4,50,-ancho-mini),new Vector(-largo,50,-ancho) ));
        listaPlanos.add(new BBQuad(new Vector(-largo,0,ancho),new Vector(-largo-mini*3/4,0,ancho+mini), new Vector(-largo-mini*3/4,50,ancho+mini),new Vector(-largo,50,ancho) ));

        //Delante
        ancho=ancho+ancho10;
        largo=largo-mini;
            //Parte izquierda
            listaPlanos.add(new BBQuad(new Vector(-largo,00,-ancho),new Vector(0-mini*4/5,00,-ancho), new Vector(0-mini*4/5,50,-ancho), new Vector(-largo,50,-ancho)));
            listaPlanos.add(new BBQuad(new Vector(-largo,00,-ancho),new Vector(-largo-mini,00,-ancho-mini*3/4),new Vector(-largo-mini,50,-ancho-mini*3/4),new Vector(-largo,50,-ancho)));
            listaPlanos.add(new BBQuad(new Vector(0-mini*4/5,00,-ancho),new Vector(0-mini*4/5+mini/4,00,-ancho-mini*4/8),new Vector(0-mini*4/5+mini/4,50,-ancho-mini*4/8),new  Vector(0-mini*4/5,50,-ancho)));
            //Parte derecha
            listaPlanos.add(new BBQuad(new Vector(0+mini*4/5,00,-ancho),new Vector(largo,00,-ancho), new Vector(largo,50,-ancho), new Vector(0+mini*4/5,50,-ancho)));
            listaPlanos.add(new BBQuad(new Vector(largo,00,-ancho),new Vector(largo+mini,00,-ancho-mini*3/4),new Vector(largo+mini,50,-ancho-mini*3/4),new Vector(largo,50,-ancho)));
            listaPlanos.add(new BBQuad(new Vector(0+mini*4/5,00,-ancho),new Vector(0+mini*4/5-mini/4,00,-ancho-mini*4/8),new Vector(0+mini*4/5-mini/4,50,-ancho-mini*4/8),new  Vector(0+mini*4/5,50,-ancho)));

        //Detras
            //Parte izquierda
            listaPlanos.add(new BBQuad(new Vector(-largo,00,ancho),new Vector(0-mini*4/5,00,ancho), new Vector(0-mini*4/5,50,ancho), new Vector(-largo,50,ancho)));
            listaPlanos.add(new BBQuad(new Vector(-largo,00,ancho),new Vector(-largo-mini,00,ancho+mini*3/4),new Vector(-largo-mini,50,ancho+mini*3/4),new Vector(-largo,50,ancho)));
            listaPlanos.add(new BBQuad(new Vector(0-mini*4/5,00,ancho),new Vector(0-mini*4/5+mini/4,00,ancho+mini*4/8),new Vector(0-mini*4/5+mini/4,50,ancho+mini*4/8),new  Vector(0-mini*4/5,50,ancho)));

            //Parte derecha
            listaPlanos.add(new BBQuad(new Vector(0+mini*4/5,00,ancho),new Vector(largo,00,ancho), new Vector(largo,50,ancho), new Vector(0+mini*4/5,50,ancho)));
        //    listaPlanos.add(new BBQuad(new Vector(largo,00,ancho),new Vector(largo+mini,00,ancho+mini*3/4),new Vector(largo+mini,50,ancho+mini*3/4),new Vector(largo,50,ancho)));

           listaPlanos.add(new BBQuad(new Vector(largo,00,ancho),new Vector(largo+mini,00,ancho+mini*3/4),new Vector(largo+mini,50,ancho+mini*3/4),new Vector(largo,50,ancho)));
            listaPlanos.add(new BBQuad(new Vector(0+mini*4/5,00,ancho),new Vector(0+mini*4/5-mini/4,00,ancho+mini*4/8),new Vector(0+mini*4/5-mini/4,50,ancho+mini*4/8),new  Vector(0+mini*4/5,50,ancho)));


        //Colisiones
        col = new CollisionsManager();
        for(int i=0;i<listaPlanos.size();i++){
            col.add(listaPlanos.get(i));
        }

        for(int i=0;i<listaEsferas.size();i++){
            col.add(listaEsferas.get(i));
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

        //Movimiento
        black.move(delta);
        //Colisiones
        col.collide(delta);

        for(int i=0;i<listaEsferas.size();i++){
            listaEsferas.get(i).move(delta);
            if (i==0){
            	camera.getCam(1).setPos(listaEsferas.get(i).getCenterPoint());
            	camera.getCam(2).setPos(listaEsferas.get(i).getCenterPoint());
            }
        }
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
      //  Dibujo.drawAxes(largo);
      //  Dibujo.drawAxes(ancho);
        Dibujo.drawAxes(sizeBilliard);
        //Billar
        glPushMatrix();
        glColor3d(0.6,0.6,0.6);
        glTranslated(sizeBilliard/11.5f,-sizeSphere,0);
        billiard.render();
        glPopMatrix();

        //Huecos
        for(int i=0;i<listaHuecos.size();i++){
            glPushMatrix();
            glColor3d(0,0,1);
            listaHuecos.get(i).draw();
            glPopMatrix();
        }

        //Esferas
        for(int i=0;i<listaEsferas.size();i++){
            glPushMatrix();
            glColor3d(1,0,0);
            listaEsferas.get(i).draw();
            glPopMatrix();
        }



        //Planos
        for(int i=0;i<listaPlanos.size();i++){
            glPushMatrix();
            glTranslated(0,-sizeSphere,0);
            listaPlanos.get(i).draw();
            glPopMatrix();
        }


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
                black.setVelocity(0, 0, 0.5f);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_K)) {
                black.setVelocity(0,0,-0.5f);
            }

            //----------------------------------------------------------------------

            if (Keyboard.isKeyDown(Keyboard.KEY_J)) {
                black.setVelocity(-1.0f,0,-0.0f);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_L)) {
                black.setVelocity(1.0f,0,0.0f);
            }
        if (Keyboard.isKeyDown(Keyboard.KEY_H)) {
            black.setVelocity(0.00f,0,0f);
            black.getCenterPoint().print();
        }

        int dWheel = Mouse.getDWheel();
        if (dWheel < 0) {
            camera.morezoom();
        } else if (dWheel > 0){
            camera.lesszoom();
        }
    }
    public static void main(String[] argv) {
        Main MainDenis = new Main();
        MainDenis.start();
    }
}

