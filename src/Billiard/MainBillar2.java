package Billiard;


import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3d;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glNormal3f;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTexCoord2d;
import static org.lwjgl.opengl.GL11.glTranslated;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex3f;
import static org.lwjgl.opengl.GL11.glViewport;

import java.io.IOException;
import java.util.ArrayList;

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
import Camera.Isometric;
import Camera.Perspective;
import Collision.*;
import Lights.DirectionalLight;
import Lights.ILight;
import Utilities.Dibujo;
import Utilities.TextureGL;
import Utilities.Vector;

public class MainBillar2{
	
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
    //Tamaños
    private float sizeBilliard=1500;
    private float sizeSphere=sizeBilliard/40;
    private float largo= (float)sizeBilliard*1.5f-sizeBilliard/13;
    private float ancho=(float)12*sizeBilliard/20+20;
    private float ancho10=sizeBilliard/10;
    private float mini=sizeBilliard/10;


    private float sizeGap=60.0f;


    //Objetos
    	//Billar
	    private Billiard billiard;
	    private TextureGL floor;
	    //Esferas
	    private Ball white, skyBox;
	    private ArrayList<Ball> bolas, bolasPerdidas = new ArrayList<Ball>();
	    //Planos
	    private ArrayList<BBQuad>listaPlanos;
    
    //Lista de colisiones
    private CollisionsManager col,col2;

    //Huecos
    private ArrayList<BBGap> listaHuecos;



    //Variables palo
    BilliardPole palo;
    
    //Variable chorras
    private float ang;
    private float strong;
    
    protected void updateStrong(float d){
    	if (strong+d<6.0f){
    		strong+=d;
    	}
    }
    
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
        Musica.dispose();
    }

    public void update(int delta) {
    //Metodos de entrada por teclado
        input(delta);
        
        palo.update(delta);
        
        //Movimiento
        white.update(delta);
        //Colisiones
        col.collide(delta);
        col2.collide(delta);

        for(int i=0; i< bolas.size();i++){
            bolas.get(i).setVel(Vector.prod(0.996f,bolas.get(i).getVel()));
            if (bolas.get(i).getVel().mod()<0.03) {bolas.get(i).setVel(0,0,0);}
            bolas.get(i).update(delta);
            
            if (i==0){
                camera.getCam(2).setPos(Vector.sum(Vector.prod(Vector.prod(-10, camera.getCam(2).getDireccion()), sizeSphere), bolas.get(i).getPoint()));
                camera.getCam(2).moveUp(100);
            }
            
            if (isOut(bolas.get(i))) {
            	if (i==0){
            		System.out.println("White out!");
            		bolas.get(i).getBbox().setPoint(new Vector (sizeBilliard,0,0));
            		bolas.get(i).setVel(new Vector (0,0,0));
            		
            		if (!Musica.bad.isPlaying())
            		  Musica.bad.playAsMusic(1.0f, 1.0f, false);
            	} else {
            		System.out.println("Una menos");
            		bolasRestantes--;
            		bolas.get(i).getBbox().setPoint(new Vector (sizeBilliard*2+300,200,sizeBilliard - (14-bolasRestantes)*sizeSphere));
            		bolas.get(i).setVel(new Vector (0,0,0));
            		bolasPerdidas.add(bolas.get(i));
            		bolas.remove(i);
            		
            		if (!Musica.good.isPlaying())
            		  Musica.good.playAsMusic(1.0f, 1.0f, false);
            	}
            }	
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
//    		//Mirilla
//	        glColor3f(0,1,1);
//	        Dibujo.drawPoint(camera.getFront(5), 10);
        	palo.render();
        light1.on();
		
        //Billar
        glPushMatrix();
	        glColor4f(0.6f,0.6f,0.6f, 0.5f);
	        glTranslated(sizeBilliard/11.5f,-sizeSphere,0);
	        billiard.render();
        glPopMatrix();
	       
        for (int i=0; i<bolas.size(); i++){
            if(bolas.get(i).getPoint().y> (-sizeSphere*2))
        	bolas.get(i).render();
        }

        for(int i=0;i<bolasPerdidas.size(); i++){
        	bolasPerdidas.get(i).render();
        }

        for(int i=0;i<listaHuecos.size(); i++){
            listaHuecos.get(i).draw();
        }

        ang++; if (ang>360){ang=0;}
        
        //SkyBox
        glColor3d(1,1,1);
        glPushMatrix();
              //glRotatef(ang, 1, 0, 0);
              skyBox.render();
              
              glTranslatef(0,-1250,0);
              	floor.on();
			        glBegin(GL11.GL_QUADS);
			        	glTexCoord2d(0.0f, 0.0f);
			        	glVertex3f( +5*sizeBilliard,1, +5*sizeBilliard);
			        	glNormal3f( +5*sizeBilliard,1, +5*sizeBilliard);
			        	
			        	glTexCoord2d(0.0f, 1.0f);
			        	glVertex3f( +5*sizeBilliard,1, -5*sizeBilliard);
			        	glNormal3f( +5*sizeBilliard,1, -5*sizeBilliard);
			        	
			        	glTexCoord2d(1.0f, 1.0f);
			        	glVertex3f( -5*sizeBilliard,1, -5*sizeBilliard);
			        	glNormal3f( -5*sizeBilliard,1, -5*sizeBilliard);
			        	
			        	glTexCoord2d(1.0f, 0.0f);
			        	glVertex3f( -5*sizeBilliard,1, +5*sizeBilliard);
			        	glNormal3f( -5*sizeBilliard,1, +5*sizeBilliard);
		        	glEnd();
	        	floor.off();
        glPopMatrix();
    }


    //Init opengl
    public void initGL() {               

    }
    // Called to destroy our MainDenis upon exiting
    protected void dispose() {
    }

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
    
    public void input(int delta){
    	
    	if (camera.getSelected()!=0){
    		camera.listen();
    	} else {
    		if (Keyboard.isKeyDown(Keyboard.KEY_T)) {
                while(Keyboard.next()) {
                    camera.prevCam();
                }
            }
    	}
    	
        palo.listen();

		if (Keyboard.isKeyDown(Keyboard.KEY_I)) {
			palo.girar(2);;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_K)) {
		    palo.girar(-2);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_H)) {
			bolas.get(0).setVel(0, 0, 0);
		}

            
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			updateStrong((float)delta/500);
			palo.updateColor(strong);
//			while(Keyboard.next()) {
//				palo.disparar(2);
//			}
		} else {
			if (strong>0){
				palo.disparar(strong);
				strong = 0;
				palo.updateColor(10);
			}
		}
		
    }
    
    public static void main(String[] argv) {
        MainBillar2 BILL = new MainBillar2();
        BILL.start();
    }
    
	private void CreateObjects() {
		
		try {
			Musica.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		//Camara
        camera= new CamListener(); 
        camera.addCam(new Perspective()); 
        camera.addCam(new Perspective()); 
        
	        //Cámara 0 - Cámara Aérea
	        camera.setPos(0,5800,0);
	        camera.setAngle(90, 270, 0);
	        
	        //Camara 1 - Cámara Isométrica
	        camera.getCam(1).setPos(sizeBilliard*3, sizeBilliard*2, sizeBilliard*2);
	        camera.getCam(1).setAngle(35, -52, 0);
	        
	        camera.getCam(2).setAngle(0, -90, 0);

        //Luces
        light1 = new DirectionalLight();
        light1.on();
        light1.setLight_position(new float[]{ 20,20,-1,0});

        //Arrays auxiliares

        //Objetos
        billiard = new Billiard(sizeBilliard);
        floor = TextureGL.loadTexture("res/images/wood.jpg");
        white = new Ball(new Vector (sizeBilliard,0,0), 20,sizeSphere,"/res/images/pallina"+13+".jpg");
        skyBox = new Ball(new Vector (0,0,0), 20,sizeSphere*200, "/res/images/sky1.jpg");
        
        bolas = new ArrayList<Ball>();
        bolas.add(white);
        //bolas.add(new Ball());
        int k=0, l=0;
	        for (int j=0; j<6; j++){
	        	for (int i=0; i<j; i++){
	        		k++; bolasRestantes++;
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


        //Huecos
        listaHuecos = new ArrayList<BBGap>();
        //Medio
        System.out.println(sizeSphere*1.5f);
        listaHuecos.add(new BBGap(new Vector(0,-sizeSphere,-1150),1,sizeGap));
        listaHuecos.add(new BBGap(new Vector(0,-sizeSphere,+1150),1,sizeGap));
        //Derecha
        listaHuecos.add(new BBGap(new Vector(2190,-sizeSphere,-1100),1,sizeGap));
        listaHuecos.add(new BBGap(new Vector(2190,-sizeSphere,1100),1,sizeGap));
        //Izquierda
        listaHuecos.add(new BBGap(new Vector(-2190,-sizeSphere,-1100),1,sizeGap));
        listaHuecos.add(new BBGap(new Vector(-2190,-sizeSphere,1100),1,sizeGap));



        //Colisiones
        col=new CollisionsManager();
        col2=new CollisionsManager();
	        for(int i=0; i< bolas.size();i++){
	            col.add(bolas.get(i).getBbox());
	        }
	        for(int i=0; i<listaPlanos.size();i++){
	            col.add(listaPlanos.get(i));
                col2.add(listaPlanos.get(i));
	        }

	        
	    //Palo
	        palo = new BilliardPole(bolas.get(0));
	}

	protected boolean isOut(Ball b){
		Vector pos = b.getPoint();
		return ((pos.x>2*sizeBilliard+200||pos.x<-2*sizeBilliard+200)||(pos.y>200||pos.y<-200)||(pos.z>sizeBilliard+200||pos.z<-sizeBilliard+200));
	}
	
	protected double updateang(double ang){
		if (ang>360){
			return ang-360;
		} else {
			if (ang<-360) {
				return ang+360;
			} else {
				return ang;
			}
		}
	}
}

