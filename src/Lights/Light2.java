package Lights;

/**
 * Created by Denis on 30/04/2015.
 */

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;

public class Light2 {



    private FloatBuffer light_ambient;
    private FloatBuffer light_diffuse;
    private FloatBuffer light_specular;
    private FloatBuffer light_position;
    private FloatBuffer spotDir;


    private FloatBuffer mat_ambient;
    private FloatBuffer mat_diffuse;
    private FloatBuffer mat_specular;
    private FloatBuffer high_shininess;


//    private FloatBuffer diffuse;
//    private FloatBuffer matSpecular;
//    private FloatBuffer lightPosition;
//    private FloatBuffer whiteLight;
//    private FloatBuffer lModelAmbient;

    public Light2(){
        initLightComponents();
    }
    public void on(){
    initGL();

    }

    public void off(){
        glDisable (GL_LIGHT0);
        glDisable (GL_NORMALIZE);
        glDisable (GL_COLOR_MATERIAL);
        glDisable (GL_LIGHTING);
    }

private void initGL() {


    glEnable (GL_NORMALIZE);
    glEnable (GL_LIGHTING);
    glLight(GL_LIGHT0, GL_AMBIENT, light_ambient);
    glLight(GL_LIGHT0, GL_DIFFUSE, light_diffuse);
    glLight(GL_LIGHT0, GL_SPECULAR, light_specular);
    glLight(GL_LIGHT0, GL_POSITION, light_position);
    glLight(GL_LIGHT0, GL_SPOT_DIRECTION, spotDir);
    glEnable (GL_LIGHT0);

    glEnable (GL_COLOR_MATERIAL);
    glMaterial(GL_FRONT_AND_BACK, GL_AMBIENT, mat_ambient);
    glMaterial(GL_FRONT_AND_BACK, GL_DIFFUSE, mat_diffuse);
    glMaterial(GL_FRONT_AND_BACK, GL_SPECULAR, mat_specular);
    glMaterialf(GL_FRONT_AND_BACK, GL_SHININESS, 50.0f);
    glLightf( GL_LIGHT0, GL_SPOT_CUTOFF, 45.0f);
    glLightf( GL_LIGHT0, GL_SPOT_EXPONENT, 100.0f);

    }


    public void initLightComponents(){
        light_ambient=asFloatBuffer(new float[]{0.0f, 0.0f, 0.0f, 1.0f});
        light_diffuse=asFloatBuffer(new float[]{1.0f, 1.0f, 1.0f, 1.0f });
        light_specular=asFloatBuffer(new float[]{ 1.0f, 1.0f, 1.0f, 1.0f});
        light_position=asFloatBuffer(new float[]{ 2.0f, 5.0f, 5.0f, 0.0f});
        spotDir=asFloatBuffer(new float[]{ 0.0f, 0.0f, 1.0f,0.0f});
        mat_ambient=asFloatBuffer(new float[]{ 0.7f, 0.7f, 0.7f, 1.0f});
        mat_diffuse=asFloatBuffer(new float[]{ 0.8f, 0.8f, 0.8f, 1.0f });
        mat_specular=asFloatBuffer(new float[]{ 1.0f, 1.0f, 1.0f, 1.0f});

    }

    public void initLightComponents2(){
        light_ambient=asFloatBuffer(new float[]{0.0f, 0.0f, 0.0f, 1.0f});
        light_diffuse=asFloatBuffer(new float[]{1.0f, 1.0f, 1.0f, 1.0f });
        light_specular=asFloatBuffer(new float[]{ 1.0f, 1.0f, 1.0f, 1.0f});
        light_position=asFloatBuffer(new float[]{ 2.0f, 5.0f, 5.0f, 0.0f});
        spotDir=asFloatBuffer(new float[]{ 0.0f, 0.0f, 1.0f,0.0f});
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

    public void setLight_ambient(float [] light_ambient) {
        this.light_ambient = asFloatBuffer(light_ambient);
    }

    public void setLight_diffuse(float [] light_diffuse) {
        this.light_diffuse = asFloatBuffer(light_diffuse);
    }

    public void setLight_position(float [] parameter) {
        this.light_position = asFloatBuffer(parameter);
    }

    public void setLight_specular(float [] parameter) {
        this.light_specular = asFloatBuffer(parameter);
    }

    public void setSpotDir(float [] parameter) {
        this.spotDir = asFloatBuffer(parameter);
    }

    public void setMat_ambient(float [] parameter) {
        this.mat_ambient = asFloatBuffer(parameter);
    }


    public void setMat_diffuse(float [] parameter) {
        this.mat_diffuse = asFloatBuffer(parameter);
    }

    public void setMat_specular(float [] parameter) {
        this.mat_specular = asFloatBuffer(parameter);
    }


    public void setHigh_shininess(FloatBuffer high_shininess) {
        this.high_shininess = high_shininess;
    }
}