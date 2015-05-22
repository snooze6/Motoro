package Billiard.Pyshics;

import java.util.ArrayList;

import Billiard.World.Ball;
import Billiard.World.BilliardObject;

/**
 * Created by Denis on 30/04/2015.
 */
public class CollisionManager {
    int p = 0;
    private ArrayList<BilliardObject> list;
    private int size = 0;
    private float delta = 0;

    //-------------------------------------------------------------------------

    public CollisionManager(ArrayList<BilliardObject> list) {
        super();
        this.list = list;
        size = list.size();
    }

    public CollisionManager() {
        super();
        this.list = new ArrayList<BilliardObject>();
        size = 0;
    }

    //-------------------------------------------------------------------------

    public void add(BilliardObject ob) {
        list.add(ob);
        size++;
    }

    public void del(BilliardObject ob) {
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

    public boolean collide(BilliardObject A, BilliardObject B) {
	    boolean aux = false;
//        if (A instanceof Plano) {
//            if (B instanceof Plano) {
//                //System.out.println("[Main]: Colisión entre planos aún no implementada");
//            }
//            if (B instanceof Esfera) {
//                aux = collide((Plano) B, (Esfera) A);
//            }
//
//        }
        if (A instanceof Ball) {
            if (B instanceof Ball) {
                aux =  collide((Ball)A, (Ball)B);
            }
//            if (B instanceof Esfera) {
//                aux = collide((Esfera) A, (Esfera) B);
//            }
//            if (B instanceof BoundingBoxCube) {
//                aux = collide((Esfera) A, (BoundingBoxCube) B);
//
//            }
        }
	    return aux;
    }
    
    public static boolean collide(Ball A, Ball B){
    	return true;
    }

}






