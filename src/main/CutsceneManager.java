package main;

import java.awt.Graphics2D;

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
			
			gp.player.drawing = false;
			
			scenePhase++;
			
		}
		
		if (scenePhase == 1) {
			gp.player.worldY -= 2;
		}
	}
	
}
