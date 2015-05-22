package Collision;

import java.util.ArrayList;

import Utilities.Vector;


/**
 * Created by Denis on 30/04/2015.
 */
public class CollisionsManager {
    int p = 0;
    private ArrayList<BoundingBox> list;
    private int size = 0;
    private float delta = 0;

    //-------------------------------------------------------------------------

    public CollisionsManager(ArrayList<BoundingBox> list) {
        super();
        this.list = list;
        size = list.size();
    }

    public CollisionsManager() {
        super();
        this.list = new ArrayList<BoundingBox>();
        size = 0;
    }

    //-------------------------------------------------------------------------

    public void add(BoundingBox ob) {
        list.add(ob);
        size++;
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

    public boolean collide(BoundingBox A, BoundingBox B) {
	    boolean aux = false;
        if (A instanceof BBPlane) {
            if (B instanceof BBPlane) {
                //System.out.println("[Main]: Colisión entre planos aún no implementada");
            }
            if (B instanceof BBSphere) {
                aux = collide((BBPlane) B, (BBSphere) A);
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

    public boolean collide(BBSphere A, BBSphere B) {

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
            
            return true;
        }
        return false;

    }

    public boolean collide(BBSphere A, BBPlane B) {
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
        boolean dentro=true;


        if(B instanceof BBQuad){
            dentro=((BBQuad)B).intersection(punto,A);
        }



        if (distancia < A.getSize() && dentro) {
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

            A.setVelocity(vf);




            ret = true;



        }

        //Si la distancia1  y la distancia 2 tienen distintos signos es que ha cruzado el plano
//        if ((distancia1 > 0 && distancia2 < 0) ) {
//            //Como ha cruzado el plano, lo devolvemos a donde debería estar recalculando su
//            //Dirección y velocidad de salida
//            A.setPoint(copia);
//            I = Vector.norm(copiaVel);
//            N = Vector.norm(normalPlano);
//            R = Vector.sum(Vector.mult(Vector.mult(Vector.prod(-1, I), N), Vector.prod(2, N)), I);
//            Vector aux = Vector.prod(R, copiaVel.mod());
//            A.setVelocity(aux);
//        }

        return ret;
    }

    public boolean collide(BBPlane B, BBSphere A) {
        return collide(A, B);
    }

    public boolean collide(BBPlane B, BBPlane A) {
        return false;
    }


//    public boolean collide(Esfera A,BoundingBoxCube B) {
//
//        int sizeCube,sizeSphere;
//        sizeCube = B.getCube().getSize();
//        sizeSphere= (int)A.getSize();
//        int posicionX,posicionY,posicionZ;
//        int posicionXSiguiente,posicionYSiguiente,posicionZSiguiente;
//        int posicionXSiguiente2,posicionYSiguiente2,posicionZSiguiente2;
//
//        Vector puntoActual = A.getPoint();
//
//        Vector puntoSiguiente = Vector.sum(puntoActual, Vector.prod(delta, A.getVel()));
//
//      Vector  puntoSiguiente2 = Vector.sum(puntoSiguiente, Vector.prod(delta, A.getVel()));
//
//        posicionX = (int) puntoActual.x / sizeCube;
//        posicionY = (int) puntoActual.y / sizeCube;
//        posicionZ = (int) puntoActual.z / sizeCube;
//
//        posicionXSiguiente = (int) puntoSiguiente.x / sizeCube;
//        posicionYSiguiente = (int) puntoSiguiente.y / sizeCube;
//        posicionZSiguiente = (int) puntoSiguiente.z / sizeCube;
//
//        posicionXSiguiente2 = (int) puntoSiguiente2.x / sizeCube;
//        posicionYSiguiente2 = (int) puntoSiguiente2.y / sizeCube;
//        posicionZSiguiente2 = (int) puntoSiguiente2.z / sizeCube;
//
//        //System.out.println("  X   "+posicionX + "   Y  "+posicionY + "   Z  "+posicionZ);
//
//
//       // System.out.print("  Cubo-------------X   "+B.getPoint().x + "   Y  "+B.getPoint().y + "   Z  "+B.getPoint().z);
//
//        if((int)B.getPoint().x==posicionXSiguiente && (int)B.getPoint().y==posicionYSiguiente && (int)B.getPoint().z==posicionZSiguiente){
//
//                System.out.println("  X   "+posicionX + "   Y  "+posicionY + "   Z  "+posicionZ);
//                System.out.println("  X   "+posicionXSiguiente + "   Y  "+posicionYSiguiente + "   Z  "+posicionZSiguiente);
//               // collide(A, new Plano(1,0,0,0,0,0));
//            A.setVelocity(0,0,0);
//
//
//
//
//
//
////            //Formula distancia punto plano actual
////            float D1;
////            float D2;
////            float distancia,distancia2;
////            Plano horizontalArriba;
////            Plano horizontalAbajo;
////            horizontalArriba= new Plano(0,1,0,0,posicionY*size,0);
////            D1 = Vector.dot(horizontalArriba.getVel(), horizontalArriba.getPoint());
////            D2 = Vector.dot(horizontalArriba.getVel(), puntoActual);
////            distancia = D2 - D1;
////
////
////            horizontalAbajo= new Plano(0,1,0,0,posicionY*size-size,0);
////            D1 = Vector.dot(horizontalAbajo.getVel(), horizontalAbajo.getPoint());
////            D2 = Vector.dot(horizontalAbajo.getVel(), puntoActual);
////            distancia2 = D2 - D1;
//////            System.out.println(distancia);
//////            System.out.println(distancia2);
////            if(Math.abs(distancia2-distancia) <=size){
//////                System.out.println("Colision no por arriba o abajo");
////                collide(A, new Plano(1,0,0,posicionX,posicionY,posicionZ));
////            }
////            else{
////                collide(A,new Plano(0,1,0,))
////            }
//
//
//
//
//        }
//        return true;
//    }



}






