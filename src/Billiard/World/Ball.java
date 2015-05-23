package Billiard.World;

import static org.lwjgl.opengl.GL11.*;
import Collision.BBSphere;
import Utilities.Dibujo;
import Utilities.TextureGL;
import Utilities.Vector;


public class Ball extends BilliardObject{
	
	private BBSphere bbox;
	private TextureGL tex = null;
	
	public Ball(){
		bbox = new BBSphere();
		NewLista(20, 40, 40);
	}
	public Ball(String path){
		this();
		tex = TextureGL.loadTexture(path);
	}

    public BBSphere getBbox() {
        return bbox;
    }

    public Ball(BBSphere box){

		bbox = box;
		NewLista(box.getSize(),40,40);
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
	
	@Override
	public void render(){
		Vector punto = bbox.getPoint();
		if (tex!=null){
			tex.on();
			  glPushMatrix();
					glTranslated(punto.x, punto.y, punto.z);
					super.render();
			  glPopMatrix();
			tex.off();
		} else {
			  glPushMatrix();
					glTranslated(punto.x, punto.y, punto.z);
					super.render();
			  glPopMatrix();
		}
	}
	
	//--------------------------------------------------------------------------

	public void update(float delta) {
		bbox.move(delta);
	}
	
	public Vector getPoint() {
		return bbox.getPoint();
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
