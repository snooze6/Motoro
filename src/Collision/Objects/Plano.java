package Collision.Objects;

import static org.lwjgl.opengl.GL11.*;

public class Plano {

	Vector punto, normal;
	
	public Plano(Vector normal, Vector punto) {
		super();
		this.normal = normal;
		this.punto = punto;
	}
	
	public Plano() {
		super();
		normal = new Vector(0,1,0);
		punto = new Vector(0,0,0);
	}
	
	public Plano(float a, float b, float c, float d, float e, float f){
		super();
		normal = new Vector(a,b,c);
		punto = new Vector(d,e,f);
	}
	
	public void draw(){
		int size = 100;
		
		//		System.out.println("[PLANO]: n[1] "+normal.x+" - n[2] "+normal.y+" - n[3] "+normal.z);
		//		System.out.println("[PLANO]: p[1] "+punto.x+" - p[2] "+punto.y+" - p[3] "+punto.z);
		//		System.out.println("[PLANO]: r[1] "+punto.x+100*normal.x+" - r[2] "+(punto.y+100*normal.y)+" - r[3] "+ punto.z+100*normal.z);
			
		glPushMatrix();
			glTranslated(punto.x, punto.y, punto.z);
			glPushMatrix();
			Vector p = Vector.prod(normal, Vector.ejey);
			glRotated(-Vector.ang(normal, Vector.ejey), p.x, p.y, p.z );
		
				glScaled(size,size,size);
				glBegin(GL_QUADS);                // Begin drawing the color cube with 6 quads
				
					glColor3f(1.0f, 0.5f, 0.0f);     // Orange
		
					glVertex3f( -1, 0,  1);
					glVertex3f( -1, 0, -1);
					glVertex3f(  1, 0, -1);
					glVertex3f(  1, 0,  1);
				glEnd();
			  glScaled(1/size,1/size,1/size);
			glPopMatrix();
			glPushMatrix();
				glColor3f(1.0f, 1.0f, 0.0f);
				p = Vector.norm(normal);
				glBegin(GL_LINES);
					glVertex3f(0,0,0);
					glVertex3f(size*p.x, size*p.y, size*p.z); 
				glEnd();
			glPopMatrix();
		glPopMatrix();
	}
	
	public int collision(){
		return 0;
	}
	
	public float[] nuevaDireccion(){
		return new float[3];
	}
	
	
	
}
