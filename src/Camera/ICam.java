package Camera;

public interface ICam {

	public void setPosition(float x, float y, float z);
	public void setAngle(float angx, float angy, float angz);
	public void setZoom(float zoom);
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
}
