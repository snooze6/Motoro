package Collision.Objects;


/**
 * Created by Denis on 11/05/2015.
 */
public class BoundingBoxCube extends BoundingBox {
    private Cubo cube;
    private Vector velocidad;
    private Vector position;
    private Vector point;
    private boolean flota=true;
    private boolean debajo;

    public BoundingBoxCube(Cubo cube){
        this.cube=cube;

        this.velocidad= new Vector(0,0,0);
    }

    @Override
    public Vector getPoint() {
        return cube.getPosition();
    }


    @Override
    public Vector getVel() {
        System.out.println("LOs cubos no se mueven aun, clase bounding box cube");
        return null;
    }

    @Override
    public float getSize() {
        return cube.getSize();
    }

    @Override
    public void setVel(Vector v) {
        System.out.println("Los cubos no se mueven aun clase boundingBox cube");
    }

    @Override
    public void move(float delta) {
        System.out.println("El cubo no se traslada aun");
    }

    public Cubo getCube() {
        return cube;
    }

    public void setCube(Cubo cube) {
        this.cube = cube;
    }
    
    //--------------------------------------------------------------------------

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void add(BoundingBox c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void del(BoundingBox c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BoundingBox getSon(int i) {
		return cube;
	}

	@Override
	public void del(int c) {
		// TODO Auto-generated method stub
		
	}
}
