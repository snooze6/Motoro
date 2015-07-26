package Rubik;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex3f;

import org.lwjgl.util.vector.Matrix4f;

public class Utils {

	public static void printMatrix4f(Matrix4f m){
	    	System.out.println("["+m.m00+","+m.m01+","+m.m02+","+m.m03+"]");
	    	System.out.println("["+m.m10+","+m.m11+","+m.m12+","+m.m13+"]");
	    	System.out.println("["+m.m20+","+m.m21+","+m.m22+","+m.m23+"]");
	    	System.out.println("["+m.m30+","+m.m31+","+m.m32+","+m.m33+"]");
	    }
	    
    public static boolean compareMatrix4f(Matrix4f m1, Matrix4f m2){
    	Matrix4f aux = new Matrix4f();
    	aux = Matrix4f.sub(m1, m2, aux);
    	float tol = 0.005f;
    	
    	if (Math.abs(aux.m00) >= tol ||
			Math.abs(aux.m01) >= tol ||
			Math.abs(aux.m02) >= tol ||
			Math.abs(aux.m03) >= tol ||
			Math.abs(aux.m10) >= tol ||
			Math.abs(aux.m11) >= tol ||
			Math.abs(aux.m12) >= tol ||
			Math.abs(aux.m13) >= tol ||
			Math.abs(aux.m20) >= tol ||
			Math.abs(aux.m21) >= tol ||
			Math.abs(aux.m22) >= tol ||
			Math.abs(aux.m23) >= tol ||
			Math.abs(aux.m30) >= tol ||
			Math.abs(aux.m31) >= tol ||
			Math.abs(aux.m32) >= tol ||
			Math.abs(aux.m33) >= tol
			){
    		return false;
    	} else {
    		return true;
    	}
    }
    
    public static void drawCube(float size){

	        glBegin(GL_QUADS);                // Begin drawing the color cube with 6 quads
	        // Top face (y = size)
	        glColor3f(1.0f, 1.0f, 0.0f);     // Yellow
	        glVertex3f(size, size, -size);
	        glVertex3f(-size, size, -size);
	        glVertex3f(-size, size, size);
	        glVertex3f(size, size, size);

	        // Bottom face (y = -size)
	        glColor3f(1.0f, 1.0f, 1.0f);     // White
	        glVertex3f(size, -size, size);
	        glVertex3f(-size, -size, size);
	        glVertex3f(-size, -size, -size);
	        glVertex3f(size, -size, -size);
	        // Front face  (z = size)
	        glColor3f(1.0f, 0.0f, 0.0f);     // Red
	        glVertex3f(size, size, size);
	        glVertex3f(-size, size, size);
	        glVertex3f(-size, -size, size);
	        glVertex3f(size, -size, size);

	        // Back face (z = -size)
	        glColor3f(1.0f, 0.6f, 0.0f);     // Orange
	        glVertex3f(size, -size, -size);
	        glVertex3f(-size, -size, -size);
	        glVertex3f(-size, size, -size);
	        glVertex3f(size, size, -size);

	        // Left face (x = -size)
	        glColor3f(0.0f, 0.0f, 1.0f);     // Blue
	        glVertex3f(-size, size, size);
	        glVertex3f(-size, size, -size);
	        glVertex3f(-size, -size, -size);
	        glVertex3f(-size, -size, size);

	        // Right face (x = size)
	        glColor3f(0.0f, 1.0f, 0.0f);     // Green
	        glVertex3f(size, size, -size);
	        glVertex3f(size, size, size);
	        glVertex3f(size, -size, size);
	        glVertex3f(size, -size, -size);
	        glEnd();  // End of drawing color-cube
	}
	
}
