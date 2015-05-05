

import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glViewport;

import java.util.ArrayList;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import rubik.Node;
import Camera.ICam;
import Camera.Ortho;
import Camera.Perspective;
import Collision.Objects.Plano;
import Others.Dibujo;
import Others.Face;


/**
 * A bare-bones implementation of a LWJGL application.
 * @author davedes
 */
public class TestPlano {

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

    private Face cara;
    
    private ICam camera, cam1, cam2;
    Node nod;
    
    private ArrayList<Plano> p;

    float fAngulo=0;
    public static void main(String[] args) throws LWJGLException {
        new TestPlano().start();
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
        cam2 = new Perspective();
        camera = cam2;
        camera.setWindow(Display.getWidth(), Display.getHeight());
        
        //nod = new Node();
        cara = new Face(20);
        
        p=new ArrayList<Plano>();
        
        p.add(new Plano(1,1,0 , 100,100,0));
        p.add(new Plano(-1,-1,0 , -100,-100,0));
        p.add(new Plano(-1,1,0 , -100,100,0));
        p.add(new Plano(1,-1,0 , 100,-100,0));
        
        p.add(new Plano(1,0,0 , 150,0,0));
        p.add(new Plano(-1,0,0 , -150,0,0));
        p.add(new Plano(0,1,0 , 0,150,0));
        p.add(new Plano(0,-1,0 , 0,-150,0));
        
        p.add(new Plano(0,0,1 , 0,0,+150));
        p.add(new Plano(0,0,-1 , 0,0,-150));
        
        
        p.add(new Plano(1,1,1 , 300,300,300));
        p.add(new Plano(1,-1,1 , 300,-300,300));
        p.add(new Plano(-1,1,-1 , -300,300,-300));
        p.add(new Plano(-1,-1,-1 , -300,-300,-300));
        
        p.add(new Plano(1,1,-1 , 300,300,-300));
        p.add(new Plano(-1,-1,1 , -300,-300,300));
        p.add(new Plano(1,-1,-1 , 300,-300,-300));
        p.add(new Plano(-1,1,1 , -300,300,300));
         
        //create and show our display
        Display.create();
       

        // Create our OpenGL context and initialize any resources
        create();

        // Call this before running to set up our initial size
        resize();
        

        running = true;

        // While we're still running and the user hasn't closed the window...
        while (running && !Display.isCloseRequested()) {
            // If the MainDenis was resized, we need to update our projection
            if (Display.wasResized())
                resize();

            // Render the MainDenis
             render();
            // Flip the buffers and sync to 60 FPS
            Display.update();
            Display.sync(60);
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
    protected void create() {
        // 2D games generally won't require depth testing



                   // Set background depth to farthest


        // ... initialize resources here ...
    }

    // Called to render our MainDenis
    protected void render() {

    	camera.render();

//        //Mirilla que apunta hacia la cámara
//        /*
//         * Si tienes alguna duda te lo explico más detenidamente
//         */
//        glPushMatrix();
//            float[] v = camera.getDireccion();
//	        glTranslated(camera.getCam_x() + 5*v[0],camera.getCam_y() + 5*v[1] ,camera.getCam_z() + 5*v[2]);
//	        	glColor3f(2.0f, 0.5f, 0.0f);
//	            Dibujo.drawSphere(0.2f, 20, 20);
//	            Dibujo.drawAxes(1);
//        glPopMatrix();

        input();

        glPushMatrix();
          //Dibujo.drawCube(50);
          cara.draw();
          glTranslatef(0.0f, -61.2f, 0.0f);
            Dibujo.drawMalla(1000);
        glPopMatrix();
        
        for (int i=0; i<p.size(); i++){
        	p.get(i).draw();
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

    public void input(){
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



}
