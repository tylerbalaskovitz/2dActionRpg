package main;

import java.awt.Rectangle;

public class EventHandler {

	GamePanel gp;
	
	EventRect eventRect[][][];
	
	int previousEventX, previousEventY;
	boolean canTouchEvent = true;
	int tempMap, tempCol, tempRow;
	
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
		
		//the .max(int x, int y) method takes the greater of two different numbers. The distance if statement allows you to know whether or not you can touch the tile again
		//this prevents players from stepping on events many times again and again while the two rectangles for collision are overriding in each, and helps for moving
		//from one map to another
		int distance = Math.max(xDistance, yDistance);
		if (distance >gp.tileSize) {
			canTouchEvent = true;
		}
		
		if(canTouchEvent == true) {
		if (hit(0, 27,16, "right") == true) {damagePit(gp.dialogueState);}
		else if (hit(0, 23,12,"up") == true) { healingPool(gp.dialogueState);}
		else if (hit(0, 10, 39, "any") == true){teleport(1, 12, 13);}
		else if (hit(1, 12, 13, "any") == true){teleport(0, 10, 39);}
		}
		
	}
	
	
	
	public boolean hit (int map, int col, int row, String reqDirection) {
		boolean hit = false;
		
		if (map == gp.currentMap) {
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
			}
		return hit;
	}
	
	public void teleport(int map, int col, int row) {
		//used to change the first dimension of the three dimensional array in order to change maps
		gp.gameState = gp.transitionState;
		tempMap = map;
		tempCol = col;
		tempRow = row;
		canTouchEvent = false;
		gp.playSE(13);
	}
	
	public void damagePit( int gameState) {
		gp.gameState = gameState;
		gp.playSE(6);
		
		gp.ui.currentDialogue = "You fall into a pit!";
		
		gp.player.life -= 1;
		//eventRect[col][row].eventDone = true;
		canTouchEvent = false;
	}
	
	public void healingPool(int gameState) {
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
