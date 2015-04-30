package Lights;


        import static org.lwjgl.opengl.GL11.*;

public class HappyEaster {


    private static float[] lightPosition = {-2.19f, 1.36f, 11.45f, 1f};
 // private   FloatBuffer qaAmbientLight  = floatBuffer(0.0f, 0.0f, 0.0f, 1.0f);


    private static void setUpLighting() {
        glShadeModel(GL_SMOOTH);
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_LIGHTING);
        glEnable(GL_LIGHT0);
       // glLightModel(GL_LIGHT_MODEL_AMBIENT, lightPosition);
       // glLight(GL_LIGHT0, GL_POSITION, BufferTools.asFlippedFloatBuffer(new float[]{0, 0, 0, 1}));
        glEnable(GL_CULL_FACE);
        glCullFace(GL_BACK);
        glEnable(GL_COLOR_MATERIAL);
        glColorMaterial(GL_FRONT, GL_DIFFUSE);
    }



}