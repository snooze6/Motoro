package com.company;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.glu.GLU;

import static org.lwjgl.opengl.GL11.*;


public class SistemaSolar {

    private static int lista;
    private static final String MODEL_LOCATION = "res/models/bunny.obj";

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

    //Additional class
    Planeta sol;
    Planeta mercurio;
    Planeta venus;
    Planeta tierra;
    Planeta luna1;
    Planeta luna2;
    Planeta luna3;
    Cubo cubo;
    Ejes axes = new Ejes();
    Cubo cube= new Cubo();
    Esfera sphere= new Esfera();
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

    int lista1;
    int lista2;
    int lista3;
    float fAngulo=0;
    float tiempo=0;
    public static void main(String[] args) throws LWJGLException {
        new com.company.SistemaSolar().start();
    }

    // Start our game
    public void start() throws LWJGLException {
        // Set up our display
        Display.setTitle("Display example"); //title of our window
        Display.setResizable(true); //whether our window is resizable
        Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT)); //resolution of our display
        Display.setVSyncEnabled(VSYNC); //whether hardware VSync is enabled
        Display.setFullscreen(FULLSCREEN); //whether fullscreen is enable

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


            // Render the game
            render();

            // Flip the buffers and sync to 60 FPS
            Display.update();
            Display.sync(20);

            System.out.println("caca");
        }

        // Dispose any resources and destroy our window
        dispose();
        glDeleteLists(lista, 1);
        Display.destroy();
    }

    // Exit our game loop and close the window
    public void exit() {
        running = false;
    }

    // Called to setup our game and context
    protected void create() {
//
        //Creacion de planetas
        //Planeta(float posicion,float velocidadRotacion,float velocidadTranslacion, float tamano)
        sol = new Planeta(0, 0.05f, 0, 40);
        mercurio = new Planeta(100, 0.05f * 30, 0.05f * 50, 20);
        venus = new Planeta(200, 8 * 0.05f, 10 * 0.05f, 10);
        tierra= new Planeta(0,0.0f,0.0f,20);
        luna1 = new Planeta(-50,0.05f*5,4,10);
        luna2= new Planeta(-80,0.05f*8,3,10);
        luna3 = new Planeta(-40,0.05f*2,2,10);
        cubo= new Cubo();
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
        GLU.gluPerspective(zoom*45,(float)Display.getWidth()/(float)Display.getHeight(),1,3000);

        glRotated(xRotate, 1.0, 0.0, 0.0);
        glRotated(yRotate, 0.0, 1.0, 0.0);
        glTranslated(-xTranslate,-yTranslate,-zTranslate);
        //GLU.gluLookAt(xTranslate ,yTranslate,zTranslate,xLookAt ,yLookAt,zTranslate-10,0,1,0);

        glMatrixMode(GL_MODELVIEW); // Activamos la matriz del modelo
        glLoadIdentity(); // Inicializamos la matriz del modelo a la identidad



        input();

        glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
        glPushMatrix();
        glColor3f(1.0f, 1.0f, 0.0f);
        glScaled(50,50,50);
       //glCallList(lista);

        glPopMatrix();

        fAngulo=Idle(fAngulo);
       // axes.drawAxes(100);
        cube.drawCube(1000);

        glPushMatrix();
        glColor3f(1.0f, 1.0f, 0.0f);
        sol.drawPlaneta();
        glPopMatrix();


        //Esfera 1
        glPushMatrix();
        glColor3f(0.8f, 0.8f, 0.8f);
        mercurio.drawPlaneta();
        //sphere.drawSphere(20,50,50);
        glPopMatrix();

        //Esfera 2
        glPushMatrix();
        glColor3f(1.0f, 0.0f, 0.0f);
        venus.drawPlaneta();
        glPopMatrix();

        //Esfera 3
        glPushMatrix();
        glColor3f(0.0f, 0.0f, 1.0f);
        glRotatef(5 * fAngulo, 0.0f, 1.0f, 0.0f);//Antes de transladar para que gire respecto a origen
        glTranslatef(0.0f, 0.0f, 300.0f);
        glPushMatrix();
        glRotatef(fAngulo * 7, 1.0f, 1.0f, 0.0f);
        cubo.drawCube(15);
        glPopMatrix();
        glPushMatrix();

            //sphere.drawSphere(20.0f, 20, 20);
            //tierra.drawPlaneta();

                //Esfera  girando alrededor de esfera 2
                glPushMatrix();

                glColor3f(1.0f, 0.5f, 0.5f);
//                glRotatef(fAngulo*2.0f, 0.0f, 1.0f, 0.0f);
//                glTranslatef(0.0f, 0.0f, -50.0f);
//                glRotatef(fAngulo, 0.0f, 1.0f, 0.0f);
//                sphere.drawSphere(10.0f, 20, 20);
                luna1.drawPlaneta();
                glPopMatrix();
                //Esfera  girando alrededor de esfera 2

                glPushMatrix();
                glColor3f(0.0f, 1.0f, 0.0f);
                luna2.drawPlaneta();
//                glRotatef(fAngulo*2.0f + 120.0f, 0.0f, 1.0f, 0.0f);
//                glTranslatef(0.0f, 0.0f, -50.0f);
//                glRotatef(fAngulo, 0.0f, 1.0f, 0.0f);
//                sphere.drawSphere(10.0f, 20, 20);
                glPopMatrix();
                //Esfera  girando alrededor de esfera 2
                glPushMatrix();
                glColor3f(1.0f, 0.0f, 1.0f);

                luna3.drawPlaneta();
//                glRotatef(fAngulo*2.0f + 240.0f, 0.0f, 1.0f, 0.0f);
//                glTranslatef(0.0f, 0.0f, -50.0f);
//                glRotatef(fAngulo, 0.0f, 1.0f, 0.0f);
//                sphere.drawSphere(10.0f, 20, 20);
                glPopMatrix();
        glPopMatrix();
//        // ... render our game here ...

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
        int control=0;
        int speedMovement=3;
        float rotateMovement=1.5f;
        //Translate cam
        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            xTranslate+=Math.sin(Math.toRadians(yRotate))*speedMovement;
            zTranslate-=Math.cos(Math.toRadians(yRotate))*speedMovement;
            yTranslate-=Math.sin(Math.toRadians(xRotate))*speedMovement;
            control=1;

        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            xTranslate-=Math.sin(Math.toRadians(yRotate))*speedMovement;
            zTranslate+=Math.cos(Math.toRadians(yRotate))*speedMovement;
            yTranslate+=Math.sin(Math.toRadians(xRotate))*speedMovement;
            control=1;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            xTranslate+=Math.cos(Math.toRadians(yRotate))*speedMovement;
            zTranslate+=Math.sin(Math.toRadians(yRotate))*speedMovement;
            control=1;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            xTranslate-=Math.cos(Math.toRadians(yRotate))*speedMovement;
            zTranslate-=Math.sin(Math.toRadians(yRotate))*speedMovement;
            control=1;
        }



        //RotateCam
        if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
            xRotate+=1f*rotateMovement;
            control=1;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
            xRotate-=1f*rotateMovement;
            control=1;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
            yRotate+=1f*rotateMovement;
            control=1;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
            yRotate-=1f*rotateMovement;
            control=1;
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