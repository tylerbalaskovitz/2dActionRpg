package main;

import java.awt.Graphics2D;

import entity.PlayerDummy;
import monster.MON_SkeletonLord;
import object.OBJ_Door_Iron;

public class CutsceneManager {

	GamePanel gp;
	Graphics2D g2;
	public int sceneNum;
	public int scenePhase;
	
	//Scene number
	public final int NA = 0;
	public final int skeletonLord = 1;
	
	public CutsceneManager(GamePanel gp) {
		this.gp = gp;
	}
	
	public void draw (Graphics2D g2	) {
		this.g2 = g2;
		
		switch(sceneNum) {
		case skeletonLord: scene_skeletonLord(); break;
		
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
	
}
