package Collision.Objects;

public class Vector {

	/*
	 * ¿Qué se puede hacer con un vector en R^3?
	 * Esperamos que esta clase resuelva sus dudas
	 *
	 * Bibliografía: http://www.vitutor.net/1/vectores_espacio.html
	 */

    public float x, y, z;

    public Vector(){
        x=0;
        y=0;
        z=0;
    }

    public Vector(float x,float y, float z) {
        this.x=x;
        this.y=y;
        this.z=z;
    }
    public Vector(Vector a) {
        this.x=a.x;
        this.y=a.y;
        this.z=a.z;
    }


    public float mod(){
        return (float)Math.sqrt(x*x+y*y+z*z);
    }
    public static float mod(Vector A){
        return (float)Math.sqrt(A.x*A.x+A.y*A.y+A.z*A.z);
    }

    public void print(){
        System.out.println("[Vector] - [X:"+x+"][Y:"+y+"][Z:"+z+"]");
    }

    //--------------------------------------------------------------------------

    public static Vector ejex = new Vector(1,0,0);
    public static Vector ejey = new Vector(0,1,0);
    public static Vector ejez = new Vector(0,0,1);

    public static Vector norm(Vector v){
        if (v.mod()!=0){
            return new Vector(v.x/v.mod(), v.y/v.mod(), v.z/v.mod());
        } else {
            return new Vector(0,0,0);
        }
    }

    public static Vector norm(float x,float y,float z){
        Vector v = new Vector(x,y,z);
        if (v.mod()!=0){
            return new Vector(v.x/v.mod(), v.y/v.mod(), v.z/v.mod());
        } else {
            return new Vector(0,0,0);
        }
    }

    public static Vector sum(Vector a, Vector b){
        return new Vector(a.x+b.x, a.y+b.y, a.z+b.z);
    }

    public static Vector sum(Vector a, float x,float y,float z){
        return new Vector(a.x+x, a.y+y, a.z+z);
    }

    public static Vector sum(float ax,float ay,float az, float bx, float by,float bz){
        return new Vector(ax+bx, ay+by, az+bz);
    }

    public static Vector del(Vector a, Vector b){
        return new Vector(a.x-b.x, a.y-b.y, a.z-b.z);
    }

    public static Vector del(Vector a, float a1, float b1,float c1){
        return new Vector(a.x-a1, a.y-b1, a.z-c1);
    }

    public static Vector del(float ax,float ay,float az, float bx, float by,float bz){
        return new Vector(ax-bx, ay-by, az-bz);
    }

    public static Vector prod(float ax,float ay,float az, float bx, float by,float bz){
        return new Vector(ay*bz - by*az,-1*(ax*bz - bx*az),ax*by - bx*ay);
    }

    public static Vector prod(Vector a, Vector b){
        return new Vector(a.y*b.z - b.y*a.z,-1*(a.x*b.z - b.x*a.z),a.x*b.y - b.x*a.y);
    }

    public static Vector prod(Vector a, float b){
        return new Vector(a.x*b, a.y*b, a.z*b);
    }

    public static Vector prod(float a, Vector b){
        return new Vector(a*b.x, a*b.y, a*b.z);
    }



    public static Vector mult(Vector a, Vector b){
        return new Vector(a.x*b.x, a.y*b.y, a.z*b.z);
    }

    public static Vector mult(float ax,float ay,float az, float bx, float by,float bz){
        return new Vector(ax*bx, ay*by, az*bz);
    }

    public static float dist(Vector a, Vector b){
        return (sum(a, prod(b, -1))).mod();
    }

    public static float dist(Vector a,float bx,float by,float bz){
        Vector b= new Vector(bx,by,bz);
        return dist(a,b);
    }

    public static float dot(Vector a, Vector b){
        return (a.x*b.x + a.y*b.y + a.z*b.z);
    }

    public static float dot(float ax,float ay,float az, float bx, float by,float bz){
        return (ax*bx + ay*by + az*bz);
    }

    public static float ang(Vector a, Vector b){
        if(a.mod()>0 && b.mod()>0){
            return (float) Math.toDegrees(Math.acos((dot(a,b))/(a.mod() * b.mod())));
        } else {
            return 0;
        }
    }
}