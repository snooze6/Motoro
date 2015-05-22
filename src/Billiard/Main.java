package Billiard;

import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTranslated;
import static org.lwjgl.opengl.GL11.glViewport;

import java.util.ArrayList;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import Billiard.Pyshics.TimeManager;
import Billiard.World.Ball;
import Billiard.World.Billiard;
import Camera.CamListener;
import Camera.Ortho;
import Camera.Perspective;
import Collision.Objects.Vector;
import Lights.DirectionalLight;
import Lights.ILight;
import Others.Dibujo;

public class Main {

    // Whether to enable VSync in hardware.
    public static final boolean VSYNC = true;
    // Width and height of our window
    public static final int WIDTH = 800;
    public static final int HEIGHT = 800;
    // Whether to use fullscreen mode
    public static final boolean FULLSCREEN = false; 
    // Whether our MainDenis loop is running
    protected boolean running = false;
    private int afps = 60;
    
    //--------------------------------------------------------------------------
    
    private CamListener camera;
    private Billiard billar;
    private ArrayList<Ball> balls;
    private ILight light1;
    
    //--------------------------------------------------------------------------
	
	public static void main(String[] args) {
		try {
			new Main().start();
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void start() throws LWJGLException{
	     // Set up our display
        Display.setTitle("Billiard"); //title of our window
        Display.setResizable(true); //whether our window is resizable
        Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT)); //resolution of our display
        Display.setVSyncEnabled(VSYNC); //whether hardware VSync is enabled
        Display.setFullscreen(FULLSCREEN); //whether fullscreen is enable
        
        //create and show our display
        Display.create();
       
        TimeManager.update();
        // Create our OpenGL context and initialize any resources
        create();
        // Call this before running to set up our initial size
        resize();
        
        running = true;
        
        // While we're still running and the user hasn't closed the window...
        while (running && !Display.isCloseRequested()) {
            // If the MainDenis was resized, we need to update our projection
        	
            if (Display.wasResized()) resize();

            // Render the MainDenis
            update(TimeManager.getDelta());
            render();
             
            // Flip the buffers and sync to 60 FPS
            Display.update();
            Display.sync(afps);
        }

        // Dispose any resources and destroy our window
        dispose();
        Display.destroy();
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
    
    protected void create(){
    	camera = new CamListener();
    	camera.addCam(new Ortho());
    	camera.addCam(new Perspective());
    	
        light1=new DirectionalLight();
        //light1.setCutoff(120);
        light1.on();
    	
    	balls = new ArrayList<Ball>();
    	  balls.add(new Ball());
    	  balls.get(0).setPoint(new Vector(0,1+balls.get(0).getSize()+40,0));
    	  
    	billar = new Billiard();
    }
    
    //--------------------------------------------------------------------------
    
    protected void update(int delta){

        if (Keyboard.isKeyDown(Keyboard.KEY_V)) {
        	afps--;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_B)) {
        	afps++;
        }

        if (!TimeManager.lag) {
	         camera.listen();;
        }
    }
    
    protected void render() {
        TimeManager.updateFPS(); // update FPS Counter
  	    camera.render();
  	    
  	    light1.setLight_ambient(new float[]{1.0f, 1.0f, 1.0f, 0.0f});
        light1.setLight_position(new float[]{camera.getX(), camera.getY(), camera.getZ(),1.0f});
        light1.setSpotDir(new float[]{camera.getFront(5).x, camera.getFront(5).y,camera.getFront(5).z, 1.0f});
        
        if (!TimeManager.lag){
        	billar.render();
        	
        	for (int i=0; i<balls.size(); i++){
        		glColor3f(1,0,0);
        		balls.get(i).render();
        	}
        	
        	glColor3f(1,1,1);
        	Dibujo.drawMalla(900);
        } else {
            glPushMatrix();
    	        Vector v = camera.getDireccion();
    	        glTranslated(camera.getX() + 5*v.x,camera.getY() + 5*v.y ,camera.getZ() + 5*v.z);
    	        	glColor3f(2.0f, 0.5f, 0.0f);
    	            Dibujo.drawSphere(0.2f, 20, 20);
    	            Dibujo.drawAxes(1);
    	    glPopMatrix();
        }
      } 


}
