package Camera;

public interface ICam {

	public void setPosition(float x, float y, float z);
	public void setAngle(float angx, float angy, float angz);
	public void setZoom(float zoom);
	
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
	
	public void lookUp();
	public void lookDown();
	public void lookStraight();
	public void lookBack();
	public void lookRight();
	public void lookLeft();
}
