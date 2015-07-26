package Rubik;

import static org.lwjgl.opengl.GL11.glColor3f;

import java.util.ArrayList;

import org.lwjgl.Sys;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import Utilities.Dibujo;
import Utilities.Vector;

public class Cube {
	
	//Lista de piezas del cubo
	//private ArrayList<Piece> piezas = new ArrayList<Piece>();
	private Piece[][][] piezas;
	private Vector[][][] pos;
	//Lista que dice donde están los 6 centros
	private ArrayList<Vector> centros = new ArrayList<Vector>();
	// Dimensión, distancia entre piezas y tamaño de la pieza
	private int n = 2, dist = 2, size = 20;
	
	Cube(int n){
		this.n = n;
		generarPiezas(n);
	}

	public Cube(int n, int size) {
		super();
		this.n = n;
		this.size = size;
		generarPiezas(n);
	}
	
	public Cube(int n, int size, int dist) {
		super();
		this.n = n;
		this.dist = dist;
		this.size = size;
		generarPiezas(n);
	}
	
	//--------------------------------------------------------------------------
	
	/*
	 * Genera las piezas necesarias para contruir el cubo y las coloca
	 *  - También calcula los centros principales
	 *  - Finalmente inicializa el vector de posiciones
	 */
	private void generarPiezas(int n) {
		Matrix4f m = new Matrix4f();
		
		piezas = new Piece[n][n][n];
		pos = new Vector[n][n][n];
		
        for (int i=0; i<n; i++){
        	for (int j=0; j<n; j++){
        		for (int k=0; k<n; k++){
        	    	m.setIdentity();
        	    	piezas[i][j][k] = new Piece(new Matrix4f(m.translate(new Vector3f(i*(2*size + dist),j*(2*size + dist),k*(2*size + dist)))),size);
        		}
        	}
        }
        this.translate(new Vector(size, size, size));
        
        // Cálculo de centros principales
        float c = (2*n*size + (n-1)*dist);
        centros.add(new Vector(c/2,c/2,c-size)); //Delante
        centros.add(new Vector(c/2,c/2,0+size)); //Atrás
        centros.add(new Vector(c/2,c-size,c/2)); //Arriba
        centros.add(new Vector(c/2,0+size,c/2)); //Abajo
        centros.add(new Vector(c-size,c/2,c/2)); //Derecha
        centros.add(new Vector(0+size,c/2,c/2)); //Izquierda
        
        //Centramos el cubo
        this.translate(new Vector(-c/2, -c/2, -c/2));
	}
	
	//--------------------------------------------------------------------------
	
	/*
	 * Actualiza y pinta le cubo
	 */
	public void render(){
		update();
		
        for (int i=0; i<n; i++){
        	for (int j=0; j<n; j++){
        		for (int k=0; k<n; k++){
        	    	piezas[i][j][k].render();        		
        	    }
        	}
        }   
        glColor3f(1,1,1);
        for (int i=0; i<centros.size(); i++){
        	Dibujo.drawPoint(centros.get(i), 20);
        }
	}

	private void update() {
		float cantime = ((Sys.getTime() * 1000) / Sys.getTimerResolution())-timer;
		Vector centro;
		int j;
		switch (status){
			case U:
				j=(n-1)-move; //Capa a mover
				centro = Vector.del(centros.get(2), new Vector(0,move*(2*size+dist),0));
				if (cantime >= timeAnimation){
					/*
					 * Se acabó la animación
					 *  - Roto el ángulo que falta hasta 90
					 *  - Cambio el estado del cubo pues he acabado
					 */				
			        for (int i=0; i<n; i++){
		        		for (int k=0; k<n; k++){
		        	    	piezas[i][j][k].setPos(centro);
		        	    	piezas[i][j][k].rotate(-90-angR, new Vector(0,1,0));
		        	    	piezas[i][j][k].translate(pos[i][j][k]);
		        		}	
			        }
			        
			        //TO-DO: Cambiar piezas en la matriz
		        	Piece back;
		        	//piezas[0][j][0].translate(new Vector(-22, 0, 0));
		        	for (int i=0; i<n-1; i++){
		        		back = piezas[0][j][0];
		        		piezas[0][j][0] = piezas[n-1][j][0] ;
		        		piezas[n-1][j][0] = piezas[n-1][j][n-1];
		        		piezas[n-1][j][n-1] = piezas[0][j][n-1];
		        		piezas[0][j][n-1] = back;
		        	}
			        
					System.out.println("Acabo - Ángulo Recorrido: "+angR+" - Tiempo empleado: "+cantime);
					status = CubeState.RESTING;
				} else {
					/*
					 * Estamos en animación
					 * - En el primer giro guardo la posición de cada pieza que voy a girar
					 * - Giro todas las piezas que tengo que girar
					 *   - Translado al centro
					 *   - Roto el ángulo necesario
					 *   - Lo vuelvo a rotar la cantidad que estaba antes
					 */
					cantime-=past;
					float ang = (-90/timeAnimation)*cantime;
			        for (int i=0; i<n; i++){
		        		for (int k=0; k<n; k++){
		        			if (cantime==0) {
		        				//En el primer giro copio la posición de la pieza
		        				pos[i][j][k] = new Vector(piezas[i][j][k].getPos());
		        				pos[i][j][k].y=0;
		        				System.out.print("  - Save - ");pos[i][j][k].print();
		        			}
		        	    	piezas[i][j][k].setPos(centro);
		        	    	piezas[i][j][k].rotate(ang, new Vector(0,1,0));
		        	    	piezas[i][j][k].translate(pos[i][j][k]);
		        		}	
			        }
			        System.out.println("Me muevo - Tiempo pasado: "+cantime+" - Angulo: "+ang);
			        angR+=ang; past+=cantime;
				}
				break;
			case R:
				j=(n-1)-move; //Capa a mover
				centro = Vector.del(centros.get(2), new Vector(0,move*(2*size+dist),0));
				if (cantime >= timeAnimation){
					/*
					 * Se acabó la animación
					 *  - Roto el ángulo que falta hasta 90
					 *  - Cambio el estado del cubo pues he acabado
					 */				
			        for (int i=0; i<n; i++){
		        		for (int k=0; k<n; k++){
		        	    	piezas[i][j][k].setPos(centro);
		        	    	piezas[i][j][k].rotate(-90-angR, new Vector(0,1,0));
		        	    	piezas[i][j][k].translate(pos[i][j][k]);
		        		}	
			        }
			        
			        //TO-DO: Cambiar piezas en la matriz
		        	Piece back;
		        	//piezas[0][j][0].translate(new Vector(-22, 0, 0));
		        	for (int i=0; i<n-1; i++){
		        		back = piezas[0][j][0];
		        		piezas[0][j][0] = piezas[n-1][j][0] ;
		        		piezas[n-1][j][0] = piezas[n-1][j][n-1];
		        		piezas[n-1][j][n-1] = piezas[0][j][n-1];
		        		piezas[0][j][n-1] = back;
		        	}
			        
					System.out.println("Acabo - Ángulo Recorrido: "+angR+" - Tiempo empleado: "+cantime);
					status = CubeState.RESTING;
				} else {
					/*
					 * Estamos en animación
					 * - En el primer giro guardo la posición de cada pieza que voy a girar
					 * - Giro todas las piezas que tengo que girar
					 *   - Translado al centro
					 *   - Roto el ángulo necesario
					 *   - Lo vuelvo a rotar la cantidad que estaba antes
					 */
					cantime-=past;
					float ang = (-90/timeAnimation)*cantime;
			        for (int i=0; i<n; i++){
		        		for (int k=0; k<n; k++){
		        			if (cantime==0) {
		        				//En el primer giro copio la posición de la pieza
		        				pos[i][j][k] = new Vector(piezas[i][j][k].getPos());
		        				pos[i][j][k].y=0;
		        				System.out.print("  - Save - ");pos[i][j][k].print();
		        			}
		        	    	piezas[i][j][k].setPos(centro);
		        	    	piezas[i][j][k].rotate(ang, new Vector(0,1,0));
		        	    	piezas[i][j][k].translate(pos[i][j][k]);
		        		}	
			        }
			        System.out.println("Me muevo - Tiempo pasado: "+cantime+" - Angulo: "+ang);
			        angR+=ang; past+=cantime;
				}
				break;
			case D:
				D(move);
				break;
			case L:
				L(move);
				break;
			case F:
				F(move);
				break;
			case B:
				B(move);
				break;
			default:
				break;
		}
	}
	
	public void translate(Vector v){
        for (int i=0; i<n; i++){
        	for (int j=0; j<n; j++){
        		for (int k=0; k<n; k++){
        	    	piezas[i][j][k].translate(v);        		
        		}
        	}
        }   
		for (int i=0; i<centros.size(); i++){
			centros.set(i, Vector.sum(centros.get(i), v));
		}
	}
	
	/*
	 * No parece necesario y es un rollo de programar, con senos y cosenos
	 */
//	public void rotate(float angle, Vector v){
//		for (int i=0; i<piezas.size(); i++){
//			piezas.get(i).rotate(angle, v);
//		}
//	}
	
	//--------------------------------------------------------------------------

	//Tiempo en ms de la animación
	private float timeAnimation = 300;
	public enum CubeState {RESTING, U, D, R, L, F, B, SOLVED}
	private CubeState status = CubeState.RESTING;
	private float timer= 0, past=0, angR=0;
	private int move = 0;
	
	/*
	 * Si el cubo está descansando digo:
	 *  - Me puedo mover y lo haré
	 *  - El ángulo recorrido que llevo es 0
	 *  - Voy a mover la fila m
	 *  - Empiezo ahora
	 */
	
	public void U(int capa){
		if (status == CubeState.RESTING){
			status = CubeState.U;
			angR=0; past=0;
			move=capa;
			// Tiempo del sistema en ms
			timer = (Sys.getTime() * 1000) / Sys.getTimerResolution();
		}
	}
	public void R(int capa){
		if (status == CubeState.RESTING){
			status = CubeState.R;
			angR=0; past=0;
			move=capa;
			// Tiempo del sistema en ms
			timer = (Sys.getTime() * 1000) / Sys.getTimerResolution();
		}
	}
	public void D(int n){
		
	}
	public void L(int n){
		
	}
	public void F(int n){
		
	}
	public void B(int n){
		
	}
	
	//--------------------------------------------------------------------------
	
	public Piece[][][] getPiezas() {
		return piezas;
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public int getDist() {
		return dist;
	}

	public void setDist(int dist) {
		this.dist = dist;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	
	
}
