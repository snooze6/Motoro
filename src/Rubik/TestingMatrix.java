package Rubik;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class TestingMatrix {

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
	
	public static void main(String[] argv) {
	       
		Matrix4f m1 = new Matrix4f(), m2 = new Matrix4f();
		
		m1.setIdentity();
		m1.rotate((float)Math.toRadians(45), new Vector3f(0,1,0));
		m1.rotate((float)Math.toRadians(45), new Vector3f(0,1,0));
		//m1.rotate((float)Math.toRadians(90), new Vector3f(0,1,0));
		printMatrix4f(m1);
		
		m2.setIdentity();
		m2.rotate((float)Math.toRadians(90), new Vector3f(0,1,0));
		printMatrix4f(m2);
		
		System.out.println(compareMatrix4f(m1,m2));
		
		//----------------------------------------------------------------------
		System.out.println("------------------------------------------------------------------");
		
		m1.setIdentity();
		m1.translate(new Vector3f(24,0,0));
		m2.setIdentity();
		m2.translate(new Vector3f(24,0,0));
		
		m1.m30 = 0; m1.m31 = 0; m1.m32 = 0;
		m1.rotate((float)Math.toRadians(45), new Vector3f(0,1,0));
		m1.translate(new Vector3f(24,0,0));
		m1.m30 = 0; m1.m31 = 0; m1.m32 = 0;
		m1.rotate((float)Math.toRadians(45), new Vector3f(0,1,0));
		m1.translate(new Vector3f(24,0,0));
		printMatrix4f(m1);
		
		m2.m30 = 0; m2.m31 = 0; m2.m32 = 0;
		m2.rotate((float)Math.toRadians(90), new Vector3f(0,1,0));
		m2.translate(new Vector3f(24,0,0));
		printMatrix4f(m2);
		
		System.out.println(compareMatrix4f(m1,m2));
		
		
		
	}
	
}
