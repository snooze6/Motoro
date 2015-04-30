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
import java.lang.String;import java.lang.StringBuilder;import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import static org.lwjgl.opengl.GL11.*;

public class Light {



    private FloatBuffer light_ambient;
    private FloatBuffer light_diffuse;
    private FloatBuffer light_specular;
    private FloatBuffer light_position;




    private FloatBuffer mat_ambient;
    private FloatBuffer mat_diffuse;
    private FloatBuffer mat_specular;
    private FloatBuffer high_shininess;


    private FloatBuffer diffuse;
    private FloatBuffer matSpecular;
    private FloatBuffer lightPosition;
    private FloatBuffer whiteLight;
    private FloatBuffer lModelAmbient;

    public Light(){

    }
    public void on(){
        //<Variables para las luces>
        initLightArrays();
    initGL();


    }

    public void off(){
        glDisable (GL_LIGHT0);
        glDisable (GL_NORMALIZE);
        glDisable (GL_COLOR_MATERIAL);
        glDisable (GL_LIGHTING);
    }

private void initGL() {
//    initLightArrays();
//    glShadeModel(GL_SMOOTH);
//    glMaterial(GL_FRONT, GL_SPECULAR, matSpecular);				// sets specular material color
//    glMaterialf(GL_FRONT, GL_SHININESS, 50.0f);					// sets shininess
//
//    glLight(GL_LIGHT0, GL_POSITION, lightPosition);
//    glLight(GL_LIGHT0, GL_AMBIENT, lightPosition);// sets light position
//    glLight(GL_LIGHT0, GL_SPECULAR, whiteLight);				// sets specular light to white
//    glLight(GL_LIGHT0, GL_DIFFUSE, whiteLight);					// sets diffuse light to white
//    glLightModel(GL_LIGHT_MODEL_AMBIENT, lModelAmbient);		// global ambient light
//    glEnable(GL_LIGHTING);										// enables lighting
//    glEnable(GL_LIGHT0);										// enables light0
//
//    glEnable(GL_COLOR_MATERIAL);								// enables opengl to use glColor3f to define material color
//    glColorMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE);			// tell opengl glColor3f effects the ambient and diffuse properties of material
    initLightComponents();
    glEnable (GL_NORMALIZE);
    glEnable (GL_LIGHTING);
    glLight(GL_LIGHT0, GL_AMBIENT, light_ambient);
    glLight(GL_LIGHT0, GL_DIFFUSE, light_diffuse);
    glLight(GL_LIGHT0, GL_SPECULAR, light_specular);
    glLight(GL_LIGHT0, GL_POSITION, light_position);
    glEnable (GL_LIGHT0);

    glEnable (GL_COLOR_MATERIAL);
    glMaterial(GL_FRONT_AND_BACK, GL_AMBIENT, mat_ambient);
    glMaterial(GL_FRONT_AND_BACK, GL_DIFFUSE, mat_diffuse);
    glMaterial(GL_FRONT_AND_BACK, GL_SPECULAR, mat_specular);
    glMaterialf(GL_FRONT_AND_BACK, GL_SHININESS, 100.0f);

    }
    private void initLightArrays() {


        lModelAmbient = BufferUtils.createFloatBuffer(4);
        lModelAmbient.put(1.0f).put(0.05f).put(0.05f).put(1.0f).flip();

        lightPosition = BufferUtils.createFloatBuffer(4);
        lightPosition.put(0.0f).put(0.0f).put(0.0f).put(1.0f).flip();


        matSpecular = BufferUtils.createFloatBuffer(4);
        matSpecular.put(1.0f).put(1.0f).put(1.0f).put(1.0f).flip();

       diffuse = BufferUtils.createFloatBuffer(4);
        diffuse.put(1.0f).put(1.0f).put(1.0f).put(1.0f).flip();



        whiteLight = BufferUtils.createFloatBuffer(4);
        whiteLight.put(1.0f).put(1.0f).put(1.0f).put(1.0f).flip();


    }

    public void initLightComponents(){
        light_ambient=asFloatBuffer(new float[]{0.0f, 0.0f, 0.0f, 1.0f});
        light_diffuse=asFloatBuffer(new float[]{1.0f, 1.0f, 1.0f, 1.0f });
        light_specular=asFloatBuffer(new float[]{ 1.0f, 1.0f, 1.0f, 1.0f});
        light_position=asFloatBuffer(new float[]{ 2.0f, 5.0f, 5.0f, 0.0f});


        mat_ambient=asFloatBuffer(new float[]{ 0.7f, 0.7f, 0.7f, 1.0f});
        mat_diffuse=asFloatBuffer(new float[]{ 0.8f, 0.8f, 0.8f, 1.0f });
        mat_specular=asFloatBuffer(new float[]{ 1.0f, 1.0f, 1.0f, 1.0f});

    }

    public static FloatBuffer asFloatBuffer(float [] values) {

        FloatBuffer buffer = BufferUtils.createFloatBuffer(values.length);
        buffer.put(values);
        buffer.flip();
        return buffer;
    }

}
