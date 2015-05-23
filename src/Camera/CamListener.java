package Camera;

import java.util.ArrayList;
import org.lwjgl.input.Keyboard;
import Utilities.Vector;

public class CamListener extends Cam{
	
	ArrayList<Cam> list;
	private int selected=0;
	private int size=-1;
	
    public int speedMovement=10;
    public float rotateMovement=4.0f;

	public CamListener(){
		list = new ArrayList<Cam>();
		list.add(new Perspective());
		size++;
	}
	
	public CamListener(ArrayList<Cam> list){
		this();
		setList(list);
	}
	
	public CamListener(Cam c){
		this();
		addCam(c);
	}
	
	//--------------------------------------------------------------------------
	
	public void addCam(Cam c){
		list.add(c);
		size++;
	}
	
	public int getSpeedMovement() {
		return speedMovement;
	}

	public ArrayList<Cam> getList() {
		return list;
	}

	public void setList(ArrayList<Cam> list) {
		if (list!=null && list.size()!=0){
			this.list = list;
			size = list.size();
		}
	}

	public void setSpeedMovement(int speedMovement) {
		this.speedMovement = speedMovement;
	}

	public float getRotateMovement() {
		return rotateMovement;
	}

	public void setRotateMovement(float rotateMovement) {
		this.rotateMovement = rotateMovement;
	}
	
	//--------------------------------------------------------------------------

	public void setWindow(int i, int j) {
		list.get(selected).setWindow(i, j);
	}

	public void render() {
		list.get(selected).render();
	}
	
	//--------------------------------------------------------------------------
	
	public void listen(){

        // Translate cam
        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
        	list.get(selected).moveStraight(speedMovement);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
        	list.get(selected).moveBack(speedMovement);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
        	list.get(selected).moveRight(speedMovement);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
        	list.get(selected).moveLeft(speedMovement);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
        	list.get(selected).moveUp(speedMovement);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
        	list.get(selected).moveDown(speedMovement);
        }
        // Rotate cam
        if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
            list.get(selected).lookDown(rotateMovement);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
            list.get(selected).lookUp(rotateMovement);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
            list.get(selected).lookRight(rotateMovement);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
            list.get(selected).lookLeft(rotateMovement);
        }

        //----------------------------------------------------------------------
        while(Keyboard.next()) {
            if (Keyboard.isKeyDown(Keyboard.KEY_Z)) {
                list.get(selected).morezoom();
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_X)) {
                list.get(selected).lesszoom();
            }

            //----------------------------------------------------------------------

            if (Keyboard.isKeyDown(Keyboard.KEY_U)) {
                prevCam();
            }
        }
	}
	
	public void nextCam(){
		if (selected<size){
			selected++;
		} else {
			selected=0;
		}
	}
	
	public void prevCam(){
		if (selected>0){
			selected--;
		} else {
			selected=size;
		}
	}
	
	//--------------------------------------------------------------------------

	@Override
	public void moveUp(float v) {
		list.get(selected).moveUp(v);
	}

	@Override
	public void moveDown(float v) {
		list.get(selected).moveDown(v);
	}

	@Override
	public void moveStraight(float v) {
		list.get(selected).moveStraight(v);
	}

	@Override
	public void moveBack(float v) {
		list.get(selected).moveBack(v);
	}

	@Override
	public void moveRight(float v) {
		list.get(selected).moveRight(v);
	}

	@Override
	public void moveLeft(float v) {
		list.get(selected).moveLeft(v);
	}

	@Override
	public void morezoom() {
		list.get(selected).morezoom();
	}

	@Override
	public void lesszoom() {
		list.get(selected).lesszoom();
	}

	@Override
	public void setPos(float x, float y, float z) {
		list.get(selected).setPos(x, y, z);
	}

	@Override
	public void setPos(Vector v) {
		list.get(selected).setPos(v);
	}

	@Override
	public void setAngle(float angx, float angy, float angz) {
		list.get(selected).setAngle(angx, angy, angz);
	}

	@Override
	public void setAngle(Vector v) {
		list.get(selected).setAngle(v);
	}

	@Override
	public void lookUp(float v) {
		list.get(selected).lookUp(v);
	}

	@Override
	public void lookDown(float v) {
		list.get(selected).lookDown(v);
	}

	@Override
	public void lookRight(float v) {
		list.get(selected).lookRight(v);
	}

	@Override
	public void lookLeft(float v) {
		list.get(selected).lookLeft(v);
	}

	@Override
	public Vector getDireccion() {
		return list.get(selected).getDireccion();
	}

	@Override
	public void setDireccion(float x, float y, float z) {
		list.get(selected).setDireccion(x, y, z);
	}

	@Override
	public void setDireccion(Vector v) {
		list.get(selected).setDireccion(v);
	}

	@Override
	public float getX() {
		return list.get(selected).getX();
	}

	@Override
	public float getY() {
		return list.get(selected).getY();
	}

	@Override
	public float getZ() {
		return list.get(selected).getZ();
	}

	@Override
	public float getAngX() {
		return list.get(selected).getAngX();
	}

	@Override
	public float getAngY() {
		return list.get(selected).getAngY();
	}

	@Override
	public float getAngZ() {
		return list.get(selected).getAngZ();
	}

	@Override
	public Vector getFront(float far) {
		return list.get(selected).getFront(far);
	}

	@Override
	public Vector getPos() {
		return list.get(selected).getPos();
	}

	@Override
	public Vector getAng() {
		return list.get(selected).getAng();
	}
	
	public void firstPerson(){
		if (list.get(selected) instanceof Perspective){
			((Perspective)list.get(selected)).firstPerson();
		}
	}
	
	public void thirdPerson(){
		if (list.get(selected) instanceof Perspective){
			((Perspective)list.get(selected)).thirdPerson();
		}
	}
	
	

	
	
}
