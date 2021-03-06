package Collision;

import Utilities.Vector;

import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotated;
import static org.lwjgl.opengl.GL11.glScaled;
import static org.lwjgl.opengl.GL11.glTranslated;
import static org.lwjgl.opengl.GL11.glVertex3f;

public class BBPlane extends BoundingBox{

	public Vector punto, normal;
	private float sizeDraw,size;

	
	public BBPlane(Vector normal, Vector punto) {
		this.normal = normal;
		this.punto = punto;
	}
    public BBPlane(Vector normal, Vector punto, float size) {
        this.normal = normal;
        this.punto = punto;
        this.size=size;
        this.sizeDraw=size/2;
    }
	
	public BBPlane() {
		normal = new Vector(0,1,0);
		punto = new Vector(0,0,0);
       size =100;
        sizeDraw=50;
	}
	
	public BBPlane(float a, float b, float c, float d, float e, float f){
		normal = new Vector(a,b,c);
		punto = new Vector(d,e,f);
	}
	
	public BBPlane(float a, float b, float c, float d, float e, float f, float size){
		normal = new Vector(a,b,c);
		punto = new Vector(d,e,f);
        this.size=size;
        this.sizeDraw=size/2;

	}
	
	//--------------------------------------------------------------------------
	
	public void draw(){
        glPushMatrix();
			glTranslated(punto.x, punto.y, punto.z);
			glPushMatrix();
			Vector p = Vector.prod(normal, Vector.ejey);
			glRotated(-Vector.ang(normal, Vector.ejey), p.x, p.y, p.z );
		
				glScaled(sizeDraw,sizeDraw,sizeDraw);
				glBegin(GL_QUADS);                // Begin drawing the color cube with 6 quads
				
					glColor3f(1.0f, 0.5f, 0.0f);     // Orange
					glVertex3f( -1, 0,  1);
					glVertex3f( -1, 0, -1);
					glVertex3f(  1, 0, -1);
					glVertex3f(  1, 0,  1);
				glEnd();

			glPopMatrix();
			
			glPushMatrix();
				glColor3f(1.0f, 1.0f, 0.0f);
				Vector n = Vector.norm(normal);
				glBegin(GL_LINES);
					glVertex3f(0,0,0);
					glVertex3f(sizeDraw*n.x, sizeDraw*n.y, sizeDraw*n.z);
				glEnd();
			glPopMatrix();
			
			glPushMatrix();
			glColor3f(0.0f, 0.0f, 1.0f);
			glBegin(GL_LINES);
				glVertex3f(0,0,0);
				glVertex3f(sizeDraw*p.x, sizeDraw*p.y, sizeDraw*p.z);
			glEnd();
			glPopMatrix();
			
			Vector a = Vector.prod(p, normal);
			glPushMatrix();
			glColor3f(0.0f, 1.0f, 0.0f);
			glBegin(GL_LINES);
				glVertex3f(0,0,0);
				glVertex3f(a.x, a.y, a.z);
			glEnd();
			glPopMatrix();
        glScaled(1/sizeDraw,1/sizeDraw,1/sizeDraw);
		glPopMatrix();
	}
	
	//--------------------------------------------------------------------------

	@Override
	public Vector getCenterPoint() {
		return punto;
	}

	@Override
	public Vector getVel() {
		return normal;
	}


    public Vector getNormal() {
        return normal;
    }

	@Override
	public void setVel(Vector v) {
		normal = v;
	}

	@Override
	public float getSize() {
		return this.size;
	}
	
	public void move(float delta){
		
	}
	@Override
	public void add(BoundingBox c) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void del(BoundingBox c) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public BoundingBox getSon(int i) {
		return this;
		// TODO Auto-generated method stub
		
	}
	@Override
	public void del(int c) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
