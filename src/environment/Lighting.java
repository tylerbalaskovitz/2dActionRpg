package environment;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.image.BufferedImage;

import main.GamePanel;

public class Lighting {

	GamePanel gp;
	BufferedImage darknessFilter;
	public int dayCounter;
	public float filterAlpha = 0f;
	
	//Different states of time throughout the daty. 
	public final int day = 0;
	public final int dusk = 1;
	public final int night = 2;
	public final int dawn = 3;
	public int dayState = day;
	
	public Lighting(GamePanel gp) {
		this.gp = gp;
		
		setLightSource();
		
	}
	
	public void setLightSource() {
		//Create a buffered image
		darknessFilter = new BufferedImage(gp.screenWidth, gp.screenHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = (Graphics2D)darknessFilter.getGraphics();
		
		//This is going to be a screen sized rectangle that functions as a filter for darkness. If completely dark,
		//this works for creating caves or other dungeons where you have to have a lantern or another light source to function in.
		//The opacity of this can be adjusted
		
		//The area class allows you to handle various shapes
		
		if (gp.player.currentLight == null) {
			g2.setColor(new Color(0,0, 0,0.98f));
		} else {
		
			//This gets the center x and y of the light circle 
			int centerX = gp.player.screenX + (gp.tileSize)/2;
			int centerY = gp.player.screenY + (gp.tileSize)/2;
			
			//get the top left x and y of the light circle 
			
			//creating a gradation effect to simulate light getting darker as the light is further away from the character
			//the size of the array determines the levels of gradation that will be used when implementing different shades of light
			Color color[] = new Color[12];
			float fraction[] = new float[12];
			
			//all the colors are set to black but the alpha values changes 
			color[0] = new Color(0,0, 0,0f); //this is completely transparent
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
			RadialGradientPaint gPaint = new RadialGradientPaint(centerX, centerY, (gp.player.currentLight.lightRadius/2), fraction, color);
			
			//setting the RadialGradientPaint data onto the graphics2d object to allow the lighting effect to look sharp
			g2.setPaint(gPaint);
			
			//Draw the light circle
			
			//now we're going to draw this onto the darkness filter;
			//setgs the color to black, while any color can be used to adjust the darkness filter based on RGB values
			//	g2.setColor(new Color(0,0,0,0.95f));
			
			//draws the screen rectangle without the light circle area
		}
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		g2.dispose();
	}
	
	public void resetDay() {
		dayState = day;
		filterAlpha = 0f;
	}
	
	public void update() {
		if(gp.player.lightUpdated == true) {
			setLightSource();
			gp.player.lightUpdated = false;
		}
		
		//check the state of the day
		if (dayState == day) {
			dayCounter++;
			if(dayCounter > 3600) {
				dayState = dusk;
				dayCounter = 0;
			}
		}
		if (dayState == dusk) {
			filterAlpha += 0.001f;
			
			if (filterAlpha > 1f) {
				filterAlpha = 1f;
				dayState = night;
			}
		}
		if (dayState == night) {
			dayCounter++;
			
			if (dayCounter > 3600) {
				dayState = dawn;
				dayCounter = 0;
			}
		}
		if (dayState == dawn) {
			filterAlpha -=  0.001f;
			
			if (filterAlpha < 0f) {
				filterAlpha = 0;
				dayState = day;
			}
		}
	}
	
	public void draw(Graphics2D g2) {
		
		//If the current area is outside, then the filter is set to G2 with teh applied filter alpha, whereas if it's a dungeon, then the value will stay at 1f
		//However, if it's indoors then we don't apply any darkness effect. 
		if (gp.currentArea == gp.outside) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, filterAlpha));
		}
		if (gp.currentArea == gp.outside || gp.currentArea == gp.dungeon) {
			g2.drawImage(darknessFilter, 0, 0, null);

		}
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		
		//INformation for debugging
		String situation = "";
		switch (dayState) {
		case day: situation = "Day"; break;
		case dusk: situation = "Dusk"; break;
		case night: situation = "Night"; break;
		case dawn: situation = "Dawn"; break;
		}
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(50f));
		g2.drawString(situation, 800, 500);
	}
	
}
