

import java.util.ArrayList;

import Collision.Objects.Vector;

public class TestVector {

	public static void main(String[] args) {
		Vector v1 = new Vector(1,1,0);
		
		Vector.sum(v1, v1).print(); //Debería dar 2,2,2
		
		System.out.println(Vector.ang(Vector.ejex, Vector.ejex)); //Debería dar 0
		System.out.println(Vector.ang(Vector.ejex, Vector.ejey)); //90
		System.out.println(Vector.ang(Vector.ejex, Vector.ejez)); //90
		System.out.println(Vector.ang(Vector.ejez, Vector.ejey)); //90
		
		Vector.prod(Vector.ejey, Vector.ejez).print(); //1,0,0
		Vector.prod(Vector.ejez, Vector.ejex).print(); //0,1,0
		Vector.prod(Vector.ejex, Vector.ejey).print(); //0,0,1
		
		System.out.println(Vector.ang(v1, Vector.ejex)); //54.73
		System.out.println(Vector.ang(v1, Vector.ejey)); //54.73
		System.out.println(Vector.ang(v1, Vector.ejez)); //54.73
		
		Vector.prod(3, v1).print(); // 3,3,3
		
		
		ArrayList<Vector> a = new ArrayList<Vector>();
		a.add(new Vector(-1,1,1));
		a.add(new Vector(-1,1,-1));
		a.add(new Vector(1,1,-1));
		a.add(new Vector(1,-1,1));
		
		for (int i=0; i<a.size(); i++){
			float aux = a.get(i).z; a.get(i).z=0;
			System.out.println(Vector.ang(a.get(i),Vector.ejez)/2);
			a.get(i).print();
			a.get(i).z=aux;
			a.get(i).print();
		}
		
		System.out.println(Vector.ang(new Vector(1,1,1),  new Vector(1,1,0)));
	}

}
