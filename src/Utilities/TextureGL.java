package Utilities;

import static org.lwjgl.opengl.GL11.*;

import java.io.IOException;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class TextureGL{
	
	private Texture tex;
	private boolean tra = false;

	public TextureGL(String path){
        tex = load2Texture(path);
	}

	private static Texture load2Texture(String path) {
		Texture tex = null;
		
		String[] aux = path.split("\\.");
		String ext = new String();
		if (aux.length>0) ext= aux[aux.length-1].toUpperCase();

		try {
			tex = TextureLoader.getTexture(ext, ResourceLoader.getResourceAsStream(path));
			
				System.out.println("Texture loaded: "+tex);
				System.out.println(">> Path: "+path);
				System.out.println(">> Extension: "+ext);
				System.out.println(">> Image width: "+tex.getImageWidth());
				System.out.println(">> Image height: "+tex.getImageHeight());
				System.out.println(">> Texture width: "+tex.getTextureWidth());
				System.out.println(">> Texture height: "+tex.getTextureHeight());
				System.out.println(">> Texture ID: "+tex.getTextureID());
				
			return tex;
        } catch (IOException e) {
			System.out.println("Texture failed to load");
			System.out.println(">> Path: "+path);
			System.out.println(">> Extension: "+ext);
			e.printStackTrace();
		}
		return null;
	}
	
	public static TextureGL loadTexture(String path){
		return new TextureGL(path);
	}
	
	public void on(){
		if (tra){
	      //enable transparency
	      glEnable(GL_BLEND);
		}
		glEnable(GL_TEXTURE_2D);  
		tex.bind();
	}
	
	public void off(){
		if (tra) {
			glDisable(GL_BLEND);
		}
		glDisable(GL_TEXTURE_2D);  
	}
	
	public void setTransparent(){
		setTransparent(!tra);
	}
	
	public void setTransparent(Boolean b){
		tra=b;
		if (tra) {
			 glBlendFunc(GL_SRC_ALPHA, GL_ONE);
		}
	}
	
}
