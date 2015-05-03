package Collision.Objects;

public class TestVector {

	public static void main(String[] args) {
		Vector v1 = new Vector(1,1,1);
		
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
	}

}
