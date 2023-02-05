package main;

import entity.NPC_OldMan;
import monster.MON_GreenSlime;

public class AssetSetter {

	
	GamePanel gp;
	public AssetSetter(GamePanel gp) {
		this.gp = gp;
	}
	
	public void setObject() {
	}
	
	public void setNPC() {
		gp.npc[0] = new NPC_OldMan(gp);
		gp.npc[0].worldX = gp.tileSize * 21;
		gp.npc[0].worldY = gp.tileSize * 21;
		
	}
	
	public void setMonster() {
		
		gp.monster[0] = new MON_GreenSlime(gp);
		gp.monster[0].worldX = gp.tileSize*23;
		gp.monster[0].worldY = gp.tileSize*37;
		
	}
}
