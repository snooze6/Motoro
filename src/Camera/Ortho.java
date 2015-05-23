package Camera;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glRotated;
import static org.lwjgl.opengl.GL11.glTranslated;
import Utilities.Vector;

public class Ortho extends Cam{
	
	protected float tam, scale;
	
	//--------------------------------------------------------------------------
	
	public Ortho() {
		super();
		pos = new Vector(0,0,0);
		ang = new Vector(0,0,0);
		tam = 100; W_WIDTH=800; W_HEIGHT=400;
		setScale();
	}
	
	public Ortho(float cam_x, float cam_y, float cam_z,
			float cam_ang_x, float cam_ang_y, float cam_ang_z) {
		super();
		pos = new Vector(cam_x,cam_y,cam_z);
		ang = new Vector(cam_ang_x,cam_ang_y,cam_ang_z);
		tam = 100; W_WIDTH=800; W_HEIGHT=400;
		setScale();
	}
	
	public Ortho(Vector p, Vector a) {
		super();
		pos = new Vector(p);
		ang = new Vector(a);
		tam = 100; W_WIDTH=800; W_HEIGHT=400;
		setScale();
	}
	
	public Ortho(float cam_x, float cam_y, float cam_z,
			float cam_ang_x, float cam_ang_y, float cam_ang_z,
			int ulo) {
		super();
		pos = new Vector(cam_x,cam_y,cam_z);
		ang = new Vector(cam_ang_x,cam_ang_y,cam_ang_z);
		tam = ulo; W_WIDTH=800; W_HEIGHT=400;
		setScale();
	}
	
	public Ortho(Vector p, Vector a, int ulo) {
		super();
		pos = new Vector(p);
		ang = new Vector(a);
		tam = ulo; W_WIDTH=800; W_HEIGHT=400;
		setScale();
	}
	
	public Ortho(float cam_x, float cam_y, float cam_z,
			float cam_ang_x, float cam_ang_y, float cam_ang_z,
			int ulo, int w, int h) {
		super();
		pos = new Vector(cam_x,cam_y,cam_z);
		ang = new Vector(cam_ang_x,cam_ang_y,cam_ang_z);
		tam = ulo; W_WIDTH=w; W_HEIGHT=h;
		setScale();
	}
	
	public Ortho(Vector p, Vector a, int ulo, int w, int h) {
		super();
		pos = new Vector(p);
		ang = new Vector(a);
		tam = ulo; W_WIDTH=w; W_HEIGHT=h;
		setScale();
	}
	
	protected void setScale() {
		if (W_HEIGHT>0){
			scale = (float)W_WIDTH/W_HEIGHT;
		} else {
			scale = 1;
		}
	}
	
	//--------------------------------------------------------------------------

	@Override
	public void setWindow(int i, int j) {
		W_WIDTH=i; W_HEIGHT=j;
		setScale();
	}
	
	//--------------------------------------------------------------------------

	@Override
	public void render() {
		
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // limpias los buffer
        glEnable(GL_DEPTH_TEST);

        glMatrixMode(GL_PROJECTION); //La camara
	        glLoadIdentity(); // Inicializamos la matriz de la cámara a la identidad
	        glOrtho(-tam*scale, tam*scale, -tam, tam, -(5000),(5000));
	        glRotated(ang.x, 1.0, 0.0, 0.0);
	        glRotated(ang.y, 0.0, 1.0, 0.0);
	        glTranslated(-pos.x,-pos.y,-pos.z);

        glMatrixMode(GL_MODELVIEW); // Activamos la matriz del modelo
        	glLoadIdentity(); // Inicializamos la matriz del modelo a la identidad
	}
	
	//--------------------------------------------------------------------------
	
	@Override
	public void moveUp(float v) {
		pos.y=pos.y+v;
	}
	@Override
	public void moveDown(float v) {
		pos.y=pos.y-v;
	}
	@Override
	public void moveStraight(float v) {
		pos.z=pos.z+v;
	}
	@Override
	public void moveBack(float v) {
		pos.z=pos.z-v;
	}
	@Override
	public void moveRight(float v) {
		pos.x=pos.x+v;
	}
	@Override
	public void moveLeft(float v) {
		pos.x=pos.x-v;
	}
	
	//--------------------------------------------------------------------------
	
	@Override
	public void morezoom() {
		if (tam-5>0){
			tam=tam-5;
		}
	}
	
	@Override
	public void lesszoom() {
		if (tam+5>0){
			tam=tam+5;
		}
	}

}
