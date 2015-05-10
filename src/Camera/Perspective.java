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
import static org.lwjgl.opengl.GL11.glRotated;
import static org.lwjgl.opengl.GL11.glTranslated;

import org.lwjgl.util.glu.GLU;

import Collision.Objects.Vector;

public class Perspective implements ICam{
	
	private Vector pos, ang;
	private int angvision, W_WIDTH, W_HEIGHT;
	
	//--------------------------------------------------------------------------
	
	public Perspective() {
		super();
		pos = new Vector(0,0,0);
		ang = new Vector(0,0,0);
		angvision=45; W_WIDTH=800; W_HEIGHT=400;
	}
	
	public Perspective(float cam_x, float cam_y, float cam_z,
			float cam_ang_x, float cam_ang_y, float cam_ang_z) {
		super();
		pos = new Vector(cam_x,cam_y,cam_z);
		ang = new Vector(cam_ang_x,cam_ang_y,cam_ang_y);
		angvision=45; W_WIDTH=800; W_HEIGHT=400;
	}
	
	public Perspective(Vector p, Vector a) {
		super();
		pos = new Vector(p);
		ang = new Vector(a);
		angvision=45; W_WIDTH=800; W_HEIGHT=400;
	}
	
	public Perspective(float cam_x, float cam_y, float cam_z,
			float cam_ang_x, float cam_ang_y, float cam_ang_z,
			int ulo) {
		super();
		pos = new Vector(cam_x,cam_y,cam_z);
		ang = new Vector(cam_ang_x,cam_ang_y,cam_ang_y);
		angvision=ulo; W_WIDTH=800; W_HEIGHT=400;
	}
	
	public Perspective(Vector p, Vector a, int ulo) {
		super();
		pos = new Vector(p);
		ang = new Vector(a);
		angvision=ulo; W_WIDTH=800; W_HEIGHT=400;
	}
	
	public Perspective(float cam_x, float cam_y, float cam_z,
			float cam_ang_x, float cam_ang_y, float cam_ang_z,
			int ulo, int w, int h) {
		super();
		pos = new Vector(cam_x,cam_y,cam_z);
		ang = new Vector(cam_ang_x,cam_ang_y,cam_ang_y);
		angvision=ulo; W_WIDTH=w; W_HEIGHT=h;
	}
	
	public Perspective(Vector p, Vector a, int ulo, int w, int h) {
		super();
		pos = new Vector(p);
		ang = new Vector(a);
		angvision=ulo; W_WIDTH=w; W_HEIGHT=h;
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
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glEnable(GL_DEPTH_TEST);

        glMatrixMode(GL_PROJECTION); //La camara
	        glLoadIdentity(); // Inicializamos la matriz de la cámara a la identidad
	        GLU.gluPerspective(angvision,(float)W_WIDTH/(float)W_HEIGHT,1,2000);
	        glRotated(ang.x, 1.0, 0.0, 0.0);
	        glRotated(ang.y, 0.0, 1.0, 0.0);
	        glTranslated(-pos.x,-pos.y,-pos.z);

        glMatrixMode(GL_MODELVIEW); // Activamos la matriz del modelo
        	glLoadIdentity(); // Inicializamos la matriz del modelo a la identidad
	}
	
	//--------------------------------------------------------------------------

	@Override
	public void moveUp(float v) {
		pos.y+=+v;
	}

	@Override
	public void moveDown(float v) {
		pos.y-=v;
	}

	public void moveStraight(float v) {
        pos.x+=Math.sin(Math.toRadians(ang.y))*v;
        pos.z-=Math.cos(Math.toRadians(ang.y))*v;
        pos.y-=Math.sin(Math.toRadians(ang.x))*v;		
	}
	
	public void moveBack(float v) {
        pos.x-=Math.sin(Math.toRadians(ang.y))*v;
        pos.z+=Math.cos(Math.toRadians(ang.y))*v;
        pos.y+=Math.sin(Math.toRadians(ang.x))*v;		
	}

	@Override
	public void moveRight(float v) {
        pos.x+=Math.cos(Math.toRadians(ang.y))*v;
        pos.z+=Math.sin(Math.toRadians(ang.y))*v;
	}
	
	@Override
	public void moveLeft(float v) {
        pos.x-=Math.cos(Math.toRadians(ang.y))*v;
        pos.z-=Math.sin(Math.toRadians(ang.y))*v;	
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

	private void setZoom(float zoom) {
		if (!(angvision + zoom < 35 || angvision + zoom > 100)){
			angvision+=zoom;
		}
	}
	
	@Override
	public void morezoom() {
		setZoom(1);
	}
	
	@Override
	public void lesszoom() {
		setZoom(-1);
		
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
