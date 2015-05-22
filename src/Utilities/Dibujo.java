package Utilities;

import static org.lwjgl.opengl.GL11.*;

public class Dibujo {
	
	public static void drawCube(float size){

	        glBegin(GL_QUADS);                // Begin drawing the color cube with 6 quads
	        // Top face (y = size)
	        glColor3f(1.0f, 1.0f, 0.0f);     // Yellow
	        glVertex3f(size, size, -size);
	        glVertex3f(-size, size, -size);
	        glVertex3f(-size, size, size);
	        glVertex3f(size, size, size);

	        // Bottom face (y = -size)
	        glColor3f(1.0f, 1.0f, 1.0f);     // White
	        glVertex3f(size, -size, size);
	        glVertex3f(-size, -size, size);
	        glVertex3f(-size, -size, -size);
	        glVertex3f(size, -size, -size);
	        // Front face  (z = size)
	        glColor3f(1.0f, 0.0f, 0.0f);     // Red
	        glVertex3f(size, size, size);
	        glVertex3f(-size, size, size);
	        glVertex3f(-size, -size, size);
	        glVertex3f(size, -size, size);

	        // Back face (z = -size)
	        glColor3f(1.0f, 0.6f, 0.0f);     // Orange
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
	        glColor3f(0.0f, 1.0f, 0.0f);     // Green
	        glVertex3f(size, size, -size);
	        glVertex3f(size, size, size);
	        glVertex3f(size, -size, size);
	        glVertex3f(size, -size, -size);
	        glEnd();  // End of drawing color-cube
	}

	
	public static void drawSphere(double r, int lats, int longs) {
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
    	//s.draw((float)r, lats, longs);
    }
    public static void drawAxes(float longitud){
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
    public static void drawMalla(float tam){
    	glColor3f (0.25f,0.25f,0.25f);
    	int i;
    	glBegin(GL_LINES);
    		for (i=0; i<=(2*tam/25); i++) {
    			glVertex3f(-tam,0.0f,-tam+25*i);
    			glVertex3f(tam,0.0f,-tam+25*i);
    		}
    		for (i=0; i<=(2*tam/25); i++) {
    			glVertex3f(-tam+25*i,0.0f,-tam);
    			glVertex3f(-tam+25*i,0.0f,tam);
    		}
    	glEnd();
    }
    public static void drawMalla(float numCubos,int sizeCubo){


        float sizeTotal=sizeCubo*numCubos;



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
