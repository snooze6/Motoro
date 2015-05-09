

import static org.lwjgl.opengl.GL11.GL_COMPILE;
import static org.lwjgl.opengl.GL11.GL_QUAD_STRIP;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glCallList;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glEndList;
import static org.lwjgl.opengl.GL11.glGenLists;
import static org.lwjgl.opengl.GL11.glNewList;
import static org.lwjgl.opengl.GL11.glNormal3d;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTranslated;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex3d;
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
import Collisions.IBoundingBox;
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
        
    	int r=10, lats=35, longs=35;
    	
        int i, j; double lat0, z0, zr0, lat1, z1, zr1; 
        displayListHandle = glGenLists(1);
		glNewList(displayListHandle, GL_COMPILE);
		
	        for(i = 0; i <= lats; i++) {
	            lat0 = Math.PI * (-0.5 + (double) (i - 1) / lats);
	            z0  = Math.sin(lat0);
	            zr0 =  Math.cos(lat0);
	
	            lat1 = Math.PI * (-0.5 + (double) i / lats);
	            z1 =  Math.sin(lat1);
	            zr1 = Math.cos(lat1);
	
	            glBegin(GL_QUAD_STRIP);
	            for(j = 0; j <= longs; j++) {
	                double lng = 2 * Math.PI * (double) (j - 1) / longs;
	                double x = r*Math.cos(lng);
	                double y = r*Math.sin(lng);
	
	                glNormal3d(x * zr0, y * zr0, r*z0);
	                glVertex3d(x * zr0, y * zr0, r*z0);
	                glNormal3d(x * zr1, y * zr1, r*z1);
	                glVertex3d(x * zr1, y * zr1, r*z1);
	            }
	            glEnd();
	         }
		
			//Dibujo.drawCube(size);
         glEndList();

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
    private int displayListHandle = -1;

    // Called to setup our MainDenis and context
    Vector point2 ;
    protected void create() {
    	point2=new Vector(0,0,1);
    	int cte=80;
    	
    	listaEsferas = new ArrayList<Esfera>();
       int value=0;
    	
    	for (int i=0; i<1; i++){
    		listaEsferas.add(new Esfera(new Vector(-400, 0+11, -220), new Vector(-0.0f,-0.0f,-0.0f), 20, 10));
//    		col.add(listaEsferas.get(i));
    	}
    	
    	for (int i=0; i<1; i++){
    		listaEsferas.add(new Esfera(new Vector(-400, 0+11, -360+10*value-1-cte), new Vector(-0.0f,-0.0f,0.0f), 20, 10));
//    		col.add(listaEsferas.get(i));
    	}
    	value++;
    	for (int i=0; i<2; i++){
    		listaEsferas.add(new Esfera(new Vector(-400-10*value+i*20, 0+11, -360-20*value-1-cte), new Vector(-0.0f,-0.0f,-0.0f), 20, 10));
//    		col.add(listaEsferas.get(i));
    	}
    	value++;
    	for (int i=0; i<3; i++){
    		listaEsferas.add(new Esfera(new Vector(-400-10*value+i*20, 0+11, -360-20*value-1-cte), new Vector(-0.0f,-0.0f,-0.0f), 20, 10));
//    		col.add(listaEsferas.get(i));
    	}
    	    	value++;
    	for (int i=0; i<4; i++){
    		listaEsferas.add(new Esfera(new Vector(-400-10*value+i*20, 0+11, -360-20*value-1-cte), new Vector(-0.0f,-0.0f,-0.0f), 20, 10));
//    		col.add(listaEsferas.get(i));
    	}
    	value++;
    	for (int i=0; i<5; i++){
    		listaEsferas.add(new Esfera(new Vector(-400-10*value+i*20, 0+11, -360-20*value-1-cte), new Vector(-0.0f,-0.0f,-0.0f), 20, 10));
//    		col.add(listaEsferas.get(i));
    	}
   	

       
//    	for (int i=0; i<10; i++){
//    		listaEsferas.add(new Esfera(new Vector(-490+20*i, 0+11, -400), new Vector(-0.0f,-0.0f,-0.0f), 20, 10));
////    		col.add(listaEsferas.get(i));
//    	}
//
//    	for (int i=0; i<20; i++){
//    		listaEsferas.add(new Esfera(new Vector(-480, 50+22*i, -400), new Vector(-0.1f,+0.01f*i*2,0), 10, 10));
////    		col.add(listaEsferas.get(i));
//    	}
//    	for (int i=0; i<20; i++){
//    		listaEsferas.add(new Esfera(new Vector(-480, 50, -400+22*i), new Vector(-0.1f*i,+0.01f*i*2,0), 30, 10));
////    		col.add(listaEsferas.get(i));
//    	}
//    	for (int i=0; i<10; i++){
//    		listaEsferas.add(new Esfera(new Vector(-480+22*i, 50+50, -400), new Vector(-0.1f*i*2,-0.01f,0), 10, 10));
//    		
//    	}
//    	for (int i=0; i<10; i++){
//    		listaEsferas.add(new Esfera(new Vector(-480+22*i, 50+100, -400), new Vector(-0.1f,-0.01f,0.01f*i*2), 10, 10));
//    		
//    	}
//    	for (int i=0; i<10; i++){
//    		listaEsferas.add(new Esfera(new Vector(-480+22*i, 50+50+50+50, -400), new Vector(-0.1f*i*2,-0.01f,0), 10, 10));
//    		
//    	}
//    	for (int i=0; i<200; i++){
//    		listaEsferas.add(new Esfera(new Vector(-480+22*i, 20, -400), new Vector(-0.1f,-0.01f,0.01f*i), 10, 10));
//    		
//    	}
    	
    	ArrayList<IBoundingBox> lista = new ArrayList<IBoundingBox>(listaEsferas);
    	
    	 
    	 
    	aba = new Plano(0,1,0 , -400, 0  , -400, 200);
    	arr = new Plano(0,-1,0, -400, 400, -400, 200);
    	izq = new Plano(1,0,0 , -600, 200, -400, 200);
    	der = new Plano(-1,0,0, -200, 200, -400, 200);
    	del = new Plano(0,0,-1, -400, 200, -200, 200);
    	atr = new Plano(0,0,1 , -400, 200, -600, 200);
    	
        lista.add(arr);
        lista.add(aba);
        lista.add(der);
        lista.add(izq);
        lista.add(atr);
        lista.add(del);
        
        col = new CollisionsManager(lista);
    	
        light1=new SpotLight();
        light1.setCutoff(120);
        light1.on();
        

      
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
        	Vector point = listaEsferas.get(i).getPoint();
    		glPushMatrix();
			glTranslated(point.x,point.y,point.z);
		
			if(i==0){
				glColor3f(0.0f,0.0f,0.0f);
				glCallList(displayListHandle);
			}
				
			else{
				
				glColor3f(1.0f,1.0f,1.0f);
				glCallList(displayListHandle);
			}
			
			//Dibujo.drawSphere(size, 10, 10);
			//Dibujo.drawCube(size);
			//s.draw(size, 30, 30);
		glPopMatrix();
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
        if (Keyboard.isKeyDown(Keyboard.KEY_G)) {
	    	float[] v = camera.getDireccion();
	        light1.setLight_position(new float[]{camera.getCam_x(), camera.getCam_y(), camera.getCam_z(),1.0f});
	        light1.setSpotDir(new float[]{5*v[0], 5*v[1], 5*v[2], 1.0f});
	        Vector aux = new Vector(5*v[0], 5*v[1], 5*v[2]);
	        Vector.norm(aux);
            listaEsferas.get(0).setVelocity(Vector.prod(0.9f, Vector.norm(aux)));
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_H)) {
            listaEsferas.get(0).setVelocity(0.0f,0.0f,0.0f);
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
	        	if(i==0){
	        		camera.setPosition(listaEsferas.get(i).getPoint().x+20, listaEsferas.get(i).getPoint().y, listaEsferas.get(i).getPoint().z);
	        		//camera.setDireccion(listaEsferas.get(i).getVelocity().x,listaEsferas.get(i).getVelocity().y,listaEsferas.get(i).getVelocity().z);
	        	}
	        }
	        
	        col.collide(delta);
        }
     }


}
