package Collision;

import Utilities.Vector;

import java.util.ArrayList;

public class Mezcla extends BoundingBox{
	
	private ArrayList<BoundingBox> list;

	public Mezcla() {
		super();
		this.list = new ArrayList<BoundingBox>();
	}
	
	public Mezcla(ArrayList<BoundingBox> list) {
		super();
		this.list = list;
	}

	@Override
	public void draw() {
		for (int i=1; i<list.size(); i++){
			list.get(i).draw();
		}
	}

	@Override
	public void add(BoundingBox c) {
		list.add(c);
	}

	@Override
	public void del(BoundingBox c) {
		list.remove(c);
	}
	
	@Override
	public void del(int c) {
		list.remove(c);
	}

	@Override
	public BoundingBox getSon(int i) {
		return list.get(i);
	}
	
	@Override
	public Vector getCenterPoint() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector getVel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float getSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setVel(Vector v) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void move(float delta) {
		// TODO Auto-generated method stub
		
	}

}
