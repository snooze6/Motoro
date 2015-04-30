package Camera;

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

import org.lwjgl.opengl.Display;
import org.lwjgl.util.glu.GLU;

public class Perspective implements ICam {
	private float cam_x, cam_y, cam_z;                //Posición de la cámara
	private float cam_ang_x, cam_ang_y, cam_ang_z;    //Ángulos de la cámara
	private int angvision;                          //Ángulo de visión (Zoom)
	private int W_WIDTH, W_HEIGHT;
	
	//--------------------------------------------------------------------------
	
	public Perspective() {
		super();
		cam_x=0;
		cam_y=0;
		cam_z=0;
		cam_ang_x=0;
		cam_ang_y=0;
		cam_ang_z=0;
		angvision=45;
	}
	public Perspective(float cam_x, float cam_y, float cam_z,
			float cam_ang_x, float cam_ang_y, float cam_ang_z) {
		super();
		this.cam_x = cam_x;
		this.cam_y = cam_y;
		this.cam_z = cam_z;
		this.cam_ang_x = cam_ang_x;
		this.cam_ang_y = cam_ang_y;
		this.cam_ang_z = cam_ang_z;
		this.angvision = 45;
	}
	public Perspective(float cam_x, float cam_y, float cam_z,
			float cam_ang_x, float cam_ang_y, float cam_ang_z,
			int angvision) {
		super();
		this.cam_x = cam_x;
		this.cam_y = cam_y;
		this.cam_z = cam_z;
		this.cam_ang_x = cam_ang_x;
		this.cam_ang_y = cam_ang_y;
		this.cam_ang_z = cam_ang_z;
		this.angvision = angvision;
	}

	//--------------------------------------------------------------------------
	
	@Override
	public void setPosition(float x, float y, float z) {
		cam_x = x; cam_y = y; cam_z = z;
	}
	@Override
	public void setAngle(float angx, float angy, float angz) {
		cam_ang_x = angx; cam_ang_y = angy; cam_ang_z = angz;
		
	}
	@Override
	public void setZoom(float zoom) {
		if (!(angvision + zoom < 35 || angvision + zoom > 100)){
			angvision+=zoom;
		}
	}
	
	public void setWindow(int i, int j){
		W_WIDTH=i;
		W_HEIGHT=j;
	}
	
	//--------------------------------------------------------------------------
	
	@Override
	public void render() {
//        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // limpias los buffer
//        glEnable(GL_DEPTH_TEST);
//        //glEnable(GL_CULL_FACE);
//		
//		glMatrixMode (GL_PROJECTION);
//		glLoadIdentity();
//		GLU.gluPerspective(angvision,(float)W_WIDTH/(float)W_HEIGHT ,1,2000);
//			glRotatef(cam_ang_x, 1.0f, 0.0f, 0.0f);
//			glRotatef(cam_ang_y, 0.0f, 1.0f, 0.0f);
//			glTranslatef(-cam_x, -cam_y, -cam_z);
//		glMatrixMode (GL_MODELVIEW);
//		glViewport(0,0,W_WIDTH,W_HEIGHT);
//		
//		glLoadIdentity(); // Inicializamos la matriz del modelo a la identidad
		
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // limpias los buffer
        glEnable(GL_DEPTH_TEST);
        //glEnable(GL_CULL_FACE);

        glMatrixMode(GL_PROJECTION); //La camara
        glLoadIdentity(); // Inicializamos la matriz del modelo a la identidad propiedades de la camara
        //Ortho camss
        //glOrtho(-10*scale, 10*scale, -10, 10, -10, 10);

        //perspective cam
        GLU.gluPerspective(45,(float)Display.getWidth()/(float)Display.getHeight(),1,2000);

        glRotated(cam_ang_x, 1.0, 0.0, 0.0);
        glRotated(cam_ang_y, 0.0, 1.0, 0.0);
        glTranslated(-cam_x,-cam_y,-cam_z);
        //GLU.gluLookAt(xTranslate ,yTranslate,zTranslate,xLookAt ,yLookAt,zTranslate-10,0,1,0);

        glMatrixMode(GL_MODELVIEW); // Activamos la matriz del modelo
        glLoadIdentity(); // Inicializamos la matriz del modelo a la identidad
		
	}
	
	//--------------------------------------------------------------------------
	
	@Override
	public void moveUp(float v) {
		cam_y=cam_y+5;
	}
	@Override
	public void moveDown(float v) {
		cam_y=cam_y-5;
	}
	@Override
	public void moveStraight(float v) {
        cam_x+=Math.sin(Math.toRadians(cam_ang_y))*v;
        cam_z-=Math.cos(Math.toRadians(cam_ang_y))*v;
        cam_y-=Math.sin(Math.toRadians(cam_ang_x))*v;		
	}
	@Override
	public void moveBack(float v) {
        cam_x-=Math.sin(Math.toRadians(cam_ang_y))*v;
        cam_z+=Math.cos(Math.toRadians(cam_ang_y))*v;
        cam_y+=Math.sin(Math.toRadians(cam_ang_x))*v;		
	}
	@Override
	public void moveRight(float v) {
        cam_x+=Math.cos(Math.toRadians(cam_ang_y))*v;
        cam_z+=Math.sin(Math.toRadians(cam_ang_y))*v;
	}
	@Override
	public void moveLeft(float v) {
        cam_x-=Math.cos(Math.toRadians(cam_ang_y))*v;
        cam_z-=Math.sin(Math.toRadians(cam_ang_y))*v;	
	}
	
	//--------------------------------------------------------------------------
	
	private float updateang(float ang){
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
	@Override
	public void lookUp(float ang) {
		cam_ang_x=updateang(cam_ang_x-ang);
	}
	@Override
	public void lookDown(float ang) {
		cam_ang_x=updateang(cam_ang_x+ang);
	}
	@Override
	public void lookRight(float ang) {
		cam_ang_y=updateang(cam_ang_y+ang);
	}
	@Override
	public void lookLeft(float ang) {
		cam_ang_y=updateang(cam_ang_y-ang);
	}
		
	//--------------------------------------------------------------------------
	
	

	

}
