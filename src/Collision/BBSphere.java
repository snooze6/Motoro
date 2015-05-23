package Collision;

import static org.lwjgl.opengl.GL11.*;
import Utilities.Dibujo;

import Utilities.Vector;
import org.lwjgl.util.glu.Sphere;

public class BBSphere extends BoundingBox{
	Vector point, velocity;
    public Vector lastPoint;
	float mass;
	float size;
	int displayListHandle = glGenLists(1);
    Sphere s = new Sphere();
	
	//--------------------------------------------------------------------------
	
	public BBSphere() {
		super();
		this.point = new Vector(0,0,0);
        this.lastPoint=point;
		this.velocity = new Vector(0,0,0);
		this.mass = 1;
		this.size = 10;
		
		NewLista(size, 30, 30);
	}
	public BBSphere(Vector point, float mass, float size) {
		super();
		this.point = point;
        this.lastPoint=point;
		this.velocity = new Vector(0,0,0);
		this.mass = mass;
		this.size = size;
		
		NewLista(this.size, 30, 30);
	}
	public BBSphere(Vector point, Vector velocity, float mass, float size) {
		super();
		this.point = point;
        this.lastPoint=point;
		//this.velocity = Vector.prod(1.0f/1000.0f,velocity);
        this.velocity = velocity;
		this.mass = mass;
		this.size = size;
		
		NewLista(size, 15, 15);
	}
	public BBSphere(float x, float y, float z, float mass, float size) {
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
		org.lwjgl.util.glu.Sphere aux= new org.lwjgl.util.glu.Sphere();
		glNewList(this.displayListHandle, GL_COMPILE);
		    aux.draw((float)r,lats,longs);
//	        for(i = 0; i <= lats; i++) {
//	            lat0 = Math.PI * (-0.5 + (double) (i - 1) / lats);
//	            z0  = Math.sin(lat0);
//	            zr0 =  Math.cos(lat0);
//
//	            lat1 = Math.PI * (-0.5 + (double) i / lats);
//	            z1 =  Math.sin(lat1);
//	            zr1 = Math.cos(lat1);
//
//	            glBegin(GL_QUAD_STRIP);
//	            for(j = 0; j <= longs; j++) {
//	                double lng = 2 * Math.PI * (double) (j - 1) / longs;
//	                double x = r*Math.cos(lng);
//	                double y = r*Math.sin(lng);
//
//	                glNormal3d(x * zr0, y * zr0, r*z0);
//	                glVertex3d(x * zr0, y * zr0, r*z0);
//	                glNormal3d(x * zr1, y * zr1, r*z1);
//	                glVertex3d(x * zr1, y * zr1, r*z1);
//	            }
//	            glEnd();
//	         }
         glEndList();
	}
	
	//--------------------------------------------------------------------------
	
	public void move(float delta){
		point=Vector.sum(point, Vector.prod(delta, velocity));
	}
	
	public void draw(){
		glPushMatrix();
            lastPoint=point;
			glTranslated(point.x,point.y,point.z);
			glCallList(this.displayListHandle);
		glPopMatrix();
	}
    public void draw2(){
        glPushMatrix();
        glTranslated(point.x,point.y,point.z);
        Dibujo.drawCube(size);
        glPopMatrix();
    }
	
	//--------------------------------------------------------------------------
	
	public Vector getPosition() {
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

	public Vector getVel() {
		return velocity;
	}

	public void setVel(Vector v) {
		velocity = v;
	}
	
	//--------------------------------------------------------------------------
	
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
	
	//--------------------------------------------------------------------------
	

}
