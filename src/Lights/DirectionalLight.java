package Lights;

/**
 * Created by Denis on 30/04/2015.
 */

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;

public class DirectionalLight implements ILight {



    private FloatBuffer light_ambient;
    private FloatBuffer light_diffuse;
    private FloatBuffer light_specular;
    private FloatBuffer light_position;
    private FloatBuffer light_global;


    private FloatBuffer mat_ambient;
    private FloatBuffer mat_diffuse;
    private FloatBuffer mat_specular;
    private FloatBuffer mat_emission;
    private FloatBuffer high_shininess;

    //spotlight
    private FloatBuffer spotDir;
    private float cutoff;
    private float exponent;
    private float atenuation;



    public DirectionalLight(){
        initLightComponents();

    }

    public DirectionalLight(float[] position){
        initLightComponents();
        setLight_position(position);


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

public void initGL() {


    glEnable (GL_NORMALIZE);
    glEnable (GL_LIGHTING);
    glLightModel(GL_LIGHT_MODEL_AMBIENT, light_global);
    glLight(GL_LIGHT0, GL_AMBIENT, light_ambient);
    glLight(GL_LIGHT0, GL_DIFFUSE, light_diffuse);
    glLight(GL_LIGHT0, GL_SPECULAR, light_specular);
    glLight(GL_LIGHT0, GL_POSITION, light_position);
    
    glCullFace(GL_BACK);

    glEnable(GL_LIGHT0);
    glMaterial(GL_FRONT_AND_BACK, GL_AMBIENT, mat_ambient);
    glMaterial(GL_FRONT_AND_BACK, GL_DIFFUSE, mat_diffuse);
    glMaterial(GL_FRONT_AND_BACK, GL_SPECULAR, mat_specular);
    glMaterialf(GL_FRONT_AND_BACK, GL_SHININESS, 50.0f);
    glEnable (GL_COLOR_MATERIAL);

    }


    public void initLightComponents(){
        light_global=asFloatBuffer(new float[]{ 0.1f, 0.1f, 0.1f, 1.0f});//Luz global
        light_ambient=asFloatBuffer(new float[]{0.3f, 0.3f, 0.3f, 1.0f});
        light_position=asFloatBuffer(new float[]{ 0.0f, 200.0f, 0.0f, 1.0f}); //Posicion de la luz, ultimo parametro a 0 indica foco
        light_diffuse=asFloatBuffer(new float[]{1.0f, 1.0f, 1.0f, 1.0f });
        light_specular=asFloatBuffer(new float[]{ 1.0f, 1.0f, 1.0f, 1.0f});


        mat_ambient=asFloatBuffer(new float[]{ 0.7f, 0.7f, 0.7f, 1.0f});
        mat_diffuse=asFloatBuffer(new float[]{ 0.7f, 0.7f, 0.7f, 1.0f });
        mat_specular=asFloatBuffer(new float[]{ 0.7f, 0.7f, 0.7f, 1.0f});



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
        light_position.put(3,0.0f);
        glLight(GL_LIGHT0, GL_POSITION, light_position);
    }

    public void setLight_specular(float [] parameter) {
        this.light_specular = asFloatBuffer(parameter);
        glLight(GL_LIGHT0, GL_SPECULAR, light_specular);
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

    @Override
    public void setExponent(float exponent) {

    }

    @Override
    public float getCutoff() {
        return 0;
    }

    @Override
    public float getExponent() {
        return 0;
    }

    @Override
    public void setCutoff(float cutoff) {

    }

    @Override
    public void setSpotDir(float[] parameter) {

    }

    @Override
    public float getAtenuation() {
        return 0;
    }

    @Override
    public void setAtenuation(float atenuation) {

    }

    @Override
    public void setEmission(float[] parameter) {
        this.mat_emission = asFloatBuffer(parameter);
        glMaterial(GL_FRONT_AND_BACK, GL_EMISSION, mat_emission);
    }
    @Override
    public void disableEmission() {
        float[] parameter ={0,0,0,0};
        this.mat_emission = asFloatBuffer(parameter);
        glMaterial(GL_FRONT_AND_BACK, GL_EMISSION, mat_emission);
    }
}
