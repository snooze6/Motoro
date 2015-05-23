package Billiard;


import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import Billiard.World.Ball;
import Billiard.World.Billiard;
import Camera.CamListener;
import Camera.Isometric;
import Camera.Perspective;
import Collision.BBQuad;
import Collision.BBSphere;
import Collision.CollisionsManager;
import Lights.DirectionalLight;
import Lights.ILight;
import Utilities.Dibujo;
import Utilities.Vector;

public class MainBillar2 {
	
	public enum BillarState {LOADING, READY, RUNNING, FINISHED}

	//Atributos ventana
	    //Enable vsync
	    public static final boolean VSYNC = true;
	    // Width and height of our window
	    public static final int WIDTH = 800;
	    public static final int HEIGHT = 800;
	    // Whether to use fullscreen mode
	    public static final boolean FULLSCREEN = false;
	    // Whether our MainDenis loop is running
	    //Frames
	    long lastFrame;/** time at last frame */
	    int fps;/** frames per second */
	    long lastFPS;/** last fps time */
	    
	//Atributos juego
	    int bolasRestantes=0;
	    BillarState state = BillarState.LOADING;

    //Cam
    private CamListener camera;

    //Lights
    private ILight light1;

    //Tamaños
    private float sizeBilliard=1500;
    private float sizeSphere=sizeBilliard/40;
    private float largo= (float)sizeBilliard*1.5f-sizeBilliard/13;
    private float ancho=(float)12*sizeBilliard/20;
    private float ancho10=sizeBilliard/10;
    private float mini=sizeBilliard/10;
    
    //Objetos
    	//Billar
	    private Billiard billiard;
	    //Esferas
	    private Ball white, skyBox;
	    private ArrayList<Ball> bolas;
	    //Planos
	    private ArrayList<BBQuad>listaPlanos;
    
    //Lista de colisiones
    private CollisionsManager col;

    private float ang;
    
    //Create objects
    protected void create() {
        CreateObjects();
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
        state = BillarState.RUNNING;

        while (state == BillarState.RUNNING &&!Display.isCloseRequested()) {
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
        camera.listen();

        //Movimiento
        white.update(delta);
        //Colisiones
        col.collide(delta);

        for(int i=0; i< bolas.size();i++){
            bolas.get(i).setVel(Vector.prod(0.997f,bolas.get(i).getVel()));
            bolas.get(i).update(delta);
            
            if (i==0){
            	camera.getCam(1).setPos(Vector.sum(Vector.prod(Vector.prod(-25, camera.getCam(1).getDireccion()), sizeSphere), bolas.get(i).getPoint()));
            	camera.getCam(1).setDireccion(bolas.get(i).getVel());
            	
            	camera.getCam(2).setPos(Vector.sum(Vector.prod(Vector.prod(-10, camera.getCam(2).getDireccion()), sizeSphere), bolas.get(i).getPoint()));

            	camera.getCam(3).setPos(Vector.sum(new Vector (0,2*sizeSphere, 0), Vector.sum(Vector.prod(Vector.prod(-3, camera.getCam(3).getDireccion()), sizeSphere), bolas.get(i).getPoint())));
            }
        }
        
        for (int i=0; i<listaEsferasLanzar.size(); i++){
        	listaEsferasLanzar.get(i).setVel(Vector.prod(0.997f,bolas.get(i).getVel()));
        	listaEsferasLanzar.get(i).move(delta);
        }
    }

    public void renderGL() {
        updateFPS(); // update FPS Counter
        //Renderizo la camara
        camera.render();

        //Dibujo

        //Utilidades
        //Dibujo.drawAxes(sizeBilliard);
        //Mirilla
    	light1.off();
	        glColor3f(0,1,1);
	        Dibujo.drawPoint(camera.getFront(5), 10);
        light1.on();
        
        //Bolas
        glColor3d(0,1,1);
        glPushMatrix();
              glBegin(GL_LINES);
                Vector pcam = bolas.get(1).getPoint(), pball = bolas.get(0).getPoint();
                glVertex3f(pcam.x, pcam.y, pcam.z);
                glVertex3f(pball.x, pball.y, pball.z);
              glEnd();
        glPopMatrix();
		
        //Billar
        glBlendFunc(GL_SRC_ALPHA, GL_ONE);
        //glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_BLEND);
	        glPushMatrix();
		        glColor4f(0.6f,0.6f,0.6f, 0.5f);
		        glTranslated(sizeBilliard/11.5f,-sizeSphere,0);
		        billiard.render();
	        glPopMatrix();
        glDisable(GL_BLEND);
        
        for (int i=0; i<bolas.size(); i++){
        	bolas.get(i).render();
        }
        
        for(int i=0;i<listaEsferasLanzar.size(); i++){
            listaEsferasLanzar.get(i).draw();
        }

        ang++; if (ang>360){ang=0;}
        
        //SkyBox
        glColor3d(1,1,1);
        glPushMatrix();
            //  glRotated(ang, 1, 0, 0);
              skyBox.render();
        glPopMatrix();
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
        state = BillarState.FINISHED;
    }
    
    // Metralleta
    int valorLanzamiento=0;
    int rafaga=10;
    private ArrayList<BBSphere> listaEsferasLanzar;

    public void input(int delta){

            if (Keyboard.isKeyDown(Keyboard.KEY_I)) {
                white.setVel(0, 0, 1.0f);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_K)) {
                white.setVel(0,0,-1.0f);
            }

        //----------------------------------------------------------------------

            if (Keyboard.isKeyDown(Keyboard.KEY_J)) {
                white.setVel(-1.0f,0,0.0f);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_L)) {
                white.setVel(1.0f,0,0f);
            }
//            if (Keyboard.isKeyDown(Keyboard.KEY_R)) {
//            	white.setTransparent();
//            	for(int i=0; i<bolas.size(); i++){
//            		bolas.get(i).setTransparent();
//            	}
//            }
            
        //----------------------------------------------------------------------
            
            while(Keyboard.next()) {
                if(valorLanzamiento==listaEsferasLanzar.size()){
                    valorLanzamiento=0;
                }

                if (Keyboard.isKeyDown(Keyboard.KEY_R)) {
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
            
        //----------------------------------------------------------------------
            
        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
            Vector v = camera.getDireccion();
            Vector aux = new Vector(5 * v.x, 0 * v.y, 5 * v.z);
            aux=Vector.norm(aux);
            bolas.get(0).setVel(Vector.prod(2.0f, new Vector(aux.x,0,aux.z)));
        }      
    }
    
    public static void main(String[] argv) {
        MainBillar2 BILL = new MainBillar2();
        BILL.start();
    }
    
	private void CreateObjects() {
		//Camara
        camera= new CamListener(); 
        camera.addCam(new Perspective()); //Cámara 3ª persona
        camera.addCam(new Perspective());
        camera.addCam(new Isometric());
        
	        //Cámara 0 - Cámara Aérea
	        camera.setPos(0,5800,0);
	        camera.setAngle(90, 270, 0);
	        
	        //Camara 4 - Cámara Isométrica
	        for (int i=0; i<(sizeSphere/4); i++){
	        	camera.getCam(3).lesszoom();
	        }

        //Luces
        light1 = new DirectionalLight();
        light1.on();
        light1.setLight_position(new float[]{ 20,20,-1,0});

        //Arrays auxiliares

        //Objetos
        billiard = new Billiard(sizeBilliard);
        white = new Ball(new Vector (sizeBilliard,0,0), 20,sizeSphere);
        skyBox = new Ball(new Vector (0,0,0), 20,sizeSphere*200, "/res/images/sky1.jpg");
        
        bolas = new ArrayList<Ball>();
        bolas.add(white);
        //bolas.add(new Ball());
        int k=0, l=0;
	        for (int j=0; j<6; j++){
	        	for (int i=0; i<j; i++){
	        		k++;
	        		bolas.add(new Ball(new Vector(-(2*sizeSphere)*j-(float)(sizeBilliard/2.0f), sizeSphere*2*l, (i)*2*sizeSphere-(sizeSphere)*j+sizeSphere), 5, sizeSphere, "/res/images/pallina"+k+".jpg"));
	        	}
	        }

        //Lista planos
        listaPlanos= new ArrayList<BBQuad>();
        //Lado derecho
	        listaPlanos.add(new BBQuad(new Vector(largo,0,-ancho),new Vector(largo,0,ancho), new Vector(largo,50,ancho), new Vector(largo,50,-ancho)));
	        listaPlanos.add(new BBQuad(new Vector(largo,0,-ancho),new Vector(largo+mini*3/4,0,-ancho-mini), new Vector(largo+mini*3/4,50,-ancho-mini),new Vector(largo,50,-ancho) ));
	        listaPlanos.add(new BBQuad(new Vector(largo,0,ancho),new Vector(largo+mini*3/4,0,ancho+mini), new Vector(largo+mini*3/4,50,ancho+mini),new Vector(largo,50,ancho) ));
        //Lado izquierdo
	        listaPlanos.add(new BBQuad(new Vector(-largo,0,-ancho),new Vector(-largo,0,ancho), new Vector(-largo,50,ancho), new Vector(-largo,50,-ancho)));
	        listaPlanos.add(new BBQuad(new Vector(-largo,0,-ancho),new Vector(-largo-mini*3/4,0,-ancho-mini), new Vector(-largo-mini*3/4,50,-ancho-mini),new Vector(-largo,50,-ancho) ));
	        listaPlanos.add(new BBQuad(new Vector(-largo,0,ancho),new Vector(-largo-mini*3/4,0,ancho+mini), new Vector(-largo-mini*3/4,50,ancho+mini),new Vector(-largo,50,ancho) ));
        //Delante
	        ancho=ancho+ancho10;
	        largo=largo-mini;
	        //Parte izquierda
	        listaPlanos.add(new BBQuad(new Vector(-largo,00,-ancho),new Vector(0-mini*4/5,00,-ancho), new Vector(0-mini*4/5,50,-ancho), new Vector(-largo,50,-ancho)));
	        listaPlanos.add(new BBQuad(new Vector(-largo,00,-ancho),new Vector(-largo-mini,00,-ancho-mini*3/4),new Vector(-largo-mini,50,-ancho-mini*3/4),new Vector(-largo,50,-ancho)));
	        listaPlanos.add(new BBQuad(new Vector(0-mini*4/5,00,-ancho),new Vector(0-mini*4/5+mini/4,00,-ancho-mini*4/8),new Vector(0-mini*4/5+mini/4,50,-ancho-mini*4/8),new  Vector(0-mini*4/5,50,-ancho)));
	        //Parte derecha
	        listaPlanos.add(new BBQuad(new Vector(0+mini*4/5,00,-ancho),new Vector(largo,00,-ancho), new Vector(largo,50,-ancho), new Vector(0+mini*4/5,50,-ancho)));
	        listaPlanos.add(new BBQuad(new Vector(largo,00,-ancho),new Vector(largo+mini,00,-ancho-mini*3/4),new Vector(largo+mini,50,-ancho-mini*3/4),new Vector(largo,50,-ancho)));
	        listaPlanos.add(new BBQuad(new Vector(0+mini*4/5,00,-ancho),new Vector(0+mini*4/5-mini/4,00,-ancho-mini*4/8),new Vector(0+mini*4/5-mini/4,50,-ancho-mini*4/8),new  Vector(0+mini*4/5,50,-ancho)));
        //Detras
	        //Parte izquierda
	        listaPlanos.add(new BBQuad(new Vector(-largo,00,ancho),new Vector(0-mini*4/5,00,ancho), new Vector(0-mini*4/5,50,ancho), new Vector(-largo,50,ancho)));
	        listaPlanos.add(new BBQuad(new Vector(-largo,00,ancho),new Vector(-largo-mini,00,ancho+mini*3/4),new Vector(-largo-mini,50,ancho+mini*3/4),new Vector(-largo,50,ancho)));
	        listaPlanos.add(new BBQuad(new Vector(0-mini*4/5,00,ancho),new Vector(0-mini*4/5+mini/4,00,ancho+mini*4/8),new Vector(0-mini*4/5+mini/4,50,ancho+mini*4/8),new  Vector(0-mini*4/5,50,ancho)));
	        //Parte derecha
	        listaPlanos.add(new BBQuad(new Vector(0+mini*4/5,00,ancho),new Vector(largo,00,ancho), new Vector(largo,50,ancho), new Vector(0+mini*4/5,50,ancho)));
	        //    listaPlanos.add(new BBQuad(new Vector(largo,00,ancho),new Vector(largo+mini,00,ancho+mini*3/4),new Vector(largo+mini,50,ancho+mini*3/4),new Vector(largo,50,ancho)));
	        listaPlanos.add(new BBQuad(new Vector(largo,00,ancho),new Vector(largo+mini,00,ancho+mini*3/4),new Vector(largo+mini,50,ancho+mini*3/4),new Vector(largo,50,ancho)));
	        listaPlanos.add(new BBQuad(new Vector(0+mini*4/5,00,ancho),new Vector(0+mini*4/5-mini/4,00,ancho+mini*4/8),new Vector(0+mini*4/5-mini/4,50,ancho+mini*4/8),new  Vector(0+mini*4/5,50,ancho)));


	    //Metralleta
        listaEsferasLanzar = new ArrayList<BBSphere>();
        for (int i=0; i<100; i++){
            listaEsferasLanzar.add(new BBSphere(new Vector(-5000, +100, +100), new Vector(-0.0f,-0.0f,-0.0f), 20, 20));
        } 
	        
        //Colisiones
        col=new CollisionsManager();
	        for(int i=0; i< bolas.size();i++){
	            col.add(bolas.get(i).getBbox());
	        }
	        for(int i=0; i<listaPlanos.size();i++){
	            col.add(listaPlanos.get(i));
	        }
	        for(int i=0;i<listaEsferasLanzar.size();i++){
	            col.add(listaEsferasLanzar.get(i));
	        }
	}
}

