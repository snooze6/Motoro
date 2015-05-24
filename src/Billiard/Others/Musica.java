package Billiard.Others;

import java.io.IOException;

import org.lwjgl.openal.AL;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Musica {
	
    public static Audio bad, good, ballball, ballplane;
    
    public static void load() throws IOException{
    	bad= loadMusic("res/sounds/lose.wav");
    	good = loadMusic("res/sounds/applause.wav");
    	ballball = loadMusic("res/sounds/ballball.wav");
    	ballplane = loadMusic("res/sounds/ballplane.wav");
    }
    
    
    public static void dispose(){
    	AL.destroy();
    }
    
	public static Audio loadMusic(String path) {
		Audio tex = null;
		
		String[] aux = path.split("\\.");
		String ext = new String();
		if (aux.length>0) ext= aux[aux.length-1].toUpperCase();

		try {
			tex = AudioLoader.getAudio(ext, ResourceLoader.getResourceAsStream(path));
			return tex;
        } catch (IOException e) {
			System.out.println("Texture failed to load");
			System.out.println(">> Path: "+path);
			System.out.println(">> Extension: "+ext);
			e.printStackTrace();
		}
		return null;
	}

}