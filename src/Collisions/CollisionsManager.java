package Collisions;

import java.util.ArrayList;

import Collision.Objects.Esfera;
import Collision.Objects.Plano;
import Collision.Objects.Vector;
import org.lwjgl.Sys;

/**
 * Created by Denis on 30/04/2015.
 */
public class CollisionsManager {
    int p = 0;
    private ArrayList<IBoundingBox> list;
    private int size = 0;
    private float delta = 0;

    //-------------------------------------------------------------------------

    public CollisionsManager(ArrayList<IBoundingBox> list) {
        super();
        this.list = list;
        size = list.size();
    }

    public CollisionsManager() {
        super();
        this.list = new ArrayList<IBoundingBox>();
        size = 0;
    }

    //-------------------------------------------------------------------------

    public void add(IBoundingBox ob) {
        int i = 0;
        list.add(ob);
        size++;
    }

    public void del(IBoundingBox ob) {
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
        int position = 0;
        for (int i = 0; i < list.size(); i++) {
            for (int j = i + 1; j < list.size(); j++) {
                collide(list.get(i), list.get(j), position);
                position++;
            }
        }
    }

    public boolean collide(IBoundingBox A, IBoundingBox B, int ij) {

        boolean aux = false;
        if (true) {
            if (A instanceof Plano) {
                if (B instanceof Plano) {
                    //System.out.println("[Main]: Colisión entre planos aún no implementada");
                }
                if (B instanceof Esfera) {
                    aux = collide((Plano) B, (Esfera) A);
                }
            }
            if (A instanceof Esfera) {
                if (B instanceof Plano) {
                    aux =  collide((Esfera)A, (Plano)B);
                   // aux =  collideP((IBoundingBox) A, (IBoundingBox) B);
                }
                if (B instanceof Esfera) {
                    //System.out.println("[Main]: Colisión entre esferas aún no implementada");
                    aux = collide((Esfera) A, (Esfera) B);
                }
            }

            return aux;
        }
        return false;
    }

    public boolean collide(Esfera A, Esfera B) {

        Vector puntoA = Vector.sum(A.getPoint(), Vector.prod(delta, A.getVel()));
        Vector puntoB = Vector.sum(B.getPoint(), Vector.prod(delta, B.getVel()));

        float distR;
        if ((distR = Vector.dist(puntoA, puntoB)) < (A.getSize() + B.getSize())) {

            Vector vel1, vel2, v1, v2, v1x, v2x, v1y, v2y, x;
            float m1, m2;

            x = Vector.norm(Vector.del(puntoA, puntoB));

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

            Vector AB, ABN, distanciaA, distanciaB, posFA, posFB;

            //Direccion AB
            AB = Vector.del(A.getPoint(), B.getPoint());
            //Direccion AB normalizada
            ABN = Vector.norm(AB);
            //Cantidad a desplazar
            float dist = A.getSize() + B.getSize() - distR;

            distanciaA = Vector.prod(dist / 2.0f, ABN);
            distanciaB = Vector.prod(-dist / 2.0f, ABN);

            posFA = Vector.sum(distanciaA, puntoA);
            posFB = Vector.sum(distanciaB, puntoB);

            A.setPoint(posFA);
            B.setPoint(posFB);

            //B.setPoint(B.lastPoint);
            A.setVelocity((vel1));
            B.setVelocity(vel2);
            ;
            return true;
        }
        return false;

    }

    public boolean collide(Esfera A, Plano B) {
        System.out.println("Metodo ejecutando");
        float D1 = 0, D2 = 0, distancia = 0, distancia1, distancia2;
        boolean ret = false;
        float angulo1 = 0;
        Vector I, N, R;


        Vector punto = A.getPoint(); //Punto actual
        Vector copia = new Vector(punto); //Copia punto actual comprobacion si traspaso plano
        Vector copiaVel = new Vector(A.getVelocity()); //Copia velocidad de punto A

        Vector normalPlano = new Vector(B.getVel()); //Vector normal plano
        Vector puntoPlano = B.getPoint(); //Punto plano
        angulo1 = Vector.ang(normalPlano, A.getVelocity()); //Angulo entre la normal y la velocidad con la que llega la esfera


        if (angulo1 <= 90) { //Segun llegue la esfera por un lado u otro del plano, giramos la normal
            normalPlano = Vector.prod(-1, normalPlano);
        }

        //Formula distancia punto plano actual
        D1 = Vector.dot(normalPlano, puntoPlano);
        D2 = Vector.dot(normalPlano, punto);
        distancia1 = D2 - D1;

        // Formula distancia punto plano siguiente
        punto = Vector.sum(punto, Vector.prod(delta, A.getVel()));
        D1 = Vector.dot(normalPlano, puntoPlano);
        D2 = Vector.dot(normalPlano, punto);
        distancia2 = D2 - D1;
        distancia = Math.abs(D2 - D1);

        //Normalizar
        distancia = distancia / normalPlano.mod();


        Vector desplEsf;
        if (distancia < A.getSize()) {
            //Cuando choca el punto futuro, segun la cantidad que haya traspasado del plano al chocar
            //Se corrige su posicion
            if (distancia2 < 0) {
                desplEsf = Vector.prod(A.getSize() - distancia + A.getSize(), Vector.norm(normalPlano));
            } else {
                desplEsf = Vector.prod(A.getSize() - distancia, Vector.norm(normalPlano));
            }
            A.setPoint(Vector.sum(punto, desplEsf));

            //calculo nueva velocidad esfera
            Vector vf,vo,n,prod;
            float aux;
            n=Vector.norm(normalPlano);
            vo=A.getVel();
            aux= Vector.dot(vo,n);
            prod=Vector.prod(2*aux,n);
            vf=Vector.del(vo,prod);
            A.setVel(vf);
            ret = true;
        }

        //Si la distancia1  y la distancia 2 tienen distintos signos es que ha cruzado el plano
        if ((distancia1 > 0 && distancia2 < 0)) {
            //Como ha cruzado el plano, lo devolvemos a donde debería estar recalculando su
            //Dirección y velocidad de salida
            A.setPoint(copia);
            I = Vector.norm(copiaVel);
            N = Vector.norm(normalPlano);
            R = Vector.sum(Vector.mult(Vector.mult(Vector.prod(-1, I), N), Vector.prod(2, N)), I);
            Vector aux = Vector.prod(R, copiaVel.mod());
            A.setVelocity(aux);
        }


        return ret;
    }

    public boolean collide(Plano B, Esfera A) {
        return collide(A, B);
    }

    public boolean collide(Plano B, Plano A) {
        return false;
    }


    private boolean collideP(IBoundingBox A, IBoundingBox B) {
        //System.out.println("[Main]: Colisión entre esfera y plano aún no implementada");
        float D1 = 0, D2 = 0, distancia = 0;
        System.out.println(Vector.mod(A.getVel()));
        Vector punto = A.getPoint(), normal = B.getVel(), punto2 = B.getPoint();

        D1 = (normal.x * punto2.x + normal.y * punto2.y + normal.z * punto2.z);
        D2 = (normal.x * punto.x + normal.y * punto.y + normal.z * punto.z);
        distancia = Math.abs(D2 - D1);
        //distancia=fabs(_normal.getX()*punto.getX()+_normal.getY()*punto.getY()+_normal.getZ()*punto.getZ()+D);
        distancia = distancia / normal.mod();
        if (distancia <= A.getSize()) {
            Vector vf,vo,n,prod;
            float aux;
            n=Vector.norm(normal);
            vo=A.getVel();
           aux= Vector.dot(vo,n);
           prod=Vector.prod(2*aux,n);
            vf=Vector.del(vo,prod);
            A.setVel(vf);
            System.out.println(Vector.mod(vf));

            return true;
        }
        return false;


    }

     private boolean collideP2(IBoundingBox A, IBoundingBox B) {
        //System.out.println("[Main]: Colisión entre esfera y plano aún no implementada");
        float D1 = 0, D2 = 0, distancia = 0;

        Vector punto = A.getPoint(), normal = B.getVel(), punto2 = B.getPoint();

        D1 = (normal.x * punto2.x + normal.y * punto2.y + normal.z * punto2.z);
        D2 = (normal.x * punto.x + normal.y * punto.y + normal.z * punto.z);
        distancia = Math.abs(D2 - D1);
        //distancia=fabs(_normal.getX()*punto.getX()+_normal.getY()*punto.getY()+_normal.getZ()*punto.getZ()+D);
        distancia = distancia / normal.mod();
        if (distancia <= A.getSize()) {
            System.out.println("[COLISIONADOR]: Colisión esfera-plano");

            Vector I = Vector.norm(A.getVel()), N = Vector.norm(normal), R;

            R = Vector.sum(Vector.mult(Vector.mult(Vector.prod(-1.0f, I), N), Vector.prod(2.0f, N)), I);
            ((Esfera) A).setVelocity(Vector.prod(R, A.getVel().mod()));
            R.print();
            return true;
        }
        return false;


    }

}






