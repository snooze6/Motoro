package Demos;

import Camera.CamListener;
import Camera.Ortho;
import Collision.BBPlane;
import Collision.BBSphere;
import Collision.BoundingBox;
import Collision.CollisionsManager;
import Lights.ILight;
import Lights.SpotLight;
import Others.Face;
import Utilities.Dibujo;
import Utilities.Vector;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;


/**
 * A bare-bones implementation of a LWJGL application.
 * @author davedes
 */
public class ShooterQuadDemo {

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

    private Face cara;

    private CamListener camera;
    private int delta;

    private BBPlane arr, aba, der, izq, del, atr, atratr;
    ILight light1;
    private CollisionsManager col;
    int valorLanzamiento=0;
    int rafaga=10;

    float fAngulo=0;
    public static void main(String[] args) throws LWJGLException {
        new ShooterQuadDemo().start();
    }

    // Start our MainDenis
    public void start() throws LWJGLException {
        // Set up our display
        Display.setTitle("LOL, soy yo"); //title of our window
        Display.setResizable(true); //whether our window is resizable
        Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT)); //resolution of our display
        Display.setVSyncEnabled(VSYNC); //whether hardware VSync is enabled
        Display.setFullscreen(FULLSCREEN); //whether fullscreen is enable

        camera = new CamListener();
        camera.addCam(new Ortho());
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

    private ArrayList<BBSphere> listaBBSpheres;
    private ArrayList<BBSphere> listaEsferasLanzar;
    private int displayListHandle = -1;

    // Called to setup our MainDenis and context
    Vector point2 ;
    protected void create() {
        point2=new Vector(0,0,1);
        int cte=80;

        listaBBSpheres = new ArrayList<BBSphere>();
        listaEsferasLanzar = new ArrayList<BBSphere>();
        int value=0;

        for (int i=0; i<100; i++){
            listaEsferasLanzar.add(new BBSphere(new Vector(-5000, +100, +100), new Vector(-0.0f,-0.0f,-0.0f), 20, 20));
        }



        int siz = 11, tam=0;
        for (int i=0; i<siz; i++){
            for (int j=0; j<siz; j++){
                for (int k=0; k<siz; k++){
                    tam++;
                    listaBBSpheres.add(new BBSphere(new Vector(-580 + 22*k, 12 + 22*j, -580 + 22*i), new Vector(0.0f,0.0f,0.0f),50 , 40));
                }
            }
        }
        for(int i=0;i<10;i++){
            listaEsferasLanzar.add(new BBSphere(new Vector(-5000, +100, +100), new Vector(-0.0f,-0.0f,-0.0f), 20, 20));
        }
        System.out.println(tam);
        System.out.println(siz*siz*siz);

//    	for (int i=0; i<1; i++){
//    		listaEsferas.add(new Esfera(new Vector(-400, 0+11, -360+10*value-1-cte), new Vector(-0.0f,-0.0f,0.0f), 20, 10));
//    	}
//    	value++;
//    	for (int i=0; i<2; i++){
//    		listaEsferas.add(new Esfera(new Vector(-400-10*value+i*20, 0+11, -360-20*value-1-cte), new Vector(-0.0f,-0.0f,-0.0f), 20, 10));
//    	}
//    	value++;
//    	for (int i=0; i<3; i++){
//    		listaEsferas.add(new Esfera(new Vector(-400-10*value+i*20, 0+11, -360-20*value-1-cte), new Vector(-0.0f,-0.0f,-0.0f), 20, 10));
//    	}

//    	for (int i=0; i<400; i++){
//    		listaEsferas.add(new Esfera(new Vector(-480+22*i, 20, -400), new Vector(-0.1f,-0.01f,0.01f*i), 10, 10));
//
//    	}

//    	//Lanzadera desde atrás
//    	for (int i=0; i<300; i++){
//    		listaEsferas.add(new Esfera(new Vector(-300, 200, +2000+70*i), new Vector(0,0,-0.2f), 10, 10));
//    	}

        ArrayList<BoundingBox> lista = new ArrayList<BoundingBox>(listaBBSpheres);


//    	for (int i=0; i<2; i++){
//
//	    	aba = new Plano(0,1,0 , -400, 0  , -400, 200);
//	    	arr = new Plano(0,-1,0, -400, 400, -400, 200);
//	    	izq = new Plano(1,0,0 , -600, 200, -400, 200);
//	    	der = new Plano(1,0,0, -200, 200, -400, 200);
//	    	del = new Plano(0,0,-1, -400, 200, -200, 200);
//	    	atr = new Plano(0,0,1 , -400, 200, -600, 200);
//	        atratr = new Plano(0,1,1 , -400, 200, +200, 200);
//	//
//	        lista.add(arr);
//	        lista.add(aba);
//	        lista.add(der);
//	        lista.add(izq);
//	        lista.add(atr);
//	        //lista.add(del);
//	        lista.add(atratr);
//
//    	}



        col = new CollisionsManager(lista);
        for(int i=0;i<listaEsferasLanzar.size();i++){
            col.add(listaEsferasLanzar.get(i));
        }

        light1=new SpotLight();
        light1.setCutoff(120);
        light1.on();



    }

    // Called to render our MainDenis
    protected void render() {
        updateFPS(); // update FPS Counter

        if (!lag){
            camera.render();

            Vector v = camera.getDireccion();
	            light1.setLight_position(new float[]{camera.getX(), camera.getY(), camera.getZ(),1.0f});
	            light1.setSpotDir(new float[]{5*v.x, 5*v.y, 5*v.z, 1.0f});
	            
            glPushMatrix();
	            v = camera.getDireccion();
	            //glTranslated(camera.getX() + 5 * v.x, camera.getY() + 5 * v.y, camera.getZ() + 5 * v.z);
	            	light1.off();
		            glColor3f(0,1,1);
		            Dibujo.drawPoint(camera.getFront(5));
		            light1.on();

            glPopMatrix();

            glPushMatrix();
            //Dibujo.drawCube(50);
//            cara.draw();
            glTranslatef(0.0f, -61.2f, 0.0f);
            Dibujo.drawMalla(1000);
            glPopMatrix();

            for(int i=0;i<listaEsferasLanzar.size(); i++){

                glPushMatrix();
                //	glTranslated(point.x,point.y,point.z);



                glColor3f(1.0f,0.0f,0.0f);
                //glCallList(displayListHandle);
                listaEsferasLanzar.get(i).draw();

                //Dibujo.drawSphere(size, 10, 10);
                //Dibujo.drawCube(size);
                //s.draw(size, 30, 30);
                glPopMatrix();

            }

            for(int i=0; i< listaBBSpheres.size(); i++){

                glPushMatrix();
                //	glTranslated(point.x,point.y,point.z);



                    glColor3f(1.0f,1.0f,1.0f);
                    //glCallList(displayListHandle);
                    listaBBSpheres.get(i).draw2();


                //Dibujo.drawSphere(size, 10, 10);
                //Dibujo.drawCube(size);
                //s.draw(size, 30, 30);
                glPopMatrix();
            }

//        aba.draw(); arr.draw(); der.draw(); izq.draw(); atr.draw(); //del.draw();
//          atratr.draw();
        } else {
            camera.render();

            glPushMatrix();
            Vector v = camera.getDireccion();
            glTranslated(camera.getX() + 5 * v.x, camera.getY() + 5 * v.y, camera.getZ() + 5 * v.z);
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

        if (Keyboard.isKeyDown(Keyboard.KEY_O)) {
            light1.on();
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_P)) {
            light1.off();
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_N)) {
            for (int i=7; i< listaBBSpheres.size(); i++){
                listaBBSpheres.get(i).setVelocity(new Vector(((float) Math.random()/2), ((float) Math.random()/2), -1.2f));
            }
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_M)) {
            light1 = new SpotLight();
        }
        while(Keyboard.next()) {
            if(valorLanzamiento==listaEsferasLanzar.size()){
                valorLanzamiento=0;
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_C)) {
                Vector v = camera.getDireccion();
                light1.setLight_position(new float[]{camera.getX(), camera.getY(), camera.getZ(), 1.0f});
                light1.setSpotDir(new float[]{5 * v.x, 5 * v.y, 5 * v.z, 1.0f});
                Vector aux = new Vector(5 * v.x, 5 * v.y, 5 * v.z);
                Vector.norm(aux);
                listaEsferasLanzar.get(valorLanzamiento).setVelocity(Vector.prod(1.2f, Vector.norm(aux)));

            }
            if (Keyboard.isKeyDown(Keyboard.KEY_H)) {
                listaEsferasLanzar.get(valorLanzamiento).setVelocity(0.0f, 0.0f, 0.0f);

            }
            if (Keyboard.isKeyDown(Keyboard.KEY_F)) {
                listaEsferasLanzar.get(valorLanzamiento).setVelocity(-0.0f, 0.1f, -1.5f);

            }
            if (Keyboard.isKeyDown(Keyboard.KEY_G)) {
                listaEsferasLanzar.get(valorLanzamiento).setVelocity(-0.0f, 0.1f, 1.5f);

            }

            if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
                if (rafaga % 10 == 0 || true) {

                    Vector v = camera.getDireccion();
                    Vector aux = new Vector(4 * listaEsferasLanzar.get(valorLanzamiento).getSize() * v.x, 4 * listaEsferasLanzar.get(valorLanzamiento).getSize() * v.y, 4 * listaEsferasLanzar.get(valorLanzamiento).getSize() * v.z);
                    listaEsferasLanzar.get(valorLanzamiento).setVelocity(0, 0, 0);
                    listaEsferasLanzar.get(valorLanzamiento).setPoint(Vector.sum(new Vector(camera.getX(), camera.getY(), camera.getZ()), aux));


                    Vector aux2 = new Vector(5 * v.x, 5 * v.y, 5 * v.z);
                    Vector.norm(aux2);
                    listaEsferasLanzar.get(valorLanzamiento).setVelocity(Vector.prod(1.2f, Vector.norm(aux2)));

                }
            }
            valorLanzamiento++;
            rafaga++;
        }

        camera.listen();

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
            Display.setTitle("FPS: " + fps); //runtime++;
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

        //runtime+=delta;
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

            for(int i=0; i< listaBBSpheres.size(); i++){
                listaBBSpheres.get(i).move(delta);
                if(i==0){
//	        		camera.setPosition(listaEsferas.get(i).getCenterPoint().x, listaEsferas.get(i).getCenterPoint().y, listaEsferas.get(i).getCenterPoint().z-50);
//	        		camera.setDireccion(listaEsferas.get(i).getVelocity().x,listaEsferas.get(i).getVelocity().y,listaEsferas.get(i).getVelocity().z);
                }
            }
            for(int i=0; i<listaEsferasLanzar.size(); i++){
                listaEsferasLanzar.get(i).move(delta);}
            col.collide(delta);
        }
    }


}
