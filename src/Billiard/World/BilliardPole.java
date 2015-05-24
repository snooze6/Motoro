package Billiard.World;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.input.Mouse;

import Utilities.Dibujo;
import Utilities.Vector;

public class BilliardPole extends BilliardObject {
	Ball bola;
	double anjulo=0;
	Vector direccion=new Vector(0,0,0);
	Vector color = new Vector(0,1,0);
	
	public BilliardPole(Ball b){
		bola = b;
		
//		Model m = null;
//        try {
//            m = ObjectLoader.loadModel(new File("res/models/billar.obj"));
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//            Display.destroy();
//            System.exit(1);
//        } catch (IOException e) {
//            e.printStackTrace();
//            Display.destroy();
//            System.exit(1);
//        }
//		
//        glNewList(list, GL_COMPILE);
//
//        glBegin(GL_TRIANGLES);
//        //Dibujo.drawCube(100);
//        for (Model.Face face : m.getFaces()) {
//
//            try {
//            	
//                Vector3f n1 = m.getNormals().get(face.getNormalIndices()[0] - 1);
//                glNormal3f(n1.x, n1.y, n1.z);
//                Vector3f v1 = m.getVertices().get(face.getVertexIndices()[0] - 1);
//                glVertex3f(v1.x, v1.y, v1.z);
//  
//                Vector3f n2 = m.getNormals().get(face.getNormalIndices()[1] - 1);
//                glNormal3f(n2.x, n2.y, n2.z);
//                Vector3f v2 = m.getVertices().get(face.getVertexIndices()[1] - 1);
//                glVertex3f(v2.x, v2.y, v2.z);
//
//                Vector3f n3 = m.getNormals().get(face.getNormalIndices()[2] - 1);
//                glNormal3f(n3.x, n3.y, n3.z);
//                Vector3f v3 = m.getVertices().get(face.getVertexIndices()[2] - 1);
//                glVertex3f(v3.x, v3.y, v3.z);
//                
//                Vector2f vt1 = m.getTextureCoordinates().get(face.getVertexIndices()[0] - 1);
//                glTexCoord2d(vt1.x, vt1.y);
//                Vector2f vt2 = m.getTextureCoordinates().get(face.getVertexIndices()[1] - 1);
//                glTexCoord2d(vt2.x, vt2.y);
//                Vector2f vt3 = m.getTextureCoordinates().get(face.getVertexIndices()[2] - 1);
//                glTexCoord2d(vt3.x, vt3.y);
//                
//            } catch (Exception e) {
////		            		System.out.println("\nCogida excepcion Billar");
////		            		System.out.println("Tamaño normal:   "+face.getNormalIndices().length+" [X:"+face.getNormalIndices()[0]+" , Y: "+face.getNormalIndices()[1]+" , Z: "+face.getNormalIndices()[2]+"]");
////		            		System.out.println("Tamaño vertices: "+face.getVertexIndices().length+" [X:"+face.getVertexIndices()[0]+" , Y: "+face.getVertexIndices()[1]+" , Z: "+face.getVertexIndices()[2]+"]");
//                e.getStackTrace();
//                //System.out.println("Tamaño vertice: "+face.getVertexIndices().length+);
//            }
//        }
//        glEnd();
//        glEndList();
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
		if (bola.getVel().mod()==0){
			glPushMatrix();
				glColor3f(color.x, color.y, color.z);
				Dibujo.drawPoint(Vector.sum(bola.getPoint(), Vector.prod(2*bola.getSize() , direccion)));
				Dibujo.drawPoint(Vector.sum(bola.getPoint(), Vector.prod(5*bola.getSize() , direccion)));
				Dibujo.drawLine(Vector.sum(bola.getPoint(), Vector.prod(2*bola.getSize() , direccion)),
								Vector.sum(bola.getPoint(), Vector.prod(5*bola.getSize() , direccion)));
				
				Dibujo.drawLine(Vector.sum(bola.getPoint(), Vector.prod(-2*bola.getSize() , direccion)),
								Vector.sum(bola.getPoint(), Vector.prod(-100*bola.getSize() , direccion)));
			glPopMatrix();
		}
	}
	
	@Override
	public void update(float delta) {
        // Direccion palo
		double anguloInicial;
		direccion = Vector.del(bola.getPoint(), new Vector(0,0,0));
		
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
	
	public void listen(){
		mouseButton();
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
