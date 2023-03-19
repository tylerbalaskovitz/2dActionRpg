package main;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

import entity.PlayerDummy;
import monster.MON_SkeletonLord;
import object.OBJ_BlueHeart;
import object.OBJ_Door_Iron;

public class CutsceneManager {

	GamePanel gp;
	Graphics2D g2;
	public int sceneNum;
	public int scenePhase;
	
	//Scene number
	public final int NA = 0;
	public final int skeletonLord = 1;
	public final int ending = 2;
	int counter = 0;
	float alpha = 0f;
	int y;
	
	public CutsceneManager(GamePanel gp) {
		this.gp = gp;
	}
	
	public void draw (Graphics2D g2	) {
		this.g2 = g2;
		
		switch(sceneNum) {
		case skeletonLord: scene_skeletonLord(); break;
		case ending: scene_ending(); break;
		
		}
	}
	
	public void scene_skeletonLord() {
		if (scenePhase == 0) {
			
			gp.bossBattleOn = true;
			
			for (int i = 0; i < gp.obj[1].length; i ++) {
				if (gp.obj[gp.currentMap][i] == null) {
					gp.obj[gp.currentMap][i] = new OBJ_Door_Iron(gp);
					gp.obj[gp.currentMap][i].worldX = gp.tileSize*25;
					gp.obj[gp.currentMap][i].worldY = gp.tileSize*28;
					gp.obj[gp.currentMap][i].temp = true;
					gp.playSE(21);
					break;
				}
			}
			
			for (int i = 0; i < gp.npc[i].length; i++) {
				if (gp.npc[gp.currentMap][i] == null) {
					gp.npc[gp.currentMap][i] = new PlayerDummy(gp);
					gp.npc[gp.currentMap][i].worldX = gp.player.worldX;
					gp.npc[gp.currentMap][i].worldY = gp.player.worldY;
					gp.npc[gp.currentMap][i].direction = gp.player.direction;
					break;
				}
				
				
			}
			
			
			gp.player.drawing = false;
			
			scenePhase++;
			
		}
		
		if (scenePhase == 1) {
			gp.player.worldY -= 2;
			
			if (gp.player.worldY < gp.tileSize * 16) {
				scenePhase++;
			}
		}
		
		if (scenePhase == 2) {
			
			//Searches for the boss
			for (int i = 0; i < gp.monster[1].length; i++) {
				if (gp.monster[gp.currentMap][i] != null && gp.monster[gp.currentMap][i].name == MON_SkeletonLord.monName) {
					
					gp.monster[gp.currentMap][i].sleep = false;
					gp.ui.npc = gp.monster[gp.currentMap][i];
					scenePhase++;
					break;
				}
			}
			
		}
		if (scenePhase == 3) {
			//THe boss speaks
			gp.ui.drawDialogueScreen();
		}
		
		if (scenePhase == 4) {
			//Return to the player
			
			//Search the dummy to the NPC array
			for (int i = 0; i < gp.npc[1].length; i++) {
				
				//restore the player's position
				if (gp.npc[gp.currentMap][i] != null && gp.npc[gp.currentMap][i].name.equals(PlayerDummy.npcName)) {
					gp.player.worldX = gp.npc[gp.currentMap][i].worldX;
					gp.player.worldY = gp.npc[gp.currentMap][i].worldY;
					
					//revmoes the dummy by assigning a null value
					gp.npc[gp.currentMap][i] = null;
					break;
				}
			}
			
			gp.player.drawing = true;
			
			scenePhase = 0;
			sceneNum = NA;
			gp.gameState = gp.playState;
			
			gp.stopMusic();
			gp.playMusic(22);
		}
	}
	
	public void scene_ending() {
		if (scenePhase == 0 ) {
			gp.stopMusic();
			//used to instaniate and set the dialogues for the final cutscene
			gp.ui.npc = new OBJ_BlueHeart(gp);
			scenePhase++;
		}
		if (scenePhase == 1) {
			gp.ui.drawDialogueScreen();
			
		}
		
		if (scenePhase == 2) {
			
			//Play the fanfare sound effect
			gp.playSE(4);
			scenePhase++;
			
		}
		if (scenePhase == 3) {
			//makes the screen darker subtely and then transition to a final message
			if (counterReached(300) == true) {
				scenePhase++;
			}
			
		}
		if (scenePhase == 4) {
			//Making the screen get darker
			alpha += 0.005f;
			if (alpha > 1f) {
				alpha = 1f;
			}
			drawBlackBackground(alpha);
			
			if (alpha == 1f) {
				alpha = 0;
				scenePhase++;
			}
			
		}
		if (scenePhase == 5) {
			//displaying a message that closes the rest of the game out
			// a black background is used
			drawBlackBackground(1f);
			alpha += 0.005f;
			if (alpha > 1f) {
				alpha = 1f;
			}
			
			//Typing the narrative message and then going from there.
			String text = "After a fierce and boney battle with the Skeleton Lord, \n"
					+ "the Texas-shaped hero found the legendary treausre. \n"
					+ "Although it was mostly forgotten, I'm sure someone \n"
					+ "vaguely remembers its midrange value and its near timeless.\n" +
					"story";
			drawString(alpha, 38f, 200, text, 70);
			
			if (counterReached(600) == true) {
				scenePhase++;
			}
			
		}
		
	}
	public boolean counterReached (int target) {
		boolean counterReached = false;
		
		counter++;
		if (counter >target) {
			counterReached = true;
			counter = 0;
		}
		
		return counterReached;
	}
	
	public void drawBlackBackground(float alpha) {
		
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		g2.setColor(Color.black);
		g2.fillRect(0,0, gp.screenWidth, gp.screenHeight);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		
	}
	
	public void drawString(float alpha, float fontSize, int y, String text, int lineHeight) {
		
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(fontSize));
		
		// a for loop that splits the text at the line break symbols so there can be texts with multiple lines 
		
		
		for (String line: text.split("\n")) {
			int x = gp.ui.getXForCenteredText(line);
			g2.drawString(line, x, y);
			y += lineHeight;
			
		}
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		
	}
	
}
