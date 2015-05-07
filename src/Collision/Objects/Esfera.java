package Collision.Objects;

import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTranslated;

import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import Collisions.IBoundingBox;
import Others.Dibujo;

public class Esfera implements IBoundingBox{
	Vector point, velocity;
	float mass;
	float size;
	
	//--------------------------------------------------------------------------
	
	public Esfera() {
		super();
		this.point = new Vector(0,0,0);
		this.velocity = new Vector(0,0,0);
		this.mass = 1;
		this.size = 10;
	}
	public Esfera(Vector point, float mass, float size) {
		super();
		this.point = point;
		this.velocity = new Vector(0,0,0);
		this.mass = mass;
		this.size = size;
	}
	public Esfera(Vector point, Vector velocity, float mass, float size) {
		super();
		this.point = point;
		this.velocity = velocity;
		this.mass = mass;
		this.size = size;
	}
	public Esfera(float x, float y, float z, float mass, float size) {
		super();
		this.point = new Vector(x,y,z);
		this.velocity = new Vector(0,0,0);
		this.mass = mass;
		this.size = size;
	}
	
	//--------------------------------------------------------------------------
	
	public void trasladar(float delta){
		point=Vector.sum(point, Vector.prod(delta, velocity));
	}
	
	public void draw(){
		glPushMatrix();
			glTranslated(point.x,point.y,point.z);
			Dibujo.drawSphere(size, 30, 30);
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
