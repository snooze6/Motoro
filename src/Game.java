

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotated;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslated;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glViewport;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.glu.GLU;

import rubik.Node;
import Others.Dibujo;
import Others.Face;


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
    float scale;
    // Whether to use fullscreen mode
    public static final boolean FULLSCREEN = false; 

    // Whether our game loop is running
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
    
    Node nod;

    float fAngulo=0;
    public static void main(String[] args) throws LWJGLException {
        new Game().start();
    }

    // Start our game
    public void start() throws LWJGLException {
        // Set up our display
        Display.setTitle("LOL, soy yo"); //title of our window
        Display.setResizable(true); //whether our window is resizable
        Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT)); //resolution of our display
        Display.setVSyncEnabled(VSYNC); //whether hardware VSync is enabled
        Display.setFullscreen(FULLSCREEN); //whether fullscreen is enable

        
        //nod = new Node();
        cara = new Face(20);
        
        //create and show our display
        Display.create();

        // Create our OpenGL context and initialize any resources
        create();

        // Call this before running to set up our initial size
        resize();
        

        running = true;

        // While we're still running and the user hasn't closed the window...
        while (running && !Display.isCloseRequested()) {
            // If the game was resized, we need to update our projection
            if (Display.wasResized())
                resize();

            // Render the game
            render();

            // Flip the buffers and sync to 60 FPS
            Display.update();
            Display.sync(60);
        }

        // Dispose any resources and destroy our window
        dispose();
        Display.destroy();
    }

    // Exit our game loop and close the window
    public void exit() {
        running = false;
    }

    // Called to setup our game and context
    protected void create() {
        // 2D games generally won't require depth testing



                   // Set background depth to farthest


        // ... initialize resources here ...
    }

    // Called to render our game
    protected void render() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // limpias los buffer
        glEnable(GL_DEPTH_TEST);
        //glEnable(GL_CULL_FACE);

        glMatrixMode(GL_PROJECTION); //La camara
        glLoadIdentity(); // Inicializamos la matriz del modelo a la identidad propiedades de la camara
        //Ortho cam
        //glOrtho(-10*scale, 10*scale, -10, 10, -10, 10);

        //perspective cam
        GLU.gluPerspective(zoom*45,(float)Display.getWidth()/(float)Display.getHeight(),1,2000);

        glRotated(xRotate, 1.0, 0.0, 0.0);
        glRotated(yRotate, 0.0, 1.0, 0.0);
        glTranslated(-xTranslate,-yTranslate,-zTranslate);
        //GLU.gluLookAt(xTranslate ,yTranslate,zTranslate,xLookAt ,yLookAt,zTranslate-10,0,1,0);

        glMatrixMode(GL_MODELVIEW); // Activamos la matriz del modelo
        glLoadIdentity(); // Inicializamos la matriz del modelo a la identidad



        input();
        fAngulo=Idle(fAngulo);
        //Dibujo.drawAxes(100);
        //cube.drawCube(5);

        glPushMatrix();
          //Dibujo.drawCube(50);
          cara.draw();
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
        // ... render our game here ...

    }

    // Called to resize our game
    protected void resize() {
        glViewport(0, 0, Display.getWidth(), Display.getHeight());

    }

    // Called to destroy our game upon exiting
    protected void dispose() {
        // ... dispose of any textures, etc ...
    }

    public void input(){
        int speedMovement=3;
        float rotateMovement=1.5f;
        //Translate cam
        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            xTranslate+=Math.sin(Math.toRadians(yRotate))*speedMovement;
            zTranslate-=Math.cos(Math.toRadians(yRotate))*speedMovement;
            yTranslate-=Math.sin(Math.toRadians(xRotate))*speedMovement;

        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            xTranslate-=Math.sin(Math.toRadians(yRotate))*speedMovement;
            zTranslate+=Math.cos(Math.toRadians(yRotate))*speedMovement;
            yTranslate+=Math.sin(Math.toRadians(xRotate))*speedMovement;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            xTranslate+=Math.cos(Math.toRadians(yRotate))*speedMovement;
            zTranslate+=Math.sin(Math.toRadians(yRotate))*speedMovement;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            xTranslate-=Math.cos(Math.toRadians(yRotate))*speedMovement;
            zTranslate-=Math.sin(Math.toRadians(yRotate))*speedMovement;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
        	yTranslate+=speedMovement;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
        	yTranslate-=speedMovement;
        }

        //RotateCam
        if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
            xRotate+=1f*rotateMovement;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
            xRotate-=1f*rotateMovement;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
            yRotate+=1f*rotateMovement;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
            yRotate-=1f*rotateMovement;
        }


            int dWheel = Mouse.getDWheel();
            if (dWheel < 0) {
                zoom=zoom+0.05f;
            } else if (dWheel > 0){
                zoom=zoom-0.05f;
            }
        if(zoom<0.1){
            zoom=0.1f;
        }
        if(zoom>2.0){
            zoom=2.0f;
        }
    }

    public float Idle(float fAngulo){
        fAngulo += 0.05f;
        if (fAngulo > 360)
            fAngulo -= 360.0f;
        return fAngulo;
    }



}