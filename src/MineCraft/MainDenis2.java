package MineCraft;

import Camera.ICam;
import Camera.Perspective;
import Collision.BoundingBoxQuad;
import Collision.CollisionsManager;
import Collision.Objects.*;
import Lights.DirectionalLight;
import Lights.ILight;
import Lights.SpotLight;
import Others.Dibujo;

import com.sun.javafx.css.StyleCacheEntry;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.input.Mouse;

import java.util.ArrayList;
import java.util.Random;

import static org.lwjgl.opengl.GL11.*;

public class MainDenis2 {


    //Frames
    long lastFrame;/** time at last frame */
    int fps;/** frames per second */
    long lastFPS;/** last fps time */

    //Cam
    private ICam camera;
    private CollisionsManager col;
    boolean flag;
    //Lights
    private ILight light1;

    //Lista de objetos
        //Esferas
        private ArrayList<Esfera> listaEsferas;
        //Cubos
        ArrayList<Cubo> listaCubos;
        //Planos
        ArrayList<Plano> listaPlanos;
        //Cuadrados
        ArrayList<BoundingBoxQuad> listaCuadrados;


    //Lista de colisiones


    //Minecraft
    int sizeMalla=5;
    int sizeCube=20;
    Random rand;

//Enable vsync
    public static final boolean VSYNC = true;
    // Width and height of our window
    public static final int WIDTH = 800;
    public static final int HEIGHT = 800;
    // Whether to use fullscreen mode
    public static final boolean FULLSCREEN = false;
    // Whether our MainDenis loop is running
    protected boolean running = false;

    //Create objects
    protected void create() {
        //Camara
        camera= new Perspective(-65, 136, 225, 28,-335, -146);
        //Luces
        light1=new DirectionalLight();
        light1.setCutoff(120);
        light1.on();

        //Random
        rand= new Random();
        int randomNumX;
        int randomNumZ;
        //Objetos
                //Esferas
                listaEsferas = new ArrayList<Esfera>();
                for (int i=0; i<20; i++){
                    listaEsferas.add(new Esfera(new Vector(25+25*i, 0, 0), new Vector(0.1f,0.1f,-0.0f), 20, 5));
                    listaEsferas.add(new Esfera(new Vector(600+25+25*i, 0, 0), new Vector(0.1f,0.1f,-0.0f), 20, 5));
                }


         //Cubos

                int sizeFinal=0;
                 listaCubos=new ArrayList<Cubo>();
//                for (int i=0; i<sizeMalla; i++){
//                    listaCubos.add(new Cubo(new Vector(i, 0, 0),sizeCube));
//                    for (int j=0; j<sizeMalla; j++){
//                        listaCubos.add(new Cubo(new Vector(i, 0, j),sizeCube));
//
//                        for (int k=0; k<sizeMalla/2; k++){
//                            if(i%5==0){
//                                randomNumX = rand.nextInt(sizeMalla);
//                                randomNumZ = rand.nextInt(sizeMalla);
//                                listaCubos.add(new Cubo(new Vector(randomNumX, k, randomNumZ),sizeCube));
//                                sizeFinal++;
//
//                            }
//                        }
//                    }
//                }
//        listaCubos.add(new Cubo(new Vector(0, 0, 0),sizeCube));
//        listaCubos.add(new Cubo(new Vector(0, 1, 0),sizeCube));
//        listaCubos.add(new Cubo(new Vector(0, 1, 1),sizeCube));
//        listaCubos.add(new Cubo(new Vector(1, 1, 1),sizeCube));
//        listaCubos.add(new Cubo(new Vector(2, 2, 2),sizeCube));
        //Planos
                listaPlanos= new ArrayList<Plano>();

        //Cuadrados

                listaCuadrados= new ArrayList<BoundingBoxQuad>();
                for(int i=0;i<20;i++){
                  //  listaCuadrados.add(new BoundingBoxQuad(new Vector(0,1,0),new Vector(0,0,0),50));
                    listaCuadrados.add(new BoundingBoxQuad(new Vector(0,1,0),new Vector(50+50*i*2,0,0),50));
                    System.out.println(listaCuadrados.get(i).getSize());
                }
            for(int i=0;i<20;i++){
                //  listaCuadrados.add(new BoundingBoxQuad(new Vector(0,1,0),new Vector(0,0,0),50));
                listaCuadrados.add(new BoundingBoxQuad(new Vector(0,1,0),new Vector(50*i*2,40,0),50));
                System.out.println(listaCuadrados.get(i).getSize());
            }

        for(int i=0;i<20;i++){
            //  listaCuadrados.add(new BoundingBoxQuad(new Vector(0,1,0),new Vector(0,0,0),50));
            listaCuadrados.add(new BoundingBoxQuad(new Vector(0,1,0),new Vector(25+50*i*2,-40,0),50));
            System.out.println(listaCuadrados.get(i).getSize());
        }
        for(int i=0;i<40;i++){
            //  listaCuadrados.add(new BoundingBoxQuad(new Vector(0,1,0),new Vector(0,0,0),50));
            listaCuadrados.add(new BoundingBoxQuad(new Vector(0,1,0),new Vector(25+25*i*2,-80,0),50));
            System.out.println(listaCuadrados.get(i).getSize());
        }

        for(int i=0;i<40;i++){
            //  listaCuadrados.add(new BoundingBoxQuad(new Vector(0,1,0),new Vector(0,0,0),50));
            listaCuadrados.add(new BoundingBoxQuad(new Vector(0,1,0),new Vector(25+25*i*2,80,0),50));
            System.out.println(listaCuadrados.get(i).getSize());
        }

        //Colisiones
        ArrayList<IBoundingBox> lista = new ArrayList<IBoundingBox>(listaEsferas);
        col = new CollisionsManager(lista);
        for(int i=0;i<listaCuadrados.size();i++){
            col.add(listaCuadrados.get(i));
        }
        Plano plane= new Plano(1,0,0,1400,0,0,180);
        Plano plane2= new Plano(1,0,0,00,0,0,180);
        col.add(plane);
        listaPlanos.add(plane);
        col.add(plane2);


    }


    protected void resize() {
        camera.setWindow(Display.getWidth(), Display.getHeight());
        glViewport(0, 0, Display.getWidth(), Display.getHeight());
    }

    //Ejecucion en bucle
    public void start() {
        try {
            Display.setResizable(true); //whether our window is resizable
            Display.setDisplayMode(new DisplayMode(WIDTH,HEIGHT));
            Display.setVSyncEnabled(VSYNC); //whether hardware VSync is enabled
            Display.setFullscreen(FULLSCREEN); //whether fullscreen is enable
            Display.create();            //Create and show our display
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(0);
        }

        create(); // Create our OpenGL context and initialize any resources
        initGL(); // init OpenGL
        resize();     // Call this before running to set up our initial size
        getDelta(); // call once before loop to initialise lastFrame
        lastFPS = getTime(); // call before loop to initialise fps timer
        running=true;

        while (running &&!Display.isCloseRequested()) {
            if (Display.wasResized())
                resize();
            update(getDelta());
            renderGL();
            Display.update();
            Display.sync(60); // cap fps to 60fps
        }
        // Dispose any resources and destroy our window
        dispose();
        Display.destroy();
    }



    public void update(int delta) {
    //Metodos de entrada por teclado
    input(delta);
        for(int i=0; i<listaEsferas.size(); i++){
            listaEsferas.get(i).move(delta);
            if(i==0){
//	        		camera.setPosition(listaEsferas.get(i).getPoint().x, listaEsferas.get(i).getPoint().y, listaEsferas.get(i).getPoint().z-50);
//	        		camera.setDireccion(listaEsferas.get(i).getVelocity().x,listaEsferas.get(i).getVelocity().y,listaEsferas.get(i).getVelocity().z);
            }
        }

        col.collide(delta);


    }

    public void renderGL() {
        updateFPS(); // update FPS Counter
        //Renderizo la camara
        camera.render();
        //Pongo las luces donde se debe
        Vector v = camera.getDireccion();
        light1.setLight_position(new float[]{camera.getX(), camera.getY(), camera.getZ(), 1.0f});
        light1.setSpotDir(new float[]{5*v.x, 5*v.y, 5*v.z, 1.0f});

        //Dibujo
        Dibujo.drawAxes(50);
       // UtilitiesMinecraft.drawMalla(sizeMalla,sizeCube);

        //Dibujo esferas
        for(int i=0; i<listaEsferas.size(); i++){
            glPushMatrix();
            if(i<11){
                glColor3f(0.0f,1.0f,1.0f);
                //glCallList(displayListHandle);
                listaEsferas.get(i).draw();
            }
            else{
                glColor3f(1.0f,1.0f,1.0f);
                listaEsferas.get(i).draw();
            }
            glPopMatrix();
        }

        //Dibujo cubos
       for(int i=0;i<listaCubos.size();i++){
            listaCubos.get(i).draw();
        }

        //Dibujo planos
        for (int i=0;i<listaPlanos.size();i++){
            listaPlanos.get(i).draw();
        }

        //Dibujo Cuadrados
        for (int i=0;i<listaCuadrados.size();i++){
            listaCuadrados.get(i).draw();
        }

    }





    //Init opengl
    public void initGL() {

    }
    // Called to destroy our MainDenis upon exiting
    protected void dispose() {}

    //------------------Time and fps
    /*** Calculate how many milliseconds have passed
     * since last frame.*/
    public int getDelta() {
        long time = getTime();
        int delta = (int) (time - lastFrame);
        lastFrame = time;
        return delta;
    }

    /*** @return The system time in milliseconds*/
    public long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }

    /**Calculate the FPS and set it in the title bar*/
    public void updateFPS() {
        if (getTime() - lastFPS > 1000) {
            Display.setTitle("FPS: " + fps);
            fps = 0;
            lastFPS += 1000;

        }
        fps++;
    }

    // Exit our MainDenis loop and close the window
    public void exit() {
        running = false;
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


        //----------------------------------------------------------------------

        if (Keyboard.isKeyDown(Keyboard.KEY_O)) {
            light1.on();
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_P)) {
            light1.off();
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_M)) {
            light1 = new SpotLight();
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_C)) {

            Vector v = camera.getDireccion();
            Vector aux = new Vector(5*v.x, 5*v.y, 5*v.z);
            Vector.norm(aux);
            listaEsferas.get(0).setVelocity(Vector.prod(1.2f, Vector.norm(aux)));
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_H)) {
            listaEsferas.get(0).setVelocity(0.0f,0.0f,0.0f);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_F)) {
            listaEsferas.get(0).setVelocity(0.01f,0.0f,0.0f);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_G)) {
            listaEsferas.get(0).setVelocity(-0.01f,0.0f,0.0f);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_R)) {
            listaEsferas.get(0).setVelocity(0.0f,0.01f,0.0f);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_T)) {
            listaEsferas.get(0).setVelocity(0.0f,-0.01f,0.0f);
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_V)) {
            listaEsferas.get(0).setVelocity(0.0f, 0.0f, 0.01f);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_B)) {
            listaEsferas.get(0).setVelocity(0.0f,-0.0f,-0.01f);}

        while(Keyboard.next()){
            if (Keyboard.isKeyDown(Keyboard.KEY_0)) {
                listaCuadrados.get(0).getPoint1().print();
                listaCuadrados.get(0).getPoint2().print();
                listaCuadrados.get(0).getPoint3().print();
                listaCuadrados.get(0).getPoint4().print();
            }
        }


        if (Keyboard.isKeyDown(Keyboard.KEY_O)) {
            System.out.println(camera.getX() + "  " + camera.getY() + "   " + camera.getZ());
            System.out.println(camera.getAngX()+ "    " + camera.getAngY() +"  " + camera.getAngZ());
        }



        //----------------------------------------------------------------------

        int dWheel = Mouse.getDWheel();
        if (dWheel < 0) {
            camera.morezoom();
        } else if (dWheel > 0){
            camera.lesszoom();
        }
    }
    public static void main(String[] argv) {
        MainDenis2 MainDenis = new MainDenis2();
        MainDenis.start();
    }
}

