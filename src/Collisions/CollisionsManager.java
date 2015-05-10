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
	int p=0;
	private ArrayList<IBoundingBox> list;
	private ArrayList<Float> gamma, gammaAcum;
	private int size = 0;
	private float delta=0;
	
	//-------------------------------------------------------------------------

	public CollisionsManager(ArrayList<IBoundingBox> list) {
		super();
		this.list = list;
		
		gamma = new ArrayList<Float>();
		gammaAcum = new ArrayList<Float>();
		size=list.size();
		
		int sum=0;
		for (int i=0; i<size; i++){
			for(int j=i+1; j<size; j++){
				sum++;
				gamma.add(0.0f);
				gammaAcum.add(0.0f);
			}
		}
		
	}
	
	public CollisionsManager() {
		super();
		this.list = new ArrayList<IBoundingBox>();
		gamma = new ArrayList<Float>();
		gammaAcum = new ArrayList<Float>();
		size=0;
	}
	
	//-------------------------------------------------------------------------
	
	public void add(IBoundingBox ob){
		int i=0;
		list.add(ob);
		for(i=0;i<size;i++){
			gamma.add(0.0f);
			gammaAcum.add(0.0f);
		}
		size++;
        System.out.println("En realidad esto funciona mal metodo add de colision manager");
    }
	
	public void del(IBoundingBox ob){
		gamma.remove(list.indexOf(ob));
		gammaAcum.remove(list.indexOf(ob));
		list.remove(ob);
		size--;
        System.out.println("En realidad esto funciona mal metodo del de colision manager");
	}
	
	//-------------------------------------------------------------------------
	
	public void collide(float delta){
		if(delta>119){this.delta=0;}
		else{
			this.delta=delta;
		}
		int position=0;
		for (int i=0; i<list.size(); i++){
			for(int j=i+1; j<list.size(); j++){
				collide(list.get(i), list.get(j), position);
				position++;
			}
		}
	}
	
	public boolean collide(IBoundingBox A, IBoundingBox B, int ij){
		
		boolean aux = false;
		
		gammaAcum.set(ij,gammaAcum.get(ij) + delta);
		//if(gammaAcum.get(ij)>gamma.get(ij)){
			if(true){
			if (A instanceof Plano) {
				if (B instanceof Plano){
					//System.out.println("[Main]: Colisión entre planos aún no implementada");
				}
				if (B instanceof Esfera){
					aux =  collide((Plano)B,(Esfera)A);
				}
			}
			if (A instanceof Esfera){
				if (B instanceof Plano){
					aux =  collide((Esfera)A, (Plano)B);
				}
				if (B instanceof Esfera){
					//System.out.println("[Main]: Colisión entre esferas aún no implementada");
					aux =  collide((Esfera)A, (Esfera)B);
				}
			}
			
			if (aux){
			    gammaAcum.set(ij,0.0f);
			    gamma.set(ij,delta);
			}
			return aux;
		}
		return false;
	}

	public boolean collide(Esfera A, Esfera B) {
		
		Vector puntoA = Vector.sum(A.getPoint(), Vector.prod(delta, A.getVel()));
		Vector puntoB = Vector.sum(B.getPoint(), Vector.prod(delta, B.getVel()));

      float distR;
		if (( distR=Vector.dist(puntoA, puntoB)) < (A.getSize() + B.getSize())){

			Vector vel1,vel2, v1, v2, v1x, v2x, v1y, v2y, x; float m1, m2;

			x = Vector.norm(Vector.del(puntoA, puntoB));

			v1 = A.getVel();
			v1x = Vector.prod(x,Vector.dot(x, v1));
			v1y = Vector.del(v1, v1x);
			m1 =  A.getMass();

			x = Vector.prod(x, -1);
			v2 = B.getVel();
			v2x = Vector.prod(x, Vector.dot(x, v2));
			v2y = Vector.del(v2, v2x);
			m2 = B.getMass();
			
			vel1 = Vector.sum(v1y, Vector.sum(Vector.prod(v1x, ((m1-m2)/(m1+m2))), Vector.prod(v2x, ((2*m2)/(m1+m2)))));
			vel2 = Vector.sum(v2y, Vector.sum(Vector.prod(v1x, ((2*m1)/(m1+m2))), Vector.prod(v2x, ((m2-m1)/(m1+m2)))));

                Vector AB,ABN,distanciaA,distanciaB,posFA,posFB;

                //Direccion AB
                AB = Vector.del(A.getPoint(),B.getPoint());
                //Direccion AB normalizada
                ABN= Vector.norm(AB);
                //Cantidad a desplazar
            float dist=A.getSize()+B.getSize()-distR;

               distanciaA=Vector.prod(dist/2.0f,ABN);
                distanciaB=Vector.prod(-dist/2.0f,ABN);

            posFA= Vector.sum(distanciaA,puntoA);
            posFB= Vector.sum(distanciaB, puntoB);

            A.setPoint(posFA);
            B.setPoint(posFB);
            float res=Vector.dist(posFA,posFB);
           // System.out.println("Tanananananan lider "+res);

            //B.setPoint(B.lastPoint);
			 A.setVelocity((vel1));
			 B.setVelocity(vel2);;
			return true;
		}
		return false;

	}
	
	public boolean collide(Esfera A, Plano B) {

		float D1=0, D2 = 0, distancia=0,distancia1,distancia2;
        boolean ret = false;
        float angulo1=0;
        Vector I,N,R;


			Vector punto=A.getPoint(); //Punto actual
            Vector copia= new Vector(punto); //Copia punto actual comprobacion si traspaso plano
            Vector copiaVel= new Vector(A.getVelocity()); //Copia velocidad de punto A

            Vector normalPlano = new Vector(B.getVel()); //Vector normal plano
            Vector puntoPlano= B.getPoint(); //Punto plano
            angulo1= Vector.ang(normalPlano,A.getVelocity()); //Angulo entre la normal y la velocidad con la que llega la esfera


        if(angulo1<=90){ //Segun llegue la esfera por un lado u otro del plano, giramos la normal
            normalPlano=Vector.prod(-1,normalPlano);
        }

        //Formula distancia punto plano actual
        D1=Vector.dot(normalPlano,puntoPlano);
        D2=Vector.dot(normalPlano,punto);
        distancia1= D2-D1;

        // Formula distancia punto plano siguiente
        punto = Vector.sum(punto, Vector.prod(delta, A.getVel()));
        D1=Vector.dot(normalPlano,puntoPlano);
        D2=Vector.dot(normalPlano,punto);
        distancia2=D2-D1;
        distancia = Math.abs(D2-D1);

        //Normalizar
		distancia=distancia/normalPlano.mod();


        Vector desplEsf;
        if(distancia<A.getSize()){
            //Cuando choca el punto futuro, segun la cantidad que haya traspasado del plano al chocar
            //Se corrige su posicion
                if(distancia2<0){
                    desplEsf=Vector.prod(A.getSize()-distancia+A.getSize(),Vector.norm(normalPlano));
                }
                 else{
                    desplEsf=Vector.prod(A.getSize()-distancia,Vector.norm(normalPlano));
                }
                A.setPoint(Vector.sum(punto,desplEsf));

            //calculo nueva velocidad esfera
            I=Vector.norm(A.getVel());
            N= Vector.norm(normalPlano) ;
            R = Vector.sum(Vector.mult(Vector.mult(Vector.prod(-1, I), N), Vector.prod(2, N)), I);
            Vector aux = Vector.prod(R, A.getVel().mod());
            A.setVelocity(aux);
            ret = true;
			}

        //Si la distancia1  y la distancia 2 tienen distintos signos es que ha cruzado el plano
        if ( (distancia1>0 && distancia2<0)) {
            //Como ha cruzado el plano, lo devolvemos a donde debería estar recalculando su
            //Dirección y velocidad de salida
            A.setPoint(copia);
            I=Vector.norm(copiaVel);
            N= Vector.norm(normalPlano) ;
            R = Vector.sum(Vector.mult(Vector.mult(Vector.prod(-1, I), N), Vector.prod(2, N)), I);
            Vector aux = Vector.prod(R, copiaVel.mod());
            A.setVelocity(aux);
        }


		return ret;
	}
	
	public boolean collide(Plano B, Esfera A){
		return collide(A,B);
	}
	public boolean collide(Plano B, Plano A){
		return false;
	}
}






