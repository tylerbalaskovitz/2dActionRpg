package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import entity.Entity;
import object.OBJ_Heart;


public class UI {
	
	//handles text messages, item icons, etc.
	Font maruMonica, purisaB;
	BufferedImage heart_full, heart_half, heart_blank;
	GamePanel gp;
	Graphics2D g2;
	public ArrayList<String> message = new ArrayList<>();
	public ArrayList<Integer> messageCounter = new ArrayList<>(); 	
	public boolean gameFinished = false;
	public boolean messageOn = false;
	public String currentDialogue = "";
	public int commandNum = 0;
	public int titleScreenState = 0;//0: is the first screen, 1: the second screen, etc. etc.
	public int slotCol = 0;
	public int slotRow = 0;

	
	
	public UI(GamePanel gp) {
		this.gp = gp;
		
		try {
			InputStream is = getClass().getResourceAsStream("/font/MaruMonica.ttf");
			maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
			is = getClass().getResourceAsStream("/font/PurisaBold.ttf");
			purisaB = Font.createFont(Font.TRUETYPE_FONT, is);
		} catch (FontFormatException | IOException e) {
			
			e.printStackTrace();
		}
		
		//Creating the HUD object
		Entity heart = new OBJ_Heart(gp);
		heart_full = heart.image;
		heart_half = heart.image2;
		heart_blank = heart.image3;
		
		
		
	}
	
	public void addMessage(String text) {
		
		//message and message counter are a pair
		message.add(text);
		messageCounter.add(0);
		
	
	}
	
	public void draw(Graphics2D g2) {
		
		this.g2 = g2;
		g2.setFont(maruMonica);
		g2.setColor(Color.white);
		//Title State
		if(gp.gameState == gp.titleState) {
			drawTitleScreen();
		}
		//Play state
		if(gp.gameState == gp.playState) {
			//Do play state stuff when doing the draw method
			drawPlayerLife();
			drawMessage();
		}
		//Pause State
		if (gp.gameState == gp.pauseState) {
			drawPlayerLife();
			drawPauseScreen();
		}
		//Dialogue State
		if (gp.gameState == gp.dialogueState) {
			drawPlayerLife();
			drawDialogueScreen();
		}
		//Character state	
		if (gp.gameState == gp.characterState) {
			drawCharacterScreen();
			drawInventory();
		}
		
	}
	
	public void drawMessage() {
		int messageX = gp.tileSize;
		int messageY = gp.tileSize * 4;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32));
		
		for (int i = 0; i < message.size(); i++) {
			
			if(message.get(i) != null) {
				g2.setColor(Color.white);
				g2.drawString(message.get(i), messageX+2, messageY+2);
				
				int counter = messageCounter.get(i) + 1; //THis is an array list so more or less it's messageCounter++
				messageCounter.set(i, counter); //set the counter to the array
				messageY += 50;//used so we can display the next message
				
				if (messageCounter.get(i) > 180) {
					message.remove(i);
					messageCounter.remove(i);
					
				}
				
			}
			
		}
		
	}

	public void drawPlayerLife() {
		
		int x = gp.tileSize/2;
		int y = gp.tileSize/2;
		int i = 0;
		
		//Draw a blank heart
		while (i<  gp.player.maxLife/2) {
			g2.drawImage(heart_blank, x, y, null);
			i++;
			x+= gp.tileSize;
		}
		
		x = gp.tileSize/2;
		y = gp.tileSize/2;
		i = 0;
		
		//draw current LIfe
		
		while(i < gp.player.life) {
			g2.drawImage(heart_half, x, y, null);
			i++;
			if(i < gp.player.life) {
				g2.drawImage(heart_full, x, y, null);
			}
			i++;
			x+= gp.tileSize;
		}
		
	}
	
	public void drawTitleScreen() {
		
		if(titleScreenState == 0) {
			
		g2.setColor(new Color(70, 120, 80));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		//Title name
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
		String text = "Defender Rush";
		int x = getXForCenteredText(text);
		int y = gp.tileSize*3;
		//Adding shadows
		g2.setColor(Color.black);
		g2.drawString(text, x+5, y+5);
		
		//Main colors
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
		
		//Blue Boy Image
		x = gp.screenWidth/2 - (gp.tileSize*2)/2;
		y += gp.tileSize*2;
		
		g2.drawImage(gp.player.down1, x, y, gp.tileSize*2, gp.tileSize*2, null);
		
		//Menu things
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
		
		text = "NEW GAME";
		x = getXForCenteredText(text);
		y+= gp.tileSize*4;
		g2.drawString(text, x, y);
		if(commandNum == 0) {
			g2.drawString(">", x-gp.tileSize, y);
		}
		
		text = "LOAD GAME";
		x = getXForCenteredText(text);
		y+= gp.tileSize;
		g2.drawString(text, x, y);
		if(commandNum == 1) {
			g2.drawString(">", x-gp.tileSize, y);
		}
		
		text = "QUIT";
		x = getXForCenteredText(text);
		y+= gp.tileSize;
		g2.drawString(text, x, y);
		if(commandNum == 2) {
			g2.drawString(">", x-gp.tileSize, y);
		}
		}
		
		else if (titleScreenState == 1) {
			
			//Class Selection Screen
			g2.setColor(Color.white);
			g2.setFont(g2.getFont().deriveFont(42F));
		
			String text = "Select your class!";
			int x = getXForCenteredText(text);
			int y = gp.tileSize*3;
			g2.drawString(text, x, y);
			
			text = "Fighter";
			x = getXForCenteredText(text);
			y += gp.tileSize*3;
			g2.drawString(text, x, y);
			if(commandNum == 0) {
				g2.drawString(">", x-gp.tileSize, y);
				
			}
			
			text = "Thief";
			x = getXForCenteredText(text);
			y += gp.tileSize;
			g2.drawString(text, x, y);
			if(commandNum == 1) {
				g2.drawString(">", x-gp.tileSize, y);
				
			}
			
			text = "Sorcerer";
			x = getXForCenteredText(text);
			y += gp.tileSize;
			g2.drawString(text, x, y);
			if(commandNum == 2) {
				g2.drawString(">", x-gp.tileSize, y);
				
			}
			text = "Back";
			x = getXForCenteredText(text);
			y += gp.tileSize*2;
			g2.drawString(text, x, y);
			if(commandNum == 3) {
				g2.drawString(">", x-gp.tileSize, y);
				
			}
			
		}
		
		
	}

	public void drawPauseScreen() {
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
		String text = "PAUSED";
		int x = getXForCenteredText(text);
		
		int y = gp.screenHeight/2;
		g2.drawString(text, x, y);
	}
	
	public void drawDialogueScreen() {

		
		//create a window for the dialogue
		int x = gp.tileSize*2;
		int y = gp.tileSize/2;
		int width = gp.screenWidth - (gp.tileSize*4);
		int height = gp.tileSize*4;
		
		drawSubWindow(x, y, width, height);
		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));
		x+= gp.tileSize;
		y+= gp.tileSize;
		
		for(String line : currentDialogue.split("\n")) {
			g2.drawString(line, x, y);
			y+= 40;
		}
		
		
	}
	
	public void drawCharacterScreen() {
		//Creating a frame for the 
		final int frameX = gp.tileSize;
		final int frameY = gp.tileSize;
		final int frameWidth = gp.tileSize * 5;
		final int frameHeight = gp.tileSize * 10;
		
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
		
		//Text and other attributes
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(32F));
		
		int textX = frameX + 20;
		int textY = frameY + gp.tileSize;
		final int lineHeight = 35;
		
		//parameter names
		g2.drawString("Level", textX, textY);
		textY += lineHeight;
		g2.drawString("Life", textX, textY);
		textY += lineHeight;
		g2.drawString("Strength", textX, textY);
		textY += lineHeight;
		g2.drawString("Dexterity", textX, textY);
		textY += lineHeight;
		g2.drawString("Attack", textX, textY);
		textY += lineHeight;
		g2.drawString("Defense", textX, textY);
		textY += lineHeight;
		g2.drawString("Exp", textX, textY);
		textY += lineHeight;
		g2.drawString("Next Level", textX, textY);
		textY += lineHeight;
		g2.drawString("Coins", textX, textY);
		textY += lineHeight +20;
		g2.drawString("Weapon", textX, textY);
		textY += lineHeight +15;
		g2.drawString("Shield", textX, textY);
		textY += lineHeight;
		
		
		//Values
		int tailX = (frameX + frameWidth) - 30;
		// reset textY
		textY = frameY + gp.tileSize;
		String value;
		
		value = String.valueOf(gp.player.level);
		textX = getXForAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.life + "/" + gp.player.maxLife);
		textX = getXForAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.strength);
		textX = getXForAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.dexterity);
		textX = getXForAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.attack);
		textX = getXForAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.defense);
		textX = getXForAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.exp);
		textX = getXForAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.nextLevelExp);
		textX = getXForAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;

		value = String.valueOf(gp.player.coin);
		textX = getXForAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		g2.drawImage(gp.player.currentWeapon.down1, tailX- gp.tileSize, textY-14, null);
		textY += gp.tileSize;
		g2.drawImage(gp.player.currentShield.down1, tailX- gp.tileSize, textY-14, null);
		
	}
	public void drawInventory() {
		
		int frameX = gp.tileSize*9;
		int frameY = gp.tileSize;
		int frameWidth = gp.tileSize*6;
		int frameHeight = gp.tileSize*5;
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
		
		//Code for different slots
		final int slotXStart = frameX+ 20;
		final int slotYStart = frameY+ 20;
		int slotX = slotXStart;
		int slotY = slotYStart;
		
		//Drawing the player's items
		for (int i = 0; i < gp.player.inventory.size(); i++) {
			g2.drawImage(gp.player.inventory.get(i).down1, slotX, slotY, null);
			
			slotX += gp.tileSize;
			if (i == 4|| i == 9 || i == 14) {
				slotX = slotXStart;
				slotY += gp.tileSize;
			}
		}
		
		
		//Drawing a cursor so we can move an item
		int cursorX = slotXStart + (gp.tileSize * slotCol);
		int cursorY = slotYStart + (gp.tileSize * slotRow);
		int cursorWidth = gp.tileSize;
		int cursorHeight = gp.tileSize;
		
		//Drawing the cursor
		g2.setColor(Color.white);
		g2.setStroke(new BasicStroke(3));
		g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);
		
	}
	public void drawSubWindow(int x, int y,  int width, int height) {
		
		Color c = new Color(0, 0, 0, 210); //you can adjust the alpha value which allows for some transparency to exist
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height, 35, 35);
		
		c = new Color(255, 255, 255);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
		
		
	}
	
	public int getXForCenteredText (String text) {
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWidth/2 - length/2;
		return x;
	}

	public int getXForAlignToRightText(String text, int tailX) {
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = tailX - length;
		return x;
	}
}
