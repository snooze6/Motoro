package Billiard.World;

import static org.lwjgl.opengl.GL11.*;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import Collision.BBSphere;
import Utilities.Dibujo;
import Utilities.TextureGL;
import Utilities.Vector;


public class Ball extends BilliardObject{
	
	private BBSphere bbox;
	private TextureGL tex = null;
	private float ang=0;
	
	public Ball(){
		bbox = new BBSphere();
		NewLista(20, 40, 40);
		initMatrix();
	}
	public Ball(String path){
		this();
		tex = TextureGL.loadTexture(path);
	}


    public Ball(BBSphere box){
		bbox = box;
		NewLista(box.getSize(),40,40);
		initMatrix();
	}
	public Ball(BBSphere box, String path){
		this(box);
		tex = TextureGL.loadTexture(path);
	}
	public Ball(Vector point, float mass, float size) {
		this(new BBSphere(point, mass, size));
	}
	public Ball(Vector point, Vector velocity, float mass, float size) {
		this(new BBSphere(point, velocity, mass, size));
	}
	public Ball(Vector point, float mass, float size, String path) {
		this(new BBSphere(point, mass, size));
		tex = TextureGL.loadTexture(path);
	}
	public Ball(Vector point, Vector velocity, float mass, float size, String path) {
		this(new BBSphere(point, velocity, mass, size));
		tex = TextureGL.loadTexture(path);
	}
	
	//--------------------------------------------------------------------------
	
	private void NewLista(float r, int lats, int longs){
		glNewList(this.list, GL_COMPILE);
			Dibujo.drawSphere(r, lats, longs);
		glEndList();
	}

	FloatBuffer rotationMatrix;
	protected void initMatrix(){
		rotationMatrix = BufferUtils.createFloatBuffer(16);;
		float aux[] = {1,0,0,0,
					   0,1,0,0,
					   0,0,1,0,
					   0,0,0,1};
        
        rotationMatrix.put(aux);
        rotationMatrix.rewind();
	}


    public BBSphere getBbox() {
        return bbox;
    }

    public static void print() {
        FloatBuffer modelMatrix;
        modelMatrix = BufferUtils.createFloatBuffer(16);;
        modelMatrix.rewind();
        GL11.glGetFloat( GL_MODELVIEW_MATRIX, modelMatrix );

        final float[] array = new float[16];
        modelMatrix.get(array);

        //System.out.println("  <  Matrix >");
        for(int i=3; i<4; i++){
            System.out.print("  (");
            for (int j=0; j<4; j++){
                System.out.print(" "+array[i*4+j]+" ");
            }
            System.out.println(")");
        }
        //System.out.println("  < /Matrix >");

        modelMatrix.rewind();
    }

    public static Vector  getVectorPosition() {
        FloatBuffer modelMatrix;
        modelMatrix = BufferUtils.createFloatBuffer(16);;
        modelMatrix.rewind();
        GL11.glGetFloat( GL_MODELVIEW_MATRIX, modelMatrix );

        final float[] array = new float[16];
        modelMatrix.get(array);

        //System.out.println("  <  Matrix >");
//        for(int i=3; i<4; i++){
//            System.out.print("  (");
//            for (int j=0; j<4; j++){
//                System.out.print(" "+array[i*4+j]+" ");
//            }
//            System.out.println(")");
//        }
        //System.out.println("  < /Matrix >");

        modelMatrix.rewind();
        return new Vector(array[12],array[13],array[14]);
    }


    protected double updateang(double ang){
		if (ang>360){
			return ang-360;
		} else {
			if (ang<-360) {
				return ang+360;
			} else {
				return ang;
			}
		}
	}
	
	@Override
	public void render(){
		Vector punto = bbox.getCenterPoint();
		Vector vel = Vector.prod(Vector.ejey, bbox.getVel());
		
		ang = vel.mod()*10;
		vel = Vector.norm(vel);
		
		glPushMatrix();
			glLoadIdentity();
			if (vel.mod()!=0){
				glRotatef(ang, vel.x, 0, vel.z);
				glMultMatrix(rotationMatrix);
			} else {
				//glRotatef(ang, ant.x, 0, ant.z);
				glMultMatrix(rotationMatrix);
			}
			
	        GL11.glGetFloat( GL_MODELVIEW_MATRIX, rotationMatrix);
		glPopMatrix();
		
		if (tex!=null){
			tex.on();
			  glPushMatrix();  
					glTranslated(punto.x, punto.y, punto.z);
					glMultMatrix(rotationMatrix);
					super.render();
			  glPopMatrix();
			tex.off();
		} else {
			  glPushMatrix();
					glTranslated(punto.x, punto.y, punto.z);
					glMultMatrix(rotationMatrix);

					super.render();
                   // print();
			  glPopMatrix();
		}
		
	}
	
//	public void render(){
//		Vector punto = bbox.getCenterPoint();
//		Vector vel = Vector.prod(Vector.ejey, bbox.getVel());
//		
//		change = false;
//		if (vel.mod()!=0){
//			ang += updateang(vel.mod()*10);
//			if (vel.x!=0){
//				angX+=updateang(vel.x*10);
//			}
//			if (vel.z!=0){
//				angZ+=updateang(vel.z*10);
//			}
//			if ((int)Vector.ang(vel, ant)!=0){
//				change = true;
////				angX+=updateang(vel.x*10);
////				angZ+=updateang(vel.z*10);
//			}
//			ant = vel;
//		}
//		
//		vel = Vector.norm(vel);
//		
//		if (tex!=null){
//			tex.on();
//			  glPushMatrix();  
//					glTranslated(punto.x, punto.y, punto.z);
//					//glRotatef(ang, vel.x, vel.y, vel.z);
//					if (vel.mod()!=0){
//						//glRotatef(ang, vel.x, 0, vel.z);
//						System.out.println("El ang x es ------------ " + angX);
//						glRotatef(angX, 1, 0, 0);
//			
//						glRotatef(angZ, 0, (float)Math.sin(Math.toRadians(angX)), (float) (Math.cos(Math.toRadians(angX))));
//					} else {
//						glRotatef(angX, 1, 0, 0);
//						glRotatef(angZ, 0, 0, (float) (Math.cos(Math.toRadians(angX))+Math.sin(Math.toRadians(angX))));
//					}
//					Dibujo.drawAxes(bbox.getSize()*2);
//					super.render();
//			  glPopMatrix();
//			tex.off();
//		} else {
//			  glPushMatrix();
//					glTranslated(punto.x, punto.y, punto.z);
//					glRotatef(ang, 1, 1, 1);
//					super.render();
//			  glPopMatrix();
//		}
//		
//	}
	
	//--------------------------------------------------------------------------

	public void update(float delta) {
		bbox.move(delta);
	}
	
	public Vector getCenterPoint() {
		return bbox.getCenterPoint();
	}

	public Vector getVel() {
		return bbox.getVel();
	}

	public float getSize() {
		return bbox.getSize();
	}
	
	public void setVel(Vector v) {
		bbox.setVel(v);
	}
	
	public void setVel(float x, float y, float z) {
		setVel(new Vector(x,y,z));
	}
	
	//--------------------------------------------------------------------------
	
	public void setTransparent(){
		tex.setTransparent();
	}
	
	public void setTransparent(boolean b){
		tex.setTransparent(b);
	}
	

}
