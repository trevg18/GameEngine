package engineTester;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import models.RawModel;
import models.TexturedModel;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.OBJLoader;
import renderEngine.Renderer;
import shaders.StaticShader;
import textures.ModelTexture;

public class MainGameLoop {

	public static void main(String[] args) {	
		
		DisplayManager.createDisplay();	//Creates a new display window
		Loader loader = new Loader();	//Creates a new Loader object for accessing functions
		StaticShader shader = new StaticShader();	//Creates a new StaticShader object									
		Renderer renderer = new Renderer(shader);
		
		RawModel model = OBJLoader.loadObjModel("stall", loader);
		RawModel dragonModel = OBJLoader.loadObjModel("dragon", loader);
		TexturedModel dragonTextured = new TexturedModel(dragonModel, new ModelTexture(loader.loadTexture("aqua")));
		TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("stallTexture")));
		ModelTexture texture = staticModel.getTexture();
		//texture.setReflectivity(1);
		//texture.setShineDamper(10);
		
		Entity entityDragon = new Entity(dragonTextured, new Vector3f(0,-4.0f, -25), 0,0,0,1);
		Entity entity = new Entity(staticModel, new Vector3f(0,0,-25),0,160,0,1);
		Light light = new Light(new Vector3f(3000,2000,20), new Vector3f(1,1,1));
		Camera camera = new Camera();
		
		while(!Display.isCloseRequested()){
			//entity.increaseRotation(0, 1, 0);
			camera.move();
			renderer.prepare();
			shader.start();
			shader.loadLight(light);
			shader.loadViewMatrix(camera);
			renderer.render(entityDragon, shader);
			renderer.render(entity,shader);
			shader.stop();
			DisplayManager.updateDisplay();
		}
		shader.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
		
	}

}
