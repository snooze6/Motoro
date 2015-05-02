

import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslated;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glViewport;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import rubik.Node;
import Camera.ICam;
import Camera.Perspective;
import Others.Dibujo;
import Others.Face;
import Lights.*;


/**
 * A bare-bones implementation of a LWJGL application.
 * @author davedes
 */
public class Game {

    // Whether to enable VSync in hardware.
    public static final boolean VSYNC = true;


    // Width and height of our window
    public static final int WIDTH = 800;
    public static final int HEIGHT = 800;
    // Whether to use fullscreen mode
    public static final boolean FULLSCREEN = false; 

    // Whether our MainDenis loop is running
    protected boolean running = false;
  
    //Cam perspective parameters
    //CamPosition
    float xTranslate=0;
    float yTranslate=0;
    float zTranslate=10;

    //CamRotate
    //float xRotate=35.264f;
    //float yRotate=-45.0f;
    float xRotate=0;
    float yRotate=0;
    float zRotate=0;

    //LookAt
    float xLookAt=0;
    float zLookAt=0;
    float yLookAt=0;

    float zoom=1;
    //Mouse
    int controlPulsacion=0;
    int xPosition=0;
    int yPosition=0;
    int lastX;
    int lastY;

    private Face cara;
    private ILight lightTest;
    
    private ICam camera;
    Node nod;

    float fAngulo=0;
    public static void main(String[] args) throws LWJGLException {
        new Game().start();
    }

    // Start our MainDenis
    public void start() throws LWJGLException {
        // Set up our display
        Display.setTitle("LOL, soy yo"); //title of our window
        Display.setResizable(true); //whether our window is resizable
        Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT)); //resolution of our display
        Display.setVSyncEnabled(VSYNC); //whether hardware VSync is enabled
        Display.setFullscreen(FULLSCREEN); //whether fullscreen is enable

        camera = new Perspective(0,0,0,0,0,0,45);
        camera.setWindow(Display.getWidth(), Display.getHeight());
        
        //nod = new Node();
        cara = new Face(20);
        lightTest=new SpotLight();

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
        lightTest.on();
    }

    // Called to render our MainDenis
    protected void render() {

    	camera.render();

        //Mirilla que apunta hacia la cámara
        /*
         * Si tienes alguna duda te lo explico más detenidamente
         */
        glPushMatrix();
            float desx, desy, desz;
            desx = (float) (Math.sin(Math.toRadians(camera.getCam_ang_y()))*Math.cos(Math.toRadians(camera.getCam_ang_x())));
            desz = (float) (Math.cos(Math.toRadians(camera.getCam_ang_y()))*Math.cos(Math.toRadians(camera.getCam_ang_x())));
            desy = (float) Math.sin(Math.toRadians(camera.getCam_ang_x()));
	        glTranslated(camera.getCam_x() + 5*desx,camera.getCam_y() - 5*desy ,camera.getCam_z() - 5*desz);
	        	glColor3f(2.0f, 0.5f, 0.0f);
	            Dibujo.drawSphere(0.2f, 20, 20);
	            Dibujo.drawAxes(1);
        glPopMatrix();

        input();
        fAngulo=Idle(fAngulo);
        //Dibujo.drawAxes(100);
        //cube.drawCube(5);

        glPushMatrix();
          //Dibujo.drawCube(50);
          cara.draw();
          glTranslatef(0.0f, -61.2f, 0.0f);
            Dibujo.drawMalla(1000);
        glPopMatrix();


        //Esfera 1
        glPushMatrix();
        glColor3f(0.8f, 0.8f, 0.8f);
        glRotatef( 50*fAngulo, 0.0f, 1.0f, 0.0f);
        glTranslatef(0.0f, 0.0f, 100.0f);
        glRotatef( 30*fAngulo, 0.0f, 1.0f, 0.0f);
        Dibujo.drawSphere(20,50,50);
        Dibujo.drawAxes(40);
        glPopMatrix();

        //Esfera 2
        glPushMatrix();
        glColor3f(1.0f, 0.0f, 0.0f);
        glRotatef(8 * fAngulo, 0.0f, 1.0f, 0.0f);
        glTranslatef(0.0f, 0.0f, 200.0f);
        glRotatef(10 * fAngulo, 0.0f, 1.0f, 0.0f);
        Dibujo.drawSphere(10.0f, 20, 20);
        Dibujo.drawAxes(40);
        glPopMatrix();

        //Esfera 3
        glPushMatrix();
        glColor3f(1.0f, 1.0f, 1.0f);
        glRotatef(5 * fAngulo, 0.0f, 1.0f, 0.0f);//Antes de transladar para que gire respecto a origen
        glTranslatef(0.0f, 0.0f, 300.0f);
        glPushMatrix();
        glRotatef(fAngulo * 2, 0.0f, 1.0f, 0.0f);
        glPopMatrix();
        Dibujo.drawSphere(20.0f, 20, 20);
        Dibujo.drawAxes(40);

                //Esfera  girando alrededor de esfera 2
                glPushMatrix();
                glColor3f(1.0f, 0.5f, 0.5f);
                glRotatef(fAngulo*2.0f, 0.0f, 1.0f, 0.0f);
                glTranslatef(0.0f, 0.0f, -50.0f);
                glRotatef(fAngulo, 0.0f, 1.0f, 0.0f);
                Dibujo.drawSphere(10.0f, 20, 20);
                Dibujo.drawAxes(40);
                glPopMatrix();
                //Esfera  girando alrededor de esfera 2
                glPushMatrix();
                glColor3f(0.0f, 1.0f, 0.0f);
                glRotatef(fAngulo*2.0f + 120.0f, 0.0f, 1.0f, 0.0f);
                glTranslatef(0.0f, 0.0f, -50.0f);
                glRotatef(fAngulo, 0.0f, 1.0f, 0.0f);
                Dibujo.drawSphere(10.0f, 20, 20);
                Dibujo.drawAxes(40);
                glPopMatrix();
                //Esfera  girando alrededor de esfera 2
                glPushMatrix();
                glColor3f(1.0f, 0.0f, 1.0f);
                glRotatef(fAngulo*2.0f + 240.0f, 0.0f, 1.0f, 0.0f);
                glTranslatef(0.0f, 0.0f, -50.0f);
                glRotatef(fAngulo, 0.0f, 1.0f, 0.0f);
                Dibujo.drawSphere(10.0f, 20, 20);
                Dibujo.drawAxes(40);
                glPopMatrix();
        glPopMatrix();
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
            System.out.println("[CAM]: Angx: "+camera.getCam_ang_x()+" - Angy: "+camera.getCam_ang_y());
            System.out.println("  --   Posx: "+camera.getCam_x()+" - Posy: "+camera.getCam_y()+" - Posz: "+ camera.getCam_z());
	            float desx, desy, desz;
	            desx = (float) (Math.sin(Math.toRadians(camera.getCam_ang_y()))*Math.cos(Math.toRadians(camera.getCam_ang_x())));
	            desz = (float) (Math.cos(Math.toRadians(camera.getCam_ang_y()))*Math.cos(Math.toRadians(camera.getCam_ang_x())));
	            desy = (float) Math.sin(Math.toRadians(camera.getCam_ang_x()));
            System.out.println("  --   Desx: "+desx+" - Desy: "+desy+" - Desz: "+desz);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
            camera.lookUp(rotateMovement);
            System.out.println("[CAM]: Angx: "+camera.getCam_ang_x()+" - Angy: "+camera.getCam_ang_y());
            System.out.println("  --   Posx: "+camera.getCam_x()+" - Posy: "+camera.getCam_y()+" - Posz: "+ camera.getCam_z());
	            float desx, desy, desz;
	            desx = (float) (Math.sin(Math.toRadians(camera.getCam_ang_y()))*Math.cos(Math.toRadians(camera.getCam_ang_x())));
	            desz = (float) (Math.cos(Math.toRadians(camera.getCam_ang_y()))*Math.cos(Math.toRadians(camera.getCam_ang_x())));
	            desy = (float) Math.sin(Math.toRadians(camera.getCam_ang_x()));
        System.out.println("  --   Desx: "+desx+" - Desy: "+desy+" - Desz: "+desz);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
            camera.lookRight(rotateMovement);
            System.out.println("[CAM]: Angx: "+camera.getCam_ang_x()+" - Angy: "+camera.getCam_ang_y());
            System.out.println("  --   Posx: "+camera.getCam_x()+" - Posy: "+camera.getCam_y()+" - Posz: "+ camera.getCam_z());
	            float desx, desy, desz;
	            desx = (float) (Math.sin(Math.toRadians(camera.getCam_ang_y()))*Math.cos(Math.toRadians(camera.getCam_ang_x())));
	            desz = (float) (Math.cos(Math.toRadians(camera.getCam_ang_y()))*Math.cos(Math.toRadians(camera.getCam_ang_x())));
	            desy = (float) Math.sin(Math.toRadians(camera.getCam_ang_x()));
        System.out.println("  --   Desx: "+desx+" - Desy: "+desy+" - Desz: "+desz);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
            camera.lookLeft(rotateMovement);
            System.out.println("[CAM]: Angx: "+camera.getCam_ang_x()+" - Angy: "+camera.getCam_ang_y());
            System.out.println("  --   Posx: "+camera.getCam_x()+" - Posy: "+camera.getCam_y()+" - Posz: "+ camera.getCam_z());
	            float desx, desy, desz;
	            desx = (float) (Math.sin(Math.toRadians(camera.getCam_ang_y()))*Math.cos(Math.toRadians(camera.getCam_ang_x())));
	            desz = (float) (Math.cos(Math.toRadians(camera.getCam_ang_y()))*Math.cos(Math.toRadians(camera.getCam_ang_x())));
	            desy = (float) Math.sin(Math.toRadians(camera.getCam_ang_x()));
        System.out.println("  --   Desx: "+desx+" - Desy: "+desy+" - Desz: "+desz);
        }

        //----------------------------------------------------------------------
        
        if (Keyboard.isKeyDown(Keyboard.KEY_Z)) {
            camera.morezoom();
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_X)) {
            camera.lesszoom();
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
