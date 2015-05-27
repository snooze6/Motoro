package Billiard.World;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import Utilities.Dibujo;
import Utilities.Vector;

public class BilliardPole extends BilliardObject {
	Ball bola;
	double anjulo=0;
	public Vector direccion=new Vector(0,0,0);
	Vector color = new Vector(0,1,0);
    float strong=0;
	
	public BilliardPole(Ball b){
		bola = b;

	}
	
	protected double updateang(double ang){
		if (ang>360){
			return ang-360;
		} else {
			if (ang<-360) {
				return ang+360;
			} else {
				return ang;
			}
		}
	}
	
	public double getAngulo() {
		return anjulo;
	}

	public void setAngulo(double anjulo) {
		this.anjulo = anjulo;
	}
	
	public void girar(double aux){
		anjulo+=updateang(aux);
	}
	
	public void updateColor(float caca){
		switch((int)caca){
			case 0:
				color = new Vector(0,1,0);
				break;
			case 1:
				color = new Vector(0.5f,1,0);
				break;
			case 2:
				color = new Vector(1, 1,0);
				break;
			case 3:
				color = new Vector(1,0.5f,0);
				break;
			case 4:
				color = new Vector(1,0,0);
				break;
			case 5:
				color = new Vector(1,0,0);
				break;
			case 6:
				color = new Vector(1,0,0);
				break;
			default:
				color = new Vector(0,1,1);
				break;
		}
//		System.out.println("Strong: "+(int)caca);
//		color.print();
	}
	
	@Override
	public void render(){
		if (bola.getVel().mod()==0 || 1==1){
			glPushMatrix();
				glColor3f(color.x, color.y, color.z);
				Dibujo.drawPoint(Vector.sum(bola.getCenterPoint(), Vector.prod(2*bola.getSize() , direccion)));
				Dibujo.drawPoint(Vector.sum(bola.getCenterPoint(), Vector.prod(5*bola.getSize() , direccion)));
				Dibujo.drawLine(Vector.sum(bola.getCenterPoint(), Vector.prod(2*bola.getSize() , direccion)),
								Vector.sum(bola.getCenterPoint(), Vector.prod(5*bola.getSize() , direccion)));
				
				Dibujo.drawLine(Vector.sum(bola.getCenterPoint(), Vector.prod(-2*bola.getSize() , direccion)),
								Vector.sum(bola.getCenterPoint(), Vector.prod(-100*bola.getSize() , direccion)));
			glPopMatrix();
		}
	}
	
	@Override
	public void update(float delta) {
        // Direccion palo
		double anguloInicial;
		direccion = Vector.del(bola.getCenterPoint(), new Vector(0,0,0));
		
		if (direccion.z!=0){
			anguloInicial = Vector.ang(Vector.ejex, direccion)*((direccion.z)/Math.abs(direccion.z));
		} else {
			anguloInicial = Vector.ang(Vector.ejex, direccion);
		}
	
		direccion = new Vector((float)Math.cos(Math.toRadians(anjulo+anguloInicial)),
				   				  0,
				   				(float)Math.sin(Math.toRadians(anjulo+anguloInicial)));
	}
	
	public void disparar(float f){
		if (bola.getVel().mod()==0){
			Vector aux = Vector.norm(Vector.prod(-1,direccion));
			bola.setVel(Vector.prod(0.7f*f, aux));
		}
	}
	
	public void listen(float delta){
		mouseButton();
        //Disparo palo
        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
            if (strong+((float)delta/500)<5.0f){
                strong+=(float)delta/500;
            }
            updateColor(strong);
        } else {
            if (strong>0){
                disparar(strong);
                strong = 0;
                updateColor(10);
            }
        }//Fin disparo palo
	}

	//<Variables para el ratón>
	protected float deltaAngley = 0.0f;
	protected float deltaAnglex = 0.0f;
	//Copias de el ángulo inicial de la cámara
	protected double copyx=0;
	//Posición inicial del ratón
	protected int xOrigin = -1;
	
	boolean first;
	//</Variables para el ratón>

	protected void mouseButton() {
		
		if (Mouse.isButtonDown(1)){
			if (first){
				//System.out.println("Clicked");
				first = false;
				
				xOrigin = Mouse.getX();
				copyx=this.anjulo;
			} else {
				mouseMove(Mouse.getX(), Mouse.getY());
			}
		} else {
			if (!first){
				//System.out.println("Released");
				first = true;
				xOrigin = -1;
			}
		}
	}
	protected void mouseMove(int x, int y) {
		if (xOrigin >= 0) {
			deltaAnglex = (x - xOrigin) * 0.1f;
			anjulo = updateang(copyx-deltaAnglex);
		}
	}
	
	
}
