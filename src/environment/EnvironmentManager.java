package environment;

import java.awt.Graphics2D;

import main.GamePanel;

// a management class that handles weather and other environment features
public class EnvironmentManager {

	GamePanel gp;
	Lighting lighting;
	
	public EnvironmentManager(GamePanel gp) {
		this.gp = gp;
	}
	
	public void setup() {
		//instantiate the lighting class. If you adjust the integer below, it changes the size of the circle that is going to be put in as a lighting circle
		lighting = new Lighting(gp, 350);
	}
	
	public void draw(Graphics2D g2) {
		lighting.draw(g2);
	}
	
}
