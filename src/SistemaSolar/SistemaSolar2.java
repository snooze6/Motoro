package SistemaSolar;

import static org.lwjgl.opengl.GL11.GL_AMBIENT;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_COLOR_MATERIAL;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_DIFFUSE;
import static org.lwjgl.opengl.GL11.GL_FILL;
import static org.lwjgl.opengl.GL11.GL_FRONT_AND_BACK;
import static org.lwjgl.opengl.GL11.GL_LIGHT0;
import static org.lwjgl.opengl.GL11.GL_LIGHTING;
import static org.lwjgl.opengl.GL11.GL_LIGHT_MODEL_AMBIENT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_NORMALIZE;
import static org.lwjgl.opengl.GL11.GL_POSITION;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_SHININESS;
import static org.lwjgl.opengl.GL11.GL_SPECULAR;
import static org.lwjgl.opengl.GL11.GL_SPOT_CUTOFF;
import static org.lwjgl.opengl.GL11.GL_SPOT_DIRECTION;
import static org.lwjgl.opengl.GL11.GL_SPOT_EXPONENT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glDeleteLists;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glLight;
import static org.lwjgl.opengl.GL11.glLightModel;
import static org.lwjgl.opengl.GL11.glLightf;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMaterial;
import static org.lwjgl.opengl.GL11.glMaterialf;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glPolygonMode;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotated;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glScaled;
import static org.lwjgl.opengl.GL11.glTranslated;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glViewport;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.glu.GLU;

import Lights.Light;

public class SistemaSolar2 {

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

    // Whether our MainDenis loop is running
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
    float xRotate=0;
    float yRotate=0;


    float zoom=1;

    float fAngulo=0;
    float tiempo=0;
    public static void main(String[] args) throws LWJGLException {
        new SistemaSolar2().start();
    }

    // Start our MainDenis
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
            // If the MainDenis was resized, we need to update our projection


            // Render the MainDenis
            render();

            // Flip the buffers and sync to 60 FPS
            Display.update();
            Display.sync(60);
        }

        // Dispose any resources and destroy our window
        dispose();
        glDeleteLists(lista, 1);
        Display.destroy();
    }

    // Exit our MainDenis loop and close the window
    public void exit() {
        running = false;
    }

    // Called to setup our MainDenis and context
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
        
        {
	         FloatBuffer light_ambient;
	         FloatBuffer light_diffuse;
	         FloatBuffer light_specular;
	         FloatBuffer light_position;
	         FloatBuffer light_global;
	         FloatBuffer mat_ambient;
	         FloatBuffer mat_diffuse;
	         FloatBuffer mat_specular;
	         FloatBuffer high_shininess;
	         FloatBuffer spotDir;
	         float cutoff;
	         float exponent;
	        
	         light_global=Light.asFloatBuffer(new float[]{0.0f, 0.0f, 0.0f, 1.0f});//Luz global
	         light_ambient=Light.asFloatBuffer(new float[]{1.0f, 1.0f, 1.0f, 1.0f}); //Todo unos ilumina con el color del objeto
	         light_position=Light.asFloatBuffer(new float[]{ 0.0f, 0.0f, -50.0f, 1.0f}); //Posicion de la luz, ultimo parametro a 0 indica foco
	         light_diffuse=Light.asFloatBuffer(new float[]{1.0f, 1.0f, 1.0f, 1.0f });
	         light_specular=Light.asFloatBuffer(new float[]{ 1.0f, 1.0f, 1.0f, 1.0f});
	         mat_ambient=Light.asFloatBuffer(new float[]{ 0.7f, 0.7f, 0.7f, 1.0f});
	         mat_diffuse=Light.asFloatBuffer(new float[]{ 0.8f, 0.8f, 0.8f, 1.0f });
	         mat_specular=Light.asFloatBuffer(new float[]{ 1.0f, 1.0f, 1.0f, 1.0f});
	         spotDir=Light.asFloatBuffer(new float[]{ 0.0f, 0.0f, 1.0f,0.0f});
	         cutoff=15; //Angulo de apertura del foco
	         exponent=100; //Grado de concentración de la luz
	        
	         glEnable (GL_NORMALIZE);
	         glEnable (GL_LIGHTING);
	         glLightModel(GL_LIGHT_MODEL_AMBIENT, light_global);
	         glLight(GL_LIGHT0, GL_AMBIENT, light_ambient);
	         glLight(GL_LIGHT0, GL_DIFFUSE, light_diffuse);
	         glLight(GL_LIGHT0, GL_SPECULAR, light_specular);
	         glLight(GL_LIGHT0, GL_POSITION, light_position);
	         glEnable(GL_LIGHT0);
//	         glEnable (GL_COLOR_MATERIAL);
//	         glMaterial(GL_FRONT_AND_BACK, GL_AMBIENT, mat_ambient);
//	         glMaterial(GL_FRONT_AND_BACK, GL_DIFFUSE, mat_diffuse);
//	         glMaterial(GL_FRONT_AND_BACK, GL_SPECULAR, mat_specular);
//	         glMaterialf(GL_FRONT_AND_BACK, GL_SHININESS, 50.0f);
	         glLight(GL_LIGHT0, GL_SPOT_DIRECTION, spotDir);
	         glLightf( GL_LIGHT0, GL_SPOT_CUTOFF, cutoff);
	         glLightf( GL_LIGHT0, GL_SPOT_EXPONENT, exponent);
        }
    }


    // Called to render our MainDenis
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

        glPushMatrix();
        glColor3f(1.0f, 1.0f, 0.0f);
        sol.drawPlaneta();
        glPopMatrix();


        //Esfera 1
        glPushMatrix();
        glColor3f(1.0f, 1.0f, 1.0f);
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

                //Esfera  girando alrededor de esfera 2
                glPushMatrix();
	                glColor3f(1.0f, 0.5f, 0.5f);
	                luna1.drawPlaneta();
                glPopMatrix();
                //Esfera  girando alrededor de esfera 2

                glPushMatrix();
	                glColor3f(0.0f, 1.0f, 0.0f);
	                luna2.drawPlaneta();
                glPopMatrix();
                //Esfera  girando alrededor de esfera 2
                glPushMatrix();
	                glColor3f(1.0f, 0.0f, 1.0f);	
	                luna3.drawPlaneta();
                glPopMatrix();
        glPopMatrix();
//        // ... render our MainDenis here ...

    }

    // Called to resize our MainDenis
    protected void resize() {
        glViewport(0, 0, Display.getWidth(), Display.getHeight());

    }

    // Called to destroy our MainDenis upon exiting
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