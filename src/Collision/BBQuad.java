package Collision;

import Billiard.World.Ball;
import Utilities.*;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glPopMatrix;



/**
 * Created by Denis on 12/05/2015.
 */
public class BBQuad extends BBPlane  {
    public Vector position;
    public Vector punto1,punto2,punto3,punto4;
    public Vector vector1,vector2;
    public Vector normal;
    public BBPlane plane;
    public float size;
    public float size2;
    public float sizeDraw;
    public int constant;


public BBQuad(Vector punto1, Vector punto2, Vector punto3, Vector punto4){

    this.punto1=punto1;
    this.punto2=punto2;
    this.punto3=punto3;
    this.punto4=punto4;
    size=(Vector.mod(Vector.del(punto1,punto2)));
    size2=(Vector.mod(Vector.del(punto2,punto3)));
    this.vector1= Vector.del(punto1,punto2);
    this.vector2=Vector.del(punto3,punto2);
    vector1=Vector.norm(vector1);
    vector2=Vector.norm(vector2);
    this.position=Vector.sum(punto2, Vector.prod(size/2, Vector.norm(vector1)));
    this.position=Vector.sum(position,Vector.prod(size2/2,Vector.norm(vector2)));
    this.normal=Vector.prod(vector1,vector2);
//    normal.print();
}

    public void move(Vector traslacion){
        punto1=Vector.sum(punto1,traslacion);
        punto2=Vector.sum(punto2,traslacion);
        punto3=Vector.sum(punto3,traslacion);
        punto4=Vector.sum(punto4,traslacion);
        position= Vector.sum(position,traslacion);
    }

    public Vector getCenterPoint(){
        return this.position;
    }

    public void draw(){
        glPushMatrix();
        if(constant==0){
      //  glRotatef(90.0f, 1, 0, 0);
      }

        glBegin(GL_QUADS);                // Begin drawing the color cube with 6 quads
        glColor3f(0.0f, 1.0f, 0.0f);     // Orange
            glVertex3f( punto1.x, punto1.y,  punto1.z);
            glVertex3f( punto2.x, punto2.y,  punto2.z);
            glVertex3f( punto3.x, punto3.y,  punto3.z);
            glVertex3f( punto4.x, punto4.y,  punto4.z);
        glEnd();

if(constant == 0){
            glPushMatrix();
            glTranslatef(punto1.x, punto1.y, punto1.z);
            punto1 = Ball.getVectorPosition();

            glPopMatrix();

            glPushMatrix();
            glTranslatef(punto2.x, punto2.y, punto2.z);
            punto2 = Ball.getVectorPosition();

            glPopMatrix();

            glPushMatrix();
            glTranslatef(punto3.x, punto3.y, punto3.z);
            punto3 = Ball.getVectorPosition();

            glPopMatrix();

            glPushMatrix();
            glTranslatef(punto4.x, punto4.y, punto4.z);
            punto4 = Ball.getVectorPosition();
            glPopMatrix();
            constant=1;


    size=(Vector.mod(Vector.del(punto1,punto2)));
    size2=(Vector.mod(Vector.del(punto2,punto3)));
    this.vector1= Vector.del(punto1,punto2);
    this.vector2=Vector.del(punto3,punto2);
    vector1=Vector.norm(vector1);
    vector2=Vector.norm(vector2);
    this.position=Vector.sum(punto2, Vector.prod(size/2, Vector.norm(vector1)));
    this.position=Vector.sum(position,Vector.prod(size2/2,Vector.norm(vector2)));
    this.normal=Vector.prod(vector1,vector2);
        }

        glPopMatrix();




//        glPushMatrix();
//        glTranslated(0,0,-100);
//        glBegin(GL_QUADS);                // Begin drawing the color cube with 6 quads
//        glColor3f(0.0f, 1.0f, 0.0f);     // Orange
//        glVertex3f( punto1.x, punto1.y,  punto1.z);
//        glVertex3f( punto2.x, punto2.y,  punto2.z);
//        glVertex3f( punto3.x, punto3.y,  punto3.z);
//        glVertex3f( punto4.x, punto4.y,  punto4.z);
//        glEnd();
//        glPopMatrix();

    }

    public Vector getNormal() {
        return normal;
    }


    public boolean intersection2( BBSphere A){
      float  D1,D2;
        float distancia1,distancia2;

        D1 = Vector.dot(vector1, position);
        D2 = Vector.dot(vector1, A.getCenterPoint());
        distancia1 = Math.abs(D2 - D1)-A.getSize();
        D1 = Vector.dot(vector2, position);
        D2 = Vector.dot(vector2, A.getCenterPoint());
        distancia2 = Math.abs(D2 - D1)-A.getSize();

//
//        System.out.print("El vector 1 es  ");
//        vector1.print();
//        System.out.print("El vector 2 es  ");
//        vector2.print();
//        System.out.println("La distancia 1 es " + distancia1);
//        System.out.println("La distancia 2 es " + distancia2);

        if(distancia1>=size/2 || distancia2>=size2/2){
            return false;
        }
            else{

            return true;
        }

    }


}