package Collision;

import Utilities.Vector;

import java.util.ArrayList;


public class CollisionsManager {
    private ArrayList<BoundingBox> list;
    private int size = 0;
    private float delta = 0;

    //-------------------------------------------------------------------------
    public CollisionsManager(ArrayList<BoundingBox> list) {
        this.list = list;
        size = list.size();
    }

    public CollisionsManager() {
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

    public boolean collide(BBSphere A, BBSphere B) {

    	/*
    	 * Calculamos las colisiones en el futuro, por lo tanto calculamos los puntos futuros
    	 */
        Vector puntoA = Vector.sum(A.getCenterPoint(), Vector.prod(delta, A.getVel()));
        Vector puntoB = Vector.sum(B.getCenterPoint(), Vector.prod(delta,B.getVel()));

        float distR;
        if ((distR = Vector.dist(puntoA, puntoB)) < (A.getSize() + B.getSize())) {
            Vector vel1, vel2, v1 = A.getVel(), v2 = B.getVel(), v1x, v2x, v1y, v2y, x;
            float m1= A.getMass(), m2 = B.getMass();

/*
 * Vaaaamos a ver, aquí estaba la maravilla de bucle para resolver una ecuación
 * 
 * Me parece que lo que buscabamos inicialmente era lo siguiente:
 * - 2 Bolas colisionaban con diferentes velocidades y ángulos
 * - Retroceder ambas una cantidad pertinente
 * 
 * Sin embargo aquí lo que haces es retroceder la primera bola hasta que la distancia
 * entre ellas es mayor que la suma de sus radios, lo cual no parece adecuado
 */
  //Calculo la direccion en la que ha penetrado A sobre B
  Vector aux=Vector.norm(Vector.del(puntoB,puntoA));
  //Sobre esa dirección, quiero
  aux=Vector.prod(-A.getSize(),aux);
  Vector puntoA2=Vector.sum(puntoB,aux), puntoB2;
  
    puntoA2=puntoA;
	puntoB2=puntoB;
	aux=Vector.norm(A.getVel());
    if(aux.x!=0 || aux.y!=0 || aux.z!=0){
        while(( Vector.distPuntos(puntoA2, puntoB2)) <= (A.getSize() + B.getSize())){
            puntoA2=Vector.sum(puntoA2,Vector.prod(-0.01f,aux));
        }
    }
            
//            /*
//             * PROBLEMA 1:
//             * 
//             * Las bolas al chocar se pueden poner una sobre otra
//             * 
//             * Bien, segundo razonamiento, sabemos que colisionaron, en consecuencia
//             * es evidente que debemos retrocederlas, cada una en la dirección de su velocidad
//             * pero invertida, y la pregunta es que cantidad, la respuesta es evidente
//             * - Cantidad = (A.getSize() + B.getSize())-distR
//             * o lo que es lo mismo
//             * cantidad + distR = A.getSize() + B.getSize()
//             * 
//             * Bien, una vez solucionado esto simplemente hay que saber cuanto retroceder cada una
//             * y eso depende únicamente del módulo de la velocidad de cada una, así pues
//             */
//            float cant = (A.getSize() + B.getSize())-distR;
//            float cantA = (cant*v1.mod())/(v1.mod()+v2.mod()),
//            	  cantB = (cant*v2.mod())/(v1.mod()+v2.mod());
//            Vector puntoA2=Vector.sum(puntoA, Vector.prod(cantA, Vector.norm(v1))),
//            	   puntoB2=Vector.sum(puntoB, Vector.prod(cantB, Vector.norm(v2)));
//            System.out.println((cant + distR) + " - "+ (A.getSize() + B.getSize()));
           
            x = Vector.norm(Vector.del(puntoA2, puntoB2));
            v1x = Vector.prod(x, Vector.dot(x, v1));
            v1y = Vector.del(v1, v1x);

            x = Vector.prod(x, -1);
            v2x = Vector.prod(x, Vector.dot(x, v2));
            v2y = Vector.del(v2, v2x);

            vel1 = Vector.sum(v1y, Vector.sum(Vector.prod(v1x, ((m1 - m2) / (m1 + m2))), Vector.prod(v2x, ((2 * m2) / (m1 + m2)))));
            vel2 = Vector.sum(v2y, Vector.sum(Vector.prod(v1x, ((2 * m1) / (m1 + m2))), Vector.prod(v2x, ((m2 - m1) / (m1 + m2)))));

            Vector AB, ABN, distanciaA, distanciaB, posFA, posFB;

            //Direccion AB
            AB = Vector.del(A.getCenterPoint(), B.getCenterPoint());
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

            A.setVelocity((vel1));
            B.setVelocity(vel2);

            return true;
        }
        return false;
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

        return ret;
    }

}






