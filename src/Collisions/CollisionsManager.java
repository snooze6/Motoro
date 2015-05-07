package Collisions;

import java.util.ArrayList;

import Collision.Objects.Esfera;
import Collision.Objects.Plano;
import Collision.Objects.Vector;

/**
 * Created by Denis on 30/04/2015.
 */
public class CollisionsManager {
	
	ArrayList<IBoundingBox> list;

	public CollisionsManager(ArrayList<IBoundingBox> list) {
		super();
		this.list = list;
	}
	
	public CollisionsManager() {
		super();
		this.list = new ArrayList<IBoundingBox>();
	}
	
	public void collide(){
		
	}
	
//	public static boolean collide(IBoundingBox A, IBoundingBox B){
//		if (A instanceof Plano) {
//			if (B instanceof Plano){
//				System.out.println("[Main]: Colisión entre planos aún no implementada");
//			}
//			if (B instanceof Esfera){
//				return collide((Plano)B,(Esfera)A);
//			}
//		}
//		if (A instanceof Esfera){
//			if (B instanceof Plano){
//				return collide((Esfera)A, (Plano)B);
//			}
//			if (B instanceof Esfera){
//				//System.out.println("[Main]: Colisión entre esferas aún no implementada");
//				return collide((Esfera)A, (Esfera)B);
//			}
//		}
//		return false;
//	}

	public static boolean collide(Esfera A, Esfera B, float delta) {
		if (Vector.dist(A.getPoint(), B.getPoint()) <= (A.getSize() + B.getSize())){
			//System.out.println("[COLISIONADOR]: Colisión esfera-esfera");
			Vector vel1,vel2, v1, v2, v1x, v2x, v1y, v2y, x; float m1, m2;

			x = Vector.norm(Vector.del(A.getPoint(), B.getPoint()));

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
	}

	public static boolean collide(Esfera A, Plano B, float delta) {
		//System.out.println("[Main]: Colisión entre esfera y plano aún no implementada");
		
		gammaAcum=delta+gammaAcum;
		int x=1;
		if (gammaAcum>2*gamma ){
			x=0;
			float D1=0, D2 = 0, distancia=0;
			Vector punto=A.getPoint(), normal = B.getVel(), punto2 = B.getPoint();
			D1=(normal.x*punto2.x + normal.y*punto2.y + normal.z*punto2.z);
			D2=(normal.x*punto.x + normal.y*punto.y + normal.z*punto.z);
			distancia = Math.abs(D2-D1);
			//distancia=fabs(_normal.getX()*punto.getX()+_normal.getY()*punto.getY()+_normal.getZ()*punto.getZ()+D);
			distancia=distancia/normal.mod();
			if(distancia<A.getSize()){
					gammaAcum = 0; gamma=delta;
				
					System.out.println("Y:  "+(A.getPoint().y - A.getSize()));
					//System.out.println("VY: "+(A.getVelocity().y));

					Vector b = Vector.prod(distancia, Vector.prod(-1, Vector.norm(A.getVel())));
					//A.setPoint(Vector.sum(punto,  b));
					
					Vector I=Vector.norm(A.getVel()), N= Vector.norm(normal), R;
					R = Vector.sum(Vector.mult(Vector.mult(Vector.prod(-1, I), N), Vector.prod(2, N)), I);
					 A.setVelocity(Vector.prod(R, A.getVel().mod()));
				return true;
			}
		}
		else{
			//System.out.println("tururu");
		}
		return false;
	}
	
	public static float gamma = 0;
	public static float gammaAcum = 0;
	
	public static boolean collide(Plano B, Esfera A, float delta){
		return collide(A,B, delta);
	}
	
}






