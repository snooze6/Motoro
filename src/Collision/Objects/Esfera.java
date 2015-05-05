package Collision.Objects;

import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTranslated;
import Collisions.IBoundingBox;
import Others.Dibujo;

public class Esfera implements IBoundingBox{
	Vector point, direction;
	float mass;
	float size;
	
	//--------------------------------------------------------------------------
	
	public Esfera() {
		super();
		this.point = new Vector(0,0,0);
		this.direction = new Vector(0,0,0);
		this.mass = 1;
		this.size = 10;
	}
	public Esfera(Vector point, float mass, float size) {
		super();
		this.point = point;
		this.direction = new Vector(0,0,0);
		this.mass = mass;
		this.size = size;
	}
	public Esfera(Vector point, Vector direction, float mass, float size) {
		super();
		this.point = point;
		this.direction = direction;
		this.mass = mass;
		this.size = size;
	}
	public Esfera(float x, float y, float z, float mass, float size) {
		super();
		this.point = new Vector(x,y,z);
		this.direction = new Vector(0,0,0);
		this.mass = mass;
		this.size = size;
	}
	
	//--------------------------------------------------------------------------
	
	public void trasladar(){
		glPushMatrix();
			point=Vector.sum(point, direction);
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
	public Vector getDirection() {
		return direction;
	}
	public void setDirection(Vector direction) {
		this.direction = direction;
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
		return direction;
	}
	@Override
	public void setVel(Vector v) {
		direction = v;
	}
	
	

}
