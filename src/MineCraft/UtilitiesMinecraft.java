package MineCraft;
import static org.lwjgl.opengl.GL11.*;
import Collision.Objects.Vector;
/**
 * Created by Denis on 11/05/2015.
 */
public class UtilitiesMinecraft {

    public static void drawMalla(float numCubos,int sizeCubo){


        float sizeTotal=sizeCubo*numCubos;

        float numLineas=numCubos/sizeCubo;



        glColor3f (0.0f,1.0f,0.0f);
        int i,j,k;

        glPushMatrix();
        glBegin(GL_LINES);

        for(j=0;j<=numCubos;j++){
            glColor3f(1.0f,1.0f,1.0f);
            for (i=0; i<=numCubos; i++) {
                glVertex3f(0,sizeCubo*j,sizeCubo*i);
                glVertex3f(sizeTotal,sizeCubo*j,sizeCubo*i);
            }
            for (i=0; i<=numCubos; i++) {
                glVertex3f(sizeCubo*i,sizeCubo*j,0);
                glVertex3f(sizeCubo*i,sizeCubo*j,sizeTotal);
            }
        }
////Pintado eje y
//        for(j=0;j<=numCubos;j++){
//            glColor3f(1.0f,0.0f,0.0f);
//                for (i=0; i<=numCubos; i++) {
//                    glVertex3f(sizeCubo*i,0.0f,sizeCubo*j);
//                    glVertex3f(sizeCubo*i,sizeTotal,sizeCubo*j);
//                }
//            }



        glEnd();
        glPopMatrix();

    }

}