package Camera;

import Collision.Objects.Vector;

public interface ICam {
	
	//--------------------------------------------------------------------------

	public void setPosition(float x, float y, float z);
	public void setPosition(Vector v);
	public void setAngle(float angx, float angy, float angz);
	public void setAngle(Vector v);
	public void setWindow(int i, int j);
	
	//--------------------------------------------------------------------------
	
	public void render();
	
	//--------------------------------------------------------------------------
	
	public void moveUp(float v);
	public void moveDown(float v);
	public void moveStraight(float v);
	public void moveBack(float v);
	public void moveRight(float v);
	public void moveLeft(float v);
	
	//--------------------------------------------------------------------------
	
	public void lookRight(float ang);
	public void lookLeft(float ang);
    public void lookUp(float ang);
    public void lookDown(float ang);
    
    public Vector getDireccion();
    public void setDireccion(float x, float y, float z);
    public void setDireccion(Vector v);
    
    //--------------------------------------------------------------------------

    public void morezoom();
    public void lesszoom();
    
    //--------------------------------------------------------------------------
    
    public float getX();
	public float getY();
	public float getZ();
	public float getAngX();
	public float getAngY();
	public float getAngZ();
	
	//--------------------------------------------------------------------------
	
}
