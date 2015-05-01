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
import static org.lwjgl.opengl.GL11.glOrtho;

public class Ortho implements ICam{

	private float cam_x, cam_y, cam_z;                //Posición de la cámara
	private float cam_ang_x, cam_ang_y, cam_ang_z;    //Ángulos de la cámara                //Ángulo de visión (Zoom)
	private int W_WIDTH, W_HEIGHT;
	
	//--------------------------------------------------------------------------
	
	public Ortho() {
		super();
		cam_x=0;
		cam_y=0;
		cam_z=0;
		cam_ang_x=0;
		cam_ang_y=0;
		cam_ang_z=0;
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
	}
	
	//--------------------------------------------------------------------------
	
	@Override
	public void setPosition(float x, float y, float z) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setAngle(float angx, float angy, float angz) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setZoom(float zoom) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setWindow(int i, int j) {
		// TODO Auto-generated method stub
		
	}

	//--------------------------------------------------------------------------
	
	@Override
	public void render() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // limpias los buffer
        glEnable(GL_DEPTH_TEST);
        //glEnable(GL_CULL_FACE);

        glMatrixMode(GL_PROJECTION); //La camara
        glLoadIdentity(); // Inicializamos la matriz de la cámara a la identidad
        glOrtho(-10, 10, -10, 10, -10, 10);

        glRotated(cam_ang_x, 1.0, 0.0, 0.0);
        glRotated(cam_ang_y, 0.0, 1.0, 0.0);
        glTranslated(-cam_x,-cam_y,-cam_z);

        glMatrixMode(GL_MODELVIEW); // Activamos la matriz del modelo
        glLoadIdentity(); // Inicializamos la matriz del modelo a la identidad
	}
	
	//--------------------------------------------------------------------------

	@Override
	public void moveUp(float v) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void moveDown(float v) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void moveStraight(float v) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void moveBack(float v) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void moveRight(float v) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void moveLeft(float v) {
		// TODO Auto-generated method stub
		
	}
	
	//--------------------------------------------------------------------------
	
	@Override
	public void lookRight(float ang) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void lookLeft(float ang) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void lookUp(float ang) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void lookDown(float ang) {
		// TODO Auto-generated method stub
		
	}

	//--------------------------------------------------------------------------
	
	@Override
	public void morezoom() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void lesszoom() {
		// TODO Auto-generated method stub
		
	}

	//--------------------------------------------------------------------------
	
	@Override
	public float getCam_x() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getCam_y() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getCam_z() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getCam_ang_x() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getCam_ang_y() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getCam_ang_z() {
		// TODO Auto-generated method stub
		return 0;
	}

}
