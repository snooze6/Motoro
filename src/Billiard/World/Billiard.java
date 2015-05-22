package Billiard.World;

import static org.lwjgl.opengl.GL11.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import Billiard.Loader.Model;
import Billiard.Loader.ObjectLoader;

public class Billiard extends BilliardObject{
	
	public Billiard(){

            Model m = null;
            try {
                m = ObjectLoader.loadModel(new File(ObjectLoader.MODEL_LOCATION));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Display.destroy();
                System.exit(1);
            } catch (IOException e) {
                e.printStackTrace();
                Display.destroy();
                System.exit(1);
            }
				
			glNewList(list, GL_COMPILE);
			
	            glBegin(GL_TRIANGLES);
		            //Dibujo.drawCube(100);
		            for (Model.Face face : m.getFaces()) {
		            	
		                Vector3f n1 = m.getNormals().get(face.getNormalIndices()[0] - 1);
		                glNormal3f(n1.x, n1.y, n1.z);
		                Vector3f v1 = m.getVertices().get(face.getVertexIndices()[0] - 1);
		                glVertex3f(v1.x, v1.y, v1.z);
		                Vector3f n2 = m.getNormals().get(face.getNormalIndices()[1] - 1);
		                glNormal3f(n2.x, n2.y, n2.z);
		                Vector3f v2 = m.getVertices().get(face.getVertexIndices()[1] - 1);
		                glVertex3f(v2.x, v2.y, v2.z);
		                Vector3f n3 = m.getNormals().get(face.getNormalIndices()[2] - 1);
		                glNormal3f(n3.x, n3.y, n3.z);
		                Vector3f v3 = m.getVertices().get(face.getVertexIndices()[2] - 1);
		                glVertex3f(v3.x, v3.y, v3.z);
		                
		            }
	            glEnd();
            
	        glEndList();
			
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void render() {
		int tam=50;
		glScalef(tam, tam, tam);
		super.render();
		glScalef(tam, tam, tam);
	}

}