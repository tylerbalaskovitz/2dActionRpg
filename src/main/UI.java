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
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;


public class UI {
	
	//handles text messages, item icons, etc.
	Font maruMonica, purisaB;
	BufferedImage heart_full, heart_half, heart_blank, crystal_full, crystal_blank, coin;
	GamePanel gp;
	Graphics2D g2;
	public ArrayList<String> message = new ArrayList<>();
	public ArrayList<Integer> messageCounter = new ArrayList<>(); 	
	public boolean gameFinished = false;
	public boolean messageOn = false;
	public String currentDialogue = "";
	public int commandNum = 0;
	public int titleScreenState = 0;//0: is the first screen, 1: the second screen, etc. etc.
	public int playerSlotCol = 0;
	public int playerSlotRow = 0;
	public int npcSlotCol = 0;
	public int npcSlotRow = 0;
	public int subState = 0;
	public int counter = 0;
	public Entity npc;

	
	
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
		Entity crystal = new OBJ_ManaCrystal(gp);
		crystal_full = crystal.image;
		crystal_blank = crystal.image2;
		Entity bronzeCoin = new OBJ_Coin_Bronze(gp);
		coin = bronzeCoin.down1;
		
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
			drawInventory(gp.player, true);
		}
		//Options state
		if (gp.gameState == gp.optionsState) {
			drawOptionsScreen();
		}
		//Game over state
		if (gp.gameState == gp.gameOverState) {
			drawGameOverScreen();
		}
		//Transition state
		if (gp.gameState == gp.transitionState) {
			drawTransition();
		}
		if (gp.gameState == gp.tradeState) {
			drawTradeScreen();
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
		
		//similar to the heart counter, you also can draw the mana crystals, first starting with the blank mana crystal spaces and then moving up to the the full spaces. 
		x = (gp.tileSize/2)-5;
		y = (int)(gp.tileSize*1.5);
		i = 0;
		while (i < gp.player.maxMana) {
			g2.drawImage(crystal_blank, x, y, null);
			i++;
			x += 35;
		}
		
		//Draw mana
		x = (gp.tileSize/2)-5;
		y = (int)(gp.tileSize*1.5);
		i = 0;
		while (i < gp.player.mana) {
			g2.drawImage(crystal_full, x, y, null);
			i++;
			x += 35;
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
		int x = gp.tileSize*4;
		int y = gp.tileSize/2;
		int width = gp.screenWidth - (gp.tileSize*6);
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
		final int frameX = gp.tileSize*2;
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
		g2.drawString("Mana", textX, textY);
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
		textY += lineHeight +10;
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
		
		value = String.valueOf(gp.player.mana + "/" + gp.player.maxMana);
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
		
		g2.drawImage(gp.player.currentWeapon.down1, tailX- gp.tileSize, textY-24, null);
		textY += gp.tileSize;
		g2.drawImage(gp.player.currentShield.down1, tailX- gp.tileSize, textY-24, null);
		
	}
	public void drawInventory(Entity entity, boolean cursor) {
		
		int frameX = 0;
		int frameY  = 0;
		int frameWidth = 0;
		int frameHeight = 0;
		int slotCol = 0;
		int slotRow = 0;
		
		if (entity == gp.player) {
			frameX = gp.tileSize*12;
			frameY = gp.tileSize;
			frameWidth = gp.tileSize*6;
			frameHeight = gp.tileSize*5;
			slotCol = playerSlotCol;
			slotRow = playerSlotRow;
		} else {
			frameX = gp.tileSize*2;
			frameY = gp.tileSize;
			frameWidth = gp.tileSize*6;
			frameHeight = gp.tileSize*5;
			slotCol = npcSlotCol;
			slotRow = npcSlotRow;
		}
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
		
		//Code for different slots
		final int slotXStart = frameX+ 20;
		final int slotYStart = frameY+ 20;
		int slotX = slotXStart;
		int slotY = slotYStart;
		int slotSize = gp.tileSize+3;
		
		//Drawing the player's items
		for (int i = 0; i < entity.inventory.size(); i++) {
			
			//Cursor used for equipping items
			if (entity.inventory.get(i) == entity.currentWeapon || entity.inventory.get(i) == entity.currentShield || entity.inventory.get(i) == entity.currentLight) {
				g2.setColor(new Color(240, 190, 90));
				g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10, 10);
			}
			
			g2.drawImage(entity.inventory.get(i).down1, slotX, slotY, null);
			
			// Display the amount for stackable items
			if (entity == gp.player && entity.inventory.get(i).amount > 1) {
				g2.setFont(g2.getFont().deriveFont(32f));
				int amountX;
				int amountY;
				
				String s = "" + entity.inventory.get(i).amount;
				amountX = getXForAlignToRightText(s, slotX + 44);
				amountY = slotY + gp.tileSize;
				
				//Drawing a shadow onto the numbers
				g2.setColor(new Color(60, 60 , 60));
				g2.drawString(s, amountX, amountY);
				
				g2.setColor(Color.white);
				g2.drawString(s, amountX-3, amountY-3);
			}
			
			slotX += slotSize;
			if (i == 4|| i == 9 || i == 14) {
				slotX = slotXStart;
				slotY += slotSize;
			}
		}
		
		
		//Drawing a cursor so we can move an item
		if (cursor == true) {
			int cursorX = slotXStart + (slotSize * slotCol);
			int cursorY = slotYStart + (slotSize * slotRow);
			int cursorWidth = gp.tileSize;
			int cursorHeight = gp.tileSize;
			
			//Drawing the cursor
			g2.setColor(Color.white);
			g2.setStroke(new BasicStroke(3));
			g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);
			
			//Description frame
			int dFrameX = frameX;
			int dFrameY = frameY + frameHeight;
			int dFrameWidth = frameWidth;
			int dFrameHeight = gp.tileSize*3;
			
		
			
			//draw the text for the description
			int textX = dFrameX + 20;
			int textY = dFrameY + gp.tileSize;
			g2.setFont(g2.getFont().deriveFont(28F));
			
			int itemIndex = getItemIndexOnSlot(slotCol, slotRow);
			
			if (itemIndex < entity.inventory.size()) {
				
				drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);
				
				for (String line: entity.inventory.get(itemIndex).description.split("\n")) {
					g2.drawString(line, textX, textY);
					textY += 32;
				}
			}
		}
	}
	
	public void drawGameOverScreen() {
		g2.setColor(new Color(0, 0, 0, 150));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		int x;
		int y;
		String text;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110));
		text = "Game Over";
		
		//Text's shadow
		g2.setColor(Color.black);
		x = getXForCenteredText(text);
		y = gp.tileSize*4;
		g2.drawString(text, x, y);
		
		//Main
		g2.setColor(Color.white);
		g2.drawString(text,  x-4, y-4);
		
		//Retrying and other options
		g2.setFont(g2.getFont().deriveFont(50f));
		text = "Retry";
		x = getXForCenteredText(text);
		y+= gp.tileSize*4;
		g2.drawString(text, x, y);
		if (commandNum == 0) {
			g2.drawString(">", x-40, y);
			
		}
		
		//Back to the title screen options
		text = "Quit";
		x = getXForCenteredText(text);
		y+= 55;
		g2.drawString(text, x, y);
		if (commandNum == 1) {
			g2.drawString(">", x-40, y);
		}
		
	}
	
	public void drawOptionsScreen() {
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(32F));

		//Sub window
		int frameX = gp.tileSize * 6;
		int frameY = gp.tileSize;
		int frameWidth = gp.tileSize*8;
		int frameHeight = gp.tileSize*10;
		
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
		
		switch(subState) {
		case 0: options_top(frameX, frameY); break;
		case 1: options_fullScreenNotification(frameX, frameY); break;
		case 2: options_control(frameX, frameY); break;
		case 3: options_endGameConfirmation(frameX, frameY); break;
		}
		
		gp.keyH.enterPressed = false;
	}
	
	public void options_top(int frameX, int frameY) {
		int textX;
		int textY;
		
		//Title
		String text = "Options";
		textX = getXForCenteredText(text);
		textY = frameY + gp.tileSize;
		g2.drawString(text, textX, textY);
		
		//Full Screen on/off
		textX = frameX + gp.tileSize;
		textY += gp.tileSize*2;
		g2.drawString("Full Screen", textX, textY);
		if (commandNum == 0) {
			g2.drawString(">", textX- 25, textY);
			if (gp.keyH.enterPressed == true) {
				if (gp.fullScreenOn == false) {
					gp.fullScreenOn = true;
				} else if (gp.fullScreenOn == true) {
					gp.fullScreenOn = false;
				}
				subState = 1;
			}
		}
		
		//Music
		textY += gp.tileSize;
		g2.drawString("Music", textX, textY);
		if (commandNum == 1) {
			g2.drawString(">", textX- 25, textY);
		}
		
		//SE
		textY += gp.tileSize;
		g2.drawString("Sound", textX, textY);
		if (commandNum == 2) {
			g2.drawString(">", textX- 25, textY);
		}
		//Controls
		textY += gp.tileSize;
		g2.drawString("Controls", textX, textY);
		if (commandNum == 3) {
			g2.drawString(">", textX- 25, textY);
			if (gp.keyH.enterPressed == true) {
				subState = 2;
				commandNum = 0;
			}
			
		}
		//End Game
		textY += gp.tileSize;
		g2.drawString("End Game", textX, textY);
		if (commandNum == 4) {
			g2.drawString(">", textX- 25, textY);
			if (gp.keyH.enterPressed == true) {
				subState = 3;
				commandNum = 0;
			}
		}
		
		textY += gp.tileSize*2;
		g2.drawString("Return", textX, textY);
		if (commandNum == 5) {
			g2.drawString(">", textX- 25, textY);
			if (gp.keyH.enterPressed == true) {
				subState = 0;
				commandNum = 0;
				gp.gameState = gp.playState;
			}
		}
		
		//Checkbox for a full screen
		textX = frameX + (int)(gp.tileSize*4.5);
		textY = frameY + gp.tileSize*2 + 24;
		g2.setStroke(new BasicStroke(3));
		g2.drawRect(textX, textY, 24, 24);
		if (gp.fullScreenOn == true) {
			g2.fillRect(textX, textY, 24, 24);
		}
		
		//Music volue
		textY += gp.tileSize;
		g2.drawRect(textX, textY, 120, 24); //120 / 5 = 24. 
		int volumeWidth = 24 * gp.music.volumeScale;
		g2.fillRect(textX, textY, volumeWidth, 24);
		
		//Sound Effect Volume
		textY += gp.tileSize;
		g2.drawRect(textX, textY, 120, 24);
		volumeWidth = 24 * gp.se.volumeScale;
		g2.fillRect(textX, textY, volumeWidth, 24);
		
		gp.config.saveConfig();
		
	}
	
	public void options_control(int frameX, int frameY) {
		int textX;
		int textY;
		String text ="Control";
		textX = getXForCenteredText(text);
		textY = frameY + gp.tileSize;
		g2.drawString(text, textX, textY);
		
		textX = frameX + gp.tileSize;
		textY += gp.tileSize;
		g2.drawString("Move", textX, textY); textY += gp.tileSize;
		g2.drawString("Confirm/Attack", textX, textY); textY += gp.tileSize;
		g2.drawString("Shoot/Cast", textX, textY); textY += gp.tileSize;
		g2.drawString("Character Screen", textX, textY); textY += gp.tileSize;
		g2.drawString("Pause", textX, textY); textY += gp.tileSize;
		g2.drawString("Options", textX, textY); textY += gp.tileSize;
		
		textX = frameX + gp.tileSize * 6;
		textY = frameY + gp.tileSize *2;
		g2.drawString("WASD", textX, textY); textY += gp.tileSize;
		g2.drawString("ENTER", textX, textY); textY += gp.tileSize;
		g2.drawString("F", textX, textY); textY += gp.tileSize;
		g2.drawString("C", textX, textY); textY += gp.tileSize;
		g2.drawString("P", textX, textY); textY += gp.tileSize;
		g2.drawString("ESC", textX, textY); textY += gp.tileSize;
		
		textX = frameX + gp.tileSize;
		textY = frameY + gp.tileSize * 9;
		g2.drawString("Return", textX, textY);
		if (commandNum == 0) {
			g2.drawString(">", textX-25, textY);
			if (gp.keyH.enterPressed == true) {
				subState = 0;
				commandNum = 3;
			}
			
		}
		
	}
	
	public void options_fullScreenNotification(int frameX, int frameY) {
		int textX = frameX + gp.tileSize;
		int textY = frameY + gp.tileSize*3;
		
		currentDialogue = "The changes will take \neffect after restarting \nthe game.";
		
		for (String line: currentDialogue.split("\n")){
			g2.drawString(line, textX, textY);
			textY += 40;
		}
		
		//Back
		textY = frameY +  gp.tileSize*9;
		g2.drawString("Return", textX, textY);
		if (commandNum == 0) {
			g2.drawString(">", textX-25, textY);
			if (gp.keyH.enterPressed == true) {
				subState = 0;
				commandNum = 0;
				if (gp.keyH.enterPressed == true) {
					commandNum = 0;
				}
			}
		}
	}
	
	public void options_endGameConfirmation(int frameX, int frameY) {
		int textX = frameX + gp.tileSize;
		int textY = frameY + gp.tileSize*3;

		currentDialogue = "Quit the game and \nreturn to the title screen?";
		
		for (String line: currentDialogue.split("\n")) {
			g2.drawString(line, textX, textY);
			textY += 40;
		}
		
		//Yes
		String text = "Yes";
		textX = getXForCenteredText(text);
		textY += gp.tileSize * 3;
		g2.drawString(text, textX, textY);
		if (commandNum == 0) {
			g2.drawString(">", textX-25, textY);
			if (gp.keyH.enterPressed == true) {
				subState = 0;
				gp.gameState = gp.titleState;
				gp.music.stop();
			}
		}
		
		//No
		
		text = "No";
		textX = getXForCenteredText(text);
		textY += gp.tileSize;
		g2.drawString(text, textX, textY);
		//the command numbers are relative to where the arrow is going to render on screen. Each number represents a row that will correspond to a line of text
		if (commandNum == 1) {
			g2.drawString(">", textX-25, textY);
			if (gp.keyH.enterPressed == true) {
				subState = 0;
				commandNum = 4;
				titleScreenState = 0;
			}
		}
		
	}
	
	public void drawTransition() {
		counter++;
		g2.setColor(new Color(0,0,0, counter*5));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		if (counter == 50) {
			counter = 0;
			gp.gameState = gp.playState;
			gp.currentMap = gp.eHandler.tempMap;
			gp.player.worldX = gp.tileSize * gp.eHandler.tempCol;
			gp.player.worldY = gp.tileSize * gp.eHandler.tempRow;
			gp.eHandler.previousEventX = gp.player.worldX;
			gp.eHandler.previousEventY = gp.player.worldY;
			
		}
	}
	
	public void drawTradeScreen() {
		//creating a switch based on the substate
		switch(subState) {
		case 0: trade_select(); break;
		case 1: trade_buy(); break;
		case 2: trade_sell(); break;
		}
		gp.keyH.enterPressed = false;
	}
	public void trade_select() {
		
		drawDialogueScreen();
		
		//drawing the window
		int x = gp.tileSize * 15;
		int y = gp.tileSize * 4;
		int width = gp.tileSize * 3;
		int height = (int)(gp.tileSize * 3.5);
		drawSubWindow(x, y, width, height);
		
		//drawing the text
		x += gp.tileSize;
		y += gp.tileSize;
		g2.drawString("Buy", x, y);
		if (commandNum == 0) {
			g2.drawString(">", x-24, y);
			if (gp.keyH.enterPressed == true) {
				subState = 1;
			}
		}
		y += gp.tileSize;
		
		g2.drawString("Sell", x, y);
		if (commandNum == 1) {
			g2.drawString(">", x-24, y);
			if (gp.keyH.enterPressed == true) {
				subState = 2;
			}
		}
		y += gp.tileSize;
		
		g2.drawString("Leave", x, y);
		if (commandNum == 2) {
			g2.drawString(">", x-24, y);
			if (gp.keyH.enterPressed == true) {
				commandNum = 0;
				gp.gameState = gp.dialogueState;
				currentDialogue = "Come again, you little prick!";
			}
		}
		y += gp.tileSize;
		
	}
	
	public void trade_buy() {
		
		//drawing two inventories. First the player inventory
		drawInventory(gp.player, false);
		
		//draw NPC inventory
		drawInventory(npc, true);
		
		//drawing the hint subwindow
		int x = gp.tileSize*2;
		int y = gp.tileSize*9;
		int width = gp.tileSize * 6;
		int height = gp.tileSize * 2;
		drawSubWindow(x, y, width, height);
		g2.drawString("[ESC] Back", x+24, y+60);
		
		//drawing the player's current coin amount as another subwindow
		x = gp.tileSize*12;
		y = gp.tileSize*9;
		width = gp.tileSize * 6;
		height = gp.tileSize * 2;
		drawSubWindow(x, y, width, height);
		g2.drawString("Coins: " + gp.player.coin, x+24, y+60);
		
		//Drawing the price window within the game
		int itemIndex = getItemIndexOnSlot(npcSlotCol, npcSlotRow);
		if (itemIndex < npc.inventory.size()) {
			x = (int)(gp.tileSize*5.5);
			y = (int)(gp.tileSize*5.5);
			width = (int)(gp.tileSize*2.5);
			height = gp.tileSize;
			drawSubWindow(x, y, width, height);
			g2.drawImage(coin, x+10, y+10, 32, 32, null);
			
			int price = npc.inventory.get(itemIndex).price;
			String text = "" + price;
			x = getXForAlignToRightText(text, gp.tileSize*8-20);
			g2.drawString(text, x, y+34);
		}
		
		//Buying an item
		if (gp.keyH.enterPressed == true) {
			if (npc.inventory.get(itemIndex).price > gp.player.coin) {
				subState = 0;
				gp.gameState = gp.dialogueState;
				currentDialogue = "You need more money to buy that!";
				drawDialogueScreen();
			}
			else {
				if(gp.player.canObtainItem(npc.inventory.get(itemIndex)) == true){
					gp.player.coin -= npc.inventory.get(itemIndex).price;
				}
				else {
					subState = 0;
					gp.gameState = gp.dialogueState;
					currentDialogue = "You can't carry any more items!";
				}
			}
		}
		
	}
	
	public void trade_sell() {
		//Draw player inventory
		drawInventory(gp.player, true);
		
		//drawing the hint subwindow
				int x = gp.tileSize*2;
				int y = gp.tileSize*9;
				int width = gp.tileSize * 6;
				int height = gp.tileSize * 2;
				drawSubWindow(x, y, width, height);
				g2.drawString("[ESC] Back", x+24, y+60);
				
				//drawing the player's current coin amount as another subwindow
				x = gp.tileSize*12;
				y = gp.tileSize*9;
				width = gp.tileSize * 6;
				height = gp.tileSize * 2;
				drawSubWindow(x, y, width, height);
				g2.drawString("Coins: " + gp.player.coin, x+24, y+60);
				
				//Drawing the price window within the game
				int itemIndex = getItemIndexOnSlot(playerSlotCol, playerSlotRow);
				
				if (itemIndex < gp.player.inventory.size()) {
					x = (int)(gp.tileSize*15.5);
					y = (int)(gp.tileSize*5.5);
					width = (int)(gp.tileSize*2.5);
					height = gp.tileSize;
					drawSubWindow(x, y, width, height);
					g2.drawImage(coin, x+10, y+10, 32, 32, null);
					
					int price = gp.player.inventory.get(itemIndex).price/2;
					String text = "" + price;
					x = getXForAlignToRightText(text, gp.tileSize*18-20);
					g2.drawString(text, x, y+34);
				
					
					//Selling an item
					if (gp.keyH.enterPressed == true) {
						if (gp.player.inventory.get(itemIndex) == gp.player.currentShield || gp.player.inventory.get(itemIndex) == gp.player.currentWeapon) {
							commandNum = 0;
							subState = 0;
							gp.gameState = gp.dialogueState;
							currentDialogue = "You cannot sell an equipped item!";
						}
						else {
							if (gp.player.inventory.get(itemIndex).amount > 1) {
								gp.player.inventory.get(itemIndex).amount--;
							} else {
								gp.player.inventory.remove(itemIndex);
							}
							gp.player.coin += price;
						}
						
					}
				}
	}
	
	public int getItemIndexOnSlot(int slotCol, int slotRow) {
		int itemIndex = slotCol + (slotRow*5);
		return itemIndex;
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
