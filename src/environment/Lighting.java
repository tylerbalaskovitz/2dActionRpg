package environment;

import java.awt.Color;
import java.awt.Graphics2D;
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
		
		//now we're going to draw this onto the darkness filter;
		//setgs the color to black, while any color can be used to adjust the darkness filter based on RGB values
		g2.setColor(new Color(0,0,0,0.95f));
		
		//draws the screen rectangle without the light circle area
		g2.fill(screenArea);
		
		g2.dispose();
		
	}
	
	public void draw(Graphics2D g2) {
		
		g2.drawImage(darknessFilter, 0, 0, null);
		
	}
	
}
