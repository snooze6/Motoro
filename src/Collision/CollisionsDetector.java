package Collision;

import Utilities.Vector;

import java.util.ArrayList;


public class CollisionsDetector {
    private ArrayList<BoundingBox> list;
    private int size = 0;
    private float delta = 0;
    private float distanceRay;
    private BoundingBox boundingBoxCollision;

    //-------------------------------------------------------------------------
    public CollisionsDetector(ArrayList<BoundingBox> list) {
        this.list = list;
        size = list.size();
    }

    public CollisionsDetector() {
        this.list = new ArrayList<BoundingBox>();
        size = 0;
    }
    //-------------------------------------------------------------------------

    public void add(BoundingBox ob) {
        list.add(ob);
        size++;
    }

    public void add(ArrayList<BoundingBox> ob) {
        for(int i=0;i<ob.size();i++){
            add(ob.get(i));
        }
    }

    public void del(BoundingBox ob) {
        list.remove(ob);
        size--;
    }

    //-------------------------------------------------------------------------

    public void collide(float delta) {
        if (delta > 119) {
            this.delta = 0;
        } else {
            this.delta = delta;
        }
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                collide(list.get(i), list.get(j));
            }
        }
    }

    public BoundingBox rayCollider(Vector origen,Vector direccion) {

        distanceRay=100000000;
        boundingBoxCollision =null;
        for (int i = 1; i < list.size(); i++) {
                if(list.get(i) instanceof BBSphere ){
                    boundingBoxCollision =collideRay(origen,direccion,(BBSphere)list.get(i));
                }
            }
        return boundingBoxCollision;
    }

    public Vector getPoint(Vector origen,Vector  direccion,BBSphere B,float size,float delta) {

        BBSphere white = new BBSphere(list.get(0).getCenterPoint(), Vector.prod(-1.0f, direccion), B.mass, B.getSize());

        BBSphere A = white;


        Vector puntoA = Vector.sum(A.getCenterPoint(), Vector.prod(delta, A.getVel()));
        Vector puntoB = Vector.sum(B.getCenterPoint(), Vector.prod(delta, B.getVel()));

        int i = 0;
        float distR;
        while ((distR = Vector.dist(puntoA, puntoB)) < (A.getSize() + B.getSize())) {
             puntoA = Vector.sum(A.getCenterPoint(), Vector.prod(delta, A.getVel()));
             puntoB = Vector.sum(B.getCenterPoint(), Vector.prod(delta, B.getVel()));
            delta=delta+0.01f;
        }
            Vector vel1, vel2, v1, v2, v1x, v2x, v1y, v2y, x;
            float m1, m2;
            //Calculo la direccion en la que ha penetrado A sobre B
//            Vector aux=Vector.norm(Vector.del(puntoB,puntoA));
//            //Sobre esa dirección, quiero
//            aux=Vector.prod(-A.getSize(),aux);
//            Vector puntoA2=Vector.sum(puntoB,aux);


            Vector puntoA2 = puntoA;
            Vector aux = Vector.norm(A.getVel());

            Vector puntoB2 = puntoB;
            Vector aux2 = Vector.norm(B.getVel());

            if (aux.x != 0 || aux.y != 0 || aux.z != 0) {
                while ((Vector.distPuntos(puntoA2, puntoB2)) <= (A.getSize() + B.getSize()) ) {
                    puntoA2 = Vector.sum(puntoA2, Vector.prod(-0.01f, aux));
                }
            }

            x = Vector.norm(Vector.del(puntoA2, puntoB2));
            v1 = A.getVel();
            v1x = Vector.prod(x, Vector.dot(x, v1));
            v1y = Vector.del(v1, v1x);
            m1 = A.getMass();

            x = Vector.prod(x, -1);
            v2 = B.getVel();
            v2x = Vector.prod(x, Vector.dot(x, v2));
            v2y = Vector.del(v2, v2x);
            m2 = B.getMass();

            vel1 = Vector.sum(v1y, Vector.sum(Vector.prod(v1x, ((m1 - m2) / (m1 + m2))), Vector.prod(v2x, ((2 * m2) / (m1 + m2)))));
            vel2 = Vector.sum(v2y, Vector.sum(Vector.prod(v1x, ((2 * m1) / (m1 + m2))), Vector.prod(v2x, ((m2 - m1) / (m1 + m2)))));

            return vel2;

        }



    public boolean collide(BoundingBox A, BoundingBox B) {
	    boolean aux = false;
        if (A instanceof BBPlane) {
            if (B instanceof BBPlane) {
            }
            if (B instanceof BBSphere) {
                aux = collide( (BBSphere) B,(BBPlane)A);
            }
        }

        if (A instanceof BBSphere) {
            if (B instanceof BBPlane) {
                aux =  collide((BBSphere)A, (BBPlane)B);
            }
            if (B instanceof BBSphere) {
                aux = collide((BBSphere) A, (BBSphere) B);
            }
        }

	    return aux;
    }

    public Vector collide2(BBSphere A, BBSphere B,Vector direccion) {
        delta=17;
        A = new BBSphere(A.getCenterPoint(),direccion,A.getMass(),A.getSize());


        Vector puntoA = Vector.sum(A.getCenterPoint(), Vector.prod(delta, A.getVel()));
        Vector puntoB = Vector.sum(B.getCenterPoint(), Vector.prod(delta,B.getVel()));


        float distR;
        while ((distR = Vector.dist(puntoA, puntoB)) > (A.getSize() + B.getSize())) {
            delta=delta+0.01f;
            puntoA = Vector.sum(A.getCenterPoint(), Vector.prod(delta, A.getVel()));
        }

            Vector vel1, vel2, v1, v2, v1x, v2x, v1y, v2y, x;
            float m1, m2;


            int i=0;
            Vector puntoA2=puntoA;
            Vector aux=Vector.norm(A.getVel());

            Vector puntoB2=puntoB;
            Vector aux2=Vector.norm(B.getVel());
            if(aux.x!=0 || aux.y!=0 || aux.z!=0){
                while(( Vector.distPuntos(puntoA2, puntoB2)) < (A.getSize() + B.getSize())){
                    puntoA2=Vector.sum(puntoA2,Vector.prod(-0.01f,aux));
                }
            }

            x = Vector.norm(Vector.del(puntoA2, puntoB2));
            v1 = A.getVel();
            v1x = Vector.prod(x, Vector.dot(x, v1));
            v1y = Vector.del(v1, v1x);
            m1 = A.getMass();

            x = Vector.prod(x, -1);
            v2 = B.getVel();
            v2x = Vector.prod(x, Vector.dot(x, v2));
            v2y = Vector.del(v2, v2x);
            m2 = B.getMass();

            vel1 = Vector.sum(v1y, Vector.sum(Vector.prod(v1x, ((m1 - m2) / (m1 + m2))), Vector.prod(v2x, ((2 * m2) / (m1 + m2)))));
            vel2 = Vector.sum(v2y, Vector.sum(Vector.prod(v1x, ((2 * m1) / (m1 + m2))), Vector.prod(v2x, ((m2 - m1) / (m1 + m2)))));
            return vel2;




    }

    public boolean collide(BBSphere A,BBPlane B) {
        float D1 = 0, D2 = 0, distancia = 0, distancia2;
        boolean ret = false;
        float angulo1 = 0;

        Vector punto = A.getCenterPoint(); //Punto actual
        Vector normalPlano = B.getNormal(); //Vector normal plano
        Vector puntoPlano = B.getCenterPoint(); //Punto plano
        angulo1 = Vector.ang(normalPlano, A.getVelocity()); //Angulo entre la normal y la velocidad con la que llega la esfera


        if (angulo1 <= 90) { //Segun llegue la esfera por un lado u otro del plano, giramos la normal
            normalPlano = Vector.prod(-1, normalPlano);
        }

        //Formula distancia punto plano actual
        D1 = Vector.dot(normalPlano, puntoPlano);
        D2 = Vector.dot(normalPlano, punto);

        // Formula distancia punto plano siguiente
        punto = Vector.sum(punto, Vector.prod(delta, A.getVel()));

        D1 = Vector.dot(normalPlano, puntoPlano);
        D2 = Vector.dot(normalPlano, punto);
        distancia2 = D2 - D1;

        distancia = Math.abs(D2 - D1);

        //Normalizar
        distancia = distancia / normalPlano.mod();

        Vector desplEsf;
        boolean dentro=true;

        if(B instanceof BBQuad ){
            dentro=((BBQuad) B).intersection2(A);
        }

        if(B instanceof BBQuadOld ){
            dentro=((BBQuadOld) B).intersection(A);
        }

        if (distancia <= A.getSize() && dentro) {
            ret = true;
        }

        return ret;
    }
    float distancia=0;



    public BoundingBox collideRay(Vector origen,Vector direccion,BBSphere B){

        float distancia;

        Vector aux;
        aux=Vector.del(B.getCenterPoint(),origen);
        aux=Vector.prod(aux,direccion);
        distancia=Vector.mod(aux);

        if(Vector.dot(direccion,Vector.del(B.getCenterPoint(),origen))>0){

        distancia=distancia/Vector.mod(direccion);
        float distanciaOrigen=Math.abs(Vector.dist(B.getCenterPoint(),origen));
       if(distancia<B.getSize()*2 && distanciaOrigen<distanceRay){
           distanceRay=distanciaOrigen;
           boundingBoxCollision =B;
       }
        }
        return boundingBoxCollision;
    }

}






