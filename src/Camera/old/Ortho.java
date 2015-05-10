package Camera.old;

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
import static org.lwjgl.opengl.GL11.glOrtho;
import Collision.Objects.Vector;

public class Ortho {

	private float cam_x, cam_y, cam_z;                //Posición de la cámara
	private float cam_ang_x, cam_ang_y, cam_ang_z;    //Ángulos de la cámara                //Ángulo de visión (Zoom)
	private int W_WIDTH, W_HEIGHT;
	private float tam, scale;
	
	//--------------------------------------------------------------------------
	
	public Ortho() {
		super();
		cam_x=0;
		cam_y=0;
		cam_z=0;
		cam_ang_x=0;
		cam_ang_y=0;
		cam_ang_z=0;
		tam = 100;
		
	}
	public Ortho(float cam_x, float cam_y, float cam_z, float cam_ang_x,
			float cam_ang_y, float cam_ang_z) {
		super();
		this.cam_x = cam_x;
		this.cam_y = cam_y;
		this.cam_z = cam_z;
		this.cam_ang_x = cam_ang_x;
		this.cam_ang_y = cam_ang_y;
		this.cam_ang_z = cam_ang_z;
	}
	public Ortho(float cam_x, float cam_y, float cam_z, float cam_ang_x,
			float cam_ang_y, float cam_ang_z, int w_WIDTH, int w_HEIGHT) {
		super();
		this.cam_x = cam_x;
		this.cam_y = cam_y;
		this.cam_z = cam_z;
		this.cam_ang_x = cam_ang_x;
		this.cam_ang_y = cam_ang_y;
		this.cam_ang_z = cam_ang_z;
		W_WIDTH = w_WIDTH;
		W_HEIGHT = w_HEIGHT;
		setScale();
	}
	
	//--------------------------------------------------------------------------
	
	
	public void setPosition(float x, float y, float z) {
		cam_x = x; cam_y = y; cam_z = z;
	}
	
	public void setAngle(float angx, float angy, float angz) {
		cam_ang_x=angx; cam_ang_y=angy; cam_ang_z=angz;
	}
	
	public void setWindow(int i, int j) {
		if ((i>0)&&(j>0)){
			W_WIDTH = i;
			W_HEIGHT = j;
			setScale();
		}
	}
	private void setScale() {
		if (W_HEIGHT>0){
			scale = (float)W_WIDTH/W_HEIGHT;
		} else {
			scale = 1;
		}
		//System.out.println("Scale: "+scale);
	}

	//--------------------------------------------------------------------------
	
	
	public void render() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // limpias los buffer
        glEnable(GL_DEPTH_TEST);
        //glEnable(GL_CULL_FACE);

        glMatrixMode(GL_PROJECTION); //La camara
        glLoadIdentity(); // Inicializamos la matriz de la cámara a la identidad
        
        glOrtho(-tam*scale, tam*scale, -tam, tam, -(2000),(2000));

        glRotated(cam_ang_x, 1.0, 0.0, 0.0);
        glRotated(cam_ang_y, 0.0, 1.0, 0.0);
        glTranslated(-cam_x,-cam_y,-cam_z);

        glMatrixMode(GL_MODELVIEW); // Activamos la matriz del modelo
        glLoadIdentity(); // Inicializamos la matriz del modelo a la identidad
	}
	
	//--------------------------------------------------------------------------

	
	public void moveUp(float v) {
		cam_y=cam_y+v;
	}
	
	public void moveDown(float v) {
		cam_y=cam_y-v;
	}
	
	public void moveStraight(float v) {
		cam_z=cam_z+v;
	}
	
	public void moveBack(float v) {
		cam_z=cam_z-v;
	}
	
	public void moveRight(float v) {
		cam_x=cam_x+v;
	}
	
	public void moveLeft(float v) {
		cam_x=cam_x-v;
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
	
	public void lookUp(float ang) {
		cam_ang_x=updateang(cam_ang_x-ang);
	}
	
	public void lookDown(float ang) {
		cam_ang_x=updateang(cam_ang_x+ang);
	}
	
	public void lookRight(float ang) {
		cam_ang_y=updateang(cam_ang_y+ang);
	}
	
	public void lookLeft(float ang) {
		cam_ang_y=updateang(cam_ang_y-ang);
	}
	
	//--------------------------------------------------------------------------
	
	
	public void morezoom() {
		if (tam-5>0){
			tam=tam-5;
		}
	}
	
	public void lesszoom() {
		if (tam+5>0){
			tam=tam+5;
		}
	}

	//--------------------------------------------------------------------------
	
	public float getCam_x() {
		return cam_x;
	}
	public float getCam_y() {
		return cam_y;
	}
	public float getCam_z() {
		return cam_z;
	}
	public float getCam_ang_x() {
		return cam_ang_x;
	}
	public float getCam_ang_y() {
		return cam_ang_y;
	}
	public float getCam_ang_z() {
		return cam_ang_z;
	}
	
	//--------------------------------------------------------------------------
	
	
	public float[] getDireccion() {
        float[] aux; aux = new float[3];
        aux[0] = (float) (Math.sin(Math.toRadians(cam_ang_y))*Math.cos(Math.toRadians(cam_ang_x)));
        aux[2] = (float) -(Math.cos(Math.toRadians(cam_ang_y))*Math.cos(Math.toRadians(cam_ang_x)));
        aux[1] = (float)  -Math.sin(Math.toRadians(cam_ang_x));
        
		return aux;
	}
	
	public void setDireccion(float x, float y, float z) {
		// TODO Auto-generated method stub
		
	}
	
	public void setDireccion(Vector v) {
		// TODO Auto-generated method stub
		
	}
}
