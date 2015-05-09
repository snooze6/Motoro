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

import Collision.Objects.Vector;

public class Ortho implements ICam {

	private Vector pos, ang;
	private int W_WIDTH, W_HEIGHT;
	private float tam, scale;
	
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
		ang = new Vector(cam_ang_x,cam_ang_y,cam_ang_y);
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
		ang = new Vector(cam_ang_x,cam_ang_y,cam_ang_y);
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
		ang = new Vector(cam_ang_x,cam_ang_y,cam_ang_y);
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
	
	private void setScale() {
		if (W_HEIGHT>0){
			scale = (float)W_WIDTH/W_HEIGHT;
		} else {
			scale = 1;
		}
	}
	
	//--------------------------------------------------------------------------

	@Override
	public void setPosition(float x, float y, float z) {
		pos = new Vector(x,y,z);
	}

	@Override
	public void setPosition(Vector v) {
		pos = new Vector(v);
	}

	@Override
	public void setAngle(float angx, float angy, float angz) {
		ang = new Vector(angx, angy, angz);
	}

	@Override
	public void setAngle(Vector v) {
		ang = new Vector(v);
	}

	@Override
	public void setWindow(int i, int j) {
		W_WIDTH=i; W_HEIGHT=j;
	}
	
	//--------------------------------------------------------------------------

	@Override
	public void render() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // limpias los buffer
        glEnable(GL_DEPTH_TEST);

        glMatrixMode(GL_PROJECTION); //La camara
	        glLoadIdentity(); // Inicializamos la matriz de la cámara a la identidad
	        glOrtho(-tam*scale, tam*scale, -tam, tam, -(2000),(2000));
	        glRotated(ang.x, 1.0, 0.0, 0.0);
	        glRotated(ang.y, 0.0, 1.0, 0.0);
	        glTranslated(-pos.x,-pos.y,-pos.z);

        glMatrixMode(GL_MODELVIEW); // Activamos la matriz del modelo
        	glLoadIdentity(); // Inicializamos la matriz del modelo a la identidad
	}
	
	//--------------------------------------------------------------------------

//	@Override
//	public void moveUp(float v) {
//		pos.y+=+v;
//	}
//
//	@Override
//	public void moveDown(float v) {
//		pos.y-=v;
//	}
//
//	public void moveStraight(float v) {
//        pos.x+=Math.sin(Math.toRadians(ang.y))*v;
//        pos.z-=Math.cos(Math.toRadians(ang.y))*v;
//        pos.y-=Math.sin(Math.toRadians(ang.x))*v;		
//	}
//	
//	public void moveBack(float v) {
//        pos.x-=Math.sin(Math.toRadians(ang.y))*v;
//        pos.z+=Math.cos(Math.toRadians(ang.y))*v;
//        pos.y+=Math.sin(Math.toRadians(ang.x))*v;		
//	}
//
//	@Override
//	public void moveRight(float v) {
//        pos.x+=Math.cos(Math.toRadians(ang.y))*v;
//        pos.z+=Math.sin(Math.toRadians(ang.y))*v;
//	}
//	
//	@Override
//	public void moveLeft(float v) {
//        pos.x-=Math.cos(Math.toRadians(ang.y))*v;
//        pos.z-=Math.sin(Math.toRadians(ang.y))*v;	
//	}
	
	public void moveUp(float v) {
		pos.y=pos.y+v;
	}
	
	public void moveDown(float v) {
		pos.y=pos.y-v;
	}
	
	public void moveStraight(float v) {
		pos.z=pos.z+v;
	}
	
	public void moveBack(float v) {
		pos.z=pos.z-v;
	}
	
	public void moveRight(float v) {
		pos.x=pos.x+v;
	}
	
	public void moveLeft(float v) {
		pos.x=pos.x-v;
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
	public void lookUp(float v) {
		ang.x=updateang(ang.x-v);
	}
	
	@Override
	public void lookDown(float v) {
		ang.x=updateang(ang.x+v);
	}
	
	@Override
	public void lookRight(float v) {
		ang.y=updateang(ang.y+v);
	}
	
	@Override
	public void lookLeft(float v) {
		ang.y=updateang(ang.y-v);
	}
	
	@Override
	public Vector getDireccion() {
		return new Vector(
				(float)  (Math.sin(Math.toRadians(ang.y))*Math.cos(Math.toRadians(ang.x))),
        		(float)  -Math.sin(Math.toRadians(ang.x)),
        		(float) -(Math.cos(Math.toRadians(ang.y))*Math.cos(Math.toRadians(ang.x)))
        		);
	}

	@Override
	public void setDireccion(float x, float y, float z) {
		ang.x = (float) Math.toDegrees(Math.asin(-y));
		ang.y = (float) Math.toDegrees(Math.asin(x/Math.cos(Math.toRadians(ang.x))));
		ang.z = 0;
	}

	@Override
	public void setDireccion(Vector v) {
		ang.x = (float) Math.toDegrees(Math.asin(-v.y));
		ang.y = (float) Math.toDegrees(Math.asin(v.x/Math.cos(Math.toRadians(ang.x))));
		ang.z = 0;
		
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
	
	//--------------------------------------------------------------------------

	@Override
	public float getX() {
		return pos.x;
	}

	@Override
	public float getY() {
		return pos.y;
	}

	@Override
	public float getZ() {
		return pos.z;
	}

	@Override
	public float getAngX() {
		return ang.x;
	}

	@Override
	public float getAngY() {
		return ang.y;
	}

	@Override
	public float getAngZ() {
		return ang.z;
	}

}
