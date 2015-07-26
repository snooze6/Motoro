package Rubik.Backup;

import static org.lwjgl.opengl.GL11.*;

import java.nio.FloatBuffer;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import Utilities.BufferTools;
import Utilities.Dibujo;
import Utilities.Vector;

public class Piece {
	
	private Matrix4f m;
	int size;
	
	Piece(){
		m = new Matrix4f();
		m.setIdentity();
		size = 10;
	}
	
	Piece(Matrix4f m){
		this.m = new Matrix4f(m);
		size = 10;
	}
	
	Piece(int size){
		m = new Matrix4f();
		m.setIdentity();
		this.size = size;
	}
	
	Piece(Matrix4f m, int size){
		this.m = new Matrix4f(m);
		this.size = size;
	}
	
	//--------------------------------------------------------------------------
	
	public void render(){
		glPushMatrix();
			FloatBuffer buf = BufferTools.asFloatBuffer(m);
			buf.rewind();
			//glLoadMatrix(buf);
			glMultMatrix(buf);
			Dibujo.drawCube(size);
		glPopMatrix();
	}
	
	//--------------------------------------------------------------------------
	
	public void rotate(float angle, Vector axis){
		m.rotate((float)Math.toRadians(angle), new Vector3f(axis.x, axis.y, axis.z));
	}
	
	public void translate(Vector v){
		m.translate(new Vector3f(v.x, v.y, v.z));
	}
	
//	public void mult(Matrix4f m){
//		m = Matrix4f.mul(this.m, m, this.m);
//	}
	
	//--------------------------------------------------------------------------

	public Matrix4f getM() {
		return new Matrix4f(m);
	}
	
	public Vector getPos(){
		return new Vector(m.m30, m.m31, m.m32);
	}
	
	public void setPos(Vector pos){
		m.m30 = pos.x;
		m.m31 = pos.y;
		m.m32 = pos.z;
	}
	
	protected Vector getP(){
		return new Vector(m.m30, m.m31, m.m32);
	}
	
	

}
