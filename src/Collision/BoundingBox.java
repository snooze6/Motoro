package Collision;
import Utilities.Vector;


public abstract class BoundingBox{
	
	public abstract void move(float delta);
	public abstract void draw();
	public abstract void add(BoundingBox c);
	public abstract void del(BoundingBox c);
	public abstract void del(int c);
	public abstract BoundingBox getSon(int i);
    public abstract Vector getCenterPoint();
    public abstract Vector getVel();
    public abstract float getSize();
    public abstract void setVel(Vector v);
}
