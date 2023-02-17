package main;

import java.awt.Rectangle;

public class EventHandler {

	GamePanel gp;
	
	EventRect eventRect[][][];
	
	int previousEventX, previousEventY;
	boolean canTouchEvent = true;
	
	public EventHandler(GamePanel gp) {
		this.gp = gp;
		eventRect = new EventRect[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];
		
		int map = 0;
		int col = 0;
		int row = 0;
		while (map < gp.maxMap && col < gp.maxWorldCol && row < gp.maxWorldRow) {
			eventRect[map][col][row] = new EventRect();
			eventRect[map][col][row].x = 23;
			eventRect[map][col][row].y = 23;
			eventRect[map][col][row].width = 2;
			eventRect[map][col][row].height = 2;
			eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
			eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;
			
			col++;
			if(col == gp.maxWorldCol) {
				col = 0;
				row++;
				
				//this allows us to create the event rectangles for each map within the game
				if (row == gp.maxWorldRow) {
					row = 0;
					map++;
				}
			}
		}
		

	}
	
	public void checkEvent() {
		
		//Check if the player character is more than one tile from the last event. the .abs() method used and is useful to know
		//the pure distance between objects so if the value is either negative or positve based on the difference it will always return 
		// a positive result allowing you to measure the distance in a game. 
		int xDistance = Math.abs(gp.player.worldX - previousEventX);
		int yDistance = Math.abs(gp.player.worldY - previousEventY);
		
		//the .max(int x, int y) method takes the greater of two different numbers allowingg you to know the 
		int distance = Math.max(xDistance, yDistance);
		if (distance >gp.tileSize) {
			canTouchEvent = true;
		}
		
		if(canTouchEvent == true) {
		if (hit(27,16, "right") == true) {damagePit(27, 16, gp.dialogueState);}
		if (hit(23,19, "any") == true) {damagePit(23, 19, gp.dialogueState);}
		if (hit(23,12,"up") == true) { healingPool(23, 12, gp.dialogueState);}
		}
		
	}
	
	
	
	public boolean hit (int map, int col, int row, String reqDirection) {
		boolean hit = false;
		
		gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
		gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
		
		eventRect[map][col][row].x = col*gp.tileSize + eventRect[map][col][row].x;
		eventRect[map][col][row].y = row*gp.tileSize + eventRect[map][col][row].y;
		
		if(gp.player.solidArea.intersects(eventRect[map][col][row]) && eventRect[map][col][row].eventDone == false) {
			if(gp.player.direction.contentEquals(reqDirection)||reqDirection.contentEquals("any")) {
				hit = true;
				
				previousEventX = gp.player.worldX;
				previousEventY = gp.player.worldY;
			}
		}
		
		gp.player.solidArea.x = gp.player.solidAreaDefaultX;
		gp.player.solidArea.y = gp.player.solidAreaDefaultY;
		eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
		eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;
		
		return hit;
	}
	
	public void teleport(int gameState) {
		gp.gameState = gameState;
		gp.ui.currentDialogue = "Teleportation!";
		gp.player.worldX = gp.tileSize*37;
		gp.player.worldY = gp.tileSize*10;
		
	}
	
	public void damagePit(int col, int row, int gameState) {
		gp.gameState = gameState;
		gp.playSE(6);
		
		gp.ui.currentDialogue = "You fall into a pit!";
		
		gp.player.life -= 1;
		//eventRect[col][row].eventDone = true;
		canTouchEvent = false;
	}
	
	public void healingPool(int col, int row, int gameState) {
		if (gp.keyH.enterPressed == true) {
			gp.gameState = gameState;
			gp.player.attackCanceled = true;
			gp.playSE(2);
			gp.ui.currentDialogue = "You drink the water . \n You feel a little more rested.";
			gp.player.life = gp.player.maxLife;
			gp.player.mana = gp.player.maxMana;
			gp.aSetter.setMonster();
		}
		
	}
	
	
}
