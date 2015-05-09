package Collisions;

import java.util.ArrayList;

import Collision.Objects.Esfera;
import Collision.Objects.Plano;
import Collision.Objects.Vector;

/**
 * Created by Denis on 30/04/2015.
 */
public class CollisionsManager {
	
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
		
		int sum2 = 0;
		for (int i=0; i<size; i++){
			sum2 += i;
		}
		
		System.out.println("Sum1: "+ sum);
		System.out.println("Sum2: "+ sum2);
		
		if (sum==sum2) {System.out.println("Iguales!!");}
		
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
	}
	
	public void del(IBoundingBox ob){
		gamma.remove(list.indexOf(ob));
		gammaAcum.remove(list.indexOf(ob));
		list.remove(ob);
		size--;
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
		
		//if(gammaAcum.get(ij)>gamma.get(ij)*16){
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
		 puntoA = Vector.sum(puntoA, Vector.prod(delta, A.getVel()));
		Vector puntoB = Vector.sum(B.getPoint(), Vector.prod(delta, B.getVel()));
		 puntoB = Vector.sum(puntoB, Vector.prod(delta, B.getVel()));
		if (Vector.dist(puntoA, puntoB) <= (A.getSize() + B.getSize())){
			//System.out.println("[COLISIONADOR]: Colisión esfera-esfera");
			Vector vel1,vel2, v1, v2, v1x, v2x, v1y, v2y, x; float m1, m2;

			x = Vector.norm(Vector.del(puntoA, puntoB));

			v1 = A.getVel();
			v1x = Vector.prod(x,Vector.dot(x, v1));
			v1y = Vector.del(v1, v1x);
			m1 = ((Esfera) A).getMass();

			x = Vector.prod(x, -1);
			v2 = B.getVel();
			v2x = Vector.prod(x, Vector.dot(x, v2));
			v2y = Vector.del(v2, v2x);
			m2 = ((Esfera) B).getMass();
			
			vel1 = Vector.sum(v1y, Vector.sum(Vector.prod(v1x, ((m1-m2)/(m1+m2))), Vector.prod(v2x, ((2*m2)/(m1+m2)))));
			vel2 = Vector.sum(v2y, Vector.sum(Vector.prod(v1x, ((2*m1)/(m1+m2))), Vector.prod(v2x, ((m2-m1)/(m1+m2)))));
			((Esfera) A).setVelocity((vel1));
			((Esfera) B).setVelocity(vel2);;
			return true;
		}
		return false;
		
//		if (Vector.dist(A.getPoint(), B.getPoint()) <= (A.getSize() + B.getSize())){
//			//System.out.println("[COLISIONADOR]: Colisión esfera-esfera");
//			Vector vel1,vel2, v1, v2, v1x, v2x, v1y, v2y, x; float m1, m2;
//
//			x = Vector.norm(Vector.del(A.getPoint(), B.getPoint()));
//
//			v1 = A.getVel();
//			v1x = Vector.prod(x,Vector.dot(x, v1));
//			v1y = Vector.del(v1, v1x);
//			m1 = ((Esfera) A).getMass();
//
//			x = Vector.prod(x, -1);
//			v2 = B.getVel();
//			v2x = Vector.prod(x, Vector.dot(x, v2));
//			v2y = Vector.del(v2, v2x);
//			m2 = ((Esfera) B).getMass();
//			
//			vel1 = Vector.sum(v1y, Vector.sum(Vector.prod(v1x, ((m1-m2)/(m1+m2))), Vector.prod(v2x, ((2*m2)/(m1+m2)))));
//			vel2 = Vector.sum(v2y, Vector.sum(Vector.prod(v1x, ((2*m1)/(m1+m2))), Vector.prod(v2x, ((m2-m1)/(m1+m2)))));
//			((Esfera) A).setVelocity((vel1));
//			((Esfera) B).setVelocity(vel2);;
//			return true;
//		}
//		return false;
	}
	
	public boolean collide(Esfera A, Plano B) {
		
		//gammaAcum += delta;
		//System.out.println("Gamma: "+gamma/1000+ "  Delta: "+delta);
		//System.out.println("[Main]: Colisión entre esfera y plano aún no implementada");
		
		//if(gammaAcum>gamma*2 || true){
		
			float D1=0, D2 = 0, distancia=0;
			Vector punto=A.getPoint(), normal = B.getVel(), punto2 = B.getPoint();
			
			punto = Vector.sum(punto, Vector.prod(delta, A.getVel()));
			punto = Vector.sum(punto, Vector.prod(delta, A.getVel()));
			D1=(normal.x*punto2.x + normal.y*punto2.y + normal.z*punto2.z);
			D2=(normal.x*punto.x + normal.y*punto.y + normal.z*punto.z);
			distancia = Math.abs(D2-D1);
			//distancia=fabs(_normal.getX()*punto.getX()+_normal.getY()*punto.getY()+_normal.getZ()*punto.getZ()+D);
			distancia=distancia/normal.mod();
			if(distancia<A.getSize()){
				    //gammaAcum=0; gamma=delta;
					//System.out.println("Y:  "+(A.getPoint().y - A.getSize()));
					//System.out.println("VY: "+(A.getVelocity().y));

//					Vector b = Vector.prod(distancia, Vector.prod(-1, Vector.norm(A.getVel())));
//					A.setPoint(Vector.sum(punto,  b));
					
					Vector I=Vector.norm(A.getVel()), N= Vector.norm(normal), R;
					R = Vector.sum(Vector.mult(Vector.mult(Vector.prod(-1, I), N), Vector.prod(2, N)), I);
					A.setVelocity(Vector.prod(R, A.getVel().mod()));
				return true;
			}
		//}
			
		return false;
	}
	
	public boolean collide(Plano B, Esfera A){
		return collide(A,B);
	}
	public boolean collide(Plano B, Plano A){
		return false;
	}
}






