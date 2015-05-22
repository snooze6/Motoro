package Collision.Objects;
import static org.lwjgl.opengl.GL11.*;
/**
 * Created by Denis on 10/05/2015.
 */
public class Cubo extends BoundingBox{
    int size;
    int sizeDraw;
    Vector position;
    int displayListHandle;

    public Cubo( Vector position,int size){
        this.size=size;
        this.sizeDraw=size/2;
        this.position=position;
        displayListHandle= glGenLists(1);
        newList();
    }

    public void newList(){
        glNewList(this.displayListHandle, GL_COMPILE);
        glScalef(sizeDraw, sizeDraw, sizeDraw);
        //Traslado a la posicion de la maya correcta
        glTranslated(position.x*2+1,position.y*2+1,position.z*2+1);
        glBegin(GL_QUADS);                // Begin drawing the color cube with 6 quads

        // Top face (y = size)
        glColor3f(1.0f, 1.0f, 0.0f);     // Yellow
        glNormal3d(0,1,0);
        glVertex3f(1.0f, 1.0f, -1.0f);
        glVertex3f(-1.0f, 1.0f, -1.0f);
        glVertex3f(-1.0f, 1.0f, 1.0f);
        glVertex3f(1.0f, 1.0f, 1.0f);

        // Bottom face (y = -1.0f)
        glColor3f(1.0f, 1.0f, 1.0f);     // White
        glNormal3d(-1,0,0);
        glVertex3f(1.0f, -1.0f, 1.0f);
        glVertex3f(-1.0f, -1.0f, 1.0f);
        glVertex3f(-1.0f, -1.0f, -1.0f);
        glVertex3f(1.0f, -1.0f, -1.0f);
        // Front face  (z = 1.0f)
        glColor3f(1.0f, 0.0f, 0.0f);     // Red
        glNormal3d(0,0,1);
        glVertex3f(1.0f, 1.0f, 1.0f);
        glVertex3f(-1.0f, 1.0f, 1.0f);
        glVertex3f(-1.0f, -1.0f, 1.0f);
        glVertex3f(1.0f, -1.0f, 1.0f);

        // Back face (z = -1.0f)
        glColor3f(1.0f, 0.6f, 0.0f);     // Orange
        glNormal3d(0,0,-1);
        glVertex3f(1.0f, -1.0f, -1.0f);
        glVertex3f(-1.0f, -1.0f, -1.0f);
        glVertex3f(-1.0f, 1.0f, -1.0f);
        glVertex3f(1.0f, 1.0f, -1.0f);

        // Left face (x = -1.0f)
        glColor3f(0.0f, 0.0f, 1.0f);     // Blue
        glNormal3d(-1,0,0);
        glVertex3f(-1.0f, 1.0f, 1.0f);
        glVertex3f(-1.0f, 1.0f, -1.0f);
        glVertex3f(-1.0f, -1.0f, -1.0f);
        glVertex3f(-1.0f, -1.0f, 1.0f);

        // Right face (x = 1.0f)
        glColor3f(0.0f, 1.0f, 0.0f);     // Green
        glNormal3d(1,0,0);
        glVertex3f(1.0f, 1.0f, -1.0f);
        glVertex3f(1.0f, 1.0f, 1.0f);
        glVertex3f(1.0f, -1.0f, 1.0f);
        glVertex3f(1.0f, -1.0f, -1.0f);
        glEnd();  // End of drawing color-cube
        glScalef(1/sizeDraw,1/sizeDraw,1/sizeDraw);
        glEndList();
    }
    public void draw(){
        glPushMatrix();
            glTranslated(position.x,position.y,position.z);
            glCallList(this.displayListHandle);
        glPopMatrix();
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Vector getPosition() {
        return position;
    }

    public void setPosition(Vector position) {
        this.position = position;
    }

	@Override
	public Vector getPoint() {
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
		return this;
		// TODO Auto-generated method stub
		
	}

	@Override
	public void del(int c) {
		// TODO Auto-generated method stub
		
	}


}
