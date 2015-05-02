package Lights;

import java.nio.FloatBuffer;

/**
 * Created by Denis on 30/04/2015.
 */
public interface ILight {

    public void on();
    public void off();


    void initGL();

    void initLightComponents();

    void setLight_ambient(float[] light_ambient);

    void setLight_diffuse(float[] light_diffuse);

    void setLight_position(float[] parameter);

    void setLight_specular(float[] parameter);

    void setMat_ambient(float[] parameter);

    void setMat_diffuse(float[] parameter);

    void setMat_specular(float[] parameter);

    void setHigh_shininess(FloatBuffer high_shininess);

    void setExponent(float exponent);

    float getCutoff();

    float getExponent();

    void setCutoff(float cutoff);

    void setSpotDir(float[] parameter);

    float getAtenuation();

    void setAtenuation(float atenuation);

    void setEmission(float[] parameter);

    void disableEmission();
}
