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
        float x, y, z, dTheta=180/lats, dLon=360/longs;
        
        //glScalef((float)r, (float)r, (float)r);
        for(float lat =0 ; lat <=180 ; lat+=dTheta){
            glBegin( GL_QUAD_STRIP ) ;
            for(float lon = 0 ; lon <=360; lon+=dLon){  
            	// Vertex 1
                x = (float) (r*Math.cos(Math.toRadians(lon)) * Math.sin(Math.toRadians(lat))) ;
                y = (float) (r*Math.sin(Math.toRadians(lon)) * Math.sin(Math.toRadians(lat))) ;
                z = (float) (r*Math.cos(Math.toRadians(lat))) ;
                glNormal3f( x, y, z) ;
                glTexCoord2d( lon/360-0.25, lat/180);
                glVertex3f( x, y, z ) ;
                
                // Vertex 2
                x = (float) (r*Math.cos(Math.toRadians(lon)) * Math.sin(Math.toRadians(lat+dTheta))) ;
                y = (float) (r*Math.sin(Math.toRadians(lon)) * Math.sin(Math.toRadians(lat+dTheta))) ;
                z = (float) (r*Math.cos(Math.toRadians(lat+dTheta))) ;
                glNormal3f( x, y, z) ;
                glTexCoord2d(lon/360-0.25, (lat + dTheta-1)/(180)); 
                glVertex3f( x, y, z ) ;
                
                // Vertex 3
                x = (float) (r*Math.cos(Math.toRadians(lon+dLon)) * Math.sin(Math.toRadians(lat))) ;
                y = (float) (r*Math.sin(Math.toRadians(lon+dLon)) * Math.sin(Math.toRadians(lat))) ;
                z = (float) (r*Math.cos(Math.toRadians(lat))) ;
                glNormal3f( x, y, z) ;
                glTexCoord2d((lon + dLon)/(360)-0.25 ,(lat)/180);
                glVertex3f( x, y, z ) ;
                
                // Vertex 4
                x = (float) (r*Math.cos(Math.toRadians(lon+dLon)) * Math.sin(Math.toRadians(lat+dTheta))) ;
                y = (float) (r*Math.sin(Math.toRadians(lon+dLon)) * Math.sin(Math.toRadians(lat+dTheta))) ;
                z = (float) (r*Math.cos(Math.toRadians(lat+dTheta))) ;
                glNormal3f( x, y, z) ;
                glTexCoord2d((lon + dLon)/360-0.25, (lat + dTheta)/(180));
                glVertex3f( x, y, z ) ;
            }
            glEnd() ;
        }
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
//		  //Pintado eje y
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
    
    public static void drawRay(Vector pos, Vector v){
    	drawRay(pos, v, 1000);
    }
    
    public static void drawRay(Vector pos, Vector v, int l){
    	Vector aux = Vector.prod(l, Vector.norm(Vector.sum(pos, v)));
    	glPushMatrix();
    	  glBegin(GL_LINES);
    	    glVertex3f(pos.x, pos.y, pos.z);
    	    glVertex3f(aux.x, aux.y, aux.z);
    	  glEnd();
    	glPopMatrix();
    }
    
    public static void drawPoint(Vector v){
    	drawPoint(v,20);
    }
    
    public static void drawPoint(Vector v, int s){
        glPointSize(s);
	        glBegin(GL_POINTS);
	        	glColor3f(0,1,1);
	            glVertex3f( v.x,  v.y, v.z);
	            glNormal3f(-v.x, -v.y, v.z) ;
	        glEnd();
        glPointSize(1.0f);
    }
}
