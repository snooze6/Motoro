package Billiard.World;

import static org.lwjgl.opengl.GL11.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import Utilities.Model;
import Utilities.ObjectLoader;
import Utilities.TextureGL;

public class Billiard extends BilliardObject{
    private float size;
    private TextureGL field = TextureGL.loadTexture("res/images/table.jpg");
    private TextureGL tex = TextureGL.loadTexture("res/images/wood2.jpg");
    public Billiard(float size){
        this.size=size;
        Model m = null;
        try {
            m = ObjectLoader.loadTexturedModel(new File("res/models/billar.obj"));
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

            try {
            	
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
                
                Vector2f vt1 = m.getTextureCoordinates().get(face.getVertexIndices()[0] - 1);
                glTexCoord2d(vt1.x, vt1.y);
                Vector2f vt2 = m.getTextureCoordinates().get(face.getVertexIndices()[1] - 1);
                glTexCoord2d(vt2.x, vt2.y);
                Vector2f vt3 = m.getTextureCoordinates().get(face.getVertexIndices()[2] - 1);
                glTexCoord2d(vt3.x, vt3.y);

            } catch (Exception e) {
//		            		System.out.println("\nCogida excepcion Billar");
//		            		System.out.println("Tamaño normal:   "+face.getNormalIndices().length+" [X:"+face.getNormalIndices()[0]+" , Y: "+face.getNormalIndices()[1]+" , Z: "+face.getNormalIndices()[2]+"]");
//		            		System.out.println("Tamaño vertices: "+face.getVertexIndices().length+" [X:"+face.getVertexIndices()[0]+" , Y: "+face.getVertexIndices()[1]+" , Z: "+face.getVertexIndices()[2]+"]");
                e.getStackTrace();
                //System.out.println("Tamaño vertice: "+face.getVertexIndices().length+);
            }

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
        glPushMatrix();
        	field.on();
		        glColor3f(1,1,1);
		        	glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		        	//glTranslatef(0,1000,0);
			        glBegin(GL11.GL_QUADS);
			        	glTexCoord2d(0.0f, 0.0f);
			        	glVertex3f( 2275,1, 1045);
			        	glNormal3f( 2275,1, 1045);
			        	
			        	glTexCoord2d(0.0f, 1.0f);
			        	glVertex3f( 2275,1,-1045);
			        	glNormal3f( 2275,1,-1045);
			        	
			        	glTexCoord2d(1.0f, 1.0f);
			        	glVertex3f(-2275,1,-1045);
			        	glNormal3f(-2275,1,-1045);
			        	
			        	glTexCoord2d(1.0f, 0.0f);
			        	glVertex3f(-2275,1, 1045);
			        	glNormal3f(-2275,1, 1045);
			        	
			        glEnd();
		        glColor3f(0.7f,0.7f,0.7f);
	        field.off();
	        //glBlendFunc(GL_SRC_ALPHA, GL_ONE);
	        //glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	        //glEnable(GL_BLEND);
				glScalef(size, size, size);
				tex.on();
				super.render();
				tex.off();
	        //glDisable(GL_BLEND);
        glPopMatrix();
	}

}
