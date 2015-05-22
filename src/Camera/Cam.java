package Camera;
import Collision.Objects.Vector;

public abstract class Cam{
	
	protected Vector pos, ang;
	protected int W_WIDTH, W_HEIGHT;
	
	//--------------------------------------------------------------------------
	
	public void setPos(float x, float y, float z) {
		pos = new Vector(x,y,z);
	}
	public void setPos(Vector v) {
		pos = new Vector(v);
	}
	public void setAngle(float angx, float angy, float angz) {
		ang = new Vector(angx, angy, angz);
	}
	public void setAngle(Vector v) {
		ang = new Vector(v);
	}

	public abstract void setWindow(int i, int j);
	
	//--------------------------------------------------------------------------
	
	public abstract void render();
	
	//--------------------------------------------------------------------------
	
	public abstract void moveUp(float v);
	public abstract void moveDown(float v);
	public abstract void moveStraight(float v);
	public abstract void moveBack(float v);
	public abstract void moveRight(float v);
	public abstract void moveLeft(float v);
	
	//--------------------------------------------------------------------------
	
	protected float updateang(float ang){
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
	
	public void lookUp(float v) {
		ang.x=updateang(ang.x-v);
	}
	public void lookDown(float v) {
		ang.x=updateang(ang.x+v);
	}
	public void lookRight(float v) {
		ang.y=updateang(ang.y+v);
	}
	public void lookLeft(float v) {
		ang.y=updateang(ang.y-v);
	}
	public Vector getDireccion() {
		return new Vector(
				(float)  (Math.sin(Math.toRadians(ang.y))*Math.cos(Math.toRadians(ang.x))),
        		(float)  -Math.sin(Math.toRadians(ang.x)),
        		(float) -(Math.cos(Math.toRadians(ang.y))*Math.cos(Math.toRadians(ang.x)))
        		);
	}
	public void setDireccion(float x, float y, float z) {
		ang.x = (float) Math.toDegrees(Math.asin(-y));
		ang.y = (float) Math.toDegrees(Math.asin(x/Math.cos(Math.toRadians(ang.x))));
		ang.z = 0;
	}
	public void setDireccion(Vector v) {
		ang.x = (float) Math.toDegrees(Math.asin(-v.y));
		ang.y = (float) Math.toDegrees(Math.asin(v.x/Math.cos(Math.toRadians(ang.x))));
		ang.z = 0;
	}
	
	//--------------------------------------------------------------------------

	public abstract void morezoom();
	public abstract void lesszoom();
	
	//--------------------------------------------------------------------------

	public float getX() {
		return pos.x;
	}
	public float getY() {
		return pos.y;
	}
	public float getZ() {
		return pos.z;
	}
	public float getAngX() {
		return ang.x;
	}
	public float getAngY() {
		return ang.y;
	}
	public float getAngZ() {
		return ang.z;
	}
	
	//--------------------------------------------------------------------------

	public Vector getFront(float far) {
        return Vector.sum(getDireccion(), Vector.prod(far, pos));
	}
	public Vector getPos() {
		return pos;
	}
	public Vector getAng() {
		return ang;
	}
}
