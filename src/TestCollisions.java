

import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTranslated;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glViewport;

import java.util.ArrayList;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import rubik.Node;
import Camera.ICam;
import Camera.Ortho;
import Camera.Perspective;
import Collision.Objects.Esfera;
import Collision.Objects.Plano;
import Collision.Objects.Vector;
import Collisions.CollisionsManager;
import Lights.DirectionalLight;
import Lights.ILight;
import Lights.SpotLight;
import Others.Dibujo;
import Others.Face;


/**
 * A bare-bones implementation of a LWJGL application.
 * @author davedes
 */
public class TestCollisions {

    // Whether to enable VSync in hardware.
    public static final boolean VSYNC = true;


    // Width and height of our window
    public static final int WIDTH = 800;
    public static final int HEIGHT = 800;
    // Whether to use fullscreen mode
    public static final boolean FULLSCREEN = false; 

    // Whether our MainDenis loop is running
    protected boolean running = false;
  
    //Mouse
    int controlPulsacion=0;
    int xPosition=0;
    int yPosition=0;
    int lastX;
    int lastY;
    
    long lastFrame;/** time at last frame */
    int fps;/** frames per second */
    long lastFPS;/** last fps time */
    private float runtime = 0;

    private Face cara;
    
    private ICam camera, cam1, cam2;
    Node nod;
    private int delta;
    
    private Esfera uno, dos, tres, cuatro, cinco;
    private Plano arr, aba, der, izq, del, atr;
    ILight light1;
    private CollisionsManager col;

    float fAngulo=0;
    public static void main(String[] args) throws LWJGLException {
        new TestCollisions().start();
    }

    // Start our MainDenis
    public void start() throws LWJGLException {
        // Set up our display
        Display.setTitle("LOL, soy yo"); //title of our window
        Display.setResizable(true); //whether our window is resizable
        Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT)); //resolution of our display
        Display.setVSyncEnabled(VSYNC); //whether hardware VSync is enabled
        Display.setFullscreen(FULLSCREEN); //whether fullscreen is enable

        cam1 = new Ortho();
        cam2 = new Perspective(-400, 50, 0, 0, 0, 0);
        camera = cam2;
        camera.setWindow(Display.getWidth(), Display.getHeight());
        
        //nod = new Node();
        cara = new Face(20);
      
        //create and show our display
        Display.create();
       
        // Create our OpenGL context and initialize any resources
        create();

        // Call this before running to set up our initial size
        resize();
        
        running = true;
        getDelta();
        lastFPS = getTime();

        // While we're still running and the user hasn't closed the window...
        while (running && !Display.isCloseRequested()) {
            // If the MainDenis was resized, we need to update our projection
        	
            if (Display.wasResized())
                resize();

            // Render the MainDenis
             update(getDelta());
             render();
            // Flip the buffers and sync to 60 FPS
            Display.update();
            Display.sync(afps);
        }

        // Dispose any resources and destroy our window
        dispose();
        Display.destroy();
    }

    // Exit our MainDenis loop and close the window
    public void exit() {
        running = false;
    }
    
    private ArrayList<Esfera> listaEsferas;

    // Called to setup our MainDenis and context
    protected void create() {
    	
    	listaEsferas = new ArrayList<Esfera>();
        col = new CollisionsManager();
    	
    	for (int i=0; i<10; i++){
    		listaEsferas.add(new Esfera(new Vector(-480+22*i, 50, -400), new Vector(-0.1f,-0.01f*i,0), 10, 10));
    		col.add(listaEsferas.get(i));
    	}
    	for (int i=0; i<10; i++){
    		listaEsferas.add(new Esfera(new Vector(-480+22*i, 50+50, -400), new Vector(-0.1f*i,-0.01f,0), 10, 10));
    		col.add(listaEsferas.get(i+10));
    	}
    	for (int i=0; i<10; i++){
    		listaEsferas.add(new Esfera(new Vector(-480+22*i, 50+100, -400), new Vector(-0.1f,-0.01f,0.01f*i), 10, 10));
    		col.add(listaEsferas.get(i+20));
    	}
    	for (int i=0; i<10; i++){
    		listaEsferas.add(new Esfera(new Vector(-480+22*i, 50+50+50+50, -400), new Vector(-0.1f*i,-0.01f,0), 10, 10));
    		col.add(listaEsferas.get(i+30));
    	}
    	for (int i=0; i<100; i++){
    		listaEsferas.add(new Esfera(new Vector(-480+22*i, 20, -400), new Vector(-0.1f,-0.01f,0.01f*i), 10, 10));
    		col.add(listaEsferas.get(i+40));
    	}
//    	listaEsferas.add(new Esfera(new Vector(-460, 50, -400), new Vector(0,-4,0), 50, 25));
//    	col.add(listaEsferas.get(0));
    	
//    	uno = new Esfera(new Vector( -460,50,-450), new Vector(-1,0,0), 50, 50);
//    	dos = new Esfera(new Vector(-200,200,0), new Vector(+1,0,0), 50, 50);
//    	tres = new Esfera(new Vector(-460, 50, -400), new Vector(0,-4,0), 50, 25);
//    	listaEsferas.add(tres);
//    	cuatro = new Esfera(new Vector(-320, 100, -350), new Vector(1,0,1), 30, 15);
//    	cinco = new Esfera(new Vector(-400, 20, -450), new Vector(1,-1,-2), 15, 10);
    	
    	aba = new Plano(0,1,0 , -400, 0  , -400, 200);
    	arr = new Plano(0,-1,0, -400, 400, -400, 200);
    	izq = new Plano(1,0,0 , -600, 200, -400, 200);
    	der = new Plano(-1,0,0, -200, 200, -400, 200);
    	del = new Plano(0,0,-1, -400, 200, -200, 200);
    	atr = new Plano(0,0,1 , -400, 200, -600, 200);
    	
        light1=new SpotLight();
        light1.setCutoff(120);
        light1.on();
        
        col.add(arr);
        col.add(aba);
        col.add(der);
        col.add(izq);
        col.add(atr);
        col.add(del);
        
    }

    // Called to render our MainDenis
    protected void render() {
      updateFPS(); // update FPS Counter
    	
      if (!lag){
    	camera.render();
       
	    	float[] v = camera.getDireccion();
	        light1.setLight_position(new float[]{camera.getCam_x(), camera.getCam_y(), camera.getCam_z(),1.0f});
	        light1.setSpotDir(new float[]{5*v[0], 5*v[1], 5*v[2], 1.0f});

        glPushMatrix();
          //Dibujo.drawCube(50);
          cara.draw();
          glTranslatef(0.0f, -61.2f, 0.0f);
            Dibujo.drawMalla(1000);
        glPopMatrix();
        
        for(int i=0; i<listaEsferas.size(); i++){
        	listaEsferas.get(i).draw();
        }
        
        aba.draw(); arr.draw(); der.draw(); izq.draw(); atr.draw(); //del.draw();
      } else {
      	camera.render();

        glPushMatrix();
	        float[] v = camera.getDireccion();
	        glTranslated(camera.getCam_x() + 5*v[0],camera.getCam_y() + 5*v[1] ,camera.getCam_z() + 5*v[2]);
	        	glColor3f(2.0f, 0.5f, 0.0f);
	            Dibujo.drawSphere(0.2f, 20, 20);
	            Dibujo.drawAxes(1);
	    glPopMatrix();

      }
    } 

    // Called to resize our MainDenis
    protected void resize() {
    	camera.setWindow(Display.getWidth(), Display.getHeight());
        glViewport(0, 0, Display.getWidth(), Display.getHeight());
        
    }

    // Called to destroy our MainDenis upon exiting
    protected void dispose() {
        // ... dispose of any textures, etc ...
    }

    public void input(int delta){
        int speedMovement=3;
        float rotateMovement=1.5f;
        // Translate cam
        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
        	camera.moveStraight(speedMovement);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
        	camera.moveBack(speedMovement);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
        	camera.moveRight(speedMovement);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
        	camera.moveLeft(speedMovement);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
        	camera.moveUp(speedMovement);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
        	camera.moveDown(speedMovement);
        }
        // Rotate cam
        if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
            camera.lookDown(rotateMovement);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
            camera.lookUp(rotateMovement);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
            camera.lookRight(rotateMovement);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
            camera.lookLeft(rotateMovement);
        }

        //----------------------------------------------------------------------
        
        if (Keyboard.isKeyDown(Keyboard.KEY_Z)) {
            camera.morezoom();
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_X)) {
            camera.lesszoom();
        }
        
        if (Keyboard.isKeyDown(Keyboard.KEY_U)) {
            camera=cam1;
            resize();
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_I)) {
            camera=cam2;
            resize();
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_J)) {
        	float[] v = camera.getDireccion();
            camera.setDireccion(v[0], v[1], v[2]);;
            resize();
        }
        
        //----------------------------------------------------------------------
        
        if (Keyboard.isKeyDown(Keyboard.KEY_O)) {
            light1.on();
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_P)) {
            light1.off();
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_N)) {
            light1 = new DirectionalLight();
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_M)) {
            light1 = new SpotLight();
        }
        
        //----------------------------------------------------------------------
        
        int dWheel = Mouse.getDWheel();
        if (dWheel < 0) {
            camera.morezoom();
            System.out.println("Rueda arriba");
        } else if (dWheel > 0){
            camera.lesszoom();
            System.out.println("Rueda abajo");
        }
    }

    public float Idle(float fAngulo){
        fAngulo += 0.05f;
        if (fAngulo > 360)
            fAngulo -= 360.0f;
        return fAngulo;
    }


	
    //--------------------------------------------------------------------------
    
    
    /*
     * Devuelve los milisegundos del tiempo del sistema
     */
    public long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }
	
    /*
     * Muestra los FPS en la barra del título
     */
    public void updateFPS() {
        if (getTime() - lastFPS > 1000) {
            Display.setTitle("FPS: " + fps); runtime++;
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
    public int getDelta() {
        long time = getTime();
        delta = (int) (time - lastFrame);
        lastFrame = time;
        //System.out.println(delta);
        //System.out.println("[DELTA]: "+delta);
        return delta;
    }

    private boolean lag=false;
    private int afps = 60;

    public void update(int delta) {
    	
        runtime+=delta;
    	//System.out.println("[Runtime]: "+runtime/1000);
    	
        float aux = (float)delta/(float)17;
        if (aux>5.882353) {aux = 0;}
        
        // Translate cam
        if (Keyboard.isKeyDown(Keyboard.KEY_V)) {
        	afps--;
        	//System.out.println("[FPS]: "+afps);
        }
        // Translate cam
        if (Keyboard.isKeyDown(Keyboard.KEY_B)) {
        	afps++;
        }
    	
        
        
    	//System.out.println("Delta "+delta);
        if (!lag) {
	        input(delta);
	        
	        for(int i=0; i<listaEsferas.size(); i++){
	        	listaEsferas.get(i).trasladar(delta);
	        }
	        
	        col.collide();
        }
     }


}
