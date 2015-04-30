package SistemaSolar;

import SistemaSolar.*;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by Denis on 23/03/2015.
 */
public class Planeta {
    float posicion;
    float velocidadRotacion;
    float velocidadTranslacion;
    float tamano;
    int lista;
    float AnguloRotacion;
    float AnguloTranslacion;
    Esfera sphere= new Esfera();

    Planeta(float posicion,float velocidadRotacion,float velocidadTranslacion, float tamano){
        this.AnguloRotacion =0;
        this.posicion=posicion;
        this.velocidadRotacion=velocidadRotacion;
        this.velocidadTranslacion=velocidadTranslacion;
         this.tamano=tamano;
        this.CrearLista();
    }

    void CrearLista(){
        this.lista = glGenLists(1);
        glNewList(lista, GL_COMPILE);
        sphere.drawSphere();
        glEndList();
    }
    void drawPlaneta(){
        AnguloRotacion += velocidadRotacion;
        if (AnguloRotacion > 360)
            AnguloRotacion -= 360.0f;

        AnguloTranslacion += velocidadTranslacion;
        if (AnguloTranslacion > 360)
            AnguloTranslacion -= 360.0f;

        glPushMatrix();
        glRotatef( AnguloTranslacion, 0.0f, 1.0f, 0.0f);
        glTranslatef(0.0f, 0.0f, posicion);
        glRotatef(AnguloRotacion, 0.0f, 1.0f, 0.0f);
        glScaled(tamano,tamano,tamano);
        glCallList(this.getLista());
        glPopMatrix();
    }
    int getLista(){
        return this.lista;
    }
}
