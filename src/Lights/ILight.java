package Lights;

/**
 * Created by Denis on 30/04/2015.
 */
public interface ILight {

    public void on();
    public void off();
    public void setColour(float r,float g,float b);
    public void setAmbient();
    public void setDiffuse();

}
