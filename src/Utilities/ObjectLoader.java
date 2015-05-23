package Utilities;

import static org.lwjgl.opengl.GL11.GL_AMBIENT;
import static org.lwjgl.opengl.GL11.GL_COMPILE;
import static org.lwjgl.opengl.GL11.GL_DIFFUSE;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_FRONT;
import static org.lwjgl.opengl.GL11.GL_SHININESS;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glEndList;
import static org.lwjgl.opengl.GL11.glGenLists;
import static org.lwjgl.opengl.GL11.glMaterial;
import static org.lwjgl.opengl.GL11.glMaterialf;
import static org.lwjgl.opengl.GL11.glNewList;
import static org.lwjgl.opengl.GL11.glNormal3f;
import static org.lwjgl.opengl.GL11.glNormalPointer;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex3f;
import static org.lwjgl.opengl.GL11.glVertexPointer;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

import org.newdawn.slick.opengl.TextureLoader;
import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class ObjectLoader {

    public static final String MODEL_LOCATION = "res/models/billar.obj";
    //public static final String MODEL_LOCATION = "res/models/radar.obj";

    public static int createDisplayList(Model m) {
        int displayList = glGenLists(1);
        glNewList(displayList, GL_COMPILE);
        {
            glMaterialf(GL_FRONT, GL_SHININESS, 120);
            glColor3f(0.4f, 0.27f, 0.17f);
            glBegin(GL_TRIANGLES);
            for (Model.Face face : m.getFaces()) {
                if (face.hasNormals()) {
                    Vector3f n1 = m.getNormals().get(face.getNormalIndices()[0] - 1);
                    glNormal3f(n1.x, n1.y, n1.z);
                }
                Vector3f v1 = m.getVertices().get(face.getVertexIndices()[0] - 1);
                glVertex3f(v1.x, v1.y, v1.z);
                if (face.hasNormals()) {
                    Vector3f n2 = m.getNormals().get(face.getNormalIndices()[1] - 1);
                    glNormal3f(n2.x, n2.y, n2.z);
                }
                Vector3f v2 = m.getVertices().get(face.getVertexIndices()[1] - 1);
                glVertex3f(v2.x, v2.y, v2.z);
                if (face.hasNormals()) {
                    Vector3f n3 = m.getNormals().get(face.getNormalIndices()[2] - 1);
                    glNormal3f(n3.x, n3.y, n3.z);
                }
                Vector3f v3 = m.getVertices().get(face.getVertexIndices()[2] - 1);
                glVertex3f(v3.x, v3.y, v3.z);
            }
            glEnd();
        }
        glEndList();
        return displayList;
    }

    private static FloatBuffer reserveData(int size) {
        return BufferUtils.createFloatBuffer(size);
    }

    private static float[] asFloats(Vector3f v) {
        return new float[]{v.x, v.y, v.z};
    }

    public static int[] createVBO(Model model) {
        int vboVertexHandle = glGenBuffers();
        int vboNormalHandle = glGenBuffers();
        // TODO: Implement materials with VBOs
        FloatBuffer vertices = reserveData(model.getFaces().size() * 9);
        FloatBuffer normals = reserveData(model.getFaces().size() * 9);
        for (Model.Face face : model.getFaces()) {
            vertices.put(asFloats(model.getVertices().get(face.getVertexIndices()[0] - 1)));
            vertices.put(asFloats(model.getVertices().get(face.getVertexIndices()[1] - 1)));
            vertices.put(asFloats(model.getVertices().get(face.getVertexIndices()[2] - 1)));
            normals.put(asFloats(model.getNormals().get(face.getNormalIndices()[0] - 1)));
            normals.put(asFloats(model.getNormals().get(face.getNormalIndices()[1] - 1)));
            normals.put(asFloats(model.getNormals().get(face.getNormalIndices()[2] - 1)));
        }
        vertices.flip();
        normals.flip();
        glBindBuffer(GL_ARRAY_BUFFER, vboVertexHandle);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);
        glVertexPointer(3, GL_FLOAT, 0, 0L);
        glBindBuffer(GL_ARRAY_BUFFER, vboNormalHandle);
        glBufferData(GL_ARRAY_BUFFER, normals, GL_STATIC_DRAW);
        glNormalPointer(GL_FLOAT, 0, 0L);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        return new int[]{vboVertexHandle, vboNormalHandle};
    }

    private static Vector3f parseVertex(String line) {
        float x = 0,y = 0,z = 0; String[] xyz = new String[1];
        try {
            xyz = line.split(" ");
            x = Float.valueOf(xyz[1]);
            y = Float.valueOf(xyz[2]);
            z = Float.valueOf(xyz[3]);
        } catch (Exception e){
            System.out.println("Cogida excepción parseVertex");
            System.out.println(line);
            System.out.println("X: "+xyz[1]);
            System.out.println("Y: "+xyz[2]);
            System.out.println("Z: "+xyz[3]);
            e.getStackTrace();
            System.exit(-1);
        }
        return new Vector3f(x, y, z);
    }

    private static Vector3f parseNormal(String line) {
        float x = 0,y = 0,z = 0; String[] xyz = new String[1];
        try {
            xyz = line.split(" ");
            x = Float.valueOf(xyz[1]);
            y = Float.valueOf(xyz[2]);
            z = Float.valueOf(xyz[3]);
        } catch (Exception e){
            System.out.println("Cogida excepción parseNormal");
            System.out.println(line);
            System.out.println("X: "+xyz[1]);
            System.out.println("Y: "+xyz[2]);
            System.out.println("Z: "+xyz[3]);
            e.getStackTrace();
            System.exit(-1);
        }
        return new Vector3f(x, y, z);
    }
    
    private static Vector2f parseTexVertex(String line){
        float x = 0,y = 0; String[] xyz = new String[1];
        try {
            xyz = line.split(" ");
            x = Float.valueOf(xyz[1]);
            y = Float.valueOf(xyz[2]);
        } catch (Exception e){
            System.out.println("Cogida excepción parseNormal");
            System.out.println(line);
            System.out.println("X: "+xyz[1]);
            System.out.println("Y: "+xyz[2]);
            e.getStackTrace();
            System.exit(-1);
        }
        return new Vector2f(x, y);
    }

//    private static void control(Boolean w, String line) {
//    	String[] xyz = new String[1];
//    	try {
//	        xyz = line.split(" ");
//	        String x = (xyz[1]);
//	          String[] a = x.split("//");
//	        String y = (xyz[2]);
//	          String[] b = y.split("//");
//	        String z = (xyz[3]);
//	          String[] c = z.split("//");
//    	} catch (Exception e){
//    		System.out.println("Cogida excepción parseNormal");
//    		if (w) {System.out.println("Tiene normal");} else {System.out.println("No tiene normales");}
//    		System.out.println(line);
//    		System.out.println("X: "+xyz[1]);
//    		System.out.println("Y: "+xyz[2]);
//    		System.out.println("Z: "+xyz[3]);
//    		try {
//				Thread.sleep((long) 1000);
//			} catch (InterruptedException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//    		e.getStackTrace();
//       	}
//    }

    private static Model.Face parseFace(boolean hasNormals, String line) {
        String[] faceIndices = line.split(" ");
        int[] vertexIndicesArray = new int[3];

        if (!line.isEmpty()) {
            vertexIndicesArray = new int[]{
                    Integer.parseInt(faceIndices[1].split("/")[0]),
                    Integer.parseInt(faceIndices[2].split("/")[0]),
                    Integer.parseInt(faceIndices[3].split("/")[0])};
        }
        if (hasNormals) {
            int[] normalIndicesArray = new int[3];

            normalIndicesArray[0] = Integer.parseInt(faceIndices[1].split("/")[2]);
            normalIndicesArray[1] = Integer.parseInt(faceIndices[2].split("/")[2]);
            normalIndicesArray[2] = Integer.parseInt(faceIndices[3].split("/")[2]);

            if (normalIndicesArray[0]>0 && normalIndicesArray[1]>0 && normalIndicesArray[2]>0)
                return new Model.Face(vertexIndicesArray, normalIndicesArray);
            else
                return new Model.Face((vertexIndicesArray));
        } else {
            return new Model.Face((vertexIndicesArray));
        }
    }

    public static Model loadModel(File f) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(f));
        Model m = new Model();
        String line;
        while ((line = reader.readLine()) != null) {
            String prefix = line.split(" ")[0];
            if (prefix.equals("#") || line.isEmpty()) {
                continue;
            } else if (prefix.equals("v")) {
            	//Vértice
                m.getVertices().add(parseVertex(line));
            } else if (prefix.equals("vn")) {
            	//Normal
                m.getNormals().add(parseNormal(line));
            } else if (prefix.equals("f")) {
            	//Cara
                m.getFaces().add(parseFace(m.hasNormals(), line));
            } else if (prefix.equals("vt")){
                //Vértice de textura
            	m.getTextureCoordinates().add(parseTexVertex(line));
            } else if (prefix.equalsIgnoreCase("usemtl")){
                //Archivo de materiales
            } else if (prefix.equals("mtllib")){
                //Caca
            } else if (prefix.equals("s")){
                //Caca
            } else if (prefix.equals("g")){
                //Caca
            } else if (prefix.equals("o")){
                //Caca
            } else if (prefix.isEmpty()){
                //Caca
            } else {
                System.out.println("[OBJ LOADER]: Línea irreconocible: "+line);
                reader.close();
                throw new RuntimeException("OBJ file contains line which cannot be parsed correctly: " + line);
            }
        }
        reader.close();
        return m;
    }

    public static int createTexturedDisplayList(Model m) {
        int displayList = glGenLists(1);
        glNewList(displayList, GL_COMPILE);
        {
            glBegin(GL_TRIANGLES);
            for (Model.Face face : m.getFaces()) {
                if (face.hasTextureCoordinates()) {
                    glMaterial(GL_FRONT, GL_DIFFUSE, BufferTools.asFlippedFloatBuffer(face.getMaterial()
                                    .diffuseColour[0], face.getMaterial().diffuseColour[1],
                            face.getMaterial().diffuseColour[2], 1));
                    glMaterial(GL_FRONT, GL_AMBIENT, BufferTools.asFlippedFloatBuffer(face.getMaterial()
                                    .ambientColour[0], face.getMaterial().ambientColour[1],
                            face.getMaterial().ambientColour[2], 1));
                    glMaterialf(GL_FRONT, GL_SHININESS, face.getMaterial().specularCoefficient);
                }
                if (face.hasNormals()) {
                    Vector3f n1 = m.getNormals().get(face.getNormalIndices()[0] - 1);
                    glNormal3f(n1.x, n1.y, n1.z);
                }
                if (face.hasTextureCoordinates()) {
                    Vector2f t1 = m.getTextureCoordinates().get(face.getTextureCoordinateIndices()[0] - 1);
                    glTexCoord2f(t1.x, t1.y);
                }
                Vector3f v1 = m.getVertices().get(face.getVertexIndices()[0] - 1);
                glVertex3f(v1.x, v1.y, v1.z);
                if (face.hasNormals()) {
                    Vector3f n2 = m.getNormals().get(face.getNormalIndices()[1] - 1);
                    glNormal3f(n2.x, n2.y, n2.z);
                }
                if (face.hasTextureCoordinates()) {
                    Vector2f t2 = m.getTextureCoordinates().get(face.getTextureCoordinateIndices()[1] - 1);
                    glTexCoord2f(t2.x, t2.y);
                }
                Vector3f v2 = m.getVertices().get(face.getVertexIndices()[1] - 1);
                glVertex3f(v2.x, v2.y, v2.z);
                if (face.hasNormals()) {
                    Vector3f n3 = m.getNormals().get(face.getNormalIndices()[2] - 1);
                    glNormal3f(n3.x, n3.y, n3.z);
                }
                if (face.hasTextureCoordinates()) {
                    Vector2f t3 = m.getTextureCoordinates().get(face.getTextureCoordinateIndices()[2] - 1);
                    glTexCoord2f(t3.x, t3.y);
                }
                Vector3f v3 = m.getVertices().get(face.getVertexIndices()[2] - 1);
                glVertex3f(v3.x, v3.y, v3.z);
            }
            glEnd();
        }
        glEndList();
        return displayList;
    }

    public static Model loadTexturedModel(File f) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(f));
        Model m = new Model();
        Model.Material currentMaterial = new Model.Material();
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.startsWith("#")) {
                continue;
            }
            if (line.startsWith("mtllib ")) {
//                String materialFileName = line.split(" ")[1];
//                File materialFile = new File(f.getParentFile().getAbsolutePath() + "/" + materialFileName);
//                BufferedReader materialFileReader = new BufferedReader(new FileReader(materialFile));
//                String materialLine;
//                Model.Material parseMaterial = new Model.Material();
//                String parseMaterialName = "";
//                while ((materialLine = materialFileReader.readLine()) != null) {
//                    if (materialLine.startsWith("#")) {
//                        continue;
//                    }
//                    if (materialLine.startsWith("newmtl ")) {
//                        if (!parseMaterialName.equals("")) {
//                            m.getMaterials().put(parseMaterialName, parseMaterial);
//                        }
//                        parseMaterialName = materialLine.split(" ")[1];
//                        parseMaterial = new Model.Material();
//                    } else if (materialLine.startsWith("Ns ")) {
//                        parseMaterial.specularCoefficient = Float.valueOf(materialLine.split(" ")[1]);
//                    } else if (materialLine.startsWith("Ka ")) {
//                        String[] rgb = materialLine.split(" ");
//                        parseMaterial.ambientColour[0] = Float.valueOf(rgb[1]);
//                        parseMaterial.ambientColour[1] = Float.valueOf(rgb[2]);
//                        parseMaterial.ambientColour[2] = Float.valueOf(rgb[3]);
//                    } else if (materialLine.startsWith("Ks ")) {
//                        String[] rgb = materialLine.split(" ");
//                        parseMaterial.specularColour[0] = Float.valueOf(rgb[1]);
//                        parseMaterial.specularColour[1] = Float.valueOf(rgb[2]);
//                        parseMaterial.specularColour[2] = Float.valueOf(rgb[3]);
//                    } else if (materialLine.startsWith("Kd ")) {
//                        String[] rgb = materialLine.split(" ");
//                        parseMaterial.diffuseColour[0] = Float.valueOf(rgb[1]);
//                        parseMaterial.diffuseColour[1] = Float.valueOf(rgb[2]);
//                        parseMaterial.diffuseColour[2] = Float.valueOf(rgb[3]);
//                    } else if (materialLine.startsWith("map_Kd")) {
//                        parseMaterial.texture = TextureLoader.getTexture("PNG",
//                                new FileInputStream(new File(f.getParentFile().getAbsolutePath() + "/" + materialLine
//                                        .split(" ")[1])));
//                    } else {
//                        System.err.println("[MTL] Unknown Line: " + materialLine);
//                    }
//                }
//                m.getMaterials().put(parseMaterialName, parseMaterial);
//                materialFileReader.close();
            } else if (line.startsWith("usemtl ")) {
//                currentMaterial = m.getMaterials().get(line.split(" ")[1]);
            } else if (line.startsWith("v ")) {
                String[] xyz = line.split(" ");
                float x = Float.valueOf(xyz[1]);
                float y = Float.valueOf(xyz[2]);
                float z = Float.valueOf(xyz[3]);
                m.getVertices().add(new Vector3f(x, y, z));
            } else if (line.startsWith("vn ")) {
                String[] xyz = line.split(" ");
                float x = Float.valueOf(xyz[1]);
                float y = Float.valueOf(xyz[2]);
                float z = Float.valueOf(xyz[3]);
                m.getNormals().add(new Vector3f(x, y, z));
            } else if (line.startsWith("vt ")) {
                String[] xyz = line.split(" ");
                float s = Float.valueOf(xyz[1]);
                float t = Float.valueOf(xyz[2]);
                m.getTextureCoordinates().add(new Vector2f(s, t));
            } else if (line.startsWith("f ")) {
                String[] faceIndices = line.split(" ");
                int[] vertexIndicesArray = {Integer.parseInt(faceIndices[1].split("/")[0]),
                        Integer.parseInt(faceIndices[2].split("/")[0]), Integer.parseInt(faceIndices[3].split("/")[0])};
                int[] textureCoordinateIndicesArray = {-1, -1, -1};
                if (m.hasTextureCoordinates()) {
                    textureCoordinateIndicesArray[0] = Integer.parseInt(faceIndices[1].split("/")[1]);
                    textureCoordinateIndicesArray[1] = Integer.parseInt(faceIndices[2].split("/")[1]);
                    textureCoordinateIndicesArray[2] = Integer.parseInt(faceIndices[3].split("/")[1]);
                }
                int[] normalIndicesArray = {0, 0, 0};
                if (m.hasNormals()) {
                    normalIndicesArray[0] = Integer.parseInt(faceIndices[1].split("/")[2]);
                    normalIndicesArray[1] = Integer.parseInt(faceIndices[2].split("/")[2]);
                    normalIndicesArray[2] = Integer.parseInt(faceIndices[3].split("/")[2]);
                }
                //                Vector3f vertexIndices = new Vector3f(Float.valueOf(faceIndices[1].split("/")[0]),
                //                        Float.valueOf(faceIndices[2].split("/")[0]),
                // Float.valueOf(faceIndices[3].split("/")[0]));
                //                Vector3f normalIndices = new Vector3f(Float.valueOf(faceIndices[1].split("/")[2]),
                //                        Float.valueOf(faceIndices[2].split("/")[2]),
                // Float.valueOf(faceIndices[3].split("/")[2]));
                m.getFaces().add(new Model.Face(vertexIndicesArray, normalIndicesArray,
                        textureCoordinateIndicesArray, currentMaterial));
            } else if (line.startsWith("s ")) {
                boolean enableSmoothShading = !line.contains("off");
                m.setSmoothShadingEnabled(enableSmoothShading);
            } else {
                System.err.println("[OBJ] Unknown Line: " + line);
            }
        }
        reader.close();
        return m;
    }

}
