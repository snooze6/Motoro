

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import Billiard.World.Ball;
import Billiard.World.Billiard;
import Camera.CamListener;
import Camera.Ortho;
import Camera.Perspective;
import Collision.CollisionsManager;
import Lights.DirectionalLight;
import Lights.ILight;
import Utilities.Dibujo;
import Utilities.TextureGL;
import Utilities.Vector;

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
    private Ball white, skyBox;
    private ArrayList<Ball> bolas;
    private TextureGL tex, tex2;

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

    private float ang;
    
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

        //Objetos
        billiard = new Billiard(sizeBilliard);
        white = new Ball(new Vector (sizeBilliard,0,0), 20,sizeSphere, "/res/images/Planetas/earth.jpg");
        skyBox = new Ball(new Vector (0,0,0), 20,sizeSphere*100, "/res/images/sky1.jpg");
        
        bolas = new ArrayList<Ball>();
        int k=0, l=0;
        //for (int l=0; l<1; l++){
	        k=0;
	        for (int j=1; j<6; j++){
	        	for (int i=1; i<=j; i++){
	        		k++;
	        		System.out.println("Hey: "+k);
	        		//bolas.add(new Ball(new Vector(-(2*sizeSphere)*j-(float)(sizeBilliard/2.0f), 0, i*2*sizeSphere-(sizeSphere)*j), 20, sizeSphere, "/res/images/Planetas/pallina"+k+".jpg"));
	        		bolas.add(new Ball(new Vector(-(2*sizeSphere)*j-(float)(sizeBilliard/2.0f), sizeSphere*2*l, i*2*sizeSphere-(sizeSphere)*j), 20, sizeSphere, "/res/images/Planetas/pallina"+k+".jpg"));
	        	}
	        }
        //a}
        
       
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
        white.update(delta);
        //Colisiones




    }

    public void renderGL() {
        updateFPS(); // update FPS Counter
        //Renderizo la camara
        camera.render();

        //Dibujo

        //Utilidades
        Dibujo.drawAxes(sizeBilliard);

		
        //Billar
        glBlendFunc(GL_SRC_ALPHA, GL_ONE);
        //glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_BLEND);
	        glPushMatrix();
		        glColor4f(0.6f,0.6f,0.6f, 0.5f);
		        glTranslated(sizeBilliard/11.5f,-sizeSphere,0);
		        billiard.render();
	        glPopMatrix();
        glDisable(GL_BLEND);
        
        for (int i=0; i<bolas.size(); i++){
        	bolas.get(i).render();
        }

        ang++; if (ang>360){ang=0;}

        //Bolas
        glColor3d(1,1,1);
        glPushMatrix();
              glRotated(ang, 1, 0, 0);
              white.render();
        glPopMatrix();
        
        //Bolas
        glColor3d(1,1,1);
        glPushMatrix();
              glRotated(ang, 1, 0, 0);
              skyBox.render();
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
                white.setVel(0, 0, 0.03f);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_K)) {
                white.setVel(0,0,-0.03f);
            }

            //----------------------------------------------------------------------

            if (Keyboard.isKeyDown(Keyboard.KEY_J)) {
                white.setVel(-0.03f,0,0.0f);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_L)) {
                white.setVel(0.03f,0,0f);
            }
            
            if (Keyboard.isKeyDown(Keyboard.KEY_R)) {
            	white.setTransparent();
            	for(int i=0; i<bolas.size(); i++){
            		bolas.get(i).setTransparent();
            	}
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

