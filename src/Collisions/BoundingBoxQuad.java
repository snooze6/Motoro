package Collisions;

import Collision.Objects.Esfera;
import Collision.Objects.IBoundingBox;
import Collision.Objects.Plano;
import Collision.Objects.Vector;

/**
 * Created by Denis on 12/05/2015.
 */
public class BoundingBoxQuad   extends Plano implements IBoundingBox{
    private Vector position;
    private Vector punto;
    private Vector normal;
    private Plano plane;
    private float size;

public BoundingBoxQuad(Vector normal, Vector punto, float size){
    super(normal,punto,size);
    this.normal=normal;
    this.punto=punto;
    this.size=size;
  //  punto.print();
}

    public Vector getPoint1(){
        return Vector.sum(punto, -size / 2.0f, 0.0f, -size / 2.0f);
    }

    public Vector getPoint2(){
        return Vector.sum(punto, size / 2.0f, 0.0f, -size / 2.0f);
    }
    public Vector getPoint3(){
        return Vector.sum(punto, -size / 2.0f, 0.0f, size / 2.0f);
    }

    public Vector getPoint4(){
        return Vector.sum(punto, size / 2.0f, 0.0f, size / 2.0f);
    }

    public float getPoint1x(){
        return punto.x-size;
    }

    public float getPoint2x(){
        return punto.x+size;
    }
    public float getPoint1z(){
        return punto.z-size;
    }

    public float getPoint2z(){
        return punto.z+size;
    }

    public boolean intersection(Vector intersection){
        if(intersection.x-getPoint1x()>10 && intersection.x<getPoint2x() && intersection.z>getPoint1z() && intersection.z<getPoint2z()){
            return true;
        }
        return false;
    }

    public boolean intersection(Vector intersection, Esfera A){

       float distanciaX,distanciaZ;
        float puntoX= getPoint().x;
        float puntoZ=getPoint().z;
        distanciaX=Math.abs(puntoX-A.getPoint().x)-A.getSize();

        distanciaZ=Math.abs(puntoZ-A.getPoint().z)-A.getSize();
//        System.out.println(distanciaX + "      " + size/2.0f);
        if(distanciaX>size/2 || distanciaZ>size/2.0f)
            return false;
        return true;
    }

}