package Collision.Objects;

import static org.lwjgl.opengl.GL11.GL_QUAD_STRIP;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glNormal3d;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTranslated;
import static org.lwjgl.opengl.GL11.*;

import Collisions.IBoundingBox;
import Others.Dibujo;

public class Esfera implements IBoundingBox{
	Vector point, velocity;
    public Vector lastPoint;
	float mass;
	float size;
	int displayListHandle = glGenLists(1);
	
	//--------------------------------------------------------------------------
	
	public Esfera() {
		super();
		this.point = new Vector(0,0,0);
        this.lastPoint=point;
		this.velocity = new Vector(0,0,0);
		this.mass = 1;
		this.size = 10;
		
		NewLista(size, 30, 30);
	}
	public Esfera(Vector point, float mass, float size) {
		super();
		this.point = point;
        this.lastPoint=point;
		this.velocity = new Vector(0,0,0);
		this.mass = mass;
		this.size = size;
		
		NewLista(this.size, 30, 30);
	}
	public Esfera(Vector point, Vector velocity, float mass, float size) {
		super();
		this.point = point;
        this.lastPoint=point;
		//this.velocity = Vector.prod(1.0f/1000.0f,velocity);
       this.velocity = velocity;
		this.mass = mass;
		this.size = size;
		
		NewLista(size, 15, 15);
	}
	public Esfera(float x, float y, float z, float mass, float size) {
		super();
		this.point = new Vector(x,y,z);
        this.lastPoint=point;
		this.velocity = new Vector(0,0,0);
		this.mass = mass;
		this.size = size;
		
		NewLista(size, 15, 15);
	}
	
	//--------------------------------------------------------------------------
	
	public void NewLista(double r, int lats, int longs) {

        int i, j; double lat0, z0, zr0, lat1, z1, zr1; 
		
		glNewList(this.displayListHandle, GL_COMPILE);
		
	        for(i = 0; i <= lats; i++) {
	            lat0 = Math.PI * (-0.5 + (double) (i - 1) / lats);
	            z0  = Math.sin(lat0);
	            zr0 =  Math.cos(lat0);
	
	            lat1 = Math.PI * (-0.5 + (double) i / lats);
	            z1 =  Math.sin(lat1);
	            zr1 = Math.cos(lat1);
	
	            glBegin(GL_QUAD_STRIP);
	            for(j = 0; j <= longs; j++) {
	                double lng = 2 * Math.PI * (double) (j - 1) / longs;
	                double x = r*Math.cos(lng);
	                double y = r*Math.sin(lng);
	
	                glNormal3d(x * zr0, y * zr0, r*z0);
	                glVertex3d(x * zr0, y * zr0, r*z0);
	                glNormal3d(x * zr1, y * zr1, r*z1);
	                glVertex3d(x * zr1, y * zr1, r*z1);
	            }
	            glEnd();
	         }
		
			//Dibujo.drawCube(size);
         glEndList();
	}
	
	//--------------------------------------------------------------------------
	
	public void trasladar(float delta){
		point=Vector.sum(point, Vector.prod(delta, velocity));
	}
	
	public void draw(){
		glPushMatrix();
            lastPoint=point;
			glTranslated(point.x,point.y,point.z);
			
			glCallList(this.displayListHandle);
			
			//Dibujo.drawSphere(size, 10, 10);
			//Dibujo.drawCube(size);
			//s.draw(size, 30, 30);
		glPopMatrix();
	}
	
	//--------------------------------------------------------------------------
	
	public Vector getPoint() {
		return point;
	}
	public void setPoint(Vector point) {
		this.point = point;
	}
	public Vector getVelocity() {
		return velocity;
	}
	public void setVelocity(Vector velocity) {
		this.velocity = velocity;
	}
	public void setVelocity(float x,float y,float z) {
		this.velocity.x=x;
		this.velocity.y=y;
		this.velocity.z=z;
	}
	public float getMass() {
		return mass;
	}
	public void setMass(float mass) {
		this.mass = mass;
	}
	public float getSize() {
		return size;
	}
	public void setSize(float size) {
		this.size = size;
	}
	@Override
	public Vector getVel() {
		return velocity;
	}
	@Override
	public void setVel(Vector v) {
		velocity = v;
	}
	
	//--------------------------------------------------------------------------
	

}
