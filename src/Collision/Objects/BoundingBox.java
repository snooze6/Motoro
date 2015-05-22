package Collision.Objects;


public abstract class BoundingBox implements IBoundingBox{
	
	public abstract void move(float delta);
	public abstract void draw();

	public abstract void add(BoundingBox c);
	public abstract void del(BoundingBox c);
	public abstract void del(int c);
	public abstract BoundingBox getSon(int i);
	
}
