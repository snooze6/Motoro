package Utilities;

import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslated;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glViewport;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import Camera.CamListener;
import Camera.Ortho;
import Camera.Perspective;
import Lights.ILight;
import Lights.SpotLight;
import Others.Face;

public class Testing {

    // Whether to enable VSync in hardware.
    public static final boolean VSYNC = true;
    // Width and height of our window
    public static final int WIDTH = 800;
    public static final int HEIGHT = 800;
    // Whether to use fullscreen mode
    public static final boolean FULLSCREEN = false; 
    // Whether our MainDenis loop is running
    protected boolean running = false;
    
    long lastFrame;/** time at last frame */
    int fps;/** frames per second */
    long lastFPS;/** last fps time */
    private int delta;

    private Face cara;
    private CamListener camera;
    ILight light1;
    
    public static void main(String[] args) throws LWJGLException {
        new Testing().start();
    }

    // Start our MainDenis
    public void start() throws LWJGLException {
        // Set up our display
        Display.setTitle("Utilities.Testing"); //title of our window
        Display.setResizable(true); //whether our window is resizable
        Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT)); //resolution of our display
        Display.setVSyncEnabled(VSYNC); //whether hardware VSync is enabled
        Display.setFullscreen(FULLSCREEN); //whether fullscreen is enable

        camera = new CamListener();
        camera.addCam(new Ortho());
        camera.addCam(new Perspective(-400, 50, 0, 0, 0, 0));
        
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

    // Called to setup our MainDenis and context
    Vector point2 ;
    protected void create() {
    	
        light1=new SpotLight();
        light1.setCutoff(120);
        light1.on();

    }
    
    public static void print(int name ){
    	FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
        buffer.rewind();
        GL11.glGetFloat( name, buffer );
        final float[] array = new float[16];
        buffer.get(array);
        
        //buffer.
    	
    	System.out.println("  <  Matrix >");
    	  for(int i=0; i<4; i++){
    		  System.out.print("  (");
    		  for (int j=0; j<4; j++){
    			  System.out.print(" "+array[i*4+j]+" ");
    		  }
    		  System.out.println(")");
    	  }
    	System.out.println("  < /Matrix >");
    }
   
    float fAngulo = 0;
    
    // Called to render our MainDenis
    protected void render() {
      updateFPS(); // update FPS Counter
      fAngulo=Idle(fAngulo);
      if (!lag){
    	camera.render();
       
	    	Vector v = camera.getDireccion();
	        light1.setLight_position(new float[]{camera.getX(), camera.getY(), camera.getZ(),1.0f});
	        light1.setSpotDir(new float[]{5*v.x, 5*v.y, 5*v.z, 1.0f});

        glPushMatrix();
          cara.draw();
          glTranslatef(0.0f, -61.2f, 0.0f);
            Dibujo.drawMalla(1000);
        glPopMatrix();
        
        glPushMatrix();
        	//print(GL11.GL_MODELVIEW_MATRIX);
        	//print(GL11.GL_PROJECTION_MATRIX);
        	//Esfera 1
            glPushMatrix();
	            glColor3f(1.0f, 1.0f, 1.0f);
	            glRotatef(fAngulo, 0.0f, 1.0f, 0.0f);
	            glTranslatef(200.0f, 000.0f, 000.0f);
	            Dibujo.drawSphere(20,50,50);
            
	            print(GL11.GL_MODELVIEW_MATRIX);
	            
	            //Esfera 2
	            glPushMatrix();
	            	glColor3f(1.0f, 0.0f, 0.0f);
	            	glRotatef(fAngulo, 0.0f, 1.0f, 0.0f);
	            	glTranslatef(0.0f, 0.0f, 50.0f);
	            	Dibujo.drawSphere(15, 15, 15);
	            glPopMatrix();
            glPopMatrix();
            
       glPopMatrix();

        
      } else {
      	camera.render();

        glPushMatrix();
	        Vector v = camera.getDireccion();
	        glTranslated(camera.getX() + 5*v.x,camera.getY() + 5*v.y ,camera.getZ() + 5*v.z);
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
        
    	camera.listen();
        
        //----------------------------------------------------------------------
        
        if (Keyboard.isKeyDown(Keyboard.KEY_O)) {
            light1.on();
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_P)) {
            light1.off();
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
        fAngulo += 0.5f;
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
            Display.setTitle("FPS: " + fps); 
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
        return delta;
    }

    private boolean lag=false;
    private int afps = 60;

    public void update(int delta) {
    	
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
	        
        }
     }


}
