//package Billiard;
//
//
//import Billiard.World.Ball;
//import Billiard.World.Billiard;
//import Camera.CamListener;
//import Camera.Perspective;
//import Collision.*;
//import Collision.CollisionsManager;
//import Lights.DirectionalLight;
//import Lights.ILight;
//import Utilities.Vector;
//import org.lwjgl.LWJGLException;
//import org.lwjgl.Sys;
//import org.lwjgl.input.Keyboard;
//import org.lwjgl.opengl.Display;
//import org.lwjgl.opengl.DisplayMode;
//import Utilities.*;
//
//import java.util.ArrayList;
//
//import static org.lwjgl.opengl.GL11.*;
//
//public class MainBillarDenis {
//
//    public enum BillarState {LOADING, READY, RUNNING, FINISHED}
//
//    //Atributos ventana
//    //Enable vsync
//    public static final boolean VSYNC = true;
//    // Width and height of our window
//    public static final int WIDTH = 800;
//    public static final int HEIGHT = 800;
//    // Whether to use fullscreen mode
//    public static final boolean FULLSCREEN = false;
//    // Whether our MainDenis loop is running
//    //Frames
//    long lastFrame;/** time at last frame */
//    int fps;/** frames per second */
//    long lastFPS;/** last fps time */
//
//    //Atributos juego
//    int bolasRestantes=0;
//    BillarState state = BillarState.LOADING;
//
//    //Cam
//    private CamListener camera;
//
//    //Lights
//    private ILight light1;
//
//    //Tamaños
//    private float sizeBilliard=1500;
//    private float sizeSphere=sizeBilliard/35;
//    private float largo= (float)sizeBilliard*1.5f-sizeBilliard/13;
//    private float ancho=(float)12*sizeBilliard/20+20;
//    private float ancho10=sizeBilliard/10;
//    private float mini=sizeBilliard/10;
//
//
//    private float sizeGap=60.0f;
//
//
//    //Objetos
//    //Billar
//    private Billiard billiard;
//    //Esferas
//    private Ball white, skyBox;
//    private ArrayList<Ball> bolas;
//    //Planos
//    private ArrayList<BBQuad>listaPlanos;
//    BBPlane plane;
//
//    //Huecos
//    private ArrayList<BBGap> listaHuecos;
//
//    //Lista de colisiones
//    private CollisionsManager col,col2;
//
//    private float ang;
//
//    //Create objects
//    protected void create() {
//        CreateObjects();
//    }
//
//    protected void resize() {
//        camera.setWindow(Display.getWidth(), Display.getHeight());
//        glViewport(0, 0, Display.getWidth(), Display.getHeight());
//    }
//
//    //Ejecucion en bucle
//    public void start() {
//        try {
//            Display.setResizable(true); //whether our window is resizable
//            Display.setDisplayMode(new DisplayMode(WIDTH,HEIGHT));
//            Display.setVSyncEnabled(VSYNC); //whether hardware VSync is enabled
//            Display.setFullscreen(FULLSCREEN); //whether fullscreen is enable
//            Display.create();            //Create and show our display
//        } catch (LWJGLException e) {
//            e.printStackTrace();
//            System.exit(0);
//        }
//
//        create(); // Create our OpenGL context and initialize any resources
//        initGL(); // init OpenGL
//        resize();     // Call this before running to set up our initial size
//        getDelta(); // call once before loop to initialise lastFrame
//        lastFPS = getTime(); // call before loop to initialise fps timer
//        state = BillarState.RUNNING;
//
//        while (state == BillarState.RUNNING &&!Display.isCloseRequested()) {
//            if (Display.wasResized())
//                resize();
//            update(getDelta());
//            renderGL();
//            Display.update();
//            Display.sync(60); // cap fps to 60fps
//        }
//        // Dispose any resources and destroy our window
//        dispose();
//        Display.destroy();
//    }
//
//    public void update(int delta) {
//        //Metodos de entrada por teclado
//        input(delta);
//        camera.listen();
//
//        //Movimiento
//        white.update(delta);
//        //Colisiones
//        col.collide(delta);
//        col2.collide(delta);
//
//        for(int i=0; i< bolas.size();i++){
//            bolas.get(i).setVel(Vector.prod(0.997f,bolas.get(i).getVel()));
//            bolas.get(i).update(delta);
//
//            if (i==0){
//                camera.getCam(2).setPos(Vector.sum(Vector.prod(Vector.prod(-10, camera.getCam(2).getDireccion()), sizeSphere), bolas.get(i).getPoint()));
//                camera.getCam(2).moveUp(100);
//            }
//        }
//
//        for(int i=0; i< listaHuecos.size();i++) {
//            listaHuecos.get(i).move(delta);
//        }
//    }
//
//    public void rGL() {
//        updateFPS(); // update FPS Counter
//        //Renderizo la camara
//        camera.render();
//
//        //Dibujo
//
//        //Utilidades
//        Dibujo.drawAxes(sizeBilliard);
//
//        //Bolas
//        glColor3d(0,1,1);
//        glPushMatrix();
//        glBegin(GL_LINES);
//        Vector pcam = bolas.get(1).getPoint(), pball = bolas.get(0).getPoint();
//        glVertex3f(pcam.x, pcam.y, pcam.z);
//        glVertex3f(pball.x, pball.y, pball.z);
//        glEnd();
//        glPopMatrix();
//
//        //Billar
//        glBlendFunc(GL_SRC_ALPHA, GL_ONE);
//        //glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
//        glEnable(GL_BLEND);
//        glPushMatrix();
//        glColor4f(0.6f,0.6f,0.6f, 0.5f);
//        glTranslated(sizeBilliard/11.5f,-sizeSphere,0);
//        billiard.render();
//        glPopMatrix();
//        glDisable(GL_BLEND);
//
//        for (int i=0; i<bolas.size(); i++){
//            if(bolas.get(i).getPoint().y> (-sizeSphere*2))
//                bolas.get(i).render();
//        }
//
//
//
//        //Huecos
//        for(int i=0;i<listaHuecos.size();i++){
//            glPushMatrix();
//            glColor3d(0,0,1);
//            // listaHuecos.get(i).draw();
//            glPopMatrix();
//        }
//
//        //Planos
//        for(int i=0;i<listaPlanos.size();i++){
//            glPushMatrix();
//            glTranslated(0,-sizeSphere,0);
//            listaPlanos.get(i).draw();
//            glPopMatrix();
//        }
//
//        //SkyBox
//        glColor3d(1,1,1);
//        glPushMatrix();
//        //  glRotated(ang, 1, 0, 0);
//        skyBox.render();
//        glPopMatrix();
//    }
//
//
//    //Init opengl
//    public void initGL() {
//
//    }
//    // Called to destroy our MainDenis upon exiting
//    protected void dispose() {}
//
//    //------------------Time and fps
//    /*** Calculate how many milliseconds have passed
//     * since last frame.*/
//    public int getDelta() {
//        long time = getTime();
//        int delta = (int) (time - lastFrame);
//        lastFrame = time;
//        return delta;
//    }
//
//    /*** @return The system time in milliseconds*/
//    public long getTime() {
//        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
//    }
//
//    /**Calculate the FPS and set it in the title bar*/
//    public void updateFPS() {
//        if (getTime() - lastFPS > 1000) {
//            Display.setTitle("FPS: " + fps);
//            fps = 0;
//            lastFPS += 1000;
//
//        }
//        fps++;
//    }
//
//    // Exit our MainDenis loop and close the window
//    public void exit() {
//        state = BillarState.FINISHED;
//    }
//
//    public void input(int delta){
//        int n=5;
//        int velocidad=10;
//        if (Keyboard.isKeyDown(Keyboard.KEY_I)) {
//            listaHuecos.get(n).setVel(new Vector(0, 0, -0.03f*velocidad));
//        }
//        if (Keyboard.isKeyDown(Keyboard.KEY_K)) {
//            listaHuecos.get(n).setVel(new Vector(0, 0, 0.03f*velocidad));
//        }
//
//        //----------------------------------------------------------------------
//
//        if (Keyboard.isKeyDown(Keyboard.KEY_J)) {
//            listaHuecos.get(n).setVel(new Vector(-0.03f*velocidad, 0, 0.00f));
//        }
//        if (Keyboard.isKeyDown(Keyboard.KEY_L)) {
//            listaHuecos.get(n).setVel(new Vector(+0.03f*velocidad, 0, 0.00f));
//        }
//        if (Keyboard.isKeyDown(Keyboard.KEY_H)) {
//            listaHuecos.get(n).setVel(new Vector(-0.0f, 0, 0.00f));
//            listaHuecos.get(n).getPosition().print();
//        }
//        if (Keyboard.isKeyDown(Keyboard.KEY_M)) {
//            camera.getPos().print();
//            while(Keyboard.next()){
//
//                camera.getPos().print();
//            }
//        }
//
//        if (Keyboard.isKeyDown(Keyboard.KEY_R)) {
//            white.setTransparent();
//            for(int i=0; i<bolas.size(); i++){
//                bolas.get(i).setTransparent();
//            }
//        }
//        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
//            Vector v = camera.getDireccion();
//            Vector aux = new Vector(5 * v.x, 0 * v.y, 5 * v.z);
//            aux=Vector.norm(aux);
//            bolas.get(0).setVel(Vector.prod(2.0f, new Vector(aux.x,0,aux.z)));
//        }
//        if (Keyboard.isKeyDown(Keyboard.KEY_H)) {
//            white.setVel(0.0f,0,0f);
//        }
//
//    }
//
//    public static void main(String[] argv) {
//        MainBillarDenis BILL = new MainBillarDenis();
//        BILL.start();
//    }
//
//    private void CreateObjects() {
//        //Camara
//        camera= new CamListener();
//        camera.addCam(new Perspective()); //Cámara 3ª persona
//        camera.addCam(new Perspective());
//
//
//        //Cámara 0 - Cámara Aérea
//        camera.setPos(0,5800,0);
//        camera.setAngle(90, 270, 0);
//
//
//
//        //Luces
//        light1 = new DirectionalLight();
//        light1.on();
//        light1.setLight_position(new float[]{ 20,20,-1,0});
//
//        //Arrays auxiliares
//
//        //Objetos
//        billiard = new Billiard(sizeBilliard);
//        white = new Ball(new Vector (sizeBilliard,0,0), 20,sizeSphere);
//        skyBox = new Ball(new Vector (0,0,0), 20,sizeSphere*200, "/res/images/sky1.jpg");
//
//        bolas = new ArrayList<Ball>();
//        bolas.add(white);
//        //bolas.add(new Ball());
//        int k=0, l=0;
//        for (int j=0; j<7; j++){
//            for (int i=0; i<j; i++){
//                k++;
//                if(k>10)
//                    k=1;
//                bolas.add(new Ball(new Vector(-(2*sizeSphere)*j-(float)(sizeBilliard/2.0f), sizeSphere*2*l, (i)*2*sizeSphere-(sizeSphere)*j+sizeSphere), 5, sizeSphere, "/res/images/pallina"+k+".jpg"));
//            }
//        }
//
//
//
//        //Lista planos
//        listaPlanos= new ArrayList<BBQuad>();
//        //Lado derecho
//        listaPlanos.add(new BBQuad(new Vector(largo,0,-ancho),new Vector(largo,0,ancho), new Vector(largo,50,ancho), new Vector(largo,50,-ancho)));
//        listaPlanos.add(new BBQuad(new Vector(largo,0,-ancho),new Vector(largo+mini*3/4,0,-ancho-mini), new Vector(largo+mini*3/4,50,-ancho-mini),new Vector(largo,50,-ancho) ));
//        listaPlanos.add(new BBQuad(new Vector(largo,0,ancho),new Vector(largo+mini*3/4,0,ancho+mini), new Vector(largo+mini*3/4,50,ancho+mini),new Vector(largo,50,ancho) ));
//
//        //Lado izquierdo
//        listaPlanos.add(new BBQuad(new Vector(-largo,0,-ancho),new Vector(-largo,0,ancho), new Vector(-largo,50,ancho), new Vector(-largo,50,-ancho)));
//        listaPlanos.add(new BBQuad(new Vector(-largo,0,-ancho),new Vector(-largo-mini*3/4,0,-ancho-mini), new Vector(-largo-mini*3/4,50,-ancho-mini),new Vector(-largo,50,-ancho) ));
//        listaPlanos.add(new BBQuad(new Vector(-largo,0,ancho),new Vector(-largo-mini*3/4,0,ancho+mini), new Vector(-largo-mini*3/4,50,ancho+mini),new Vector(-largo,50,ancho) ));
//
//        //Delante
//        ancho=ancho+ancho10;
//        largo=largo-mini;
//        //Parte izquierda
//        listaPlanos.add(new BBQuad(new Vector(-largo,00,-ancho),new Vector(0-mini*4/5,00,-ancho), new Vector(0-mini*4/5,50,-ancho), new Vector(-largo,50,-ancho)));
//        listaPlanos.add(new BBQuad(new Vector(-largo,00,-ancho),new Vector(-largo-mini,00,-ancho-mini*3/4),new Vector(-largo-mini,50,-ancho-mini*3/4),new Vector(-largo,50,-ancho)));
//        listaPlanos.add(new BBQuad(new Vector(0-mini*4/5,00,-ancho),new Vector(0-mini*4/5+mini/4,00,-ancho-mini*4/8),new Vector(0-mini*4/5+mini/4,50,-ancho-mini*4/8),new  Vector(0-mini*4/5,50,-ancho)));
//        //Parte derecha
//        listaPlanos.add(new BBQuad(new Vector(0+mini*4/5,00,-ancho),new Vector(largo,00,-ancho), new Vector(largo,50,-ancho), new Vector(0+mini*4/5,50,-ancho)));
//        listaPlanos.add(new BBQuad(new Vector(largo,00,-ancho),new Vector(largo+mini,00,-ancho-mini*3/4),new Vector(largo+mini,50,-ancho-mini*3/4),new Vector(largo,50,-ancho)));
//        listaPlanos.add(new BBQuad(new Vector(0+mini*4/5,00,-ancho),new Vector(0+mini*4/5-mini/4,00,-ancho-mini*4/8),new Vector(0+mini*4/5-mini/4,50,-ancho-mini*4/8),new  Vector(0+mini*4/5,50,-ancho)));
//
//        //Detras
//        //Parte izquierda
//        listaPlanos.add(new BBQuad(new Vector(-largo,00,ancho),new Vector(0-mini*4/5,00,ancho), new Vector(0-mini*4/5,50,ancho), new Vector(-largo,50,ancho)));
//        listaPlanos.add(new BBQuad(new Vector(-largo,00,ancho),new Vector(-largo-mini,00,ancho+mini*3/4),new Vector(-largo-mini,50,ancho+mini*3/4),new Vector(-largo,50,ancho)));
//        listaPlanos.add(new BBQuad(new Vector(0-mini*4/5,00,ancho),new Vector(0-mini*4/5+mini/4,00,ancho+mini*4/8),new Vector(0-mini*4/5+mini/4,50,ancho+mini*4/8),new  Vector(0-mini*4/5,50,ancho)));
//
//        //Parte derecha
//        listaPlanos.add(new BBQuad(new Vector(0+mini*4/5,00,ancho),new Vector(largo,00,ancho), new Vector(largo,50,ancho), new Vector(0+mini*4/5,50,ancho)));
//        //    listaPlanos.add(new BBQuad(new Vector(largo,00,ancho),new Vector(largo+mini,00,ancho+mini*3/4),new Vector(largo+mini,50,ancho+mini*3/4),new Vector(largo,50,ancho)));
//
//        listaPlanos.add(new BBQuad(new Vector(largo,00,ancho),new Vector(largo+mini,00,ancho+mini*3/4),new Vector(largo+mini,50,ancho+mini*3/4),new Vector(largo,50,ancho)));
//        listaPlanos.add(new BBQuad(new Vector(0+mini*4/5,00,ancho),new Vector(0+mini*4/5-mini/4,00,ancho+mini*4/8),new Vector(0+mini*4/5-mini/4,50,ancho+mini*4/8),new  Vector(0+mini*4/5,50,ancho)));
//
//
//
//        //Colisiones
//
//        col=new CollisionsManager();
//        col2=new CollisionsManager();
//        for(int i=0; i< bolas.size();i++){
//            col.add(bolas.get(i).getBbox());
//            col2.add(bolas.get(i).getBbox());
//        }
//        for(int i=0; i<listaPlanos.size();i++){
//            col.add(listaPlanos.get(i));
//        }
//
//        for(int i=0;i<listaHuecos.size();i++){
//            col2.add(listaHuecos.get(i));
//        }
//
//    }
//}