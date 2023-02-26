package environment;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import main.GamePanel;

public class Lighting {

	GamePanel gp;
	BufferedImage darknessFilter;
	
	public Lighting(GamePanel gp, int circleSize) {
		this.gp = gp;
		
		//Create a buffered image
		darknessFilter = new BufferedImage(gp.screenWidth, gp.screenHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = (Graphics2D)darknessFilter.getGraphics();
		
		//This is going to be a screen sized rectangle that functions as a filter for darkness. If completely dark,
		//this works for creating caves or other dungeons where you have to have a lantern or another light source to function in.
		//The opacity of this can be adjusted
		
		//The area class allows you to handle various shapes
		Area screenArea = new Area(new Rectangle2D.Double(0, 0, gp.screenWidth, gp.screenHeight));
		
		//This gets the center x and y of the light circle 
		int centerX = gp.player.screenX + (gp.tileSize)/2;
		int centerY = gp.player.screenY + (gp.tileSize)/2;
		
		//get the top left x and y of the light circle 
		double x = centerX - (circleSize/2);
		double y = centerY - (circleSize/2);
		
		//Create a light circle shape
		Shape circleShape =  new Ellipse2D.Double(x, y, circleSize, circleSize);
		
		//Create a light circle area
		Area lightArea = new Area(circleShape);
		
		//Subtract the light circle from the screen rectangle. SO, in photoshop it's akin to cropping out a certain part, or using the magic pen to remove certain portions of the
		//image
		screenArea.subtract(lightArea);
		
		//creating a gradation effect to simulate light getting darker as the light is further away from the character
		//the size of the array determines the levels of gradation that will be used when implementing different shades of light
		Color color[] = new Color[12];
		float fraction[] = new float[12];
		
		//all the colors are set to black but the alpha values changes 
		color[0] = new Color(0,0,0,0f); //this is completely transparent
		color[1] = new Color(0,0,0,0.42f);
		color[2] = new Color(0,0,0,0.52f);
		color[3] = new Color(0,0,0,0.61f);
		color[4] = new Color(0,0,0,0.69f);
		color[5] = new Color(0,0,0,0.76f); //this is completely transparent
		color[6] = new Color(0,0,0,0.82f);
		color[7] = new Color(0,0,0,0.87f);
		color[8] = new Color(0,0,0,0.91f);
		color[9] = new Color(0,0,0,0.94f);
		color[10] = new Color(0,0,0,0.96f);
		color[11] = new Color(0,0,0,0.98f);
		
		//set the distance between each of the gradation/ color
		fraction[0] = 0f; //this is the center of the circle
		fraction[1] = 0.4f;
		fraction[2] = 0.5f;
		fraction[3] = 0.6f;
		fraction[4] = 0.65f; // this is the edge of the circle
		fraction[5] = 0.7f;
		fraction[6] = 0.75f;
		fraction[7] = 0.8f;
		fraction[8] = 0.85f;
		fraction[9] = 0.9f;
		fraction[10] = 0.95f;
		fraction[11] = 1f;
		
		//create a gradation settings for the light circle
		RadialGradientPaint gPaint = new RadialGradientPaint(centerX, centerY, (circleSize/2), fraction, color);
		
		//setting the RadialGradientPaint data onto the graphics2d object to allow the lighting effect to look sharp
		g2.setPaint(gPaint);
		
		//Draw the light circle
		g2.fill(lightArea);
		
		//now we're going to draw this onto the darkness filter;
		//setgs the color to black, while any color can be used to adjust the darkness filter based on RGB values
	//	g2.setColor(new Color(0,0,0,0.95f));
		
		//draws the screen rectangle without the light circle area
		g2.fill(screenArea);
		
		g2.dispose();
		
	}
	
	public void draw(Graphics2D g2) {
		
		g2.drawImage(darknessFilter, 0, 0, null);
		
	}
	
}
