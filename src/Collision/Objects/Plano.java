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
	
	private int signo(float f){
		if (f>=0) {
			return 1;
		} else {
			return -1;
		}
	}
	
	private int sign(){
		if (normal.y>=0){
			return signo(normal.x)*signo(normal.y);
		} else {
			return -1*signo(normal.x)*signo(normal.y);
		}
	}
	
	public void draw(){
		//float scaleX=0,scaleZ=0;
		int size = 100;
	
		System.out.println("[PLANO]: n[1] "+normal.x+" - n[2] "+normal.y+" - n[3] "+normal.z);
		System.out.println("[PLANO]: p[1] "+punto.x+" - p[2] "+punto.y+" - p[3] "+punto.z);
		System.out.println("[PLANO]: r[1] "+punto.x+100*normal.x+" - r[2] "+(punto.y+100*normal.y)+" - r[3] "+ punto.z+100*normal.z);
		
		glPushMatrix();
			glTranslated(punto.x, punto.y, punto.z);
			
			//glRotated(90, 0.0f, 0.0f, 0.0f);
			if (normal.z!=0){
				glRotated(90, 0, 0, 1);
			}
			glPushMatrix();
			glPointSize(20.0f);
				glScaled(size,size,size);
					glBegin(GL_QUADS);                // Begin drawing the color cube with 6 quads
						glColor3f(1.0f, 0.5f, 0.0f);     // Orange
//						glVertex3f( 1.0f, 0.0f,  1.0f);
//						glVertex3f(-1.0f, 0.0f,  1.0f);
//						glVertex3f(-1.0f, 0.0f, -1.0f);
//						glVertex3f( 1.0f, 0.0f, -1.0f);
//						System.out.println("[PLANO]: Ang - "+Vector.ang(normal, Vector.ejey));
//						System.out.println("[PLANO]: 1   - "+(float) Math.sin(90-Vector.ang(normal, Vector.ejey)));
//						System.out.println("[PLANO]: 2   - "+(float) Math.cos(90-Vector.ang(normal, Vector.ejey)));

						glVertex3f( (float) -Math.sin(Math.toRadians(90-Vector.ang(normal, Vector.ejey))),
								    (float) ((+sign())*Math.cos(Math.toRadians(90-Vector.ang(normal, Vector.ejey)))), 
								    1.0f);
						glVertex3f( (float) -Math.sin(Math.toRadians(90-Vector.ang(normal, Vector.ejey))),
						            (float) ((+sign())*Math.cos(Math.toRadians(90-Vector.ang(normal, Vector.ejey)))), 
						            -1.0f);
						glVertex3f( (float) Math.sin(Math.toRadians(90-Vector.ang(normal, Vector.ejey))),
								    (float) ((-sign())*Math.cos(Math.toRadians(90-Vector.ang(normal, Vector.ejey)))), 
								    1.0f);
						glVertex3f( (float) Math.sin(Math.toRadians(90-Vector.ang(normal, Vector.ejey))),
						            (float) ((-sign())*Math.cos(Math.toRadians(90-Vector.ang(normal, Vector.ejey)))), 
						            -1.0f);
//						glVertex3f(-1.0f, 0.0f,  1.0f);
//						glVertex3f(-1.0f, 0.0f, -1.0f);

						
					glEnd();
				glScaled(1/size,1/size,1/size);
			glPopMatrix();
			glPushMatrix();
				//Dibujo.drawCube(1);
				glColor3f(1.0f, 1.0f, 0.0f);
				glBegin(GL_LINES);
					glVertex3f(0,0,0);
					glVertex3f(size*normal.x, size*normal.y, size*normal.z); 
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
