package Others;

import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTranslatef;

import org.lwjgl.util.vector.Vector3f;

public class Face {
	
	private Piece center;
	private Piece aristas[];
	private Piece esquinas[];
	@SuppressWarnings("unused")
	private Vector3f axis;
	@SuppressWarnings("unused")
	private float ratio;
	private Vector3f[] faceOffsets;
	
	//--------------------------------------------------------------------------
	
	public Face(Piece center, Piece[] aristas, Piece[] esquinas, Vector3f axis,
			float ratio) {
		super();
		this.center = center;
		this.aristas = aristas;
		this.esquinas = esquinas;
		this.axis = axis;
		this.ratio = ratio;
	}
	
	public Face(Piece center, Vector3f axis) {
		super();
		this.center = center;
		this.axis = axis;
	}
	
	
	
	public Face(float size) {
		super();
		center = new Piece(size);
		aristas = new Piece[6];
		esquinas = new Piece[6];
		for (int i=0; i<6; i++) { aristas[i] = new Piece(size);}
		for (int i=0; i<6; i++) { esquinas[i] = new Piece(size);}
		axis = new Vector3f(0.0f, 0.0f, 1.0f);
		ratio = 1.0f;
		
		faceOffsets = new Vector3f[6];
		{
			faceOffsets[0] = new Vector3f(0, -2.1f - 2*size, 0);
			faceOffsets[1] = new Vector3f(0, 0, 2.1f + 2*size);
			faceOffsets[2] = new Vector3f(0,2.1f + 2*size, 0);
			faceOffsets[3] = new Vector3f(0, 0, -2.1f -2*size);
			faceOffsets[4] = new Vector3f(-2.1f -2*size, 0, 0);
			faceOffsets[5] = new Vector3f(2.1f + 2*size, 0, 0);
		}
	}

	//--------------------------------------------------------------------------
	
	public void addEdge(Piece edge, int id){
		aristas[id]=edge;
	}
	
	public void addCorner(Piece corner, int id){
		esquinas[id]=corner;
	}
	
	//--------------------------------------------------------------------------
	
	public void draw(){
        glPushMatrix();
		center.draw();
		Vector3f[] separation = faceOffsets;
		
		for (int i=0; i<6; i++){
			glPushMatrix();
			  glTranslatef( separation[i].x, separation[i].y, separation[i].z);
			  aristas[i].draw();
			glPopMatrix();
		}
	        glTranslatef(0.0f, 0.0f, 0.0f);
	        Dibujo.drawSphere(20,50,50);
	        Dibujo.drawAxes(40);
	        
        glPopMatrix();
		
		
	}

	//--------------------------------------------------------------------------
	
	
}
