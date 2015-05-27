package Billiard;

import java.io.IOException;
import java.util.ArrayList;

import Utilities.Dibujo;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import Billiard.Others.Musica;
import Billiard.World.Ball;
import Billiard.World.Billiard;
import Billiard.World.BilliardPole;
import Camera.CamListener;
import Camera.Perspective;
import Collision.*;
import Lights.DirectionalLight;
import Lights.ILight;
import Utilities.TextureGL;
import Utilities.Vector;

import static org.lwjgl.opengl.GL11.*;

public class MainBillar2{

    public static void main(String[] argv) {
        MainBillar2 BILL = new MainBillar2();
        BILL.start();
    } //Main
    protected void create() {
        CreateObjects();
    }    //Create objects
    protected void resize() {
        camera.setWindow(Display.getWidth(), Display.getHeight());
        glViewport(0, 0, Display.getWidth(), Display.getHeight());
    }   //Resize
    //------------------Time and fps
    public int getDelta() {
        long time = getTime();
        int delta = (int) (time - lastFrame);
        lastFrame = time;
        return delta;
    }    /*** Calculate how many milliseconds have passed * since last frame.*/
    public long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    } /*** @return The system time in milliseconds*/
    public void updateFPS() {
        if (getTime() - lastFPS > 1000) {
            Display.setTitle("FPS: " + fps);
            fps = 0;
            lastFPS += 1000;

        }
        fps++;
    }/**Calculate the FPS and set it in the title bar*/
    public void exit() {
        state = BillarState.FINISHED;
    }    // Exit our MainDenis loop and close the window
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
        Musica.dispose();
    }   //Start the game
    //Dibujar suelo
    private void floorDraw() {
        floor.on();
        glBegin(GL11.GL_QUADS);
        glTexCoord2d(0.0f, 0.0f);
        glVertex3f( +5*sizeBilliard,1, +5*sizeBilliard);
        glNormal3f(+5 * sizeBilliard, 1, +5 * sizeBilliard);

        glTexCoord2d(0.0f, 1.0f);
        glVertex3f( +5*sizeBilliard,1, -5*sizeBilliard);
        glNormal3f(+5 * sizeBilliard, 1, -5 * sizeBilliard);

        glTexCoord2d(1.0f, 1.0f);
        glVertex3f(-5 * sizeBilliard, 1, -5 * sizeBilliard);
        glNormal3f(-5 * sizeBilliard, 1, -5 * sizeBilliard);

        glTexCoord2d(1.0f, 0.0f);
        glVertex3f( -5*sizeBilliard,1, +5*sizeBilliard);
        glNormal3f( -5*sizeBilliard,1, +5*sizeBilliard);
        glEnd();
        floor.off();
    }
    // Called to destroy our MainDenis upon exiting
    protected void dispose() {
    }

	//Atributos ventana
	    //Enable vsync
	    public static final boolean VSYNC = true;
	    // Width and height of our window
	    public static final int WIDTH = 800;
	    public static final int HEIGHT = 800;
	    // Whether to use fullscreen mode
	    public static final boolean FULLSCREEN = false;
	    // Whether our MainDenis loop is running
        public enum BillarState {LOADING, READY, RUNNING, FINISHED}
        BillarState state = BillarState.LOADING;

	    //Frames
	    long lastFrame;/** time at last frame */
	    int fps;/** frames per second */
	    long lastFPS;/** last fps time */

        //Cam
        private CamListener camera;

        //Lights
        private ILight light1;

        //Tamaños
        private float sizeBilliard=1500;
        private float sizeSphere=sizeBilliard/40;
        private float largo= (float)sizeBilliard*1.5f-sizeBilliard/13;
        private float ancho=(float)12*sizeBilliard/20+20;
        private float ancho10=sizeBilliard/10;
        private float mini=sizeBilliard/10;

        private float sizeGap=45.0f;

    //Objetos
    	//Billar
	    private Billiard billiard;
	    private TextureGL floor;
	    //Esferas
	    private Ball white, skyBox;
	    private ArrayList<Ball> listaBolas, bolasPerdidas = new ArrayList<Ball>();
        float numSpheres=6;
	    //Planos
	    private ArrayList<BBQuad>listaPlanos;
        //Huecos
        private ArrayList<BBGap> listaHuecos;
        //Variables palo
        BilliardPole palo;
        private Vector direccionDisparo = new Vector(0,0,0);


    //Lista de colisiones
    private CollisionsManager col,col2;
    private CollisionsDetector colDetector;


    public void update(int delta) {
    //Metodos de entrada por teclado
        input(delta);
        //Update palo
        palo.update(delta);
        
        //Movimiento
        white.update(delta);
        camera.getCam(2).setPos(Vector.sum(Vector.prod(Vector.prod(-10, camera.getCam(2).getDireccion()), sizeSphere), listaBolas.get(0).getCenterPoint()));
        camera.getCam(2).moveUp(100);

        //Colisiones
        col.collide(delta);
        col2.collide(delta);



        for(int i=0; i< listaBolas.size();i++){
               listaBolas.get(i).setVel(Vector.prod(0.996f, listaBolas.get(i).getVel()));
                listaBolas.get(i).update(delta);
                if (listaBolas.get(i).getVel().mod()<0.03) {
                    listaBolas.get(i).setVel(0,0,0);}
        }
    }//End update

    public void renderGL() {
        updateFPS(); // update FPS Counter
        //Renderizo la camara
        camera.render();

        //Mirilla
    	light1.off();
//    		//Mirilla
//	        glColor3f(0,1,1);
 //       Dibujo.drawAxes(10000);
//	        Dibujo.drawPoint(camera.getFront(5), 10);
        	palo.render();
        light1.on();
		
        //Billar
        glPushMatrix();
	        glColor4f(0.6f,0.6f,0.6f, 0.5f);
	        glTranslated(sizeBilliard/11.5f,-sizeSphere,0);
	        billiard.render();
        glPopMatrix();
	       
        for (int i=0; i< listaBolas.size(); i++){
            if(listaBolas.get(i).getCenterPoint().y> (-sizeSphere*2))
        	listaBolas.get(i).render();
        }

        for(int i=0;i<bolasPerdidas.size(); i++){
        	bolasPerdidas.get(i).render();
        }

        for(int i=0;i<listaHuecos.size(); i++){
            listaHuecos.get(i).draw();
        }
        for(int i=0;i<listaPlanos.size(); i++){
           // listaPlanos.get(i).draw();
        }
        
        //SkyBox
        glColor3d(1, 1, 1);
        glPushMatrix();
            skyBox.render();
            glTranslatef(0,-1250,0);
            floorDraw();
        glPopMatrix();

        if(white.getVel().x==0 && white.getVel().z ==0 || 1==1){
            glPushMatrix();
            light1.off();
            glBegin(GL_LINES);
            glColor3d(1.0,0,0);
            glVertex3d(inicial.x,inicial.y+sizeSphere/2,inicial.z);
            glVertex3d(terminar.x,terminar.y+sizeSphere/2,terminar.z);
            glEnd();
            glPushMatrix();
            light1.on();
        }
        else{
            inicial=white.getCenterPoint();
            terminar=white.getCenterPoint();
        }

    }


    Vector inicial= new Vector(0,0,0);
    Vector terminar=new Vector(0,0,0);
    public void input(int delta){
    	camera.listen();
        palo.listen(delta);

        if (Keyboard.isKeyDown(Keyboard.KEY_H)) {
            listaBolas.get(0).setVel(0, 0, 0);
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_H)) {
            palo.direccion.print();
        }

            while(Keyboard.next()){
                if (Keyboard.isKeyDown(Keyboard.KEY_G)) {
                direccionDisparo=Vector.prod(-1,palo.direccion);
                    if(colDetector.rayCollider(white.getCenterPoint(),direccionDisparo)!=null){
                        BBSphere a = (BBSphere) colDetector.rayCollider(white.getCenterPoint(), direccionDisparo);
//                        System.out.print("La esfera se encuentra en ");
                        a.getCenterPoint().print();
                        Vector v = colDetector.collide2(white.getBbox(), a, direccionDisparo);
                        float size=1000;
                        Vector aux=Vector.sum(a.getCenterPoint(),Vector.prod(size,Vector.norm(v)));
                        inicial=a.getCenterPoint();
                        terminar=aux;
//                        listaBolas.add(new Ball(aux,a.getMass(),a.getSize()));
                    }
            }
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_R)) {
            listaBolas.get(0).setVel(-0.5f, 0, 0);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_F)) {
            listaBolas.get(0).setVel(-3.0f, 0, 0);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_N)) {
            white.getBbox().setPoint(new Vector(sizeBilliard,0,0));
            white.setVel(0,0,0);
            int k = 0;
            for (int j = 0; j < numSpheres; j++) {
                for (int i = 0; i < j; i++) {
                    k++;
                    listaBolas.get(k).getBbox().setPoint(new Vector(-(2 * sizeSphere) * j - (float) (sizeBilliard / 2.0f), 0, 0 + (i) * 2 * sizeSphere - (sizeSphere) * j + sizeSphere));
                    listaBolas.get(k).getBbox().setVel(new Vector(0,0,0));
                }
            }
        }


    }//End of input

    
	private void CreateObjects() {
		//Music
		try {
			Musica.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
        //Arrays auxiliares
        listaBolas = new ArrayList<Ball>();
        listaPlanos= new ArrayList<BBQuad>();
        listaHuecos = new ArrayList<BBGap>();

		//Camara
        camera= new CamListener(); 
        camera.addCam(new Perspective()); 
        camera.addCam(new Perspective());
	        //Cámara 0 - Cámara Aérea
	        camera.setPos(0, 5800, 0);
	        camera.setAngle(90, 270, 0);
	        //Camara 1 - camara libre
	        camera.getCam(1).setPos(2817, 1866, -1480);
	        camera.getCam(1).setAngle(34,-120, 0);
	        //Camara 2º persona
	        camera.getCam(2).setAngle(0, -90, 0);

        //Luces
        light1 = new DirectionalLight();
        light1.on();
        light1.setLight_position(new float[]{ 20,20,-1,0});

        //Objetos
        billiard = new Billiard(sizeBilliard);
        floor = TextureGL.loadTexture("res/images/wood.jpg");

        skyBox = new Ball(new Vector (0,0,0), 20,sizeSphere*200, "/res/images/sky1.jpg");

        white = new Ball(new Vector (sizeBilliard,0,0), 5,sizeSphere);
        listaBolas.add(white);
        int k=0;
	        for (int j=0; j<numSpheres; j++){
	        	for (int i=0; i<j; i++){
	        		k++;
	        		listaBolas.add(new Ball(new Vector(-(2 * sizeSphere) * j - (float) (sizeBilliard / 2.0f), 0, 00+(i) * 2 * sizeSphere - (sizeSphere) * j + sizeSphere), 5, sizeSphere, "/res/images/pallina" + k + ".jpg"));

	        	}
	        }

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


        //Medio
//        listaHuecos.add(new BBGap(new Vector(0,-sizeSphere/2,-1150),1,sizeGap));
//        listaHuecos.add(new BBGap(new Vector(0,-sizeSphere/2,+1150),1,sizeGap));
//        //Derecha
//        listaHuecos.add(new BBGap(new Vector(2190,-sizeSphere/2,-1100),1,sizeGap));
//        listaHuecos.add(new BBGap(new Vector(2190,-sizeSphere,1100),1,sizeGap));
//        //Izquierda
//        listaHuecos.add(new BBGap(new Vector(-2190,-sizeSphere/2,-1100),1,sizeGap));
//        listaHuecos.add(new BBGap(new Vector(-2190,-sizeSphere/2,1100),1,sizeGap));

        //Palo
        palo = new BilliardPole(listaBolas.get(0));

        //Colisiones
        col=new CollisionsManager();
        col2=new CollisionsManager();
        colDetector=new CollisionsDetector();

	        for(int i=0; i< listaBolas.size();i++){
	            col.add(listaBolas.get(i).getBbox());
                col2.add(listaBolas.get(i).getBbox());
                colDetector.add(listaBolas.get(i).getBbox());
            }

	        for(int i=0; i<listaPlanos.size();i++){
	            col.add(listaPlanos.get(i));
	        }

            for(int i=0; i< listaHuecos.size();i++){
                col2.add(listaHuecos.get(i));
            }
	}
}//Fin create objects

