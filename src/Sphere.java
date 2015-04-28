
import static org.lwjgl.opengl.GL11.*;
import java.lang.Math;

/**
 * Created by Denis on 16/03/2015.
 */
public class Sphere {


    void drawSphere(double r, int lats, int longs) {
        int i, j;
        for(i = 0; i <= lats; i++) {
            double lat0 = Math.PI * (-0.5 + (double) (i - 1) / lats);
            double z0  = Math.sin(lat0);
            double zr0 =  Math.cos(lat0);

            double lat1 = Math.PI * (-0.5 + (double) i / lats);
            double z1 =  Math.sin(lat1);
            double zr1 = Math.cos(lat1);

            glBegin(GL_QUAD_STRIP);
            for(j = 0; j <= longs; j++) {
                double lng = 2 * Math.PI * (double) (j - 1) / longs;
                double x = r*Math.cos(lng);
                double y = r*Math.sin(lng);

                glNormal3d(x * zr0, y * zr0, r*z0);
                glVertex3d(x * zr0, y * zr0, r*z0);
                glNormal3d(x * zr1, y * zr1, r*z1);
                glVertex3d(x * zr1, y * zr1, r*z1);
            }
            glEnd();
        }
    }
}
