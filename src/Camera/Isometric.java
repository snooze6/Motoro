package Camera;

import Utilities.Vector;

public class Isometric extends Ortho{
	
	public Isometric() {
		super();
		pos = new Vector(0,0,0);
		ang = new Vector(10,-25,0);
		tam = 100; W_WIDTH=800; W_HEIGHT=400;
		setScale();
	}
	
	public Isometric(float cam_x, float cam_y, float cam_z,
			float cam_ang_x, float cam_ang_y, float cam_ang_z) {
		super();
		pos = new Vector(cam_x,cam_y,cam_z);
		ang = new Vector(10,cam_ang_y,cam_ang_z);
		tam = 100; W_WIDTH=800; W_HEIGHT=400;
		setScale();
	}
	
	public Isometric(Vector p, Vector a) {
		super();
		pos = new Vector(p);
		ang = new Vector(a);
		tam = 100; W_WIDTH=800; W_HEIGHT=400;
		setScale();
	}
	
	public Isometric(float cam_x, float cam_y, float cam_z,
			float cam_ang_x, float cam_ang_y, float cam_ang_z,
			int ulo) {
		super();
		pos = new Vector(cam_x,cam_y,cam_z);
		ang = new Vector(25,cam_ang_y,cam_ang_z);
		tam = ulo; W_WIDTH=800; W_HEIGHT=400;
		setScale();
	}
	
	public Isometric(Vector p, Vector a, int ulo) {
		super();
		pos = new Vector(p);
		ang = new Vector(a);
		tam = ulo; W_WIDTH=800; W_HEIGHT=400;
		setScale();
	}
	
	public Isometric(float cam_x, float cam_y, float cam_z,
			float cam_ang_x, float cam_ang_y, float cam_ang_z,
			int ulo, int w, int h) {
		super();
		pos = new Vector(cam_x,cam_y,cam_z);
		ang = new Vector(25,cam_ang_y,cam_ang_z);
		tam = ulo; W_WIDTH=w; W_HEIGHT=h;
		setScale();
	}
	
	public Isometric(Vector p, Vector a, int ulo, int w, int h) {
		super();
		pos = new Vector(p);
		ang = new Vector(a);
		tam = ulo; W_WIDTH=w; W_HEIGHT=h;
		setScale();
	}
	
	@Override
	public void lookUp(float v) {
//		super.lookUp(v);
//		ang.print();
		; //Not allowed
	}
	@Override
	public void lookDown(float v) {
//		super.lookDown(v);
//		ang.print();
		; //Not allowed
	}

}
