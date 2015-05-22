package Collision.Objects;


/**
 * Created by Denis on 30/04/2015.
 */
public interface IBoundingBox {
	
	public Vector getPoint();
	public Vector getVel();
	public float getSize();
	public void setVel(Vector v);
	public void move(float delta);
}