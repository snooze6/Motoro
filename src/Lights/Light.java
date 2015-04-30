package Lights;

/**
 * Created by Denis on 30/04/2015.
 */

import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.glu.Sphere;
import static org.lwjgl.opengl.GL11.*;

public class Light {
    private FloatBuffer diffuse;
    private FloatBuffer matSpecular;
    private FloatBuffer lightPosition;
    private FloatBuffer whiteLight;
    private FloatBuffer lModelAmbient;

    public Light(){

    }
    public void on(){
        //<Variables para las luces>
    initGL();


    }

private void initGL() {
        glClearColor(0.5f, 0.5f, 0.5f, 0.0f); // sets background to grey
        glClearDepth(1.0f); // clear depth buffer
        glEnable(GL_DEPTH_TEST); // Enables depth testing
        glDepthFunc(GL_LEQUAL); // sets the type of test to use for depth testing
        glMatrixMode(GL_PROJECTION); // sets the matrix mode to project
        glMatrixMode(GL_MODELVIEW);


        //----------- Variables & method calls added for Lighting Test -----------//
        initLightArrays();
        glShadeModel(GL_SMOOTH);
        glMaterial(GL_FRONT, GL_SPECULAR, matSpecular);				// sets specular material color
        glMaterialf(GL_FRONT, GL_SHININESS, 50.0f);					// sets shininess

        			// sets light position
        glLightModel(GL_LIGHT_MODEL_AMBIENT, lModelAmbient);		// global ambient light
        glLight(GL_LIGHT0, GL_DIFFUSE, diffuse);					// sets diffuse light to white
        glLight(GL_LIGHT0, GL_SPECULAR, whiteLight);
        glLight(GL_LIGHT0, GL_POSITION, lightPosition);	// sets specular light to white



        glEnable(GL_LIGHTING);										// enables lighting
        glEnable(GL_LIGHT0);										// enables light0

        glEnable(GL_COLOR_MATERIAL);								// enables opengl to use glColor3f to define material color
        glColorMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE);			// tell opengl glColor3f effects the ambient and diffuse properties of material
        //----------- END: Variables & method calls added for Lighting Test -----------//





    }
    private void initLightArrays() {
        matSpecular = BufferUtils.createFloatBuffer(4);
        matSpecular.put(1.0f).put(1.0f).put(1.0f).put(1.0f).flip();

        lModelAmbient = BufferUtils.createFloatBuffer(4);
        lModelAmbient.put(0.2f).put(0.2f).put(0.2f).put(1.0f).flip();

       diffuse = BufferUtils.createFloatBuffer(4);
        diffuse.put(1.0f).put(1.0f).put(1.0f).put(1.0f).flip();

        lightPosition = BufferUtils.createFloatBuffer(4);
        lightPosition.put(50.0f).put(50.0f).put(-200.0f).put(0.0f).flip();

        whiteLight = BufferUtils.createFloatBuffer(4);
        whiteLight.put(1.0f).put(1.0f).put(1.0f).put(1.0f).flip();


    }

}
