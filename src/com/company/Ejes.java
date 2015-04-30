package com.company;
        import static org.lwjgl.opengl.GL11.*;

/**
 * Created by Denis on 11/03/2015.
 */
public class Ejes {

    public Ejes(){

    }
    void drawAxes(float longitud){
        //DIBUJO LOS EJES
        glBegin(GL_LINES);
        //Rojo x
        glColor3f(1.0f, 0.0f, 0.0f);
        glVertex3f(0.0f, 0.0f, 0.0f);
        glVertex3f(longitud, 0.0f, 0.0f);
        //Verde y
        glColor3f(0.0f, 1.0f, 0.0f);
        glVertex3f(0.0f, 0.0f, 0.0f);
        glVertex3f(0.0f, longitud, 0.0f);
        //Azul z
        glColor3f(1.0f, 1.0f, 1.0f);
        glVertex3f(0.0f, 0.0f, 00.0f);
        glVertex3f(0.0f, 0.0f, longitud);
        glEnd();
        //Termino de dibujar los ejes
    }

}
