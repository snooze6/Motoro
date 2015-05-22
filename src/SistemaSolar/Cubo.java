package SistemaSolar;
import static org.lwjgl.opengl.GL11.*;


/**
 * Created by Denis on 15/03/2015.
 */
public class Cubo {
    
    public Cubo(){}
    
    public void drawCube(float size) {

        glBegin(GL_QUADS);                // Begin drawing the color cube with 6 quads
        // Top face (y = size)
        // Define vertices in counter-clockwise (CCW) order with normal pointing out
        glColor3f(0.0f, 1, 0.0f);     // Green
        glVertex3f(size, size, -size);
        glVertex3f(-size, size, -size);
        glVertex3f(-size, size, size);
        glVertex3f(size, size, size);

        // Bottom face (y = -size)
        glColor3f(1, 0.5f, 0.0f);     // Orange
        glVertex3f(size, -size, size);
        glVertex3f(-size, -size, size);
        glVertex3f(-size, -size, -size);
        glVertex3f(size, -size, -size);
        // Front face  (z = size)
        glColor3f(1, 0.5f, 0.3f);     // Red
        glVertex3f(size, size, size);
        glVertex3f(-size, size, size);
        glVertex3f(-size, -size, size);
        glVertex3f(size, -size, size);

        // Back face (z = -size)
        glColor3f(1.0f, 0.0f, 1.0f);     // Yellow
        glVertex3f(size, -size, -size);
        glVertex3f(-size, -size, -size);
        glVertex3f(-size, size, -size);
        glVertex3f(size, size, -size);

        // Left face (x = -size)
        glColor3f(0.0f, 0.0f, 1.0f);     // Blue
        glVertex3f(-size, size, size);
        glVertex3f(-size, size, -size);
        glVertex3f(-size, -size, -size);
        glVertex3f(-size, -size, size);

        // Right face (x = size)
        glColor3f(1.0f, 1.0f, 1.0f);     // Magenta
        glVertex3f(size, size, -size);
        glVertex3f(size, size, size);
        glVertex3f(size, -size, size);
        glVertex3f(size, -size, -size);
        glEnd();  // End of drawing color-cube
    }
}
