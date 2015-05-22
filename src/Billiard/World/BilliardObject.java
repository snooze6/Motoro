package Billiard.World;

import static org.lwjgl.opengl.GL11.*;

public abstract class BilliardObject {
	
	protected int list=glGenLists(1);
	
	public abstract void update(float delta);
	
	public void render(){
		glPushMatrix();
			glCallList(list);
		glPopMatrix();
	}

}
