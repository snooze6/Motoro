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
	
	public static boolean collide(IBoundingBox A, IBoundingBox B){
		if (A instanceof Plano) {
			if (B instanceof Plano){
				System.out.println("[Main]: Colisión entre planos aún no implementada");
			}
			if (B instanceof Esfera){
				System.out.println("[Main]: Colisión entre planos y esfera aún no implementada");
			}
		}
		if (B instanceof Esfera){
			if (A instanceof Plano){
				System.out.println("[Main]: Colisión entre esfera y plano aún no implementada");
			}
			if (A instanceof Esfera){
				//System.out.println("[Main]: Colisión entre esferas aún no implementada");
				if (Vector.dist(A.getVel(), B.getVel()) <= A.getSize()){
					System.out.println("Colision entre esferas");
					Vector vel1,vel2, v1, v2, v1x, v2x, v1y, v2y, x;
					float m1, m2, x1, x2;

					x = Vector.norm(Vector.del(A.getPoint(), B.getPoint()));

					v1 = A.getVel();
					x1 = Vector.dot(x, v1);
					v1x = Vector.prod(x,x1);
					v1y = Vector.del(v1, v1x);
					m1 = ((Esfera) A).getMass();

					x = Vector.prod(x, -1);
					v2 = B.getVel();
					x2 = Vector.dot(x,v2);
					v2x = Vector.prod(x, x2);
					v2y = Vector.del(v2, v2x);
					m2 = ((Esfera) B).getMass();
					
					vel1 = Vector.sum(v1y, Vector.sum(Vector.prod(v1x, ((m1-m2)/(m1+m2))), Vector.prod(v2x, ((2*m2)/(m1+m2)))));
					vel2 = Vector.sum(v2y, Vector.sum(Vector.prod(v1x, ((2*m1)/(m1+m2))), Vector.prod(v2x, ((m2-m1)/(m1+m2)))));
					((Esfera) A).setDirection((vel1));
					((Esfera) B).setDirection(vel2);;
				}
			    return false;
			}
		}
		return false;
	}
	
}






