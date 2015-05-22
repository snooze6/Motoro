package Others;

import Utilities.Dibujo;

public class Piece {
	
	private float size;

	public Piece(float size) {
		super();
		this.size = size;
	}

	public float getSize() {
		return size;
	}

    public void draw(){
    	Dibujo.drawCube(size);
    }

}
